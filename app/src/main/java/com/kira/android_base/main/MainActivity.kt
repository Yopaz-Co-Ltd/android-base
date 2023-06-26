package com.kira.android_base.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.kira.android_base.R
import com.kira.android_base.base.service.MyBindService
import com.kira.android_base.base.service.MyForegroundService
import com.kira.android_base.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private var mBindService: MyBindService? = null
    private var mBound = false


    private val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as MyBindService.LocalBinder
            mBindService = localBinder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBindService = null
            mBound = false
        }
    }

    override fun initViews() {
        invalidateAuthState()
    }

    override fun handleObservables() {
        super.handleObservables()

        mainViewModel.logoutSuccessfullyLiveData.observe(this) {
            invalidateAuthState()
        }

        mainViewModel.commandServiceLiveData.observe(this) {
            Intent(this, MyForegroundService::class.java).apply {
                action = it.value
                startService(this)
            }
        }

        mainViewModel.errorLiveData.observe(this) { error ->
            showErrorDialog(error?.message ?: return@observe)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController() ?: return super.onSupportNavigateUp()
        val shouldFinishAppFragmentIds = listOf(
            R.id.homeFragment,
            R.id.loginFragment
        )
        if (!shouldFinishAppFragmentIds.contains(navController.currentDestination?.id)) {
            return navController.navigateUp() || super.onSupportNavigateUp()
        }
        return runCatching { finishAffinity() }.isSuccess
    }

    override fun onBackPressed() {
        if (onSupportNavigateUp()) return
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()

        bindService()
    }

    override fun onStop() {
        super.onStop()

        unBindService()
    }

    private fun findNavController(): NavController? {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        return navHostFragment?.navController
    }

    private fun setUpActionBar() {
        findNavController()?.let { navController ->
            setupActionBarWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.loginFragment -> {
                        supportActionBar?.hide()
                    }
                    else -> {
                        supportActionBar?.show()
                    }
                }
            }
        }
    }

    private fun setNavigationStartDestination(startDestinationId: Int) {
        val navController = findNavController() ?: return
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        navGraph.setStartDestination(startDestinationId)
        navController.setGraph(navGraph, intent.extras)
        setUpActionBar()
    }

    fun invalidateAuthState() {
        mainViewModel.getLocalAccessToken()
            .takeIf { !it.isNullOrBlank() }
            ?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                setNavigationStartDestination(R.id.homeFragment)
                return
            }
        setNavigationStartDestination(R.id.loginFragment)
    }

    private fun bindService() {
        Toast.makeText(this, "Start Bind service", Toast.LENGTH_SHORT).show()
        Intent(this, MyBindService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unBindService() {
        Toast.makeText(this, "Stop bind service", Toast.LENGTH_SHORT).show()
        unbindService(serviceConnection)
    }

    fun getDataBindService(): Int? {
        if (!mBound) return null
        return mBindService?.randomNumber
    }
}

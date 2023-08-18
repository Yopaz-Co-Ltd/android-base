package com.kira.android_base.main

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        invalidateAuthState()
    }

    override fun handleObservables() {
        super.handleObservables()

        mainViewModel.logoutSuccessfullyLiveData.observe(this) {
            invalidateAuthState()
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
                    R.id.homeFragment -> {
                        supportActionBar?.hide()
                    }
                    R.id.postDetailFragment -> {
                        supportActionBar?.hide()
                    }
                    R.id.postEditorFragment -> {
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
}

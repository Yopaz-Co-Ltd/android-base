package com.kira.android_base.main

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.net.sip.SipAudioCall
import android.net.sip.SipManager
import android.net.sip.SipProfile
import android.net.sip.SipRegistrationListener
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kira.android_base.R
import com.kira.android_base.base.supports.utils.Enums
import com.kira.android_base.base.ui.BaseActivity
import com.kira.android_base.databinding.ActivityMainBinding
import com.kira.android_base.main.fragments.home.HomeFragment
import com.kira.android_base.main.fragments.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val sipManager: SipManager? by lazy(LazyThreadSafetyMode.NONE) {
        SipManager.newInstance(this)
    }
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        Log.d(TAG, "permissions result : $it")
    }

    override fun initViews() {
        invalidateAuthState()
    }

    fun registerSIP(userName: String, password: String, callFor: String) {
        Log.d(TAG, "registerSIP: sipManager = $sipManager")
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.USE_SIP,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.RECORD_AUDIO
            )
        )
        val sipProfile =
            SipProfile.Builder(userName, "sip.zadarma.com").setPassword(password).build()

        val intent = Intent("android.SipDemo.INCOMING_CALL")
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, Intent.FILL_IN_DATA or PendingIntent.FLAG_IMMUTABLE
        )
        sipManager?.open(sipProfile, pendingIntent, null)

        Log.d(TAG, "initViews: sipProfile?.uriString = ${sipProfile?.uriString}")
        sipManager?.setRegistrationListener(sipProfile?.uriString, object : SipRegistrationListener {

            override fun onRegistering(localProfileUri: String) {
                Log.d(TAG, "onRegistering Registering with SIP Server...")
            }

            override fun onRegistrationDone(localProfileUri: String, expiryTime: Long) {
                Log.d(TAG, "onRegistrationDone: Ready")

                callThroughSIP(sipProfile, callFor)
            }

            override fun onRegistrationFailed(
                localProfileUri: String, errorCode: Int, errorMessage: String
            ) {
                Log.d(TAG, "onRegistrationFailed: Registration failed. Please check settings.")
            }
        })
    }

    private fun callThroughSIP(sipProfile: SipProfile, callFor: String) {
        Log.d(TAG, "callThroughSIP: ")
        val listener: SipAudioCall.Listener = object : SipAudioCall.Listener() {
            override fun onCallEstablished(call: SipAudioCall) {
                Log.d(TAG, "onCallEstablished: ")
                call.apply {
                    startAudio()
                    setSpeakerMode(true)
//                    toggleMute()
                }
            }

            override fun onError(call: SipAudioCall?, errorCode: Int, errorMessage: String?) {
                super.onError(call, errorCode, errorMessage)

                Log.d(TAG, "onError: $errorMessage")
            }

            override fun onCallBusy(call: SipAudioCall?) {
                super.onCallBusy(call)

                Log.d(TAG, "onCallBusy: ")
            }

            override fun onCallEnded(call: SipAudioCall?) {
                super.onCallEnded(call)

                Log.d(TAG, "onCallEnded: ")
            }

            override fun onCalling(call: SipAudioCall?) {
                super.onCalling(call)

                Log.d(TAG, "onCalling: ")
            }

            override fun onRinging(call: SipAudioCall?, caller: SipProfile?) {
                super.onRinging(call, caller)

                Log.d(TAG, "onRinging: ")
            }

            override fun onCallHeld(call: SipAudioCall?) {
                super.onCallHeld(call)

                Log.d(TAG, "onCallHeld: ")
            }

            override fun onChanged(call: SipAudioCall?) {
                super.onChanged(call)

                Log.d(TAG, "onChanged: ")
            }

            override fun onReadyToCall(call: SipAudioCall?) {
                super.onReadyToCall(call)

                Log.d(TAG, "onReadyToCall: ")
            }

            override fun onRingingBack(call: SipAudioCall?) {
                super.onRingingBack(call)

                Log.d(TAG, "onRingingBack: ")
            }
        }
        val sipAddress = "sip:$callFor@sip.zadarma.com"
        val call: SipAudioCall? = sipManager?.makeAudioCall(
            sipProfile.uriString, sipAddress, listener, 30
        )
    }

    override fun handleObservables() {
        super.handleObservables()

        mainViewModel.logoutSuccessfullyLiveData.observe(this) {
            invalidateAuthState()
        }

        mainViewModel.errorLiveData.observe(this) { error ->
            showErrorDialog(error?.message ?: return@observe)
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
                return@addCallback
            }
            finish()
        }
    }

    fun openFragment(fragment: Fragment, transactionType: Enums.TransactionType? = null) {
        if (supportFragmentManager.isDestroyed) return
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            when (transactionType) {
                Enums.TransactionType.OPEN_CLOSE -> {
                    setCustomAnimations(
                        R.anim.up_enter, R.anim.no_animation, R.anim.no_animation, R.anim.down_exit
                    )
                }

                else -> {
                    setCustomAnimations(
                        R.anim.right_enter, R.anim.left_exit, R.anim.left_enter, R.anim.right_exit
                    )
                }
            }
            setPrimaryNavigationFragment(fragment)
            add(R.id.frame_layout_container, fragment, fragment::class.java.simpleName)
            if (supportFragmentManager.isStateSaved) commitAllowingStateLoss() else commit()
        }
    }

    fun invalidateAuthState() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        mainViewModel.getLocalAccessToken().takeIf { !it.isNullOrBlank() }?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            openFragment(HomeFragment())
            return
        }
        openFragment(LoginFragment())
    }
}

package ru.aol_panchenko.tables.presentation.login

import android.os.Bundle
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.MainActivity
import ru.aol_panchenko.tables.presentation.login.enter_code.EnterCodeFragment
import ru.aol_panchenko.tables.presentation.login.enter_number.EnterNumberFragment
import java.util.concurrent.TimeUnit


/**
 * Created by alexey on 29.08.17.
 */
class LoginActivity: AppCompatActivity(), LoginMVPView,
        EnterNumberFragment.OnSendListener, EnterCodeFragment.OnConfirmListener {

    private lateinit var _fragmentManager: FragmentManager
    private var _presenter: LoginPresenter? = null
    private var _verificationCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var _auth: FirebaseAuth? = null
    private var _loginAdapter: LoginAdapter? = null

    private var _phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        _fragmentManager = supportFragmentManager
        _loginAdapter = LoginAdapter(_fragmentManager)
        loginPager.adapter = _loginAdapter
        slidingTabs.setupWithViewPager(loginPager)
        if (savedInstanceState == null) {
            loginPager.currentItem = _loginAdapter!!.ENTER_NUMBER_FRAGMENT
        }
        _presenter = LoginPresenter(this)
        _auth = FirebaseAuth.getInstance()
        initVerificationCallback()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    private fun initVerificationCallback() {
        _verificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                _presenter!!.onVerificationCompleted(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException?) {
                _presenter!!.onVerificationFailed(exception)
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                _presenter!!.onCodeSend(verificationId!!, token!!)
            }
        }
    }

    override fun openVerificationScreen() {
        loginPager.currentItem = _loginAdapter!!.ENTER_CODE_FRAGMENT
    }

    override fun trySignIn(credential: PhoneAuthCredential) {
        _auth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    _presenter!!.signInTaskCompleted(task)
                }
    }

    override fun signIn() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onSend(phone: String) {
        this._phone = phone
        _presenter!!.onSendPhone(phone)
    }

    override fun onConfirm(code: String) {
        _presenter!!.onConfirmCode(code)
    }

    override fun onResend() {
        _presenter!!.onResendPhone()
    }

    override fun startPhoneVerification(phone: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                _verificationCallback!!)
    }

    override fun resendCode(phone: String, token: PhoneAuthProvider.ForceResendingToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                _verificationCallback!!,
                token)
    }

    override fun onStart() {
        super.onStart()
        _presenter!!.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (_presenter != null) {
            _presenter = null
        }
    }

    override fun getPhone() = _phone

    override fun showErrorMessage(message: String) {
        toast(message)
    }

    override fun showErrorSignIn() {
        toast(getString(R.string.login_code_unknown))
    }

    override fun showProgress() {
        loginProgress.visibility = View.VISIBLE
        loginPager.visibility = View.GONE
    }

    override fun showContent() {
        loginProgress.visibility = View.GONE
        loginPager.visibility = View.VISIBLE
    }
}
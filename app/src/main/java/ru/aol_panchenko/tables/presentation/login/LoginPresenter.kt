package ru.aol_panchenko.tables.presentation.login

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.jetbrains.anko.startActivity
import ru.aol_panchenko.tables.presentation.MainActivity
import ru.aol_panchenko.tables.utils.formatPhone

/**
 * Created by alexey on 30.08.17.
 */
class LoginPresenter(private val _mvpView: LoginMVPView) {

    private val TAG = LoginPresenter::class.java.simpleName

    private var _verificationId: String? = null
    private var _token: PhoneAuthProvider.ForceResendingToken? = null

    fun onSendPhone(phone: String) {
        _mvpView.showProgress()
        _mvpView.startPhoneVerification(formatPhone(phone))
    }

    fun onVerificationCompleted(credential: PhoneAuthCredential?) {
        _mvpView.showContent()
        _mvpView.trySignIn(credential!!)
    }

    fun onVerificationFailed(exception: FirebaseException?) {
        _mvpView.showContent()
        _mvpView.showErrorMessage(exception!!.message!!)
    }

    fun onConfirmCode(code: String) {
        _mvpView.showProgress()
        _mvpView.trySignIn(PhoneAuthProvider.getCredential(_verificationId!!, code))
    }

    fun onResendPhone() {
        val phone = _mvpView.getPhone()
        _mvpView.showProgress()
        _mvpView.resendCode(formatPhone(phone), _token!!)
    }

    fun onCodeSend(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        _verificationId = verificationId
        _token = token
        _mvpView.showContent()
        _mvpView.openVerificationScreen()
    }

    fun signInTaskCompleted(task: Task<AuthResult>) {
        _mvpView.showContent()
        if (task.isSuccessful || task.isComplete) {
            val user = task.result.user
            _mvpView.signIn()
        } else {
            _mvpView.showErrorSignIn()
        }
    }

    fun onStart() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            _mvpView.signIn()
        }
    }

}
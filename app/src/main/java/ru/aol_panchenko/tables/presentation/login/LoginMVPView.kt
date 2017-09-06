package ru.aol_panchenko.tables.presentation.login

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

/**
 * Created by alexey on 30.08.17.
 */
interface LoginMVPView {

    fun getPhone(): String
    fun startPhoneVerification(phone: String)
    fun showErrorMessage(message: String)
    fun trySignIn(credential: PhoneAuthCredential)
    fun resendCode(phone: String, token: PhoneAuthProvider.ForceResendingToken)
    fun showProgress()
    fun showContent()
    fun openVerificationScreen()
    fun showErrorSignIn()
    fun signIn()
}
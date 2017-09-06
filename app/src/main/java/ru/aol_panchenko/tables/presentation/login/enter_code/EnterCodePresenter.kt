package ru.aol_panchenko.tables.presentation.login.enter_code

/**
 * Created by alexey on 31.08.17.
 */
class EnterCodePresenter(private val _mvpView: EnterCodeMVPView) {


    fun onClickConfirm() {
        _mvpView.confirmCode()
    }

    fun onClickResend() {
        _mvpView.resendCode()
    }
}
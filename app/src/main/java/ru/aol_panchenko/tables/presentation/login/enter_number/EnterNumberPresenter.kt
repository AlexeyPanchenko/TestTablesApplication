package ru.aol_panchenko.tables.presentation.login.enter_number

import android.text.TextUtils

/**
 * Created by alexey on 30.08.17.
 */
class EnterNumberPresenter(private val _mvpView: EnterNumberMVPView) {

    fun onClickSend() {
        val phone = _mvpView.getPhone()
        if(TextUtils.isEmpty(phone)) {
            _mvpView.showErrorEmptyPhone()
        } else if (!phone.startsWith("+7")) {
            _mvpView.showErrorIncorrectPhone()
        } else {
            _mvpView.sendPhone()
        }
    }
}
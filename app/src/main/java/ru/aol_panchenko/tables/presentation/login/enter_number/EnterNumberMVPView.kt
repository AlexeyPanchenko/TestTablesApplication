package ru.aol_panchenko.tables.presentation.login.enter_number

/**
 * Created by alexey on 30.08.17.
 */
interface EnterNumberMVPView {
    fun sendPhone()
    fun getPhone(): String
    fun showErrorEmptyPhone()
    fun showErrorIncorrectPhone()
}
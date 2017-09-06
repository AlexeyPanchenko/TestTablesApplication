package ru.aol_panchenko.tables.presentation.model

/**
 * Created by alexey on 29.08.17.
 */
class User() {

    val phoneNumber: String? = null


    fun isAuth() = phoneNumber != null && phoneNumber.length > 7

}
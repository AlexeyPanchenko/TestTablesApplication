package ru.aol_panchenko.tables.presentation.login

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.aol_panchenko.tables.presentation.login.enter_code.EnterCodeFragment
import ru.aol_panchenko.tables.presentation.login.enter_number.EnterNumberFragment

/**
 * Created by alexey on 01.09.17.
 */
class LoginAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val ENTER_NUMBER_FRAGMENT = 0
    val ENTER_CODE_FRAGMENT = 1

    override fun getItem(position: Int): Fragment = when(position){
        ENTER_NUMBER_FRAGMENT -> EnterNumberFragment.newInstance()
        ENTER_CODE_FRAGMENT -> EnterCodeFragment.newInstance()
        else -> throw IllegalStateException("impossible")
    }

    override fun getPageTitle(position: Int): CharSequence = when(position){
        ENTER_NUMBER_FRAGMENT -> "Телефон"
        ENTER_CODE_FRAGMENT -> "Код"
        else -> throw IllegalStateException("impossible")
    }

    override fun getCount() = 2

}
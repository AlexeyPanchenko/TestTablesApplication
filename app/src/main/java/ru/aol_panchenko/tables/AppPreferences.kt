package ru.aol_panchenko.tables

import android.content.Context
import com.google.gson.Gson
import ru.aol_panchenko.tables.presentation.model.User

/**
 * Created by alexey on 29.08.17.
 */
class AppPreferences(context: Context) {

    private val SHARED_PREFERENCES_NAME = "application_ preferences"
    private val USER_KEY = "user_key"


    private val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getUser(): User = Gson().fromJson(sharedPref.getString(USER_KEY, Gson().toJson(User())), User::class.java)

}
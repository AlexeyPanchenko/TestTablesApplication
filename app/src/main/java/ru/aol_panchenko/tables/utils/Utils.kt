package ru.aol_panchenko.tables.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import ru.aol_panchenko.tables.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexey on 31.08.17.
 */
fun formatPhone(phone: String) = "+${phone.replace("\\D".toRegex(), "")}".trim()

fun formatContact(phone: String): String {
    val correctNumber = if (phone.startsWith("8")) "7${phone.substring(1)}" else phone
    return "+${correctNumber.replace("\\D".toRegex(), "")}".trim()
}

fun dateFormat(timeStamp: Long): String? {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(timeStamp)
}

fun slideDown(context: Context, view: View?) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
    if (anim != null) {
        anim.reset()
        if (view != null) {
            view.clearAnimation()
            view.startAnimation(anim)
        }
    }
}

fun slideUp(context: Context, view: View?) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    if (anim != null) {
        anim.reset()
        if (view != null) {
            view.clearAnimation()
            view.startAnimation(anim)
        }
    }
}
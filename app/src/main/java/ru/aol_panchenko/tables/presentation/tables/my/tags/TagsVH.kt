package ru.aol_panchenko.tables.presentation.tables.my.tags

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 06.09.17.
 */
class TagsVH(view: View) : RecyclerView.ViewHolder(view) {

    val tvUId = view.findViewById<TextView>(R.id.tvUserId)
    val tvTag = view.findViewById<TextView>(R.id.tvTag)

}
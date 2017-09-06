package ru.aol_panchenko.tables.presentation.tables.my.scores

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 06.09.17.
 */
class ScoresVH(view: View) : RecyclerView.ViewHolder(view) {

    val tvUId = view.findViewById<TextView>(R.id.tvUserId)
    val tvScore = view.findViewById<TextView>(R.id.tvScore)
    val tvDate = view.findViewById<TextView>(R.id.tvDate)

}
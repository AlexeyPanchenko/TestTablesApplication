package ru.aol_panchenko.tables.presentation.tables.add_table

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import org.jetbrains.anko.onClick
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 03.09.17.
 */
class AddTableTagVH(view: View) : RecyclerView.ViewHolder(view) {

    val editTag = view.findViewById<EditText>(R.id.etTag)!!
    val ivDelete = view.findViewById<ImageView>(R.id.ivDelete)!!

}
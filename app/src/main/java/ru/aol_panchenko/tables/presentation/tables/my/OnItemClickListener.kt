package ru.aol_panchenko.tables.presentation.tables.my

import android.view.View
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 07.09.17.
 */
interface OnItemClickListener {
    fun onItemClick(view: View, table: Table)
}
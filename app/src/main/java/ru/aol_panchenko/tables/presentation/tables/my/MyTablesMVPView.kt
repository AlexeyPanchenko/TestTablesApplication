package ru.aol_panchenko.tables.presentation.tables.my

import android.view.View
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 02.09.17.
 */
interface MyTablesMVPView {
    fun createTable()
    fun addTable(table: Table?)
    fun showItemMenu(view: View, table: Table)
    fun removeTable(table: Table?)
    fun showEditDialog(table: Table)
    fun changeTable(table: Table)
}
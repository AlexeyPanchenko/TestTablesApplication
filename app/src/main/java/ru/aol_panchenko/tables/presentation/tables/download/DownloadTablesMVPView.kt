package ru.aol_panchenko.tables.presentation.tables.download

import android.view.View
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 09.09.17.
 */
interface DownloadTablesMVPView {
    fun showItemMenu(view: View, table: Table)
    fun showEditDialog(table: Table)
    fun changeTable(table: Table)
    fun addTable(table: Table)
    fun removeTable(table: Table)
    fun setTables(searchTables: ArrayList<Table>)
    fun closeSearch()
}
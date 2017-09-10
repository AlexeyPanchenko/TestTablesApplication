package ru.aol_panchenko.tables.presentation.tables.all

import android.view.View
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 09.09.17.
 */
interface AllTablesMVPView {
    fun showItemMenu(view: View, table: Table)
    fun showErrorYourTable()
    fun changeTable(table: Table)
    fun addTable(table: Table)
    fun removeTable(table: Table)
    fun notifyListChangedNoConnection()
    fun showErrorNetworkState()
    fun showContentState()
    fun setTables(searchTables: ArrayList<Table>)
    fun closeSearch()
}
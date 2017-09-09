package ru.aol_panchenko.tables.presentation.tables.download

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tables_fragment.*
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.tables.edit_table.EditTableDialog
import ru.aol_panchenko.tables.presentation.tables.my.MyTablesAdapter
import ru.aol_panchenko.tables.presentation.tables.my.OnItemClickListener

/**
 * Created by alexey on 02.09.17.
 */
class DownloadTablesFragment : Fragment(), DownloadTablesMVPView, OnItemClickListener {

    private var _presenter: DownloadTablesPresenter? = null
    private var _adapter: MyTablesAdapter? = null

    companion object {
        fun newInstance() = DownloadTablesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.tables_fragment, container, false)

        _adapter = MyTablesAdapter(activity, this)
        _presenter = DownloadTablesPresenter(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTables.adapter = _adapter
        rvTables.layoutManager = LinearLayoutManager(activity)
    }

    override fun addTable(table: Table) {
        _adapter!!.addItem(table)
    }

    override fun removeTable(table: Table) {
        _adapter!!.removeItem(table)
    }

    override fun changeTable(table: Table) {
        _adapter!!.changeItem(table)
    }

    override fun showItemMenu(view: View, table: Table) {
        val popupMenu = PopupMenu(activity, view)
        popupMenu.inflate(R.menu.menu_download_tables)
        popupMenu.setOnMenuItemClickListener { item -> when(item.itemId){
            R.id.item_menu_downloaded_edit -> {
                _presenter!!.onEditMenuClick(table)
                return@setOnMenuItemClickListener true
            }
            else -> {
                return@setOnMenuItemClickListener false
            }
        } }
        popupMenu.show()
    }

    override fun showEditDialog(table: Table) {
        EditTableDialog.show(activity.supportFragmentManager, table)
    }

    override fun onItemClick(view: View, table: Table) {
        _presenter!!.onItemClick(view, table)
    }
}
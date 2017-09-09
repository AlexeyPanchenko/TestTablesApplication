package ru.aol_panchenko.tables.presentation.tables.my

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tables_fragment.*
import org.jetbrains.anko.support.v4.onRefresh
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.tables.add_table.AddTableDialog
import ru.aol_panchenko.tables.presentation.tables.edit_table.EditTableDialog

/**
 * Created by alexey on 02.09.17.
 */
class MyTablesFragment : Fragment(), MyTablesMVPView, OnItemClickListener {

    private var _presenter: MyTablesPresenter? = null
    private var _adapter: MyTablesAdapter? = null

    companion object {
        fun newInstance() = MyTablesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.tables_fragment, container, false)
        val fab = activity.findViewById<FloatingActionButton>(R.id.fabAdd)

        _adapter = MyTablesAdapter(activity, this)
        _presenter = MyTablesPresenter(this)

        fab.setOnClickListener({ _presenter!!.onCreateTableClick() })

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTables.adapter = _adapter
        rvTables.layoutManager = LinearLayoutManager(activity)
    }

    override fun createTable() {
        AddTableDialog.show(activity.supportFragmentManager)
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

    override fun showEditDialog(table: Table) {
        EditTableDialog.show(activity.supportFragmentManager, table)
    }

    override fun showItemMenu(view: View, table: Table) {
        val popupMenu = PopupMenu(activity, view)
        popupMenu.inflate(R.menu.menu_my_tables)
        popupMenu.setOnMenuItemClickListener { item -> when(item.itemId){
            R.id.item_menu_edit -> {
                _presenter!!.onEditMenuClick(table)
                return@setOnMenuItemClickListener true
            }
            R.id.item_menu_delete -> {
                _presenter!!.onDeleteMenuClick(table)
                return@setOnMenuItemClickListener true
            }
            else -> {
                return@setOnMenuItemClickListener false
            }
        } }
        popupMenu.show()
    }

    override fun onItemClick(view: View, table: Table) {
        _presenter!!.onItemClick(view, table)
    }

}
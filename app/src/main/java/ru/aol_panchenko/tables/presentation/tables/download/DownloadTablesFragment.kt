package ru.aol_panchenko.tables.presentation.tables.download

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.SearchView
import android.view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.serach_menu, menu)
        val searchItem = menu?.findItem(R.id.menu_search_action)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.queryHint = getString(R.string.search_view_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                _presenter?.onSearchQuerySubmit(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchItem!!.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?) = true

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                _presenter?.onSearchClosed()
                return true
            }
        })
    }

    override fun setTables(searchTables: ArrayList<Table>) {
        _adapter!!.setItems(searchTables)
    }

    override fun closeSearch() {
        activity.recreate()
    }
}
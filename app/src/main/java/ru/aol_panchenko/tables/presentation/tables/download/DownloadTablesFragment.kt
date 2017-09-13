package ru.aol_panchenko.tables.presentation.tables.download

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.tables_fragment.*
import org.jetbrains.anko.support.v4.toast
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.tables.edit_table.EditTableDialog
import ru.aol_panchenko.tables.presentation.tables.my.MyTablesAdapter
import ru.aol_panchenko.tables.presentation.tables.my.OnItemClickListener

/**
 * Created by alexey on 02.09.17.
 */
class DownloadTablesFragment : Fragment(), DownloadTablesMVPView, OnItemClickListener {

    private val REQUEST_PHONE: Int = 1
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
            R.id.item_menu_delete -> {
                _presenter!!.onDeleteMenuClick(table)
                return@setOnMenuItemClickListener true
            }
            R.id.item_menu_sharing -> {
                _presenter!!.onSharingMenuClick(table)
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
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                _presenter?.onSearchQuerySubmit(newText)
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

    override fun extractContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        startActivityForResult(intent, REQUEST_PHONE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHONE) {
            val contactUri = data!!.data
            val queryFields = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val c = activity.contentResolver.query(contactUri, queryFields, null, null, null)
            if (c.moveToFirst()) {
                val indexPhone = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = c.getString(indexPhone)
                _presenter!!.onContactExtracted(number)
            }
            c.close()
        }
    }

    override fun showAlreadyExistMessage() {
        toast(getString(R.string.user_already_has_table_message))
    }

    override fun showNotInstallMessage() {
        toast(getString(R.string.user_has_not_app_message))
    }
}
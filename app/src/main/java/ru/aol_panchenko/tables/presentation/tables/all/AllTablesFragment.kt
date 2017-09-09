package ru.aol_panchenko.tables.presentation.tables.all

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.tables_fragment.*
import org.jetbrains.anko.support.v4.toast
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.tables.my.MyTablesAdapter
import ru.aol_panchenko.tables.presentation.tables.my.OnItemClickListener

/**
 * Created by alexey on 02.09.17.
 */
class AllTablesFragment : Fragment(), AllTablesMVPView, OnItemClickListener {

    private var _presenter: AllTablesPresenter? = null
    private var _adapter: MyTablesAdapter? = null
    private lateinit var _errorContainer: FrameLayout

    companion object {
        fun newInstance() = AllTablesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.tables_fragment, container, false)
        _errorContainer = view.findViewById(R.id.nothing_container)
        _adapter = MyTablesAdapter(activity, this)
        _presenter = AllTablesPresenter(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTables.adapter = _adapter
        rvTables.layoutManager = LinearLayoutManager(activity)
    }

    override fun changeTable(table: Table) {
        _adapter?.changeItem(table)
    }

    override fun addTable(table: Table) {
        _adapter?.addItem(table)
    }

    override fun removeTable(table: Table) {
        _adapter?.removeItem(table)
    }

    override fun notifyListChanged() {
        _adapter?.notifyCha()
    }

    override fun showItemMenu(view: View, table: Table) {
        val popupMenu = PopupMenu(activity, view)
        popupMenu.inflate(R.menu.menu_all_tables)
        popupMenu.setOnMenuItemClickListener { item -> when(item.itemId){
            R.id.item_menu_download -> {
                _presenter!!.onDownloadMenuClick(table)
                return@setOnMenuItemClickListener true
            }
            else -> {
                return@setOnMenuItemClickListener false
            }
        } }
        popupMenu.show()
    }

    override fun showContentState() {
        _errorContainer.visibility = View.GONE
    }

    override fun showErrorNetworkState() {
        _errorContainer.visibility = View.VISIBLE
    }

    override fun showErrorYourTable() {
        toast("У вас уже есть эта таблица :)")
    }

    override fun onItemClick(view: View, table: Table) {
        _presenter!!.onItemClick(view, table)
    }
}
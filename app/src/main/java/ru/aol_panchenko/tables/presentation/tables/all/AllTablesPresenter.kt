package ru.aol_panchenko.tables.presentation.tables.all

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 09.09.17.
 */
class AllTablesPresenter(private val _mvpView: AllTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber

    init {
        initChangeListener()
    }

    private fun initChangeListener() {

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.changeTable(table)
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.addTable(table)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.removeTable(table)
            }
        }
        _database.addChildEventListener(childEventListener)
    }

    fun onItemClick(view: View, table: Table) {
        if (table.uId != _userId || (table.holders != null && table.holders!!.contains(_userId))) {
            _mvpView.showItemMenu(view, table)
        } else {
            _mvpView.showErrorYourTable()
        }
    }

    fun onDownloadMenuClick(table: Table) {
        if (table.holders == null){
            table.holders = ArrayList(1)
        }
        table.holders?.add(_userId!!)
        _database.child(table.tableId).setValue(table)
    }
}
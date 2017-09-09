package ru.aol_panchenko.tables.presentation.tables.my

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 02.09.17.
 */
class MyTablesPresenter(private val _mvpView: MyTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")

    init {
        changeListener()
    }

    fun onCreateTableClick() {
        _mvpView.createTable()
    }

    private fun changeListener() {

        _database.keepSynced(true)

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (table!!.uId == FirebaseAuth.getInstance().currentUser!!.phoneNumber!!) {
                    _mvpView.changeTable(table)
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (table!!.uId == FirebaseAuth.getInstance().currentUser!!.phoneNumber!!) {
                    _mvpView.addTable(table)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.removeTable(table)
            }
        }
        _database.addChildEventListener(childEventListener)
    }

    fun onItemClick(view: View, table: Table) {
        _mvpView.showItemMenu(view, table)
    }

    fun onEditMenuClick(table: Table) {
        _mvpView.showEditDialog(table)
    }

    fun onDeleteMenuClick(table: Table) {
        _database.child(table.tableId).removeValue()
    }

}
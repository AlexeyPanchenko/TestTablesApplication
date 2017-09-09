package ru.aol_panchenko.tables.presentation.tables.download

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
class DownloadTablesPresenter(private val _mvpView: DownloadTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber

    init {
        initChangeListener()
    }

    private fun initChangeListener() {

        _database.keepSynced(true)

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (isContains(table)) {
                    _mvpView.changeTable(table!!)
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (isContains(table)) {
                    _mvpView.addTable(table!!)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (isContains(table)) {
                    _mvpView.removeTable(table!!)
                }
            }
        }
        _database.addChildEventListener(childEventListener)
    }

    private fun isContains(table: Table?) = table?.holders != null && table.holders!!.contains(_userId)

    fun onItemClick(view: View, table: Table) {
        _mvpView.showItemMenu(view, table)
    }

    fun onEditMenuClick(table: Table) {
        _mvpView.showEditDialog(table)
    }
}
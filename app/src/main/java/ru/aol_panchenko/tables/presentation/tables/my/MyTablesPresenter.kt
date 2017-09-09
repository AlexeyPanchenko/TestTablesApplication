package ru.aol_panchenko.tables.presentation.tables.my

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 02.09.17.
 */
class MyTablesPresenter(private val _mvpView: MyTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber

    init {
        initChangeListener()
    }

    fun onCreateTableClick() {
        _mvpView.createTable()
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
                if (isMy(table)) {
                    _mvpView.changeTable(table!!)
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (isMy(table)) {
                    _mvpView.addTable(table!!)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                if (isMy(table)) {
                    _mvpView.removeTable(table!!)
                }
            }
        }

         /*_database.addListenerForSingleValueEvent(object : ValueEventListener{
             override fun onCancelled(e: DatabaseError?) {
             }

             override fun onDataChange(dataSnapshot: DataSnapshot?) {
                 val table = dataSnapshot!!.getValue(Table::class.java)
                 _mvpView.updateTable(table!!)
             }
         })*/
        _database.addChildEventListener(childEventListener)
    }

    private fun isMy(table: Table?) = table!!.uId ==  _userId

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
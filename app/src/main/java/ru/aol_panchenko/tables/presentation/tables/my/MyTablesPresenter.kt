package ru.aol_panchenko.tables.presentation.tables.my

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.utils.formatContact

/**
 * Created by alexey on 02.09.17.
 */
class MyTablesPresenter(private val _mvpView: MyTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _databaseUsers = FirebaseDatabase.getInstance().reference.child("users")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber
    private var _table: Table? = null
    private val _users = ArrayList<String>()

    init {
        initFirebaseListener()
    }

    fun onCreateTableClick() {
        _mvpView.createTable()
    }

     private fun initFirebaseListener() {

        _database.keepSynced(true)

        val childEventListener = getChildEventListener()
        _database.addChildEventListener(childEventListener)

         initValueEventListener()
    }

    private fun getChildEventListener(): ChildEventListener {
        return object : ChildEventListener {
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
                Log.d("TTT", "REMOVE ID = ${table!!.tableId}")
                if (isMy(table)) {
                    _mvpView.removeTable(table!!)
                }
            }
        }
    }

    private fun initValueEventListener() {
        _databaseUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                _users.clear()
                dataSnapshot!!
                        .children
                        .mapTo(_users) { it.value as String }

            }
        })
    }

    private fun isMy(table: Table?) = table!!.uId == _userId

    fun onItemClick(view: View, table: Table) {
        _mvpView.showItemMenu(view, table)
    }

    fun onEditMenuClick(table: Table) {
        _mvpView.showEditDialog(table)
    }

    fun onDeleteMenuClick(table: Table) {
        _database.child(table.tableId).removeValue()
    }

    fun onSearchQuerySubmit(query: String?) {
        _database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val searchTables = ArrayList<Table>()
                dataSnapshot!!.children
                        .map { it.getValue(Table::class.java) }
                        .filter { it?.uId == _userId }
                        .forEach { table ->
                            table?.tags?.forEach met@ {
                                if (it.value!!.contains(query!!) && !searchTables.contains(table)) {
                                    searchTables.add(table)
                                    return@met
                                }
                            }
                        }
                _mvpView.setTables(searchTables)
            }
        })
    }

    fun onSearchClosed() {
        _mvpView.closeSearch()
    }

    fun onSharingMenuClick(table: Table) {
        _table = table
        _mvpView.extractContact()
    }

    fun onContactExtracted(number: String?) {
        val phone = formatContact(number!!)
        if (_table!!.holders == null){
            _table!!.holders = ArrayList(1)
        }
        if (_users.contains(phone)) {
            if (!_table?.holders!!.contains(phone) && _table?.uId != phone) {
                _table!!.holders!!.add(formatContact(number))
                _database.child(_table!!.tableId).setValue(_table)
            } else {
                _mvpView.showAlreadyExistMessage()
            }
        } else {
            _mvpView.showNotInstallMessage()
        }
        _table = null
    }

}
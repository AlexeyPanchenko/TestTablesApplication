package ru.aol_panchenko.tables.presentation.tables.all

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Table
import com.google.firebase.database.ValueEventListener
import ru.aol_panchenko.tables.utils.formatContact


/**
 * Created by alexey on 09.09.17.
 */
class AllTablesPresenter(private val _mvpView: AllTablesMVPView) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _databaseUsers = FirebaseDatabase.getInstance().reference.child("users")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber
    private var _table: Table? = null
    private val _users = ArrayList<String>()

    init {
        initFirebaseListener()
    }

    private fun initFirebaseListener() {

        _database.keepSynced(false)

        val childEventListener = getChildEventListener()

        val connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java)!!
                if (connected) {
                    _database.addChildEventListener(childEventListener)
                    _mvpView.showContentState()
                } else {
                    _database.removeEventListener(childEventListener)
                    _mvpView.notifyListChangedNoConnection()
                    _mvpView.showErrorNetworkState()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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
                _mvpView.changeTable(table!!)
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.addTable(table!!)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val table = dataSnapshot!!.getValue(Table::class.java)
                _mvpView.removeTable(table!!)
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

    fun onItemClick(view: View, table: Table) {
        _mvpView.showItemMenu(view, table)
    }

    fun onDownloadMenuClick(table: Table) {
        if (table.uId == _userId || (table.holders != null && table.holders!!.contains(_userId))) {
            _mvpView.showErrorYourTable()
        } else {
            if (table.holders == null){
                table.holders = ArrayList(1)
            }
            table.holders?.add(_userId!!)
            _database.child(table.tableId).setValue(table)
        }
    }

    fun onSearchQuerySubmit(query: String?) {
        _database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val searchTables = ArrayList<Table>()
                dataSnapshot!!.children
                        .map { it.getValue(Table::class.java) }
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
                _table?.holders?.add(phone)
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
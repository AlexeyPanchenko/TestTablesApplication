package ru.aol_panchenko.tables.presentation.tables.add_table

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Score
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 03.09.17.
 */
class AddTablePresenter(private val _mvpView: AddTableMVPView, private val _viewModel: AddTableViewModel) {

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber

    init {
        _mvpView.setTags(_viewModel.tags)
    }

    fun onAddTagClick() {
        val timeStamp = System.currentTimeMillis()
        val tag = Tag(_userId!!, "", timeStamp)
        _mvpView.addTag(tag)
    }

    fun onCreateClick() {
        if (validateScore()) {
            val key = _database.push().key
            val table = createTable(key)
            _database.child(key).setValue(table)
            _mvpView.dismissDialog()
        } else {
            _mvpView.showErrorValidate()
        }
    }

    private fun createTable(key: String): Table {
        val timeStamp = System.currentTimeMillis()
        val scoreValue = if (_mvpView.getScoreValue().isNotEmpty()) _mvpView.getScoreValue().toInt() else 0
        val score = Score(_userId!!, scoreValue, timeStamp)
        return Table(_userId, key, arrayListOf(score), _mvpView.getTags()!!, ArrayList(0))
    }

    private fun validateScore(): Boolean {
        val score = _mvpView.getScoreValue()
        return !((score.startsWith("0") && score.length == 2)
                || (score.length == 2 && !score.endsWith("0")))
    }

    fun onPause() {
        syncItems()
    }

    fun onCancelClick() {
        _mvpView.dismissDialog()
    }

    fun syncItems() {
        _viewModel.tags = _mvpView.getTags()!!
    }

}
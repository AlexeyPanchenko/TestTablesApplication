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

    init {
        _mvpView.setTags(_viewModel.tags)
    }

    fun onArgumentsAccept(table: Table?) {
        val score = table!!.scores!!.first { FirebaseAuth.getInstance().currentUser!!.phoneNumber!! == it.uId }
        _mvpView.fillSCore(score.value.toString())
        val tags = if (table.tags != null) {
            table.tags!!.filter { FirebaseAuth.getInstance().currentUser!!.phoneNumber!! == it.uId }
        } else {
            ArrayList(0)
        }
        _viewModel.tags = tags as ArrayList<Tag>
        _mvpView.setTags(_viewModel.tags)
        _viewModel.isEdit = true
    }

    fun onAddTagClick() {
        val userId = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
        val timeStamp = System.currentTimeMillis()
        val tag = Tag(userId, "", timeStamp)
        _mvpView.addTag(tag)
    }

    fun onCreateClick() {
        if (validateScore()) {
            val database = FirebaseDatabase.getInstance().reference.child("tables")
            if (_viewModel.isEdit) {
                //database.child()
            } else {
                val key = database.push().key
                val table = createTable(key)
                database.child(key).setValue(table)
                _mvpView.dismissDialog()
            }
        } else {
            _mvpView.showErrorValidate()
        }
    }

    private fun createTable(key: String): Table {
        val userId = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
        val timeStamp = System.currentTimeMillis()
        val scoreValue = if (_mvpView.getScoreValue().isNotEmpty()) _mvpView.getScoreValue().toInt() else 0
        val score = Score(userId, scoreValue, timeStamp)
        return Table(userId, key, arrayListOf(score), _mvpView.getTags()!!, ArrayList(0))
    }

    private fun validateScore(): Boolean {
        val score = _mvpView.getScoreValue()
        return !((score.startsWith("0") && score.length == 2)
                || (score.length == 2 && !score.endsWith("0")))
    }

    fun onPause() {
        val items = _mvpView.getTags()
        if (items == null) {
            _viewModel.tags.clear()
        } else {
            _viewModel.tags = items
        }
    }

    fun onCancelClick() {
        _mvpView.dismissDialog()
    }

}
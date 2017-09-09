package ru.aol_panchenko.tables.presentation.tables.edit_table

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Score
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 07.09.17.
 */
class EditTablePresenter(private val _mvpView: EditTableMVPView, private val _viewModel: EditTableViewModel){

    private val _database = FirebaseDatabase.getInstance().reference.child("tables")
    private val _userId: String? = FirebaseAuth.getInstance().currentUser?.phoneNumber

    init {
        _mvpView.setTags(_viewModel.tags)
    }

    fun onArgumentsAccept(table: Table?) {
        if (table != null) {

            var score = table.scores?.firstOrNull { _userId == it.uId }
            if (score == null) {
                score = Score()
                score.uId = _userId
                table.scores!!.add(score)
            }

            _mvpView.fillSCore(score.value.toString())
            table.tags = if (table.tags != null) table.tags else ArrayList(0)
            val tags = table.tags?.filter { _userId == it.uId } ?: ArrayList(0)
            tags.forEach { _viewModel.tags.add(Tag(it)) }

            _viewModel.table = table

            _mvpView.setTags(_viewModel.tags)
        } else {
            _mvpView.showErrorToast()
        }
    }

    fun onAddTagClick() {
        val timeStamp = System.currentTimeMillis()
        val tag = Tag(_userId!!, "", timeStamp)
        _mvpView.addTag(tag)
    }

    fun onCreateClick() {
        if (validateScore()) {
            val key = _viewModel.table!!.tableId
            addingScore()
            addingTags()
            _database.child(key).setValue(_viewModel.table)
            _mvpView.dismissDialog()
        } else {
            _mvpView.showErrorValidate()
        }
    }

    private fun addingTags() {
        val tableTags = _viewModel.table!!.tags?.filter { _userId == it.uId } ?: ArrayList(0)
        _viewModel.tags = _mvpView.getTags()!!
        val resultTags = ArrayList<Tag>(_viewModel.tags.size)
        _viewModel.tags.forEach { tag ->
            tableTags.forEach {
                if (tag.timeStamp == it.timeStamp && tag.value != it.value) {
                    tag.timeStamp = System.currentTimeMillis()
                }
            }
            resultTags.add(tag)
        }
        _viewModel.table!!.tags!!.removeAll(tableTags)
        _viewModel.table!!.tags!!.addAll(resultTags)
    }

    private fun addingScore() {
        val tableScore = _viewModel.table!!.scores!!.first { it.uId == _userId }
        val scoreValue = if (_mvpView.getScoreValue().isNotEmpty()) _mvpView.getScoreValue().toInt() else 0
        if (tableScore.value != scoreValue) {
            tableScore.value = scoreValue
            tableScore.timeStamp = System.currentTimeMillis()
        }
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
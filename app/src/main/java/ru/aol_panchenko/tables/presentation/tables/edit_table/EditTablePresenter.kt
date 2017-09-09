package ru.aol_panchenko.tables.presentation.tables.edit_table

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 07.09.17.
 */
class EditTablePresenter(private val _mvpView: EditTableMVPView, private val _viewModel: EditTableViewModel){

    init {
        _mvpView.setTags(_viewModel.tags)
    }

    fun onArgumentsAccept(table: Table?) {
        if (table != null) {
            val score = table.scores!!.first { FirebaseAuth.getInstance().currentUser!!.phoneNumber!! == it.uId }
            _mvpView.fillSCore(score.value.toString())
            val tags = table.tags?.filter { FirebaseAuth.getInstance().currentUser!!.phoneNumber!! == it.uId }
                    ?: ArrayList(0)

            _viewModel.table = table

            tags.forEach { _viewModel.tags.add(Tag(it)) }
            _mvpView.setTags(_viewModel.tags)
        } else {
            _mvpView.showErrorToast()
        }
    }

    fun onAddTagClick() {
        val userId = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
        val timeStamp = System.currentTimeMillis()
        val tag = Tag(userId, "", timeStamp)
        _mvpView.addTag(tag)
    }

    fun onCreateClick() {
        if (validateScore()) {
            val userId = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
            val timeStamp = System.currentTimeMillis()
            val database = FirebaseDatabase.getInstance().reference.child("tables")
            val key = _viewModel.table!!.tableId
            addingScore(userId, timeStamp)
            addingTags(userId)
            database.child(key).setValue(_viewModel.table)
            _mvpView.dismissDialog()
        } else {
            _mvpView.showErrorValidate()
        }
    }

    private fun addingTags(userId: String) {
        val tableTags = _viewModel.table!!.tags!!
                .filter { userId == it.uId }
        _viewModel.tags = _mvpView.getTags()!!
        val resultTags = ArrayList<Tag>(_viewModel.tags.size)
        tableTags.forEach { Log.d("TTT", "До: ${it.timeStamp}   ${it.value}") }
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
        _viewModel.table?.tags?.forEach { Log.d("TTT", "После: ${it.timeStamp}   ${it.value}") }
    }

    private fun addingScore(userId: String, timeStamp: Long) {
        val tableScore = _viewModel.table!!.scores!!
                .first { it.uId == userId }
        val scoreValue = if (_mvpView.getScoreValue().isNotEmpty()) _mvpView.getScoreValue().toInt() else 0
        if (tableScore.value != scoreValue) {
            tableScore.timeStamp = timeStamp
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
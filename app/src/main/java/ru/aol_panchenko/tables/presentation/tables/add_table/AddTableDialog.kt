package ru.aol_panchenko.tables.presentation.tables.add_table

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.add_table_dialog.*
import org.jetbrains.anko.onClick
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 02.09.17.
 */
class AddTableDialog : DialogFragment(), AddTableMVPView, OnTegChangedListener {

    private var _presenter: AddTablePresenter? = null
    private var _adapter: AddTableTagsAdapter? = null
    private var _rvTags: RecyclerView? = null
    private var _editScore: EditText? = null

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val dialog = AddTableDialog()
            dialog.show(fragmentManager, AddTableDialog::class.java.simpleName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.add_table_dialog, container, false)
        initViews(view)
        initRecyclerView()
        _presenter = AddTablePresenter(this, ViewModelProviders.of(this).get(AddTableViewModel::class.java))

        return view
    }

    private fun initViews(view: View) {
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddTag)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        fabAdd.onClick { _presenter!!.onAddTagClick() }
        btnCreate.onClick { _presenter!!.onCreateClick() }
        btnCancel.onClick { _presenter!!.onCancelClick() }

        _rvTags = view.findViewById(R.id.rv_tags)
        _editScore = view.findViewById(R.id.etScore)
    }

    private fun initRecyclerView() {
        _adapter = AddTableTagsAdapter(this, activity)
        _rvTags!!.adapter = _adapter
        _rvTags!!.layoutManager = LinearLayoutManager(activity)
    }

    override fun fillSCore(value: String) {
        _editScore!!.setText(value)
    }

    override fun addTag(tag: Tag) {
        _adapter!!.addItem(tag)
    }

    override fun setTags(tags: ArrayList<Tag>) {
        if (tags.size > 0) {
            _adapter!!.setItems(tags)
        }
    }

    override fun dismissDialog() {
        dialog.dismiss()
    }

    override fun onDelete() {
        dialog.currentFocus.clearFocus()
    }

    override fun onSync() {
        _presenter!!.syncItems()
    }

    override fun showErrorValidate() {
        etScore.requestFocus()
        etScore.error = getString(R.string.validate_score_error)
    }

    override fun getScoreValue() = _editScore!!.text.toString()

    override fun getTags() = _adapter!!.getItems()

    override fun onPause() {
        super.onPause()
        _presenter!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (_presenter != null) {
            _presenter = null
        }
    }
}
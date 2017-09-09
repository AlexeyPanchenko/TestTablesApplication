package ru.aol_panchenko.tables.presentation.tables.edit_table

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
import org.jetbrains.anko.support.v4.toast
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.model.Tag
import ru.aol_panchenko.tables.presentation.tables.add_table.*

/**
 * Created by alexey on 07.09.17.
 */
class EditTableDialog : DialogFragment(), EditTableMVPView, OnTegChangedListener {

    private var _presenter: EditTablePresenter? = null
    private var _adapter: AddTableTagsAdapter? = null
    private var _rvTags: RecyclerView? = null
    private var _editScore: EditText? = null

    companion object {
        private const val ARG_TABLE = "table_key"
        fun show(fragmentManager: FragmentManager, table: Table?) {
            val dialog = EditTableDialog()
            val args = Bundle()
            args.putParcelable(ARG_TABLE, table)
            dialog.arguments = args
            dialog.show(fragmentManager, EditTableDialog::class.java.simpleName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.add_table_dialog, container, false)
        initViews(view)
        initRecyclerView()
        _presenter = EditTablePresenter(this, ViewModelProviders.of(this).get(EditTableViewModel::class.java))

        if (arguments != null && savedInstanceState == null) {
            val table = arguments.getParcelable<Table>(ARG_TABLE)
            _presenter!!.onArgumentsAccept(table)
        }
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

    override fun showErrorToast() {
        toast("Ошибка")
    }

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
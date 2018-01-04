package ru.aol_panchenko.tables.presentation.tables.add_table

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Tag


/**
 * Created by alexey on 03.09.17.
 */
class AddTableTagsAdapter(private val _listener: OnTegChangedListener, private val _context: Context) : RecyclerView.Adapter<AddTableTagVH>() {

    private var _tags = ArrayList<Tag>()
    private var _lastPos = -1

    fun setItems(items: ArrayList<Tag>?) {
        if (items == null) {
            _tags.clear()
        } else {
            _tags = items
        }
    }

    fun addItem(item: Tag?) {
        if (item != null) {
            val newPosition = itemCount
            this._tags.add(newPosition, item)
            super.notifyItemInserted(newPosition)
        }
        _listener.onSync()
    }

    fun getItems() = _tags

    override fun onBindViewHolder(holder: AddTableTagVH?, position: Int) {
        val tag = _tags[position]
        holder!!.editTag.setText(tag.value)
        holder.ivDelete.setOnClickListener({
            _listener.onDelete()
            _tags.remove(tag)
            super.notifyItemRemoved(position)
            _listener.onSync()
        })
        holder.editTag.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                tag.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                tag.value = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tag.value = s.toString()

            }
        })
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > _lastPos) {
            val animation = AnimationUtils.loadAnimation(_context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            _lastPos = position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AddTableTagVH {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.add_table_tags_item, parent, false)
        return AddTableTagVH(view)
    }

    override fun getItemCount() = _tags.size
}
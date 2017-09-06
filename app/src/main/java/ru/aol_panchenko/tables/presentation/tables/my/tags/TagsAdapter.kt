package ru.aol_panchenko.tables.presentation.tables.my.tags

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 06.09.17.
 */
class TagsAdapter(private val tags: List<Tag>) : RecyclerView.Adapter<TagsVH>() {

    override fun onBindViewHolder(holder: TagsVH?, position: Int) {
        val tag = tags[position]
        holder!!.tvUId.text = tag.uId
        holder.tvTag.text = tag.value
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TagsVH {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.tag_item, parent, false)
        return TagsVH(view)
    }

    override fun getItemCount() = tags.size
}
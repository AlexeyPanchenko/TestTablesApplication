package ru.aol_panchenko.tables.presentation.tables.my

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.onClick
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Table

/**
 * Created by alexey on 02.09.17.
 */
class TablesAdapter(private val _context: Context, private val _clikListener: OnItemClickListener)
    : RecyclerView.Adapter<TablesVH>() {

    private var _tables: List<Table> = ArrayList()

    fun setItems(items: List<Table>) {
        _tables = items
        notifyDataSetChanged()
    }

    fun addItem(item: Table) {
        val position = itemCount
        (_tables as ArrayList<Table>).add(item)
        notifyItemInserted(position)
    }

    fun removeItem(item: Table) {
        val filterTables = _tables.filter { item.tableId == it.tableId }
        if (filterTables.isEmpty()) {
            notifyDataSetChanged()
        } else {
            val table = filterTables.first { item.tableId == it.tableId }
            val position: Int = _tables.indexOf(table)
            if (position != -1) {
                (_tables as ArrayList).removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

    fun changeItem(item: Table) {
        val table = _tables.firstOrNull { it.tableId == item.tableId }
        val position: Int = _tables.indexOf(table)
        if (position != -1) {
            (_tables as ArrayList)[position] = item
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TablesVH {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.my_table_item, parent, false)
        return TablesVH(view, _context)
    }

    override fun onBindViewHolder(holder: TablesVH?, position: Int) {
        val table = _tables[position]
        bindView(table, holder)
    }

    private fun bindView(table: Table, holder: TablesVH?) {
        val sortScores = table.scores!!.sortedByDescending { it.timeStamp }
        val sortTags = if (table.tags != null) {
            table.tags!!.sortedByDescending { it.timeStamp }
        } else {
            ArrayList(0)
        }

        var sumScore = 0
        table.scores!!.forEach { sumScore += it.value }
        val middleScore = sumScore/table.scores!!.size
        val tagText = if (table.tags != null) sortTags[0].value  else "---"

        holder!!.setTextId(table.tableId!!)
        holder.setTextScore(middleScore.toString())
        holder.setTextTag(tagText)

        holder.initScores(sortScores)
        holder.initTags(sortTags)

        holder.scoreContainer.onClick { holder.toggleScores() }
        holder.tagContainer.onClick { holder.toggleTags() }
        holder.ivMenu.onClick { _clikListener.onItemClick(holder.ivMenu, table) }
    }

    override fun getItemCount() = _tables.size


    fun notifyDataClear() {
        (_tables as ArrayList).clear()
        notifyDataSetChanged()
    }

    fun getItems() = _tables

}
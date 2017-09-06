package ru.aol_panchenko.tables.presentation.tables.my

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.image
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Score
import ru.aol_panchenko.tables.presentation.model.Tag
import ru.aol_panchenko.tables.presentation.tables.my.scores.ScoresAdapter
import ru.aol_panchenko.tables.presentation.tables.my.tags.TagsAdapter
import ru.aol_panchenko.tables.utils.slideDown
import ru.aol_panchenko.tables.utils.slideUp
import android.support.v7.widget.DividerItemDecoration


/**
 * Created by alexey on 02.09.17.
 */
class MyTablesVH(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    val scoreContainer = view.findViewById<LinearLayout>(R.id.scoreContainer)!!
    private val textId = view.findViewById<TextView>(R.id.tvTableId)
    private val textScore = view.findViewById<TextView>(R.id.tvScores)
    val tagContainer = view.findViewById<LinearLayout>(R.id.tagContainer)!!
    private val textTag = view.findViewById<TextView>(R.id.tvTags)
    private val rvScores = view.findViewById<RecyclerView>(R.id.rvScores)
    private val rvTags = view.findViewById<RecyclerView>(R.id.rvTags)
    private val ivDropScore = view.findViewById<ImageView>(R.id.ivScoreDrop)
    private val ivDropTag = view.findViewById<ImageView>(R.id.ivTagDrop)
    val ivMenu = view.findViewById<ImageView>(R.id.ivPopMenu)

    fun setTextId(text: String) {
        textId.text = context.getString(R.string.table_id_title, text)
    }

    fun setTextScore(text: String) {
        textScore.text = context.getString(R.string.score_title, text)
    }

    fun setTextTag(text: String?) {
        textTag.text = context.getString(R.string.tag_title, text)
    }

    fun toggleScores() {
        if (rvScores.isShown) {
            slideUp(context, rvScores)
            rvScores.visibility = View.GONE
            ivDropScore.image = getImageDown()
        } else {
            ivDropScore.image = getImageUp()
            rvScores.visibility = View.VISIBLE
            slideDown(context, rvScores)
        }
    }

    fun toggleTags() {
        if (rvTags.isShown) {
            slideUp(context, rvTags)
            rvTags.visibility = View.GONE
            ivDropTag.image = getImageDown()
        } else {
            ivDropTag.image = getImageUp()
            rvTags.visibility = View.VISIBLE
            slideDown(context, rvTags)
        }
    }

    private fun getImageDown(): Drawable? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp)
        }
    }

    private fun getImageUp(): Drawable? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp)
        } else {
            context.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp)
        }
    }

    fun initScores(scores: List<Score>) {
        val layoutManager = LinearLayoutManager(context)
        rvScores.adapter = ScoresAdapter(scores)
        rvScores.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        rvScores.addItemDecoration(dividerItemDecoration)

    }

    fun initTags(tags: List<Tag>) {
        val layoutManager = LinearLayoutManager(context)
        rvTags.adapter = TagsAdapter(tags)
        rvTags.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        rvTags.addItemDecoration(dividerItemDecoration)
    }
}
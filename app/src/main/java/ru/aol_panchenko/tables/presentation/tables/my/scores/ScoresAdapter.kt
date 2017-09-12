package ru.aol_panchenko.tables.presentation.tables.my.scores

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.model.Score
import ru.aol_panchenko.tables.utils.dateFormat

/**
 * Created by alexey on 06.09.17.
 */
class ScoresAdapter(private val scores: List<Score>) : RecyclerView.Adapter<ScoresVH>() {

    override fun onBindViewHolder(holder: ScoresVH?, position: Int) {
        val score = scores[position]
        holder!!.tvUId.text = score.uId
        holder.tvScore.text = score.value.toString()
        holder.tvDate.text = dateFormat(score.timeStamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoresVH {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.score_item, parent, false)
        return ScoresVH(view)
    }

    override fun getItemCount() = scores.size
}
package ru.aol_panchenko.tables.presentation.tables.add_table

import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 03.09.17.
 */
interface AddTableMVPView {
    fun addTag(tag: Tag)
    fun setTags(tags: ArrayList<Tag>)
    fun getTags(): ArrayList<Tag>?
    fun getScoreValue(): String
    fun dismissDialog()
    fun showErrorValidate()
    fun fillSCore(value: String)
}
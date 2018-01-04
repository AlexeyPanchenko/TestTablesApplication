package ru.aol_panchenko.tables.presentation.tables.edit_table

import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 07.09.17.
 */
interface EditTableMVPView {
    fun fillSCore(value: String)
    fun addTag(tag: Tag)
    fun setTags(tags: ArrayList<Tag>)
    fun dismissDialog()
    fun showErrorValidate()
    fun getScoreValue(): String
    fun getTags(): ArrayList<Tag>?
    fun showErrorToast()
}
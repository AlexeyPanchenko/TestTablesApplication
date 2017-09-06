package ru.aol_panchenko.tables.presentation.tables.add_table

import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 03.09.17.
 */
interface OnTegDeleteListener {
    fun onDelete(tag: Tag, position: Int)
}
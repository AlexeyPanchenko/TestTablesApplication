package ru.aol_panchenko.tables.presentation.tables.edit_table

import android.arch.lifecycle.ViewModel
import ru.aol_panchenko.tables.presentation.model.Table
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 07.09.17.
 */
class EditTableViewModel : ViewModel() {
    var tags = ArrayList<Tag>()
    var table: Table? = null
}
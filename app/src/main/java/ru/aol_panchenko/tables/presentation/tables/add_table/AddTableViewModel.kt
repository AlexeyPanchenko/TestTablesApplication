package ru.aol_panchenko.tables.presentation.tables.add_table

import android.arch.lifecycle.ViewModel
import ru.aol_panchenko.tables.presentation.model.Tag

/**
 * Created by alexey on 03.09.17.
 */
class AddTableViewModel : ViewModel() {
    var tags = ArrayList<Tag>()
    var isEdit = false
}
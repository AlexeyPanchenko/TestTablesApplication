package ru.aol_panchenko.tables.presentation.tables.all

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 02.09.17.
 */
class AllTablesFragment : Fragment() {

    companion object {
        fun newInstance() = AllTablesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.all_tables_fragment, container, false)
        return view
    }
}
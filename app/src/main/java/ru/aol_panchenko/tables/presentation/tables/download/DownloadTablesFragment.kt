package ru.aol_panchenko.tables.presentation.tables.download

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 02.09.17.
 */
class DownloadTablesFragment : Fragment() {

    companion object {
        fun newInstance() = DownloadTablesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.download_tables_fragment, container, false)
        return view
    }
}
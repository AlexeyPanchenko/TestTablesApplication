package ru.aol_panchenko.tables.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.aol_panchenko.tables.R
import ru.aol_panchenko.tables.presentation.tables.all.AllTablesFragment
import ru.aol_panchenko.tables.presentation.tables.download.DownloadTablesFragment
import ru.aol_panchenko.tables.presentation.tables.my.MyTablesFragment

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_my -> {
                replaceFragment(MyTablesFragment.newInstance())
                fabAdd.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_downloaded -> {
                replaceFragment(DownloadTablesFragment.newInstance())
                fabAdd.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_all -> {
                replaceFragment(AllTablesFragment.newInstance())
                fabAdd.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.navigation_my
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_tables, fragment)
                .commit()
    }

}

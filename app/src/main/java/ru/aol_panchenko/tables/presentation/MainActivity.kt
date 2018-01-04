package ru.aol_panchenko.tables.presentation

import android.os.Bundle
import android.support.annotation.IdRes
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

    private val FRAGMENT_ID = "fragment_id"
    @IdRes private var _navId = R.id.navigation_my

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_my -> {
                replaceFragment(MyTablesFragment.newInstance())
                fabAdd.visibility = View.VISIBLE
                _navId = R.id.navigation_my
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_downloaded -> {
                replaceFragment(DownloadTablesFragment.newInstance())
                fabAdd.visibility = View.GONE
                _navId = R.id.navigation_downloaded
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_all -> {
                replaceFragment(AllTablesFragment.newInstance())
                fabAdd.visibility = View.GONE
                _navId = R.id.navigation_all
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = ""
        if (savedInstanceState != null) {
            _navId = savedInstanceState.getInt(FRAGMENT_ID)
        }
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = _navId
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_tables, fragment)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FRAGMENT_ID, _navId)
    }
}

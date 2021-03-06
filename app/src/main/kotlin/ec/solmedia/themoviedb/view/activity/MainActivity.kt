package ec.solmedia.themoviedb.view.activity

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ec.solmedia.themoviedb.R
import ec.solmedia.themoviedb.VimoApp
import ec.solmedia.themoviedb.commons.extensions.consume
import ec.solmedia.themoviedb.di.module.AdapterModule
import ec.solmedia.themoviedb.di.qualifier.movie.MovieQualifier
import ec.solmedia.themoviedb.di.qualifier.tv.TvQualifier
import ec.solmedia.themoviedb.view.fragment.adapter.MediaPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @field:[Inject TvQualifier]
    lateinit var tvAdapter: MediaPagerAdapter

    @field:[Inject MovieQualifier]
    lateinit var movieAdapter: MediaPagerAdapter

    lateinit private var drawerToggle: ActionBarDrawerToggle
    private var menuItemId: Int = 0

    companion object {
        val KEY_MENU_ITEM = "MainActivity:menuItem"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarDetail)

        setupInjection()
        setupListeners()
        setupDrawerToggle()
        setupSharedPreferences()

        if (savedInstanceState == null) {
            selectItem(nav_view.menu.findItem(R.id.nav_tvshows))
        } else {
            menuItemId = savedInstanceState.getInt(KEY_MENU_ITEM)
            selectItem(nav_view.menu.findItem(menuItemId))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MENU_ITEM, menuItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        menuItemId = savedInstanceState.getInt(KEY_MENU_ITEM)
    }

    private fun setupViewPagerTvShows() {
        viewPager.adapter = tvAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun setupViewPagerMovies() {
        viewPager.adapter = movieAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun selectItem(item: MenuItem) {
        menuItemId = item.itemId
        when (item.itemId) {
            R.id.nav_tvshows -> drawer_layout.consume {
                setupViewPagerTvShows()
            }
            R.id.nav_movies -> drawer_layout.consume {
                setupViewPagerMovies()
            }
            R.id.nav_logout -> drawer_layout.consume { } //TODO logout
            else -> drawer_layout.consume {
                setupViewPagerTvShows()
            }
        }
    }

    private fun setupInjection() {
        VimoApp.graph.plus(AdapterModule(this, this)).inject(this)
    }

    private fun setupListeners() {
        nav_view.setNavigationItemSelectedListener({
            selectItem(it)
            title = it.title
            true
        })
    }

    private fun setupDrawerToggle() {
        drawerToggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbarDetail,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawerToggle)
    }

    private fun setupSharedPreferences() {
        if (!sharedPreferences.contains(VimoApp.LOCALE_KEY)) {
            sharedPreferences.edit().putString(VimoApp.LOCALE_KEY, Locale.getDefault().language).apply()
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}

package dyachenko.kotlinmaterialdesign03.view.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.util.addFragmentWithBackStack
import dyachenko.kotlinmaterialdesign03.view.settings.SettingsFragment
import me.relex.circleindicator.CircleIndicator


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readSettings()
        setCurrentTheme()

        setContentView(R.layout.activity_main)

        initViewPager()
    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(supportFragmentManager)
        adapter.setStringResources { i: Int -> getString(i) }

        val viewPager = findViewById<ViewPager>(R.id.main_view_pager)
        viewPager.adapter = adapter

        val indicator = findViewById<CircleIndicator>(R.id.main_indicator)
        indicator.setViewPager(viewPager)
    }

    private fun readSettings() {
        val sharedPreferences =
            getSharedPreferences(SettingsData.PREFERENCE_NAME, Context.MODE_PRIVATE)

        SettingsData.currentTheme = sharedPreferences
            .getInt(SettingsData.CURRENT_THEME_PREF_NAME, SettingsData.THEME_PURPLE)

        SettingsData.earthLon =
            sharedPreferences.getFloat(SettingsData.CURRENT_EARTH_LON, SettingsData.EARTH_DEF_LON)
        SettingsData.earthLat =
            sharedPreferences.getFloat(SettingsData.CURRENT_EARTH_LAT, SettingsData.EARTH_DEF_LAT)
        SettingsData.earthDim =
            sharedPreferences.getFloat(SettingsData.CURRENT_EARTH_DIM, SettingsData.EARTH_DEF_DIM)
    }

    private fun setCurrentTheme() {
        if (SettingsData.currentTheme == SettingsData.THEME_TEAL) {
            setTheme(SettingsData.THEME_TEAL_ID)
        } else {
            setTheme(SettingsData.THEME_PURPLE_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (doAction(item.itemId)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun doAction(id: Int): Boolean {
        when (id) {
            R.id.settings_action -> {
                supportFragmentManager.addFragmentWithBackStack(SettingsFragment())
                return true
            }
        }
        return false
    }
}
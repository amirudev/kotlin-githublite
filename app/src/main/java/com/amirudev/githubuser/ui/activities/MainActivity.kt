package com.amirudev.githubuser.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.amirudev.githubuser.R
import com.amirudev.githubuser.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
            R.string.favorites,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()

                Intent(this@MainActivity, SearchActivity::class.java).also {
                    it.putExtra(SearchActivity.EXTRA_USERNAME, query)
                    startActivity(it)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}
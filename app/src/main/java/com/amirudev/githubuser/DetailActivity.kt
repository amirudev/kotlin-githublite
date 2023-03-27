package com.amirudev.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private var isLoading: Boolean = false
    private var progressBar: ProgressBar? = null
    private var userResponse = UserResponse()
    private var username: String = ""

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.profile,
            R.string.follower,
            R.string.following,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        progressBar = findViewById(R.id.progress_bar)

        val intent = intent
        username = intent.getStringExtra("EXTRA_USERNAME").toString()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = username

        getUserData(username)

        val sectionsPagerAdapter = SectionsPagerAdapterDetail(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getUserData(username: String): UserResponse {
        toggleLoading(true)
        GlobalScope.launch {
            try {
                val response = ApiConfig.getApiService().getUserDetail(username).execute()
                if (response.isSuccessful) {
                    userResponse = response.body() ?: UserResponse()
                    withContext(Dispatchers.Main) {
                        showUserProfileData()
                        toggleLoading(false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    toggleLoading(false)
                    Toast.makeText(this@DetailActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        return UserResponse()
    }

    private fun showUserProfileData() {
        val user = userResponse
        val tvName = findViewById<android.widget.TextView>(R.id.tv_profile_name)
        val tvUsername = findViewById<android.widget.TextView>(R.id.tv_profile_username)
        val tvLocation = findViewById<android.widget.TextView>(R.id.tv_profile_location)
        val tvProfileFollowers = findViewById<android.widget.TextView>(R.id.tv_profile_followers)
        val tvProfileFollowing = findViewById<android.widget.TextView>(R.id.tv_profile_following)
        val tvProfileBio = findViewById<android.widget.TextView>(R.id.tv_profile_bio)
        val ivAvatar = findViewById<android.widget.ImageView>(R.id.img_profile_image)

        tvName.text = user.name
        tvUsername.text = user.login
        tvLocation.text = user.location
        tvProfileFollowers.text = user.followers.toString()
        tvProfileFollowing.text = user.following.toString()
        tvProfileBio.text = user.bio
        Glide.with(this)
            .load(user.avatarUrl)
            .into(ivAvatar)
    }

    private fun toggleLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (progressBar == null) return

        if (isLoading) {
            progressBar!!.visibility = android.view.View.VISIBLE
        } else {
            progressBar!!.visibility = android.view.View.GONE
        }
    }
}
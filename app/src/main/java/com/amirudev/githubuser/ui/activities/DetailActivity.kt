package com.amirudev.githubuser.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.amirudev.githubuser.R
import com.amirudev.githubuser.SectionsPagerAdapterDetail
import com.amirudev.githubuser.UserResponse
import com.amirudev.githubuser.databinding.ActivityDetailBinding
import com.amirudev.githubuser.databinding.ActivitySearchBinding
import com.amirudev.githubuser.network.ApiConfig
import com.amirudev.githubuser.ui.fragments.DetailProfileFragment
import com.amirudev.githubuser.viewmodels.DetailViewModel
import com.amirudev.githubuser.viewmodels.SearchViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
//    private var isLoading: Boolean = false
//    private var progressBar: ProgressBar? = null
//    private var userResponse = UserResponse()
    private lateinit var binding: ActivityDetailBinding
    private val fragmentManager: FragmentManager = supportFragmentManager

    private var username: String = ""

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: "amirudev"

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = username

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root

        val sectionsPagerAdapter = SectionsPagerAdapterDetail(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

//        Add DetailProfileFragment to this and fill its xml id by value

//        val fragmentManager =
//            fragmentManager.findFragmentById(R.id.fg_detail) as DetailProfileFragment
//
//        val profileFollowerTarget =
//            fragmentManager.view?.findViewById(R.id.tv_profile_followers) as TextView
//        val profileFollowingTarget =
//            fragmentManager.view?.findViewById(R.id.tv_profile_following) as TextView
//        val profileBioTarget =
//            fragmentManager.view?.findViewById(R.id.tv_profile_bio) as TextView

        viewModel.user.observe(this) { u ->
            if (u != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(u.avatarUrl)
                        .into(imgProfileImage)
                    binding.tvProfileName.text = u.name
                    tvProfileUsername.text = u.login
                    tvProfileLocation.text = u.location
//                    profileFollowerTarget.text = u.followers.toString()
//                    profileFollowingTarget.text = u.following.toString()
//                    profileBioTarget.text = u.publicRepos.toString()
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.isError.observe(this) { isError ->
            if (isError) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getUserFromApi(username)
    }
        override fun onSupportNavigateUp(): Boolean {
            finish()
            return true
        }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.profile,
            R.string.follower,
            R.string.following,
        )

        const val EXTRA_USERNAME = "extra_username"
            const val EXTRA_FOLLOWER = "extra_username"
    }
}
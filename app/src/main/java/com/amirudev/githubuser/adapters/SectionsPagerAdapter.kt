package com.amirudev.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amirudev.githubuser.ui.fragments.DetailProfileFragment
import com.amirudev.githubuser.ui.fragments.FollowersFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, "amirudev")
                    putInt(EXTRA_FOLLOWERS_TYPE, TYPE_FOLLOWERS[0])
                }
            }
            1 -> fragment = FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, "amirudev")
                    putInt(EXTRA_FOLLOWERS_TYPE, TYPE_FOLLOWERS[1])
                }
            }
        }

        return fragment as Fragment
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLLOWERS_TYPE = "extra_followers_type"

        private val TYPE_FOLLOWERS = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}

class SectionsPagerAdapterDetail(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DetailProfileFragment()
            1 -> fragment = FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                    putInt(EXTRA_FOLLOWERS_TYPE, TYPE_FOLLOWERS[0])
                }
            }
            2 -> fragment = FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                    putInt(EXTRA_FOLLOWERS_TYPE, TYPE_FOLLOWERS[1])
                }
            }
        }

        return fragment as Fragment
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLLOWERS_TYPE = "extra_followers_type"

        private val TYPE_FOLLOWERS = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}
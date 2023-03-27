package com.amirudev.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString("EXTRA_USERNAME", "amirudev")
                }
            }
            1 -> fragment = FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("EXTRA_USERNAME", "amirudev")
                }
            }
        }

        return fragment as Fragment
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
            1 -> fragment = FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString("EXTRA_USERNAME", username)
                }
            }
            2 -> fragment = FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("EXTRA_USERNAME", username)
                }
            }
        }

        return fragment as Fragment
    }
}
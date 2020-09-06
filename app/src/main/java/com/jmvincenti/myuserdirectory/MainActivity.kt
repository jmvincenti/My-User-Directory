package com.jmvincenti.myuserdirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jmvincenti.myuserdirectory.feature.userlist.ui.UserListFragment
import com.jmvincenti.myuserdirectory.feature.userprofile.UserProfileFragment
import com.jmvincenti.myuserdirectory.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val fragment = UserListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()

            currentFragment = fragment
        }
    }

    fun onUserSelected(user: User) {
        val fragment = UserProfileFragment.newInstance(user.id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()

        currentFragment = fragment
    }

    override fun onBackPressed() {
        if (currentFragment is UserProfileFragment) {
            val fragment = UserListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()

            currentFragment = fragment

        } else {
            super.onBackPressed()
        }
    }
}

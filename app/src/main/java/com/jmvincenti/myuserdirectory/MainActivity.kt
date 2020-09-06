package com.jmvincenti.myuserdirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmvincenti.myuserdirectory.feature.userprofile.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    UserProfileFragment.newInstance("felix.harris@example.com")
                )
                .commitNow()
        }
    }
}

package com.alfonsus.explorelearn.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.KelasFragmentBinding
import com.alfonsus.explorelearn.home.Kelas.*
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav : NavigationBarView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.beranda -> {
                    changeFragment(BerandaFragment())
                    true
                }
                R.id.kelas -> {
                    changeFragment(KelasFragment())
                    true
                }
//                R.id.tugas -> {
//                    changeFragment(TugasFragment())
//                    true
//                }
//                R.id.profile -> {
//
//                    changeFragment(ProfileFragment())
//                    true
//                }
                else->false

            }
        }

    }
    fun changeFragment(fragment : Fragment?)
    {
        if(fragment != null)
        {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_home, fragment)
                .commit()
        }
    }
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount == 1) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }
}
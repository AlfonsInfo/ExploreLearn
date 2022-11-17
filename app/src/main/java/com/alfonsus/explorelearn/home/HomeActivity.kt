package com.alfonsus.explorelearn.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alfonsus.explorelearn.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount == 1) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }
}
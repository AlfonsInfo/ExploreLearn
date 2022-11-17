package com.alfonsus.explorelearn.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    //Binding XML
    private lateinit var binding : ActivityAuthBinding

    //Lifecycle onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =   ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setFragment(LoginFragment())

    }


//
    fun setFragment(fragment :Fragment?)
    {
        if(fragment != null)
        {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_auth, fragment) // binding ga nemu
                .commit()

            //                .addToBackStack(null) -> tidak perlu di activity pertama
        }
    }
}
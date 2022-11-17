package com.alfonsus.explorelearn.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.FragmentLoginBinding
import com.alfonsus.explorelearn.home.HomeActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Saat activity di inisiasi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Saat fragment di inisiasi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container,false)
        val view = binding.root
        return view

        //unreachable code -> codenya tidak bakal bejalan sampai disiini kalo dibikin button listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Info : " , "Sekarang kita di OnViewCreated Login Fragment")
        val Register = binding.btnRegister
        val Login = binding.btnLogin
        val ClearText = binding.btnClear
        val moveHome = Intent(requireContext(),HomeActivity::class.java)
        checkBundle(this.arguments)
        Register.setOnClickListener {
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_auth, RegisFragment()) // binding ga nemu
                .addToBackStack("Login") // agar kembali ke fragment sebelumnya
                .commit()
        }

        Login.setOnClickListener {
            startActivity(moveHome)
        }

    }


    //Check apakah ada bundle yang telah terdaftar melalui register
    private fun checkBundle(savedInstanceState: Bundle?)
    {
        if( savedInstanceState != null) {
            binding.loginUsername.setText(arguments?.getString("username"))
            binding.loginPassword.setText(arguments?.getString("password"))
        }
        Log.d("test", this.arguments.toString())
    }


}
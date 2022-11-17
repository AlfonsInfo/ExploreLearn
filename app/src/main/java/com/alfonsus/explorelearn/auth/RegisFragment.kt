package com.alfonsus.explorelearn.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.FragmentLoginBinding
import com.alfonsus.explorelearn.databinding.FragmentRegisBinding


class RegisFragment : Fragment() {
    private var _binding: FragmentRegisBinding? = null
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
        _binding = FragmentRegisBinding.inflate(inflater, container,false)
        val view = binding.root
        return view

        //unreachable code -> codenya tidak bakal bejalan sampai disiini kalo dibikin button listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val RegisBtn =binding.btnRegisterAkun
        val inputEmail = binding.regisEmail
        val inputUsername = binding.regisUsername
        val inputPassword = binding.regisPass
        val inputTgl = binding.regisTgl
        val inputTelp = binding.regisTelp
        var backToLogin = Intent()


        RegisBtn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("username" , inputUsername.getText().toString())
            bundle.putString("password" , inputPassword.getText().toString())
            val loginFragment = LoginFragment()
            loginFragment.setArguments(bundle)
            requireActivity().getSupportFragmentManager().popBackStack() // dikeluarkan satu fragment kemudian di replace dengan fragment lainnya
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_auth,loginFragment)
//                    .addToBackStack(false)  //-> kalo dipake ini kembali ke regis
                    .commit() // masalahnya sekarang double Fragment di layar yang sama


        }
    }

}

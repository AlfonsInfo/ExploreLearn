package com.alfonsus.explorelearn.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
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
    //Shared Preferences
    private  lateinit var sf:SharedPreferences
    private  lateinit var editorSf :SharedPreferences.Editor

    //Saat activity di inisiasi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sf = requireActivity().getSharedPreferences("RiwayatRegis", Context.MODE_PRIVATE)
        editorSf = sf.edit()
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
            //Bundle :
            val bundle = Bundle()
            bundle.putString("username" , inputUsername.getText().toString())
            bundle.putString("password" , inputPassword.getText().toString())

            //Set Bundle ke fragment login
            val loginFragment = LoginFragment()
            loginFragment.setArguments(bundle)
            //Atur agar fragmentnya sesuai ( keluarkan 1 fragment,  replace 1 lagi)
            requireActivity().getSupportFragmentManager().popBackStack()
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_auth,loginFragment)
                    .commit()


        }
    }

    //Saat Program di pause / sebelum di destroy. data-data di form akan tersimpan
    override fun onPause() {
        super.onPause()

        with(editorSf)
        {
            putString("sf_username",binding.regisUsername.text.toString())
            putString("sf_password",binding.regisPass.text.toString())
            putString("sf_email",binding.regisEmail.text.toString())
            putString("sf_tgl",binding.regisTgl.text.toString())
            putString("sf_telp",binding.regisTelp.text.toString())
            apply()
        }
    }

    //Saat program diresume /dijalankan ulang Data di SharedPreferences akan dicek kembali
    override fun onResume() {
        super.onResume()
        val username = sf.getString("sf_username",null)
        //val password = sf.getString("sf_password",null)
        val email = sf.getString("sf_email",null)
        val no_telp = sf.getString("sf_telp",null)
        val tgl =sf.getString("sf_tgl",null)

        binding.regisUsername.setText(username)
        binding.regisEmail.setText(email)
        binding.regisTgl.setText(tgl)
        binding.regisTelp.setText(no_telp)

    }



}

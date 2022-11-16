package com.alfonsus.explorelearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfonsus.explorelearn.databinding.FragmentLoginBinding

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
        binding.btnRegister.setOnClickListener {
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_auth, RegisFragment()) // binding ga nemu
                .addToBackStack(null) // agar kembali ke fragment sebelumnya
                .commit()
        }
    }


}
package com.alfonsus.explorelearn.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {
    private var _binding : FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreate.setOnClickListener {
            getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_home, ToDoFragment())
                .addToBackStack(null)
                .commit()

        }
    }

}
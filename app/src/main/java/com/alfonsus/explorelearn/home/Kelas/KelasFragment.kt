package com.alfonsus.explorelearn.home.Kelas


import android.util.Log
import android.view.WindowManager
import androidx.viewbinding.ViewBindings
import com.alfonsus.explorelearn.databinding.FragmentBerandaBinding
import com.android.volley.Request
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.KelasFragmentBinding
import com.alfonsus.explorelearn.home.Kelas.KelasAdapter
import com.alfonsus.explorelearn.home.Kelas.KelasApi
import com.alfonsus.explorelearn.home.Kelas.Kelas
import com.alfonsus.explorelearn.home.Todo.ResponseDataTodo
import com.alfonsus.explorelearn.home.Todo.Todo
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class KelasFragment : Fragment() {
    private var _binding : KelasFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: KelasAdapter? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = KelasFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutLoading = requireView().findViewById(R.id.layout_loading)
        queue = Volley.newRequestQueue(requireActivity())
        binding.srTodo.setOnRefreshListener ( SwipeRefreshLayout.OnRefreshListener{ allKelas() })
        binding.svTodo.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        adapter = KelasAdapter(ArrayList(),requireContext() , this@KelasFragment)
        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = adapter
        allKelas()
    }

    private fun allKelas(){
        binding.srTodo!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Request.Method.GET, KelasApi.GET_ALL_URL, Response.Listener { response ->
                Log.d("responsee", response)
                val gson = Gson()
//                var kelas : Array<Kelas> = gson.fromJson(response, Array<Kelas>::class.java)
                var kelas : Array<Kelas> = gson.fromJson(response, ResponseKelas::class.java).data.toTypedArray()

                adapter!!.setkelasList(kelas)
                adapter!!.filter.filter(binding.svTodo!!.query)
                binding.srTodo!!.isRefreshing = false


                if(!kelas.isEmpty())
                    Toast.makeText(requireContext(), "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(requireContext(), "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()

            }, Response.ErrorListener { error ->
                Log.d("responseror", error.toString())
                binding.srTodo!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        Log.d("Queue Test", queue.toString())
        Log.d("Link Api", queue.toString())
        queue!!.add(stringRequest)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                allKelas()
            }
        }
    }

}
package com.alfonsus.explorelearn.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBindings
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.FragmentBerandaBinding
import com.alfonsus.explorelearn.databinding.FragmentToDoBinding
import com.alfonsus.explorelearn.home.Todo.ResponseDataTodo
import com.alfonsus.explorelearn.home.Todo.Todo
import com.alfonsus.explorelearn.home.Todo.TodoAdapter
import com.alfonsus.explorelearn.home.Todo.TodoApi
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ToDoFragment : Fragment() {
    private var _binding : FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private var adapter: TodoAdapter? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

    companion object{
        val LAUNCH_ADD_ACTIVITY = 123
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToDoBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutLoading = requireView().findViewById(R.id.layout_loading)
        queue = Volley.newRequestQueue(requireActivity())
        binding.srTodo.setOnRefreshListener ( SwipeRefreshLayout.OnRefreshListener{ allTodo() })
        binding.svTodo.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        binding.fabAdd.setOnClickListener{
            val i = Intent(requireActivity(), AddEditActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        adapter = TodoAdapter(ArrayList(),requireContext() , this@ToDoFragment)
        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = adapter
        allTodo()
    }

    private fun allTodo() {
        binding.srTodo!!.isRefreshing = true
        //val url = "https://elearning-pbp.herokuapp.com/todolists"
        val stringRequest: StringRequest = object :
            StringRequest(Request.Method.GET, TodoApi.GET_ALL_URL, Response.Listener { response ->
                Log.d("responsee", response)
                val gson = Gson()
                var todo : Array<Todo> = gson.fromJson(response, ResponseDataTodo::class.java).data.toTypedArray()
                Log.d("todonyawoiii",todo[0].judul)
                Log.d("gson", gson.toString())
                Log.d("ArrayHasil", todo.toString())
                Log.d("todo", todo[0].judul)
                Log.d("responsenya", response.toString())
                adapter!!.setTodoList(todo)
                adapter!!.filter.filter(binding.svTodo!!.query)
                binding.srTodo!!.isRefreshing = false

                if(!todo.isEmpty())
                    Toast.makeText(requireContext(), "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(requireContext(), "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                Log.d("responseror", error.toString())
                binding.srTodo!!.isRefreshing = false
                try{
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

//        Log.d("Hasil Queue",queue.toString())
        Log.d("Queue Test", queue.toString())
        Log.d("Link Api", queue.toString())
        queue!!.add(stringRequest)
    }

    public fun deleteTodo(id : Long)
    {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TodoApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var mahasiswa = gson.fromJson(response, TodoApi::class.java)
                if(mahasiswa != null)
                    Toast.makeText(requireContext(), "Data Berhasil Dihapus!", Toast.LENGTH_SHORT).show()
                allTodo()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers ["Accept"] = "application/json"
                return headers
            }
        }
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
        if(requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK) allTodo()
    }

}
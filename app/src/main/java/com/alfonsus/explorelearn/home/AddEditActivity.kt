package com.alfonsus.explorelearn.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.databinding.ActivityAddEditBinding
import com.alfonsus.explorelearn.databinding.ActivityAuthBinding
import com.alfonsus.explorelearn.home.Todo.ResponseCreateTodo
import com.alfonsus.explorelearn.home.Todo.Todo
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
import java.time.LocalDate

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddEditBinding
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)


        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)

        val id = intent.getLongExtra("id", -1)
        if(id==-1L)
        {
            binding.tvJudulAddedit.setText("Tambah Todo")
            binding.btnSave.setOnClickListener{ createTodo()}
        }else
        {
            binding.tvJudulAddedit.setText("Edit Todo")
            getTodoById(id)
            binding.btnSave.setOnClickListener { updateTodo(id) }
        }
    }

    private fun createTodo()
    {
        setLoading(true)
        val current = LocalDate.now().toString()
        var judul = binding.etTodo.getText().toString()
        var pesan = binding.etPesan.getText().toString()
        var deadline = binding.etDeadline.getText().toString()
        val todoo = Todo( judul, pesan, "2022/05/05", "2022/05/05",0)
        Log.d("initodo",todoo.pesan + todoo.tglDeadline + current)
        val stringRequest: StringRequest =
            object : StringRequest(Request.Method.POST, TodoApi.ADD_URL, Response.Listener { response ->
                Log.d("iniresponse",response)
                val gson = Gson()
                var todoo = gson.fromJson(response, Todo::class.java)

                if(todoo != null)
                    Toast.makeText(this@AddEditActivity, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                Log.d("responseror",error.toString())
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(todoo)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        // Menambahkan request ke request queue
        queue!!.add(stringRequest)
    }

    private fun updateTodo(id : Long) {
        val current = LocalDate.now()
        val Todo = Todo(
            binding.etTodo.toString(),
            binding.etPesan.toString(),
            current.toString(),
            binding.etDeadline.toString(),
            0
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, TodoApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var todo = gson.fromJson(response, Todo::class.java)

                if (todo != null)
                    Toast.makeText(
                        this@AddEditActivity,
                        "Data berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(Todo)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
    private fun getTodoById(id : Long)
    {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TodoApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val todoo = gson.fromJson(response, Todo::class.java)

                binding.etTodo!!.setText(todoo.judul)
                binding.etPesan.setText(todoo.pesan)
                binding.etDeadline!!.setText(todoo.tglDeadline)


                Toast.makeText(this@AddEditActivity, "Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }


    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }
}
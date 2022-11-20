package com.alfonsus.explorelearn.home.Todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.home.AddEditActivity
import com.alfonsus.explorelearn.home.HomeActivity
import com.alfonsus.explorelearn.home.ToDoFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter(private var todoList : List<Todo> , context: Context , fragment: Fragment) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>(),Filterable{
    private var filteredTodoList : MutableList<Todo>
    private val context : Context
    private val fragment : Fragment

    init {
        filteredTodoList = ArrayList(todoList)
        this.context = context
        this.fragment = fragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            var tvJudul : TextView
            var tvPesan : TextView
            var tglDibuat : TextView
            var tglDeadline : TextView
            var status : TextView
            var cvTodo : CardView
            var btnDelete : ImageButton

            init{
                tvJudul = itemView.findViewById(R.id.tv_todo)
                tvPesan = itemView.findViewById(R.id.tv_pesan)
                tglDibuat = itemView.findViewById(R.id.tv_tgldibuat)
                tglDeadline = itemView.findViewById(R.id.tv_tgldeadline)
                status = itemView.findViewById(R.id.tv_status)
                cvTodo = itemView.findViewById(R.id.cv_todo)
                btnDelete = itemView.findViewById(R.id.btn_delete)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo,parent,false)
        return ViewHolder(view)
    }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val todolist = filteredTodoList[position]
            holder.tvJudul.text = todolist.judul
            holder.tvPesan.text = todolist.pesan
            holder.tglDibuat.text = todolist.tglDibuat.toString()
            holder.tglDeadline.text = todolist.tglDeadline.toString()
            holder.status.text = todolist.status.toString()

            holder.btnDelete.setOnClickListener{
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menghapus data todo ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Yakin"){_, _ ->
                        if(fragment is ToDoFragment) todolist.id?.let {
                            it1->fragment.deleteTodo(
                            it1
                            )
                        }
                    }.show()
            }

            holder.cvTodo.setOnClickListener{
                val i = Intent(context, AddEditActivity::class.java)
                i.putExtra("id", todolist.id)
                if(context is HomeActivity)
                    context.startActivityForResult(i, ToDoFragment.LAUNCH_ADD_ACTIVITY)
            }
        }

        override fun getItemCount(): Int {
            return filteredTodoList.size
        }

        fun setTodoList(todoList: Array<Todo>)
        {
            this.todoList = todoList.toList()
            filteredTodoList = todoList.toMutableList()
        }

        override fun getFilter(): Filter {
            return object : Filter()
            {
                override fun performFiltering(charSequence: CharSequence?): FilterResults {
                    val charSequenceString = charSequence.toString()
                    val filtered: MutableList<Todo> = java.util.ArrayList()
                    if(charSequenceString.isEmpty())
                    {
                        filtered.addAll(todoList)
                    }else
                    {
                        for(todo in todoList)
                        {
                            if(todo.judul.lowercase(Locale.getDefault())
                                    .contains(charSequenceString.lowercase(Locale.getDefault()))
                            )filtered.add(todo)
                        }
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filtered
                    return filterResults
                }

                override fun publishResults(p0: CharSequence, p1: FilterResults) {
                    filteredTodoList.clear()
                    filteredTodoList.addAll(p1.values as List<Todo>)
                    notifyDataSetChanged()
                }
            }
        }


}
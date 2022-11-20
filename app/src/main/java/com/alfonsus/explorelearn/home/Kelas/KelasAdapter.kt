package com.alfonsus.explorelearn.home.Kelas


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alfonsus.explorelearn.R
import com.alfonsus.explorelearn.home.Todo.Todo
import java.util.*

class KelasAdapter(private var kelasList: List<Kelas>, context: Context , fragment: Fragment) :
    RecyclerView.Adapter<KelasAdapter.ViewHolder>(), Filterable {

    private var filterkelasList: MutableList<Kelas>
    private val context: Context
    private val fragment : Fragment

    init {
        filterkelasList = ArrayList(kelasList)
        this.context = context
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item_kelas, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterkelasList.size
    }

    fun setkelasList(kelasList: Array<Kelas>) {
        this.kelasList = kelasList.toList()
        filterkelasList = kelasList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Kelas = filterkelasList[position]
        holder.tvNamaMatpel.text = Kelas.mataPelajaran
        holder.tvSesiKelas.text = Kelas.sesiKelas
        holder.tvThnAjaran.text = Kelas.tahunAjaran
        holder.tvGuru.text = Kelas.guruPengajar

//        holder.btnDelete.setOnClickListener {
//            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
//            materialAlertDialogBuilder.setTitle("Konfirmasi")
//                .setMessage("Apakah anda yakin ingin menghapus Kelas ini?")
//                .setNegativeButton("Batal", null)
//                .setPositiveButton("Hapus"){_,_ ->
//                    if (context is MainActivity) Kelas.id?.let { it1 ->
//                        context.deleteKelas(
//                            it1
//                        )
//                    }
//                }
//                .show()
//
//        }

        //Kalau ingin nanti buat detail kelas
//        holder.cvKelas.setOnClickListener {
//            val i = Intent(context, AddEditActivity::class.java)
//            i.putExtra("id", Kelas.id)
//            if(context is MainActivity)
//                context.startActivityForResult(i, MainActivity.LAUNCH_ADD_ACTIVITY)
//        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Kelas> = java.util.ArrayList()
                if (charSequenceString.isEmpty()){
                    filtered.addAll(kelasList)
                } else {
                    for (Kelas in kelasList) {
                        if (Kelas.mataPelajaran.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(Kelas)
                    }
                }
                val filterResult = FilterResults()
                filterResult.values = filtered
                return filterResult
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterkelasList.clear()
                filterkelasList.addAll((filterResults.values as List<Kelas>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNamaMatpel: TextView
        var tvThnAjaran: TextView
        var tvSesiKelas: TextView
        var tvGuru: TextView
        var cvKelas: CardView

        init {
            tvNamaMatpel = itemView.findViewById(R.id.tv_namaMatPel)
            tvThnAjaran = itemView.findViewById(R.id.tv_tahunAjaran)
            tvSesiKelas = itemView.findViewById(R.id.tv_sesiKelas)
            tvGuru = itemView.findViewById(R.id.tv_guru)
            cvKelas = itemView.findViewById(R.id.cv_kelas)
        }
    }
}
package com.fiap.viniciusoliveira_93136.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fiap.viniciusoliveira_93136.R
import com.fiap.viniciusoliveira_93136.model.Dica

class DicaAdapter(private var listaDicas: List<Dica>) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    inner class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tv_titulo)
        val tvDescricao: TextView = itemView.findViewById(R.id.tv_descricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = listaDicas[position]
        holder.tvTitulo.text = dica.titulo
        holder.tvDescricao.text = dica.descricao

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Mais detalhes sobre: ${dica.titulo}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listaDicas.size

    fun filterList(filteredList: List<Dica>) {
        listaDicas = filteredList
        notifyDataSetChanged()
    }
}
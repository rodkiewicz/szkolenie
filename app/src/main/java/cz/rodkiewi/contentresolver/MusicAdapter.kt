package cz.rodkiewi.contentresolver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class MusicAdapter(var list: List<Pair<String,String>> = ArrayList(), val onClick: OnClickItem) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    interface OnClickItem{
        fun onClick(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.music, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val link = list.get(position)
        holder.name.text = link.first
        holder.url.text = link.second
        holder.view.setOnClickListener {
            onClick.onClick(link.second)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.titleTV)
        val url: TextView = itemView.findViewById(R.id.urlTV)
        val view = itemView
    }
}
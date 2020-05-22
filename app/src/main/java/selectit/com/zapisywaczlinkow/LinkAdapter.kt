package selectit.com.zapisywaczlinkow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class LinkAdapter(var list: List<Link> = ArrayList(), val onClick: OnClickItem) : RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

    interface OnClickItem{
        fun onClickDodajDoUlubionych(position: Int)
        fun onClickOtworzPrzeglardarke(link: String)
        fun onClickPodglad(position: Int)
        fun onClickUsun(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.link, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val link = list.get(position)
        holder.name.text = link.link
        if(link.czyUlubione){
            holder.image.setImageResource(R.drawable.ic_favorite_red_24dp)
        }else{
            holder.image.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }
        holder.image.setOnClickListener {
            onClick.onClickDodajDoUlubionych(position)
        }
        holder.widok.setOnClickListener {
            onClick.onClickOtworzPrzeglardarke(link.link)
        }
        holder.widok.setOnLongClickListener {
            onClick.onClickPodglad(position)
            true
        }
        holder.usun.setOnClickListener {
            onClick.onClickUsun(position)
        }



    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView)
        val image: ImageView = itemView.findViewById(R.id.czyUlbione)
        val widok : ConstraintLayout = itemView.findViewById(R.id.widok)
        val usun: ImageView = itemView.findViewById(R.id.usun)
    }
}
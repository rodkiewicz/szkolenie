package selectit.com.pl.mojaapliakcja


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class KolegaAdapter(var lista: MutableList<Kolega>, var onClickItem: OnClickItem): RecyclerView.Adapter<KolegaAdapter.ViewHolder>() {
    interface OnClickItem{
        fun onClickButton1(position: Int)
        fun onClickSeeDetails(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_kolega, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imie.text = lista.get(position).imie
        holder.numerButa.text = lista.get(position).numerButa.toString()
        holder.button.setOnClickListener {
            onClickItem.onClickButton1(position)
        }
        holder.widok.setOnClickListener {
            onClickItem.onClickSeeDetails(position)
        }

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imie: TextView = itemView.findViewById<TextView>(R.id.textView3)
        val numerButa : TextView = itemView.findViewById<TextView>(R.id.textView4)
        val button : Button = itemView.findViewById<Button>(R.id.button2)
        val widok : ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.widok)
    }

}
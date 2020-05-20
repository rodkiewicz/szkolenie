package selectit.com.mojaaplikacjaszkoleniiowa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    lateinit var adapter: KolegaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lista = mutableListOf<Kolega>()
        var bazadanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
        var zapisaniKoledzy = bazadanych.getString("kolegow", "")



        adapter = KolegaAdapter(lista)
        powtarzalnaLista.adapter = adapter
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        powtarzalnaLista.layoutManager = manager



        if(zapisaniKoledzy != ""){
            val turnsType = object : TypeToken<MutableList<Kolega>>() {}.type
            lista = Gson().fromJson(zapisaniKoledzy, turnsType)
            wyswietlKolegow(lista)
        }

        button.setOnClickListener {
            var imie = editText.text.toString()
            var wiek = editText2.text.toString().toInt()
            var kolega = Kolega(imie, wiek)
            if((wiek >= 18) and (wiek <= 40)){
                lista.add(kolega)
                wyswietlKolegow(lista)
                Toast.makeText(this, "Dodano pomyślnie", Toast.LENGTH_SHORT).show()
                bazadanych.edit().putString("kolegow", Gson().toJson(lista)).apply()
                Log.d("szkolenie", Gson().toJson(lista))
            }else{
                Toast.makeText(this, "Nie kwalifikuej sie", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun wyswietlKolegow(nowaLista: MutableList<Kolega>){
        adapter.list = nowaLista
        adapter.notifyDataSetChanged()
    }
}
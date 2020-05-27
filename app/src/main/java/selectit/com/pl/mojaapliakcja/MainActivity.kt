package selectit.com.pl.mojaapliakcja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), KolegaAdapter.OnClickItem {
    lateinit var bazaDanych: SharedPreferences
    lateinit var gson: Gson
    lateinit var adapter: KolegaAdapter
    lateinit var listaKolegow: MutableList<Kolega>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bazaDanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
        gson = Gson()
        listaKolegow = mutableListOf<Kolega>()
        var listaKolegowZBazyDanych = bazaDanych.getString("koledzy", "")

        adapter = KolegaAdapter(listaKolegow, this)
        powtarzalnaLista.layoutManager = LinearLayoutManager(this)
        powtarzalnaLista.adapter = adapter

        if(listaKolegowZBazyDanych != ""){
            val turnsType = object : TypeToken<MutableList<Kolega>>() {}.type
            listaKolegow = gson.fromJson(listaKolegowZBazyDanych, turnsType)
            wyswietlKolegow(listaKolegow)
        }

        button.setOnClickListener {
            var imieZFormularza = editText.text.toString()
            var numerButaZFormularza = editText2.text.toString()
            if(numerButaZFormularza != ""){
                var nowyKolega = Kolega(imieZFormularza, numerButaZFormularza.toInt())
                listaKolegow.add(nowyKolega)
                bazaDanych.edit().putString("koledzy", gson.toJson(listaKolegow)).apply()
                wyswietlKolegow(listaKolegow)
            }else{
                Toast.makeText(this, "nie podales numeru buta", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun wyswietlKolegow(listaKolegow : MutableList<Kolega>){
        adapter.lista = listaKolegow
        adapter.notifyDataSetChanged()
    }

    override fun onClickButton1(position: Int) {
        listaKolegow.removeAt(position)
        wyswietlKolegow(listaKolegow)
        bazaDanych.edit().putString("koledzy", gson.toJson(listaKolegow)).apply()
    }

    override fun onClickSeeDetails(position: Int) {
        val intent = Intent()
        var kolega = listaKolegow.get(position)
        intent.putExtra("imie", kolega.imie)
        intent.putExtra("numerbuta", kolega.numerButa)
        startActivity(intent)
    }

}

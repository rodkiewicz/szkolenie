package selectit.com.mojaaplikacjaszkoleniiowa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lista = mutableListOf<Kolega>()
        val bazadanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
        var zapisaniKoledzy = bazadanych.getString("kolegow", "")

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
                val bazadanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
                bazadanych.edit().putString("kolegow", Gson().toJson(lista)).apply()
                Log.d("szkolenie", Gson().toJson(lista))
            }else{
                Toast.makeText(this, "Nie kwalifikuej sie", Toast.LENGTH_SHORT).show()
            }

        }




    }

    fun wyswietlKolegow(list: MutableList<Kolega>){
        var tekst = ""
        list.forEach {
            tekst += it.name + " = " + it.age + "\n"
        }
        textView4.text = tekst
    }
}

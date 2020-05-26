package selectit.com.pl.mojaaplikacjaszkolenia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_drugie.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var WYBIERZ_OBRAZEK = 1

    //Rejestracja
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bazadanych = getSharedPreferences("imiona", Context.MODE_PRIVATE)
        var zapisaneImie = bazadanych.getString("imie", "Karol")
        var imie = ""
        var numerbnuta = 0

        przycisk_drugi.setOnClickListener {
            var intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, WYBIERZ_OBRAZEK)

//            var imieZFormularza = formularz.text.toString()
//            var numerButaZFormularza = numerButa.text.toString()
//            if(numerButaZFormularza != ""){
//                numerbnuta = numerButaZFormularza.toInt()
//                bazadanych.edit().putString("imie", imieZFormularza).apply()
//                bazadanych.edit().putInt("numer", numerbnuta).apply()
//
//                Toast.makeText(this, "Dodano uzytkownika", Toast.LENGTH_LONG).show()
//
//                var intent = Intent(this, DrugieActivity::class.java)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this, "podaj numer buta", Toast.LENGTH_LONG).show()
//            }
//

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == WYBIERZ_OBRAZEK){
            if(resultCode == Activity.RESULT_OK){
                imageView.setImageURI(data?.data)
            }
        }
    }
}

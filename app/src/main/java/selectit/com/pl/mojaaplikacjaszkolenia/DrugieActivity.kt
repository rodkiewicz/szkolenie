package selectit.com.pl.mojaaplikacjaszkolenia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_drugie.*

class DrugieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drugie)
        val bazadanych = getSharedPreferences("imiona", Context.MODE_PRIVATE)

        var imieBazodanowe = bazadanych.getString("imie", "")
        var numerButaBazodanowy = bazadanych.getInt("numer", 0)

        przycisk_drugie.setOnClickListener {
            var imieZFormularza = formularz_drugie.text.toString()
            var numerButaZFormularza = numerButa_drugie.text.toString()
            if(numerButaZFormularza != ""){
                if((numerButaBazodanowy == numerButaZFormularza.toInt()) and (imieZFormularza==imieBazodanowe)){
                    Toast.makeText(this, "dane poprawne", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "dane NIE poprawne", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "wprowadz numer buta", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

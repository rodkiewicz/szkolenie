package selectit.com.zapisywaczlinkow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class DodajLinkDoUlubionychActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_link_do_ulubionych)

        var lista = mutableListOf<Link>()
        var bazadanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
        var zapisaneLinki = bazadanych.getString("liniki", "")

        if (zapisaneLinki != "") {
            val turnsType = object : TypeToken<MutableList<Link>>() {}.type
            lista = Gson().fromJson(zapisaneLinki, turnsType)

        }

        if (intent.action == Intent.ACTION_SEND) {
            var linkZPrzegladarki = intent.getStringExtra(Intent.EXTRA_TEXT)
            var link = Link(linkZPrzegladarki, true)
            lista.add(link)
            bazadanych.edit().putString("liniki", Gson().toJson(lista)).apply()
        }

        var intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

    }
}

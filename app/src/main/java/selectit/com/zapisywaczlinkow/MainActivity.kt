package selectit.com.zapisywaczlinkow

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LinkAdapter.OnClickItem {
    lateinit var adapter: LinkAdapter
    lateinit var lista: MutableList<Link>
    lateinit var bazadanych: SharedPreferences
    var czyTylkoUlubioneWyswietlac = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista = mutableListOf<Link>()
        bazadanych = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
        var zapisaneLinki = bazadanych.getString("liniki", "")

        adapter = LinkAdapter(lista, this)
        powtrzalnaLista.adapter = adapter
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        powtrzalnaLista.layoutManager = manager

        if (zapisaneLinki != "") {
            val turnsType = object : TypeToken<MutableList<Link>>() {}.type
            lista = Gson().fromJson(zapisaneLinki, turnsType)
            wyswietlLinki(lista)
        }
        adapter.notifyDataSetChanged()

        if (intent.action == Intent.ACTION_SEND) {
            var linkZPrzegladarki = intent.getStringExtra(Intent.EXTRA_TEXT)
            var link = Link(linkZPrzegladarki, false)
            lista.add(link)
            wyswietlLinki(lista)
            bazadanych.edit().putString("liniki", Gson().toJson(lista)).apply()
        }

    }

    fun filtorawanieListy(list: MutableList<Link>): MutableList<Link> {
        var nowaLista = mutableListOf<Link>()
        if (czyTylkoUlubioneWyswietlac) {
            list.forEach {
                if (it.czyUlubione) {
                    nowaLista.add(it)
                }
            }
            return nowaLista
        } else {
            return list
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ulubione -> {
                czyTylkoUlubioneWyswietlac = !czyTylkoUlubioneWyswietlac
                adapter.list = filtorawanieListy(lista)
                adapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun wyswietlLinki(list: MutableList<Link>) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    override fun onClickDodajDoUlubionych(position: Int) {
        var link = lista[position]
        var nowyLink = Link(link.link, true)
        if (link.czyUlubione) {
            nowyLink.czyUlubione = false
        }
        lista.remove(link)
        lista.add(position, nowyLink)
        Toast.makeText(this, link.link, Toast.LENGTH_SHORT).show()
        wyswietlLinki(lista)
        bazadanych.edit().putString("liniki", Gson().toJson(lista)).apply()
    }

    override fun onClickOtworzPrzeglardarke(link: String) {
        var intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

    override fun onClickPodglad(position: Int) {
        var intent = Intent(this, PodgladActivity::class.java)
        intent.putExtra("link", lista.get(position).link)
        startActivity(intent)
    }

    override fun onClickUsun(position: Int) {
        lista.removeAt(position)
        wyswietlLinki(lista)
        bazadanych.edit().putString("liniki", Gson().toJson(lista)).apply()

    }
}

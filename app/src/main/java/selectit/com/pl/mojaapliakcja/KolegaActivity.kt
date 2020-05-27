package selectit.com.pl.mojaapliakcja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kolega.*

class KolegaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kolega)

        textView5.text = intent.getStringExtra("imie")
        textView6.text = intent.getIntExtra("numerbuta", 0).toString()
    }
}

package selectit.com.pl.zapisywanieobrazka

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {
    private val PROSBA_O_OBRAZEK = 10
    private val NAZWA_OBRAZKA = "obrazek.jpeg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Wybierz obrazek"), PROSBA_O_OBRAZEK)

        }
        var bitmap = load()
        if(bitmap != null){
            imageView.setImageBitmap(bitmap)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PROSBA_O_OBRAZEK){
            imageView.setImageURI(data?.data)
            val bitmap = imageView.drawable.toBitmap()
            save(bitmap)
        }
    }

    fun save(bitmap: Bitmap) {
        var fos: FileOutputStream? = null
        try {
            val image = File(filesDir, NAZWA_OBRAZKA)
            fos = FileOutputStream(image)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            fos.write(stream.toByteArray())
            Toast.makeText(this, "Saved to $filesDir/$NAZWA_OBRAZKA",
                    Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun load(): Bitmap? {
        val image = File(filesDir, NAZWA_OBRAZKA)
        if(image.exists()){
            val bitmap =  BitmapFactory.decodeFile(image.absolutePath)
            return bitmap
        }else{
            return null
        }

    }
}

package cz.rodkiewi.contentresolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mpatric.mp3agic.Mp3File
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MusicAdapter.OnClickItem {
    private val musicList = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: MusicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MusicAdapter(musicList, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        getMusic()
    }

    private fun getMusic() {
        val contentResolver = contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(songUri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val title = it.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val url = it.getColumnIndex(MediaStore.Audio.Media.DATA)
                val mime = it.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)
                do {
                    if (it.getString(mime) == "audio/mpeg") {
                        musicList.add(Pair(it.getString(title), it.getString(url)))
                        adapter.list = musicList
                        adapter.notifyDataSetChanged()
                    }

                } while (it.moveToNext())

            }
        }
    }

    override fun onClick(url: String) {
        val mp3 = Mp3File(url)
        AlertDialog.Builder(this).setTitle(mp3.filename).setMessage(
            mp3.toInfoString()
        ).create().show()
    }
}

private fun Mp3File.toInfoString(): String {
    var output = ""
    output += "bitrate + ${this.bitrate} \n" + "bitrate + ${this.channelMode} \n"
    if (this.hasId3v1Tag()) {
        output +=
            "hasId3v1Tag \n" +
                    "album + ${this.id3v1Tag.album} \n" +
                    "artist + ${this.id3v1Tag.artist} \n" +
                    "comment + ${this.id3v1Tag.comment} \n" +
                    "genre + ${this.id3v1Tag.genre} \n" +
                    "genreDescription + ${this.id3v1Tag.genreDescription} \n" +
                    "title + ${this.id3v1Tag.title} \n" +
                    "version + ${this.id3v1Tag.version} \n" +
                    "year + ${this.id3v1Tag.year} \n"
    }
    if (this.hasId3v2Tag()) {
        output +=
            "hasId3v2Tag \n" +
                    "album + ${this.id3v2Tag.album} \n" +
                    "artist + ${this.id3v2Tag.artist} \n" +
                    "comment + ${this.id3v2Tag.comment} \n" +
                    "genre + ${this.id3v2Tag.genre} \n" +
                    "genreDescription + ${this.id3v2Tag.genreDescription} \n" +
                    "title + ${this.id3v2Tag.title} \n" +
                    "version + ${this.id3v2Tag.version} \n" +
                    "year + ${this.id3v2Tag.year} \n"
    }

    return output

}

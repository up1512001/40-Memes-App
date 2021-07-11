// https://meme-api.herokuapp.com/gimme/20

// inside json memes array there are fields such as
// postLink,subreddit,title,url,nsfw,spoiler,author,ups,preview(array type)
// we will only use title and url fields
//memes: [
//{
//    postLink: "https://redd.it/ohxega",
//    subreddit: "dankmemes",
//    title: "government drones",
//    url: "https://i.redd.it/oqz8ugjzbia71.jpg",
//    nsfw: false,
//    spoiler: false,
//    author: "FunkyMonkeyBlast",
//    ups: 554,
//    preview: [
//    "https://preview.redd.it/oqz8ugjzbia71.jpg?width=108&crop=smart&auto=webp&s=6c35b8876659197033b463d2f3b268270aeabb2c",
//    "https://preview.redd.it/oqz8ugjzbia71.jpg?width=216&crop=smart&auto=webp&s=b9ce49b962d5b7dbcc005717ed1f027acf4c63a0",
//    "https://preview.redd.it/oqz8ugjzbia71.jpg?width=320&crop=smart&auto=webp&s=acedf8e881c71bb15f1f178de8d76ae842a4ab5a",
//    "https://preview.redd.it/oqz8ugjzbia71.jpg?width=640&crop=smart&auto=webp&s=536f1e6203d36c9a75afbbe5c77c070bc0ddb8d8",
//    "https://preview.redd.it/oqz8ugjzbia71.jpg?width=960&crop=smart&auto=webp&s=9dc11544adb528cfd9059bf3e2ca7bf502696203"
//    ]
//},

package com.example.android.memeappusingrecycleview

import android.app.VoiceInteractor
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MemeItemCLicked {

    private lateinit var mAdapter : MemeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = MemeListAdapter(this)
        recycleView.adapter = mAdapter

    }

    private fun fetchData() {
        val url = "https://meme-api.herokuapp.com/gimme/40"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val memesJsonArray = it.getJSONArray("memes")
                val memesArray = ArrayList<Meme>()
                for (i in 0 until memesJsonArray.length()){
                    val memesJsonObject = memesJsonArray.getJSONObject(i)
                    val meme = Meme(
                        memesJsonObject.getString("title"),
                        memesJsonObject.getString("url")
                    )
                    memesArray.add(meme)
                }
                mAdapter.updateMemes(memesArray)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Meme) {
//        Toast.makeText(this,"Item $item Clicked",Toast.LENGTH_SHORT).show()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout new cool meme that I got from Reddit \n${Uri.parse(item.url)}")
        val chooser = Intent.createChooser(intent,"Share this meme Using...")
        startActivity(chooser)
    }

}
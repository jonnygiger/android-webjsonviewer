package com.example.jsonviewer

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var listviewDetails: ListView
    var arraylistDetails:ArrayList<Model> = ArrayList()
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("myTag", "on Create")
        val searchButton: Button = findViewById(R.id.goButton)
        val serverText: EditText = findViewById(R.id.textServer)
        val searchQueryText: EditText = findViewById(R.id.textSearchQuery)
        searchButton.setOnClickListener {
            listviewDetails = findViewById<ListView>(R.id.listView) as ListView
            run("http://" + serverText.text.toString() + "/query?search=" + searchQueryText.text.toString())
        }
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("myTag", e.toString())
                Log.i("myTag", "on Failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val strResponse = response.body.string()
                val jsonArray = JSONArray(strResponse)
                val size = jsonArray.length()
                arraylistDetails = ArrayList()
                for (i in 0 until size) {
                    val model = Model()
                    model.itemid = jsonArray.getJSONObject(i).getString("itemid")
                    Log.i("myTag", model.itemid)
                    model.name = jsonArray.getJSONObject(i).getString("name")
                    Log.i("myTag", model.name)
                    model.active = jsonArray.getJSONObject(i).getString("active")
                    Log.i("myTag", model.active)
                    arraylistDetails.add(model)
                }

                runOnUiThread {
                    val objAdapter : CustomAdapter =
                        CustomAdapter(applicationContext,arraylistDetails)
                    listviewDetails.adapter=objAdapter
                }
            }
        })
    }
}
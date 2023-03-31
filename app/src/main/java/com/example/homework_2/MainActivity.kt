package com.example.homework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var string = StringBuilder()
    private var connection = HttpConnector()
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            string = savedInstanceState.getString("text")?.let { StringBuilder(it) }!!
            findViewById<TextView>(R.id.dataTextView).text = string
        }
    }


    fun loadData(view: View){
        val loadButton = findViewById<Button>(R.id.loadData)
        loadButton.isEnabled = false
        loadButton.alpha = 0.5f
        findViewById<TextView>(R.id.dataTextView).text = ""
        lifecycleScope.launch{
            val result = withContext(Dispatchers.IO){
                connection.loadCardData(page)
            }
            withContext(Dispatchers.Main){
                if (result != null) {
                    if(result.isEmpty()){
                        page = 0
                        connection.loadCardData(page)
                    } else {
                        for (card in result) {
                                    val objectProperties =
                                        "Name: " + card.name + " , " +
                                                "manaCost: " + card.manaCost + " , " +
                                                "cmc: " + card.cmc + " , " +
                                                "colors: " + card.colors + " , " +
                                                "colorIdentity: " + card.colorIdentity + " , " +
                                                "type: " + card.type + " , " +
                                                "types: " + card.types + " , " +
                                                "subtypes: " + card.subtypes + " , " +
                                                "rarity: " + card.rarity + " , " +
                                                "set: " + card.set + " , " +
                                                "setName: " + card.setName + " , " +
                                                "text: " + card.text + " , " +
                                                "artist: " + card.artist + " , " +
                                                "number: " + card.number + " , " +
                                                "power: " + card.power + " , " +
                                                "toughness: " + card.toughness + " , " +
                                                "layout: " + card.layout + " , " +
                                                "multiverseid: " + card.multiverseid + " , " +
                                                "imageUrl: " + card.imageUrl + " , " +
                                                "variations: " + card.variations + " , " +
                                                "foreignNames: " + card.foreignNames + " , " +
                                                "printings: " + card.printings + " , " +
                                                "originalText: " + card.originalText + " , " +
                                                "originalType: " + card.originalType + " , " +
                                                "legalities: " + card.legalities + " , " +
                                                "id: " + card.id + "\n"
                                    string.append(objectProperties)
                                }
                        findViewById<TextView>(R.id.dataTextView).text = string
                        page++
                    }
                } else {
                    findViewById<TextView>(R.id.dataTextView).text = "Error when connecting to Database"
                }
                loadButton.isEnabled = true
                loadButton.alpha = 1.0f
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", string.toString())
        outState.putInt("page", page)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("text")

    }
}
package com.example.homework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var connection = HttpConnector()
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun loadData(view: View){
        !findViewById<Button>(R.id.loadData).isClickable
        !findViewById<Button>(R.id.loadData).isEnabled
        findViewById<TextView>(R.id.dataTextView).text = ""
        lifecycleScope.launch{
            val result = withContext(Dispatchers.IO){
                connection.loadCardData(page)

            }
            withContext(Dispatchers.Main){
                val string = StringBuilder()
                for(card in result) {
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
                                        "id: " + card.id+ "\n"
                    string.append(objectProperties)
                }
                findViewById<TextView>(R.id.dataTextView).text = string
                page++
                findViewById<Button>(R.id.loadData).isClickable
                findViewById<Button>(R.id.loadData).isEnabled
            }
        }

        // connection.loadCardData(page)
    }
}
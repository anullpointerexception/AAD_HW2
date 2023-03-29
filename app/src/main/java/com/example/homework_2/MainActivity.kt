package com.example.homework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        lifecycleScope.launch{
            val result = withContext(Dispatchers.IO){
                connection.loadCardData(page)
            }
            withContext(Dispatchers.Main){
                val string = StringBuilder()
                for(card in result) {
                    val objectProperties = card.name + " " + card.cmc + " " + card.manaCost + "\n"
                    string.append(objectProperties)
                }
                findViewById<TextView>(R.id.dataTextView).text = string
            }
        }

        // connection.loadCardData(page)
    }
}
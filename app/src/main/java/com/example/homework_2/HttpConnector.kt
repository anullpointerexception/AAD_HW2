package com.example.homework_2

import com.example.homework_2.models.Card
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class HttpConnector {

    // Load data from magic api
    fun loadCardData(page: Int): List<Card> {

        val url = URL("https://api.magicthegathering.io/v1/cards?page=$page")
        val connection = url.openConnection() as HttpURLConnection
        val result = try{
            connection.run{
                requestMethod = "GET"
                readTimeout = 10000
                String(inputStream.readBytes())
            }
        } finally {
            connection.disconnect()
        }
        // println(result)
        val gson = Gson()
        val jsonObject = gson.fromJson(result, JsonObject::class.java)
        val cardJsonArray = jsonObject.getAsJsonArray("cards")
        val currentCards = mutableListOf<Card>()
        for(currentCardElement in cardJsonArray){
            val card = gson.fromJson(currentCardElement, Card::class.java)
            currentCards.add(card)
        }
        return currentCards
    }
}
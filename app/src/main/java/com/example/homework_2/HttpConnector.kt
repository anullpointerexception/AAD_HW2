package com.example.homework_2

import com.example.homework_2.models.Card
import com.example.homework_2.models.ForeignName
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
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
        val jsonObject = JSONObject(result)


        // val gson = Gson()
        // val jsonObject = gson.fromJson(result, JsonObject::class.java)
        val cardJsonArray = jsonObject.getJSONArray("cards")
        val currentCards = mutableListOf<Card>()
        for(i in 0 until cardJsonArray.length()){
            val currentCardElement = cardJsonArray.getJSONObject(i)

            val name = currentCardElement.getString("name")
            val manaCost = currentCardElement.optString("manaCost", "")


            val colorsJson =currentCardElement.optJSONArray("colors") ?: JSONArray()
            val colors = (0 until colorsJson.length()).map {colorsJson.getString(it)}

            val colorIdentityJson = currentCardElement.optJSONArray("colorIdentity") ?: JSONArray()
            val colorIdentity = (0 until colorIdentityJson.length()).map {colorIdentityJson.getString(it)}

            val typesJson = currentCardElement.optJSONArray("types") ?: JSONArray()
            val types = (0 until typesJson.length()).map { typesJson.getString(it) }

            val subtypesJson = currentCardElement.optJSONArray("subtypes") ?: JSONArray()
            val subtypes = (0 until subtypesJson.length()).map {subtypesJson.getString(it)}

            val variationsJson = currentCardElement.optJSONArray("variations") ?: JSONArray()
            val variations = (0 until variationsJson.length()).map { variationsJson.getString(it) }

            val foreignNamesJson = currentCardElement.optJSONArray("foreignNames") ?: JSONArray()
            val foreignNames = (0 until foreignNamesJson.length()).map {
                val foreignNameJson = foreignNamesJson.getJSONObject(it)
                val foreignName = foreignNameJson.getString("name")
                val foreignText = foreignNameJson.optString("text", "")
                val foreignType = foreignNameJson.optString("type", "")
                val foreignFlavor = foreignNameJson.optString("flavor", "")
                val foreignImageUrl = foreignNameJson.optString("imageUrl", "")
                val foreignLanguage = foreignNameJson.getString("language")
                val foreignMultiverseid = foreignNameJson.optString("multiverseid", "")
                ForeignName(foreignName, foreignText, foreignType, foreignFlavor, foreignImageUrl, foreignLanguage, foreignMultiverseid)
            }

            val printingsJson = currentCardElement.optJSONArray("printings") ?: JSONArray()
            val printingsList = mutableListOf<String>()
            for (i in 0 until printingsJson.length()) {
                printingsList.add(printingsJson.getString(i))
            }


            val card = Card(
                currentCardElement.getString("name"),
                currentCardElement.getString("manaCost"),
                currentCardElement.getLong("cmc"),
                currentCardElement.getJSONArray("colors"),
                currentCardElement.getJSONArray("colorIdentity"),
                currentCardElement.getString("type"),
                currentCardElement.getJSONArray("types"),
                currentCardElement.getJSONArray("subtypes"),
                currentCardElement.getString("rarity"),
                currentCardElement.getString("set"),
                currentCardElement.getString("setName"),
                currentCardElement.getString("text"),
                currentCardElement.getString("artist"),
                currentCardElement.getString("number"),
                currentCardElement.optString("power", ""),
                currentCardElement.getString("toughness"),
                currentCardElement.getString("layout"),
                currentCardElement.optString("multiverseid", ""),
                currentCardElement.optString("imageUrl", ""),
                currentCardElement.getJSONArray("variations"),
                currentCardElement.getJSONArray("foreignNames"),
                currentCardElement.getJSONArray("printings"),
                currentCardElement.getString("originalText"),
                currentCardElement.getString("originalType"),
                currentCardElement.getJSONArray("legalities"),
                currentCardElement.getString("id"),
                )
            currentCards.add(card)
        }
        return currentCards
    }
}
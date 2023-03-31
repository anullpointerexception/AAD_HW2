package com.example.homework_2

import com.example.homework_2.models.Card
import com.example.homework_2.models.ForeignName
import com.example.homework_2.models.Legalities
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HttpConnector {

    // Load data from magic api
    fun loadCardData(page: Int): List<Card>? {

        val url = URL("https://api.magicthegathering.io/v1/cards?page=$page")
        val connection = url.openConnection() as HttpURLConnection
        var result: String
        try{
            connection.run{
                requestMethod = "GET"
                readTimeout = 5000
                result = String(inputStream.readBytes())
            }
        }
        catch (e: Exception) {
            return null
        }finally {
            connection.disconnect()
        }
        val currentCards = mutableListOf<Card>()

        val jsonObject = JSONObject(result)

        val cardJsonArray = jsonObject.getJSONArray("cards")
        for(i in 0 until cardJsonArray.length()) {
            val currentCardElement = cardJsonArray.getJSONObject(i)
            // colors
            val colorsJson = currentCardElement.optJSONArray("colors") ?: JSONArray()
            val colors = (0 until colorsJson.length()).map { colorsJson.getString(it) }
            // colorIdentity
            val colorIdentityJson = currentCardElement.optJSONArray("colorIdentity") ?: JSONArray()
            val colorIdentity = (0 until colorIdentityJson.length()).map { colorIdentityJson.getString(it) }
            // types
            val typesJson = currentCardElement.optJSONArray("types") ?: JSONArray()
            val types = (0 until typesJson.length()).map { typesJson.getString(it) }
            // subtypes
            val subtypesJson = currentCardElement.optJSONArray("subtypes") ?: JSONArray()
            val subtypes = (0 until subtypesJson.length()).map { subtypesJson.getString(it) }
            // variations
            val variationsJson = currentCardElement.optJSONArray("variations") ?: JSONArray()
            val variations = (0 until variationsJson.length()).map { variationsJson.getString(it) }
            // foreignNames
            val foreignNamesJson = currentCardElement.optJSONArray("foreignNames") ?: JSONArray()
            val foreignNames = (0 until foreignNamesJson.length()).map {
                val foreignNameJson = foreignNamesJson.getJSONObject(it)
                val foreignName = foreignNameJson.optString("name")
                val foreignText = foreignNameJson.optString("text", "")
                val foreignType = foreignNameJson.optString("type", "")
                val foreignFlavor = foreignNameJson.optString("flavor", "")
                val foreignImageUrl = foreignNameJson.optString("imageUrl", "")
                val foreignLanguage = foreignNameJson.optString("language")
                val foreignMultiverseid = foreignNameJson.optString("multiverseid", "")
                ForeignName(
                    foreignName,
                    foreignText,
                    foreignType,
                    foreignFlavor,
                    foreignImageUrl,
                    foreignLanguage,
                    foreignMultiverseid
                )
            }
            // printings
            val printingsJson = currentCardElement.optJSONArray("printings") ?: JSONArray()
            val printingsList = mutableListOf<String>()
            for (s in 0 until printingsJson.length()) {
                printingsList.add(printingsJson.getString(s))
            }
            // legalities
            val legalitiesJson = currentCardElement.optJSONArray("legalities") ?: JSONArray()
            val legalities = (0 until legalitiesJson.length()).map {
                val legalityJson = legalitiesJson.getJSONObject(it)
                val format = legalityJson.optString("format")
                val legality = legalityJson.optString("legality", "")
                Legalities(format, legality)
            }


            val card = Card(
                currentCardElement.optString("name", ""),
                currentCardElement.optString("manaCost", ""),
                currentCardElement.optLong("cmc"),
                colors,
                colorIdentity,
                currentCardElement.optString("type", ""),
                types,
                subtypes,
                currentCardElement.optString("rarity", ""),
                currentCardElement.optString("set", ""),
                currentCardElement.optString("setName", ""),
                currentCardElement.optString("text", ""),
                currentCardElement.optString("artist", ""),
                currentCardElement.optString("number", ""),
                currentCardElement.optString("power", ""),
                currentCardElement.optString("toughness", ""),
                currentCardElement.optString("layout", ""),
                currentCardElement.optString("multiverseid", ""),
                currentCardElement.optString("imageUrl", ""),
                variations,
                foreignNames,
                printingsList,
                currentCardElement.optString("originalText", ""),
                currentCardElement.optString("originalType", ""),
                legalities,
                currentCardElement.optString("id", ""),
            )
            currentCards.add(card)
            currentCards.sortWith(compareBy{it.name})
        }
        return currentCards
    }
}
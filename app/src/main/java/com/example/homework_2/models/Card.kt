package com.example.homework_2.models

import com.google.gson.JsonArray
import org.json.JSONArray

data class Card (
    val name: String,
    val manaCost: String,
    val cmc: Long,
    val colors: List<String>,
    val colorIdentity: List<String>,
    val type: String,
    val types: List<String>,
    val subtypes: List<String>,
    val rarity: String,
    val set: String,
    val setName: String,
    val text: String,
    val artist: String,
    val number: String,
    val power: String,
    val toughness: String,
    val layout: String,
    val multiverseid: String,
    val imageUrl: String,
    val variations: List<String>,
    val foreignNames: List<ForeignName>,
    val printings: List<String>,
    val originalText: String,
    val originalType: String,
    val legalities: List<Legalities>,
    val id: String
)


package com.bylaew.exytepokeapi.utils

import androidx.room.TypeConverter
import com.bylaew.exytepokeapi.model.PokemonById.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type




class Converter {
    private val gson = Gson()
    private val type: Type = object : TypeToken<List<String>>() {}.type


    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<Ability?>? {
        val gson = Gson()
        if (data == null) {
            return emptyList<Ability>()
        }
        val listType = object : TypeToken<List<Ability?>?>() {}.type
        return gson.fromJson<List<Ability?>>(data, listType)
    }

    @TypeConverter
    fun storedStringToMyAny(data: String?): List<Any?>? {
        val gson = Gson()
        if (data == null) {
            return emptyList<Any>()
        }
        val listType = object : TypeToken<List<Any?>?>() {}.type
        return gson.fromJson<List<Any?>>(data, listType)
    }

    @TypeConverter
    fun storedStringToMyForms(data: String?): List<Form?>? {
        val gson = Gson()
        if (data == null) {
            return emptyList<Form>()
        }
        val listType = object : TypeToken<List<Form?>?>() {}.type
        return gson.fromJson<List<Form?>>(data, listType)
    }

    @TypeConverter
    fun storedStringToMyGameIndice(data: String?): List<GameIndice?>? {
        val gson = Gson()
        return gson.fromJson<List<GameIndice?>>(data,  GameIndice::class.java.genericSuperclass)
    }


    @TypeConverter
    fun storedStringToMyMove(data: String?): List<Move?>? {
        val gson = Gson()
        if (data == null) {
            return emptyList<Move>()
        }
        val listType = object : TypeToken<List<Move?>?>() {}.type
        return gson.fromJson<List<Move?>>(data, listType)
    }

    @TypeConverter
    fun storedStringToMySpecies(data: String?): Species? {
        val gson = Gson()
        if (data == null) {
            return Species("","")
        }
        val listType = object : TypeToken<Species?>() {}.type
        return gson.fromJson<Species?>(data, listType)
    }

    @TypeConverter
    fun storedStringToMySprites(data: String?): Sprites? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val listType = object : TypeToken<Sprites?>() {}.type
        return gson.fromJson<Sprites?>(data, listType)
    }

    @TypeConverter
    fun storedStringToMyStats(data: String?): List<Stat?>? {
        val gson = Gson()
        if (data == null) {
            return emptyList<Stat>()
        }
        return gson.fromJson<List<Stat?>>(data, Stat::class.java.genericSuperclass)
    }

    @TypeConverter
    fun storedStringToMycomType(data: String?): List<com.bylaew.exytepokeapi.model.PokemonById.Type?>? {
        val gson = Gson()

        return gson.fromJson<List<com.bylaew.exytepokeapi.model.PokemonById.Type?>>(data, com.bylaew.exytepokeapi.model.PokemonById.Type::class.java.genericSuperclass)
    }


    @TypeConverter
    fun fromString(json: String?): List<String> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String {
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun fromAbilities(value: List<Ability>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Ability>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromGameIndice(value: List<GameIndice>): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<GameIndice>>() {}.type
        return listOf(gson.toJson(value, type))
    }

    @TypeConverter
    fun fromForm(value: List<Form>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Form>>() {}.type
        return gson.toJson(value, type)
    }
    @TypeConverter
    fun fromMove(value: List<Move>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Move>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromAny(obj: List<Any>?): List<String> {
        val type = object : TypeToken<List<Any>>() {}.type
        return listOf(gson.toJson(obj, type))
    }


    @TypeConverter
    fun fromSpecies(obj: Species?): String {
        val type = object : TypeToken<Species>() {}.type
        return gson.toJson(obj, type)
    }

    @TypeConverter
    fun fromSprites(obj: Sprites?): String {
        val type = object : TypeToken<Sprites>() {}.type
        return gson.toJson(obj, type)
    }
    @TypeConverter
    fun fromStat(obj: List<Stat>?): List<String> {
        val type = object : TypeToken<List<Stat>>() {}.type
        return listOf(gson.toJson(obj, type))
    }
    @TypeConverter
    fun fromType(obj: List<com.bylaew.exytepokeapi.model.PokemonById.Type>?): List<String> {
        val type = object : TypeToken<List<com.bylaew.exytepokeapi.model.PokemonById.Type>>() {}.type
        return listOf(gson.toJson(obj, type))
    }
}
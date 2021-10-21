package com.bylaew.exytepokeapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.utils.Converter

@Database(entities = [PokemonById::class], version = 5, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PokemonDB : RoomDatabase() {

    abstract fun pokemonDAO(): PokemonDAO

    companion object {
        private var instance: PokemonDB? = null

        fun getDatabase(context: Context): PokemonDB? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PokemonDB::class.java,
                "PokemonsRoom.db"
            ).build()
    }
}
package com.itzikpich.amiiboapiapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itzikpich.amiiboapiapp.db.converters.ReleaseTypeConverter
import com.itzikpich.amiiboapiapp.models.AmiiboResponse
import com.itzikpich.amiiboapiapp.models.Purchase


@Database(entities = [AmiiboResponse.Amiibo::class, AmiiboResponse.Amiibo.Release::class, Purchase::class], version = 1)
@TypeConverters(ReleaseTypeConverter::class)
abstract class AmiiboDatabase: RoomDatabase() {
    abstract fun amiiboDao(): AmiiboDao
}
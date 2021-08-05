package com.itzikpich.amiiboapiapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.itzikpich.amiiboapiapp.models.AmiiboResponse

class ReleaseTypeConverter {

    @TypeConverter
    fun fromReleaseToString(release: AmiiboResponse.Amiibo.Release?): String? =
        if (release == null) null
        else Gson().toJson(release)


    @TypeConverter
    fun fromStringToRelease(string: String?): AmiiboResponse.Amiibo.Release? {
        return try {
            Gson().fromJson(string, AmiiboResponse.Amiibo.Release::class.java)
        } catch (e:Exception) {
            null
        }
    }
}
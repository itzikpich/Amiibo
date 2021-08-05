package com.itzikpich.amiiboapiapp.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AmiiboResponse(
    @SerializedName("amiibo")
    val amiiboList: List<Amiibo?>?
) {
    @Entity
    data class Amiibo(
        @PrimaryKey
        var id: String,
        val character: String?, // Mario
        val image: String? = null, // https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png
        val head: String? = null, // 00000000
        val tail: String? = null, // 00000002
        val amiiboSeries: String? = null, // Super Smash Bros.
        val gameSeries: String? = null, // Super Mario
        val name: String? = null, // Mario
        val release: Release? = null,
        val type: String? = null, // Figure
        var isPurchased: Boolean = false // Figure
    ) {

        @Entity
        data class Release(
            @PrimaryKey(autoGenerate = true)
            val id: Int,
            val au: String?, // 2014-11-29
            val eu: String?, // 2014-11-28
            val jp: String?, // 2014-12-06
            val na: String? // 2014-11-21
        )
    }
}
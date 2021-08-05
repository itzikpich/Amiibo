package com.itzikpich.amiiboapiapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Purchase(
    @PrimaryKey
    val id: String
)

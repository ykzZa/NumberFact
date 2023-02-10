package com.example.testnumbers.data.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity("search_table")
data class Search(
    @SerializedName("text")
    val text: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("found")
    val found: Boolean,
    @SerializedName("type")
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
): Serializable
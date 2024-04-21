package com.anmp.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News (
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val title: String,
    val username: String,
    val synopsis: String,
    val description: String,
    val date: String
):Parcelable
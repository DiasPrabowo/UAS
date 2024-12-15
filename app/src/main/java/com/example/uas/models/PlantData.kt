package com.example.uas.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantData(
    @SerializedName("_id")
    val _id: String,
    val tanggal: String,
    val tinggi: String,
    val jumlahDaun: String,
    val panjangBatang: String
) : Parcelable

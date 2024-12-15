package com.example.uas.api

import com.example.uas.models.PlantData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("JgfzY/UrbanPonic")
    suspend fun createOrAddToTable(
        @Path("tableName") tableName: String,
        @Body plantData: PlantData
    ): Response<PlantData>
    @GET("JgfzY/UrbanPonic")
    suspend fun getPlants(): List<PlantData>
}

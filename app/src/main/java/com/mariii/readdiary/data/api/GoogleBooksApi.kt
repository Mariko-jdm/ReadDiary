package com.mariii.readdiary.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    suspend fun searchBooks(

        @Query("q")
        query: String,

        @Query("key")
        apiKey: String = "AIzaSyCwhTEKgtq2cGzMeevdFzqDrvu8poCR7uI"
    ): GoogleBooksResponse
}
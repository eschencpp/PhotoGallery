package com.example.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "1afc8d314a7da83571c5e44b56f6c557"


interface FlickrApi {
    @GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickrResponse


    @GET("services/rest/?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String) : FlickrResponse

}
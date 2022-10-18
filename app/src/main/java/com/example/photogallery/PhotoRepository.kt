package com.example.photogallery

import com.example.photogallery.api.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoRepository {
    private val flickrApi: FlickrApi
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }

    suspend fun fetchPhotos() = flickrApi.fetchPhotos()
}
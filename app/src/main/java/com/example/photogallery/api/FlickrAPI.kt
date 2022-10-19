package com.example.photogallery.api

import retrofit2.http.GET

private const val API_KEY = "1afc8d314a7da83571c5e44b56f6c557"


interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"

    )
    suspend fun fetchPhotos(): FlickrResponse

}
package com.rooted.deviceinfo.WebBase

import com.rooted.deviceinfo.Mvvm.Pojos.QuotePojo
import retrofit2.Response
import retrofit2.http.GET

interface ApiCallBack {

    @GET(WebCons.RANDOM_QUOTE_KEY)
    suspend fun getRandomQuote(): Response<List<QuotePojo>>
}
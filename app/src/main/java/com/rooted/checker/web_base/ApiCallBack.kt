package com.rooted.checker.web_base

import com.rooted.checker.mvvm.pojos.QuotePojo
import retrofit2.Response
import retrofit2.http.GET

interface ApiCallBack {

    @GET(WebCons.RANDOM_QUOTE_KEY)
    suspend fun getRandomQuote(): Response<List<QuotePojo>>
}
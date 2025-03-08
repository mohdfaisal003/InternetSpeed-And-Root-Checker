package com.rooted.checker.mvvm.repositories

import com.rooted.checker.mvvm.pojos.QuotePojo
import com.rooted.checker.web_base.WebConnector
import retrofit2.Response

class QuotesRepo: WebConnector() {

    suspend fun getRandomQuote(): Response<List<QuotePojo>> {
        return apiCallBack.getRandomQuote()
    }
}
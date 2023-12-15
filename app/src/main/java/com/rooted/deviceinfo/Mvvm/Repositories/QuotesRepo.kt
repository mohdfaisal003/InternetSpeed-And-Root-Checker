package com.rooted.deviceinfo.Mvvm.Repositories

import com.rooted.deviceinfo.Mvvm.Pojos.QuotePojo
import com.rooted.deviceinfo.WebBase.WebConnector
import retrofit2.Response

class QuotesRepo: WebConnector() {

    suspend fun getRandomQuote(): Response<List<QuotePojo>> {
        return apiCallBack.getRandomQuote()
    }
}
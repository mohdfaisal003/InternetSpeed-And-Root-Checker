package com.rooted.deviceinfo.Mvvm.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rooted.deviceinfo.AppUtils.AppUtil
import com.rooted.deviceinfo.Mvvm.Pojos.QuotePojo
import com.rooted.deviceinfo.Mvvm.Repositories.QuotesRepo
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesViewModel : ViewModel() {

    private val quotesRepo = QuotesRepo()
    val quotes = MutableLiveData<QuotePojo>()

    fun getRandomQuote() {
        viewModelScope.launch {
            try {
                val response = quotesRepo.getRandomQuote()
                if (response.isSuccessful) {
                    Log.d("value",response.body()?.get(0).toString())
                    quotes.value = response.body()?.get(0)
                } else if (response.code() >= 400) {

                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
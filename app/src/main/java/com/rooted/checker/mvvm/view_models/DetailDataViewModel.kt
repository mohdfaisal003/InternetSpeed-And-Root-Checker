package com.rooted.checker.mvvm.view_models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rooted.checker.app_utils.ConverterUtil
import com.rooted.checker.mvvm.pojos.DataPojo
import com.rooted.checker.R
import kotlinx.coroutines.launch

class DetailDataViewModel: ViewModel() {

    var detailData = MutableLiveData<DataPojo>()

    fun getDetailsList(context: Context) {
        viewModelScope.launch {
            try {
                detailData.value =  ConverterUtil.getDetailsList(context, R.raw.detail_items_list,DataPojo::class.java) as DataPojo
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
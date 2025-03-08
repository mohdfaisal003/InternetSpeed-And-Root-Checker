package com.rooted.checker.app_utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.InputStreamReader

object ConverterUtil {

    fun getDetailsList(context: Context, jsonFile: Int, classFile: Class<*>): Any? {
        var details: Any? = null
        try {
            val list = context.resources.openRawResource(jsonFile)
            val jsonArray = JsonParser().parse(InputStreamReader(list, "UTF-8")) as JsonObject
            details = Gson().fromJson(jsonArray, classFile)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return details
    }
}
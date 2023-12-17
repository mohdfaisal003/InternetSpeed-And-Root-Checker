package com.rooted.deviceinfo.listeners

import androidx.viewbinding.ViewBinding

interface RecyclerListener {
    fun onRecyclerCreated(binding: ViewBinding, position: Int)
}
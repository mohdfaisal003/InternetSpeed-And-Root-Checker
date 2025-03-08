package com.rooted.checker.listeners

import androidx.viewbinding.ViewBinding

interface RecyclerListener {
    fun onRecyclerCreated(binding: ViewBinding, position: Int)
}
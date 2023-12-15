package com.rooted.deviceinfo.Mvvm.Pojos

data class QuotePojo(val q: String?,val a: String?,val h: String?) {
    override fun toString(): String {
        return "q=$q, a=$a, h=$h"
    }
}

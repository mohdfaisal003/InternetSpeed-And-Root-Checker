package com.rooted.deviceinfo.lists.recycler_views

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rooted.deviceinfo.app_utils.AppUtil
import com.rooted.deviceinfo.databinding.RvDetailLayoutBinding
import com.rooted.deviceinfo.listeners.RecyclerListener
import com.rooted.deviceinfo.lists.adapters.DetailsAdapter
import com.rooted.deviceinfo.mvvm.pojos.DetailPojo

class DetailRecyclerView(
    context: Context?,
    recyclerView: RecyclerView?,
    list: List<DetailPojo>?
) : RecyclerListener {

    private var context: Context? = null
    private var recyclerView: RecyclerView? = null
    private var list: List<DetailPojo>? = null
    private lateinit var adapter: DetailsAdapter

    init {
        this.context = context
        this.recyclerView = recyclerView
        this.list = list

        recyclerView?.let { setRecyclerView(it) }
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {
        if (list!!.isNotEmpty()) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            adapter = DetailsAdapter(context, list)
            recyclerView.adapter = adapter
            adapter.setRecyclerListener(this)
        }
    }

    override fun onRecyclerCreated(binding: ViewBinding, position: Int) {
        val bind = binding as RvDetailLayoutBinding
        if (list!!.isNotEmpty()) {
            bind.detailHeadingTv.text = list!!.get(position).title
            bind.detailDescTv.text = list!!.get(position).description

            bind.cardLl.setOnClickListener {
                AppUtil.showMessageInSnackBar(context as Activity,list!!.get(position).title.toString())
            }
        }
    }
}
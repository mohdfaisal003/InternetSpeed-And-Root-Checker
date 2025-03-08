package com.rooted.checker.lists.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rooted.checker.databinding.RvDetailLayoutBinding
import com.rooted.checker.listeners.RecyclerListener
import com.rooted.checker.mvvm.pojos.DetailPojo

class DetailsAdapter(var context: Context?, var detailsList: List<DetailPojo>?) :
    Adapter<DetailsAdapter.ViewHolder>() {

    lateinit var binding: RvDetailLayoutBinding
    private var recyclerListener: RecyclerListener? = null

    fun setRecyclerListener(recyclerListener: RecyclerListener) {
        this.recyclerListener = recyclerListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvDetailLayoutBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (detailsList!!.isNotEmpty()) detailsList!!.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (detailsList!!.isNotEmpty()) {
            recyclerListener?.onRecyclerCreated(binding,position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
package com.rooted.deviceinfo.UI.Fragments

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rooted.deviceinfo.AppBase.AppBaseFragment
import com.rooted.deviceinfo.AppUtils.AppUtil
import com.rooted.deviceinfo.Mvvm.ViewModels.QuotesViewModel
import com.rooted.deviceinfo.databinding.FragmentQuoteBinding

class QuoteFragment: AppBaseFragment() {

    private lateinit var binding: FragmentQuoteBinding
    private var flag = 0
    private val quoteViewModel by viewModels<QuotesViewModel>()

    override fun layoutRes(): View {
        binding = FragmentQuoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initComponents() {
        setQuote()
        quoteViewModel.quotes.observe(this, Observer {
            if (it != null) {
                binding.quoteTv.text = it.q
                binding.autherTv.text = "author: ${it.a}"
            }
        })

        binding.quoteCard.setOnClickListener(this)
        binding.newBtn.setOnClickListener(this)
        binding.copyBtn.setOnClickListener(this)
        binding.shareBtn.setOnClickListener(this)
    }

    private fun setQuote() {
        quoteViewModel.getRandomQuote()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.quoteCard.id -> {
                if (flag == 0) {
                    binding.quoteLl.visibility = View.VISIBLE
                    flag = 1
                } else {
                    binding.quoteLl.visibility = View.GONE
                    flag = 0
                }
            }
            binding.newBtn.id -> {
                setQuote()
            }
            binding.copyBtn.id -> {
                AppUtil.copyToClipboard(requireContext(),"${binding.quoteTv.text}\n\n${binding.autherTv.text}")
            }
            binding.shareBtn.id -> {
                AppUtil.share(requireContext(),"${binding.quoteTv.text}\n\n${binding.autherTv.text}")
            }
        }
    }
}
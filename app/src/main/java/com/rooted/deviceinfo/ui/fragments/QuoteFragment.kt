package com.rooted.deviceinfo.ui.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rooted.deviceinfo.R
import com.rooted.deviceinfo.app_base.AppBaseFragment
import com.rooted.deviceinfo.app_utils.AppUtil
import com.rooted.deviceinfo.app_utils.NetworkUtil
import com.rooted.deviceinfo.databinding.FragmentQuoteBinding
import com.rooted.deviceinfo.mvvm.view_models.QuotesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteFragment : AppBaseFragment() {

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
                binding.authorTv.text = "author: ${it.a}"
            }
        })

        NetworkUtil.setConnectivityChangeListener(this)

        binding.newBtn.setOnClickListener(this)
        binding.copyBtn.setOnClickListener(this)
        binding.shareBtn.setOnClickListener(this)
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isConnected) {
                binding.dailyQuoteTv.text = getString(R.string.daily_quotes)
                binding.quoteCard.isEnabled = true
                binding.quoteCard.setOnClickListener(this@QuoteFragment)
            } else {
                binding.dailyQuoteTv.text = getString(R.string.no_internet)
                binding.quoteCard.isEnabled = false
                binding.quoteLl.visibility = View.GONE
                flag = 0
            }
        }
    }

    private fun setQuote() {
        quoteViewModel.getRandomQuote()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
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
                AppUtil.copyToClipboard(
                    requireContext(),
                    "${binding.quoteTv.text}\n\n${binding.authorTv.text}"
                )
            }

            binding.shareBtn.id -> {
                AppUtil.share(
                    requireContext(),
                    "${binding.quoteTv.text}\n\n${binding.authorTv.text}"
                )
            }
        }
    }
}
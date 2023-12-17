package com.rooted.deviceinfo.ui.fragments

import android.graphics.Color
import android.view.View
import com.rooted.deviceinfo.app_base.AppBaseFragment
import com.rooted.deviceinfo.app_utils.AppUtil
import com.rooted.deviceinfo.R
import com.rooted.deviceinfo.databinding.FragmentRootCheckerBinding
import com.scottyab.rootbeer.RootBeer

class RootCheckerFragment : AppBaseFragment() {

    lateinit var binding: FragmentRootCheckerBinding

    override fun layoutRes(): View {
        binding = FragmentRootCheckerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initComponents() {
        checkForRoot()
        binding.deviceNameTv.text = AppUtil.DEVICE_NAME
        binding.androidVersionTv.text = AppUtil.ANDROID_VERSION

        binding.checkForRootedBtn.setOnClickListener {
            checkForRoot()
        }
    }

    private fun checkForRoot() {
        try {
            if (RootBeer(requireContext()).isRooted) {
                binding.rootStatusTv.text = getString(R.string.device_is_rooted)
                binding.rootStatusTv.setTextColor(Color.RED)
            } else {
                binding.rootStatusTv.text = getString(R.string.device_is_not_rooted)
                binding.rootStatusTv.setTextColor(Color.GREEN)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}
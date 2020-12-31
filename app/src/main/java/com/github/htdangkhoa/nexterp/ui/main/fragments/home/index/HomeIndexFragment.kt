
package com.github.htdangkhoa.nexterp.ui.main.fragments.home.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.ui.main.fragments.home.HomeViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.new.StockCountNewFragmentDirections
import kotlinx.android.synthetic.main.fragment_home.*


class HomeIndexFragment() : BaseFragment<HomeViewModel>(
    HomeViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_home

    override fun render(view: View, savedInstanceState: Bundle?) {
        btnListReceiving.setOnClickListener {
            val action = HomeIndexFragmentDirections.actionNavHomeToNavReceiving()
            view.findNavController().navigate(action)
        }
        btnNewReceiving.setOnClickListener {
            val action = HomeIndexFragmentDirections.actionNavHomeToReceivingNewFragment()
            view.findNavController().navigate(action)
        }
        btnListStockCount.setOnClickListener {
            val action = HomeIndexFragmentDirections.actionNavHomeToNavStockCount()
            view.findNavController().navigate(action)
        }
        btnNewStockCount.setOnClickListener {
            val action = HomeIndexFragmentDirections.actionNavHomeToStockCountNewFragment()
            view.findNavController().navigate(action)
        }

    }
}
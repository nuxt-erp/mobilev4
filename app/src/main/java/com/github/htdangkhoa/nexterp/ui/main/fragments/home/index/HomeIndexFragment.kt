
package com.github.htdangkhoa.nexterp.ui.main.fragments.home.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.ui.main.fragments.home.HomeViewModel


class HomeIndexFragment() : BaseFragment<HomeViewModel>(
    HomeViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_home

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }

    override fun render(view: View, savedInstanceState: Bundle?) {

    }
}
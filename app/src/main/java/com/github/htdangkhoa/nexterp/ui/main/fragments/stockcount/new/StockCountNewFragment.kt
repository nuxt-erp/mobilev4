
package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.new

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockListAdapter
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.*

class StockCountNewFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_new

    override fun render(view: View, savedInstanceState: Bundle?) {

    }
}
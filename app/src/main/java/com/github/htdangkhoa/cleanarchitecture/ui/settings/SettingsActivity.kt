package com.github.htdangkhoa.cleanarchitecture.ui.settings

import android.os.Bundle
import android.util.Log
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.settings.fragments.SettingsFragment
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun render(savedInstanceState: Bundle?) {

        viewModel.resourceLogout.observe(this, object : ObserverResource<String>() {
            override fun onSuccess(data: String) {
                logout(401)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                }
            }
        })
        viewModel.resourceLocations.observe(this, object : ObserverResource<Array<LocationResponse.Location>>() {
            override fun onSuccess(data: Array<LocationResponse.Location>) {
                SettingsFragment().setListPreferenceData(data)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    Log.e("ERROR->>>",it.toString() )
                    it?.message?.let { toast(it) }
                    handleHttpError(it)
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }

        })
        viewModel.getLocations()
    }
}

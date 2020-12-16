package com.github.htdangkhoa.cleanarchitecture.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainActivity
import com.github.htdangkhoa.cleanarchitecture.ui.settings.fragments.SettingsFragment
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun render(savedInstanceState: Bundle?) {
        btnSave.setOnClickListener {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val locationId = sharedPreferences.getString("location", null)?.toInt()
            if (locationId == null) {
                showDialog("Error", "You must select a location.")
            } else {
                startActivity<MainActivity>()
            }
        }

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
    }
}

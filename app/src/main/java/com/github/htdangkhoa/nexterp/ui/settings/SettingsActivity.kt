package com.github.htdangkhoa.nexterp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseActivity
import com.github.htdangkhoa.nexterp.ui.main.MainActivity
import com.github.htdangkhoa.nexterp.ui.settings.fragments.SettingsFragment
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
            val locationId = sharedPreferences.getString("location", null)?.toBoolean()
            if (locationId == null) {
                showDialog("Error", "You must select a location.")
            } else {
                startActivity<MainActivity>()
            }
        }
    }
}

package com.github.htdangkhoa.cleanarchitecture.ui.settings.fragments
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BasePreferenceFragment
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainActivity
import com.github.htdangkhoa.cleanarchitecture.ui.settings.SettingsActivity
import com.github.htdangkhoa.cleanarchitecture.ui.settings.SettingsViewModel
import com.pawegio.kandroid.startActivity
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsFragment() : BasePreferenceFragment<SettingsViewModel, SettingsActivity>(SettingsViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_settings

    override val preferenceResID: Int
        get() = R.xml.root_preferences

    override fun render(view: View, savedInstanceState: Bundle?) {
        viewModel.resourceLocations.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<LocationResponse.Location>>() {
                override fun onSuccess(data: Array<LocationResponse.Location>) {
                    setListPreferenceData(data)
                }

                override fun onError(throwable: Throwable?) {
                    handleError(throwable) {
                        Log.e("ERROR->>>", it.toString())
                        it?.message?.let { toast(it) }
                        handleHttpError(it)
                    }
                }

            })

        viewModel.getLocations()

    }

    fun setListPreferenceData(data: Array<LocationResponse.Location>) {
        val locationPreference = findPreference<ListPreference>("location")
        if (locationPreference != null) {
            val entries: Array<CharSequence?> =
                arrayOfNulls<CharSequence>(data.size)
            val entryValues: Array<CharSequence?> = arrayOfNulls<CharSequence>(
                data.size
            )

            data.forEachIndexed { index, element ->
                entries[index] = element.name
                entryValues[index] = element.id.toString()
            }

            locationPreference.entries = entries
            locationPreference.entryValues = entryValues
            locationPreference.summary = locationPreference.entry;

            locationPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    val idx = locationPreference.findIndexOfValue(newValue as String?)
                    preference.summary = locationPreference.entries[idx]
                    true
                }
        }

    }
}

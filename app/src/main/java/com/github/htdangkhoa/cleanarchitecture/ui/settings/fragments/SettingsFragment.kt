package com.github.htdangkhoa.cleanarchitecture.ui.settings.fragments
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BasePreferenceFragment
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainViewModel

class SettingsFragment() : BasePreferenceFragment<MainViewModel>(MainViewModel::class) {
    override val preferenceResID: Int
        get() = R.xml.root_preferences

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

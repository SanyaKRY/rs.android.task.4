package com.example.animallist.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.animallist.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}
package edu.quinnipiac.ser210.sticktoitapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel to manage toggles found in the settings screen
class SettingsViewModel : ViewModel() {
    private val _militaryTimeEnabled = MutableStateFlow(false)
    val militaryTimeEnabled: StateFlow<Boolean> = _militaryTimeEnabled

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    fun setMilitaryTimeEnabled(enabled: Boolean) {
        _militaryTimeEnabled.value = enabled
    }

    fun setDarkModeEnabled(enabled: Boolean){
        _darkModeEnabled.value = enabled
    }
}
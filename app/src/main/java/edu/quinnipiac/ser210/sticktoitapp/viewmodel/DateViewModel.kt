package edu.quinnipiac.ser210.sticktoitapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.YearMonth

// ViewModel to manage the selected date across screens
@RequiresApi(Build.VERSION_CODES.O)
class DateViewModel : ViewModel() {
    private val _selectedDate = MutableStateFlow<LocalDate?>(LocalDate.now())
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    private val _currentMonth = MutableStateFlow<YearMonth>(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
        _currentMonth.value = YearMonth.from(date)
    }

    fun changeMonth(month: YearMonth) {
        _currentMonth.value = month
    }
    fun clearDate(){
        _selectedDate.value = null
    }
}
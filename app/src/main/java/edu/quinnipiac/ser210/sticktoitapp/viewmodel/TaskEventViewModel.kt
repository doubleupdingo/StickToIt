package edu.quinnipiac.ser210.sticktoitapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.sticktoitapp.data.AppRepository
import edu.quinnipiac.ser210.sticktoitapp.data.Event
import edu.quinnipiac.ser210.sticktoitapp.data.Task
import kotlinx.coroutines.launch
import java.time.LocalDate

// ViewModel to manage tasks and events on the home screen
@RequiresApi(Build.VERSION_CODES.O)
class TaskEventViewModel(private val repository: AppRepository) : ViewModel() {

    // Task functions
    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    suspend fun getTasksByDate(date: LocalDate): List<Task> =
        repository.getTasksByDate(date)

    // Event functions
    fun insertEvent(event: Event) = viewModelScope.launch {
        repository.insertEvent(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch {
        repository.updateEvent(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }

    // Get events for a given date, to be used in the home screen.
    suspend fun getEventsByDate(date: LocalDate): List<Event> =
        repository.getEventsByDate(date)
}
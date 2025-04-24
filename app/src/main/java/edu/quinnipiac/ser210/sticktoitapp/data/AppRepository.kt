package edu.quinnipiac.ser210.sticktoitapp.data

import java.time.LocalDate

// Repository used to manage events and tasks.
class AppRepository(private val taskDao: TaskDao, private val eventDao: EventDao) {

    // Task operations
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun getTasksByDate(date: LocalDate): List<Task> = taskDao.getTasksByDate(date)
    suspend fun getAllTasks(): List<Task> = taskDao.getAllTasks()

    // Event operations
    suspend fun insertEvent(event: Event) = eventDao.insertEvent(event)
    suspend fun updateEvent(event: Event) = eventDao.updateEvent(event)
    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)
    suspend fun getEventsByDate(date: LocalDate): List<Event> = eventDao.getEventsByDate(date)
    suspend fun getAllEvents(): List<Event> = eventDao.getAllEvents()
}
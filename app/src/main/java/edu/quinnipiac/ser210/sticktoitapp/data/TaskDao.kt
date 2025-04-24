package edu.quinnipiac.ser210.sticktoitapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE date = :date")
    suspend fun getTasksByDate(date: LocalDate): List<Task>

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>
}
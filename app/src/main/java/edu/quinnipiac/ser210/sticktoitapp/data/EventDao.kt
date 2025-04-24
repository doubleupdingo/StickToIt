package edu.quinnipiac.ser210.sticktoitapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate


@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsByDate(date: LocalDate): List<Event>

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>
}
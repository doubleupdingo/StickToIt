package edu.quinnipiac.ser210.sticktoitapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

// Event entity class
@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val date: LocalDate,
    val startTime: LocalTime, // Unique to events
    val endTime: LocalTime, // Unique to events
    @ColumnInfo(defaultValue = "false") val isCompleted: Boolean = false
)
package edu.quinnipiac.ser210.sticktoitapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

// Task entity class
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val date: LocalDate,
    @ColumnInfo(defaultValue = "false") val isCompleted: Boolean = false
)
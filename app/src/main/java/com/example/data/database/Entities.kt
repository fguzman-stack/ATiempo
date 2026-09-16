package com.example.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String, // "Salud", "Trabajo", "Estudio", "Hogar", "Finanzas", "Social", "Otros"
    val timeHour: Int,
    val timeMinute: Int,
    val recurrence: String, // "ONCE", "DAILY", "WEEKLY", "MONTHLY"
    val recurrenceDays: String, // e.g., "1,2,3,4,5" (1=Monday, 7=Sunday)
    val specificDate: Long?, // Timestamp if "ONCE"
    val notes: String,
    val vibrate: Boolean,
    val isActive: Boolean = true,
    val lastCompleted: Long? = null
)

@Entity(
    tableName = "completions",
    foreignKeys = [
        ForeignKey(
            entity = ReminderEntity::class,
            parentColumns = ["id"],
            childColumns = ["reminderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["reminderId"])]
)
data class CompletionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reminderId: Int,
    val completedAt: Long,
    val status: String // "COMPLETED", "POSTPONED", "DEACTIVATED"
)

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val createdAt: Long,
    val dateLabel: String // "yyyy-MM-dd"
)

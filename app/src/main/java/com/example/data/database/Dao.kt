package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders ORDER BY timeHour ASC, timeMinute ASC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: Int): ReminderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun deleteReminderById(id: Int)

    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders()
}

@Dao
interface CompletionDao {
    @Query("SELECT * FROM completions ORDER BY completedAt DESC")
    fun getAllCompletions(): Flow<List<CompletionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: CompletionEntity)

    @Query("SELECT * FROM completions WHERE reminderId = :reminderId ORDER BY completedAt DESC")
    fun getCompletionsForReminder(reminderId: Int): Flow<List<CompletionEntity>>

    @Query("DELETE FROM completions")
    suspend fun deleteAllCompletions()
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY createdAt DESC")
    fun getAllJournals(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(entry: JournalEntity): Long

    @Update
    suspend fun updateJournal(entry: JournalEntity)

    @Delete
    suspend fun deleteJournal(entry: JournalEntity)

    @Query("DELETE FROM journal_entries")
    suspend fun deleteAllJournals()
}

package com.example.data.repository

import com.example.data.database.CompletionDao
import com.example.data.database.CompletionEntity
import com.example.data.database.JournalDao
import com.example.data.database.JournalEntity
import com.example.data.database.ReminderDao
import com.example.data.database.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val completionDao: CompletionDao,
    private val journalDao: JournalDao
) {
    val allReminders: Flow<List<ReminderEntity>> = reminderDao.getAllReminders()
    val allCompletions: Flow<List<CompletionEntity>> = completionDao.getAllCompletions()
    val allJournals: Flow<List<JournalEntity>> = journalDao.getAllJournals()

    suspend fun getReminderById(id: Int): ReminderEntity? {
        return reminderDao.getReminderById(id)
    }

    suspend fun insertReminder(reminder: ReminderEntity): Long {
        return reminderDao.insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: ReminderEntity) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminderById(id: Int) {
        reminderDao.deleteReminderById(id)
    }

    suspend fun insertCompletion(completion: CompletionEntity) {
        completionDao.insertCompletion(completion)
    }

    fun getCompletionsForReminder(reminderId: Int): Flow<List<CompletionEntity>> {
        return completionDao.getCompletionsForReminder(reminderId)
    }

    suspend fun insertJournal(entry: JournalEntity): Long {
        return journalDao.insertJournal(entry)
    }

    suspend fun updateJournal(entry: JournalEntity) {
        journalDao.updateJournal(entry)
    }

    suspend fun deleteJournal(entry: JournalEntity) {
        journalDao.deleteJournal(entry)
    }

    suspend fun clearAllData() {
        reminderDao.deleteAllReminders()
        completionDao.deleteAllCompletions()
        journalDao.deleteAllJournals()
    }
}

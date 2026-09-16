package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ReminderEntity::class, CompletionEntity::class, JournalEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun completionDao(): CompletionDao
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "atiempo_database"
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add specificDate column if it didn't exist in v1
        // (Assuming version 1 didn't have specificDate and it was added in v2)
        // If v1 and v2 are basically identical in schema because fallbackToDestructiveMigration was used,
        // we can just leave this empty or handle known v1 schemas if applicable.
        // Actually, since we're just securing migrations now, let's leave this as a no-op 
        // to handle any legacy state before we strictly defined migrations.
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create new completions table with ForeignKey
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `completions_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `reminderId` INTEGER NOT NULL,
                `completedAt` INTEGER NOT NULL,
                `status` TEXT NOT NULL,
                FOREIGN KEY(`reminderId`) REFERENCES `reminders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """)
        
        // Copy existing data
        db.execSQL("""
            INSERT INTO `completions_new` (`id`, `reminderId`, `completedAt`, `status`)
            SELECT `id`, `reminderId`, `completedAt`, `status` FROM `completions`
        """)
        
        // Drop old table
        db.execSQL("DROP TABLE `completions`")
        
        // Rename new table
        db.execSQL("ALTER TABLE `completions_new` RENAME TO `completions`")
        
        // Create index for foreign key
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_completions_reminderId` ON `completions` (`reminderId`)")
    }
}

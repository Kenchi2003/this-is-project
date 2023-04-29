package com.example.inputdata



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class , sentdata::class], version = 2)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun userDAO(): UserDao
    abstract fun sentdataDAO(): sentdataDao

    companion object{
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Sendata ADD COLUMN image TEXT NOT NULL DEFAULT ''")
            }
        }
    }
    object AppDatabaseSingleton {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "User.db"
                ).addMigrations(MIGRATION_1_2).build().also { instance = it }
            }
        }
    }
}
package project.roomsiswa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Menu::class, Pesanan::class], version = 4, exportSchema = false)
abstract class DatabaseSiswa : RoomDatabase(){

    abstract fun menuDao() : MenuDao
    abstract fun pesananDao() : PesananDao

    companion object{
        @Volatile
        private var Instance:DatabaseSiswa? = null

        private val MIGRATION: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {}
        }

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): DatabaseSiswa {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context, DatabaseSiswa::class.java, "siswa_database")
                    .addMigrations(MIGRATION)
                    .build().also { Instance=it }
            })
        }
    }
}


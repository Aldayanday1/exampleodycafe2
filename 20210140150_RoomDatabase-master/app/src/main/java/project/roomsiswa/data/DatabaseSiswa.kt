package project.roomsiswa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Siswa::class, Mapel::class, Perilaku::class], version = 1, exportSchema = false)
/** DatabaseSiswa merupakan subclass/ penamaan dari RoomDatabase */
abstract class DatabaseSiswa : RoomDatabase(){
    abstract fun siswaDao() : SiswaDao
    abstract fun mapelDao() : MapelDao
    abstract fun perilakuDao() : PerilakuDao

    companion object{
        @Volatile
        private var Instance:DatabaseSiswa? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): DatabaseSiswa {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    DatabaseSiswa::class.java,
                    "siswa_database"
                ).build().also { Instance=it }
            })
        }
    }
}

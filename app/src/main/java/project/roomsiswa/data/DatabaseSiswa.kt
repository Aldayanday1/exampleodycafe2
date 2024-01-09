package project.roomsiswa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Menu::class, Pesanan::class], version = 7, exportSchema = false)
abstract class DatabaseSiswa : RoomDatabase(){

    abstract fun menuDao() : MenuDao
    abstract fun pesananDao() : PesananDao

    companion object{
        @Volatile
        private var Instance:DatabaseSiswa? = null

        private val MIGRATION: Migration = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `tblMenu_new` (" +
                        "`idmenu` INTEGER PRIMARY KEY NOT NULL," +
                        "`menu` TEXT NOT NULL," +
                        "`harga` TEXT NOT NULL," +
                        "`ketersediaan` TEXT NOT NULL," +
                        "`kategori` TEXT NOT NULL," +
                        "`foto` TEXT NOT NULL DEFAULT '' )")

                // Salin data dari tabel lama ke tabel baru
                database.execSQL("INSERT INTO `tblMenu_new` (`idmenu`, `menu`, `harga`, `ketersediaan`, `kategori`, `foto`) " +
                        "SELECT `idmenu`, `menu`, `harga`, `ketersediaan`, `kategori`, `foto` FROM `tblMenu`")

                // Hapus tabel lama
                database.execSQL("DROP TABLE `tblMenu`")

                // Ubah nama tabel baru menjadi nama tabel lama
                database.execSQL("ALTER TABLE `tblMenu_new` RENAME TO `tblMenu`")
            }
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


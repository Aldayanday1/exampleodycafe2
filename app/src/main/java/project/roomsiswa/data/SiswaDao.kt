package project.roomsiswa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(menu: Menu)

    // update -> dipakai juga u/ (Upload Img)
    @Update
    suspend fun update(menu: Menu)

    @Delete
    suspend fun delete(menu: Menu)

    // mengambil data dari idpesanan di tabel menu
    @Query("SELECT * from tblMenu WHERE idmenu = :idmenu")
    fun getMenu(idmenu: Int): Flow<Menu>

    // mengambil keseluruhan data dari tblMenu, serta mengurutkan alfabetis dari atribut nama dengan menggunakan ascending & descending, dari A-Z & Z-A
    @Query("SELECT * from tblMenu ORDER BY kategori ASC, ketersediaan DESC")
    fun getAllMenu(): Flow<List<Menu>>

    // Search Menu
    @Query("SELECT * FROM tblMenu WHERE menu LIKE '%' || :query || '%'")
    fun searchMenu(query: String): Flow<List<Menu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMenu(menu: Menu)

    // Fungsi untuk menyimpan URL gambar
    @Query("UPDATE tblMenu SET foto = :imageUrl WHERE idmenu = :idmenu")
    suspend fun updateMenuPhoto(idmenu: Int, imageUrl: String)
}


@Dao
interface PesananDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pesanan: Pesanan)

    @Update
    suspend fun update(pesanan: Pesanan)

    @Delete
    suspend fun delete(pesanan: Pesanan)

    @Query("SELECT * from tblPesanan WHERE idpesanan = :idpesanan")
    fun getPesanan(idpesanan: Int): Flow<Pesanan>
    @Query("SELECT * from tblPesanan ORDER BY idpesanan ASC")
    fun getAllPesanan(): Flow<List<Pesanan>>
}

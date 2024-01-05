package project.roomsiswa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SiswaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(siswa: Siswa)

    @Update
    suspend fun update(siswa: Siswa)

    @Delete
    suspend fun delete(siswa: Siswa)

    @Query("SELECT * from tblSiswa WHERE id = :id")
    fun getSiswa(id: Int): Flow<Siswa>

    @Query("SELECT * from tblSiswa ORDER BY nama ASC")
    fun getAllSiswa(): Flow<List<Siswa>>
}

@Dao
interface MapelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mapel: Mapel)

    @Update
    suspend fun update(mapel: Mapel)

    @Delete
    suspend fun delete(mapel: Mapel)

    @Query("SELECT * from tblMapel WHERE id = :id")
    fun getMapel(id: Int): Flow<Mapel>

    @Query("SELECT * from tblMapel ORDER BY namamapel ASC")
    fun getAllMapel(): Flow<List<Mapel>>
}

@Dao
interface PerilakuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(perilaku: Perilaku)

    @Update
    suspend fun update(perilaku: Perilaku)

    @Delete
    suspend fun delete(perilaku: Perilaku)

    @Query("SELECT * from tblPerilaku WHERE id = :id")
    fun getPerilaku(id: Int): Flow<Perilaku>

    @Query("SELECT * from tblPerilaku ORDER BY sanksi ASC")
    fun getAllPerilaku(): Flow<List<Perilaku>>
}



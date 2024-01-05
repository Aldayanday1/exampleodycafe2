package project.roomsiswa.repositori

import kotlinx.coroutines.flow.Flow
import project.roomsiswa.data.CombinedData
import project.roomsiswa.data.Mapel
import project.roomsiswa.data.Perilaku
import project.roomsiswa.data.Siswa

interface RepositoriSiswa {
    fun getAllSiswaStream(): Flow<List<Siswa>>

    fun getSiswaStream(id: Int): Flow<Siswa?>

    fun getCombinedData(): Flow<List<CombinedData>>

    suspend fun insertSiswa(siswa: Siswa)

    suspend fun deleteSiswa(siswa: Siswa)

    suspend fun updateSiswa(siswa: Siswa)
}

interface RepositoriMapel {
    fun getAllMapelStream(): Flow<List<Mapel>>

    fun getMapelStream(id: Int): Flow<Mapel?>

    suspend fun insertMapel(mapel: Mapel)

    suspend fun deleteMapel(mapel: Mapel)

    suspend fun updateMapel(mapel: Mapel)
}

interface RepositoriPerilaku {
    fun getAllPerilakuStream(): Flow<List<Perilaku>>

    fun getPerilakuStream(id: Int): Flow<Perilaku?>

    suspend fun insertPerilaku(perilaku: Perilaku)

    suspend fun deletePerilaku(perilaku: Perilaku)

    suspend fun updatePerilaku(perilaku: Perilaku)
}




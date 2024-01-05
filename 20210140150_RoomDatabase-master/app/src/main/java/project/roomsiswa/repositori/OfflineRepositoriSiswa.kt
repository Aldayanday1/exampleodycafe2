package project.roomsiswa.repositori

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import project.roomsiswa.data.CombinedData
import project.roomsiswa.data.Mapel
import project.roomsiswa.data.MapelDao
import project.roomsiswa.data.Perilaku
import project.roomsiswa.data.PerilakuDao
import project.roomsiswa.data.Siswa
import project.roomsiswa.data.SiswaDao

class OfflineRepositoriSiswa(
    private val siswaDao: SiswaDao,
    private val mapelDao: MapelDao,
    private val perilakuDao: PerilakuDao
) : RepositoriSiswa {
    override fun getCombinedData(): Flow<List<CombinedData>> {
        return siswaDao.getAllSiswa().map { listSiswa ->
            listSiswa.map { siswa ->
                val mapel = mapelDao.getMapel(siswa.id).firstOrNull() ?: Mapel(0, "", "", "")
                val perilaku = perilakuDao.getPerilaku(siswa.id).firstOrNull() ?: Perilaku(0, "", "")
                CombinedData(siswa, mapel, perilaku)
            }
        }
    }
    override fun getAllSiswaStream(): Flow<List<Siswa>> = siswaDao.getAllSiswa()

    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)

    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)

    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)

    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)
}


class OfflineRepositoriMapel(private val mapelDao: MapelDao) : RepositoriMapel {

    override fun getAllMapelStream(): Flow<List<Mapel>> = mapelDao.getAllMapel()

    override fun getMapelStream(id: Int): Flow<Mapel?> = mapelDao.getMapel(id)

    override suspend fun insertMapel(mapel: Mapel) = mapelDao.insert(mapel)

    override suspend fun deleteMapel(mapel: Mapel) = mapelDao.delete(mapel)

    override suspend fun updateMapel(mapel: Mapel) = mapelDao.update(mapel)
}


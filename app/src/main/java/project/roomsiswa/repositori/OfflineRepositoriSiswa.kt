package project.roomsiswa.repositori

import kotlinx.coroutines.flow.Flow
import project.roomsiswa.data.Menu
import project.roomsiswa.data.MenuDao
import project.roomsiswa.data.Pesanan
import project.roomsiswa.data.PesananDao

class OfflineRepositoriSiswa(
//    private val siswaDao: SiswaDao,
    private val menuDao: MenuDao,
    private val pesananDao: PesananDao,

):RepositoriPesanan, RepositoriMenu {

//    override fun getAllSiswaStream(): Flow<List<Siswa>> = siswaDao.getAllSiswa()
//    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)
//
//    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)
//
//    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)
//
//    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)
//
    override suspend fun insertMenu(menu: Menu) = menuDao.insert(menu)

    override suspend fun updateMenu(menu: Menu) = menuDao.update(menu)

    override suspend fun deleteMenu(menu: Menu) = menuDao.delete(menu)

    override fun getMenuStream(id: Int): Flow<Menu?> = menuDao.getMenu(id)

    override fun getAllMenuStream(): Flow<List<Menu>> = menuDao.getAllMenu()

    // Search Fiture Menu
    override fun searchMenu(query: String): Flow<List<Menu>> = menuDao.getAllMenu()

    override suspend fun insertSearchMenu(menu: Menu) = menuDao.insert(menu)

    // Image Fiture
     override suspend fun updateMenuPhoto(idmenu: Int, imageUrl: String) {
        menuDao.updateMenuPhoto(idmenu, imageUrl)
    }

    override suspend fun insertPesanan(pesanan: Pesanan) = pesananDao.insert(pesanan)

    override suspend fun updatePesanan(pesanan: Pesanan) = pesananDao.update(pesanan)

    override suspend fun deletePesanan(pesanan: Pesanan) = pesananDao.delete(pesanan)

    override fun getPesananStream(id: Int): Flow<Pesanan?> = pesananDao.getPesanan(id)

    override fun getAllPesananStream(): Flow<List<Pesanan>> = pesananDao.getAllPesanan()

    // Search Fiture Pesanan
    override fun searchPesanan(query: String): Flow<List<Pesanan>> = pesananDao.getAllPesanan()
    override suspend fun insertSearchPesanan(pesanan: Pesanan) = pesananDao.insert(pesanan)
}
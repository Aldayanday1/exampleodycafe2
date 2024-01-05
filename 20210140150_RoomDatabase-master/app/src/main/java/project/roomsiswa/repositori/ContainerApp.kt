package project.roomsiswa.repositori

import android.content.Context
import project.roomsiswa.data.DatabaseSiswa

interface ContainerApp {
    val repositoriSiswa : RepositoriSiswa
    val repositoriMapel: RepositoriMapel
}

class ContainerDataApp(private val context: Context) : ContainerApp {

    /**DatabaseSiswa disini memanggil siswaDao(), mapelDao(), dan perilakuDao() untuk kemudian mengakses objek DAO*/
    private val database = DatabaseSiswa.getDatabase(context)

    override val repositoriSiswa: RepositoriSiswa by lazy {
        OfflineRepositoriSiswa(
            database.siswaDao(),
            database.mapelDao(),
            database.perilakuDao()
        )
    }

    override val repositoriMapel: RepositoriMapel by lazy {
        OfflineRepositoriMapel(database.mapelDao())
    }
}
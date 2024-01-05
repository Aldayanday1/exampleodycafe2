package project.roomsiswa.repositori

import android.content.Context
import project.roomsiswa.data.DatabaseSiswa
import project.roomsiswa.data.MenuDao
import project.roomsiswa.data.PesananDao

interface ContainerApp {
    val repositoriMenu : RepositoriMenu
    val repositoriPesanan : RepositoriPesanan
}

class ContainerDataApp(private val context: Context): ContainerApp {

    private val menuDao: MenuDao = DatabaseSiswa.getDatabase(context).menuDao()
    private val pesananDao: PesananDao = DatabaseSiswa.getDatabase(context).pesananDao()

    override val repositoriMenu: RepositoriMenu by lazy {
        OfflineRepositoriSiswa(menuDao, pesananDao)
    }
    override val repositoriPesanan: RepositoriPesanan by lazy {
        OfflineRepositoriSiswa(menuDao, pesananDao)
    }
}
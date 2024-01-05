package project.roomsiswa

import android.app.Application
import project.roomsiswa.repositori.ContainerApp
import project.roomsiswa.repositori.ContainerDataApp

class AplikasiSiswa : Application() {
    /*
    * AppContainer instance digunakan oleh kelas-kelas lainnya untuk medapatkan dependensi
    */
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}
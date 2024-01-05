package project.roomsiswa.model

import android.app.PictureInPictureUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import project.roomsiswa.data.Mapel
import project.roomsiswa.data.Perilaku
import project.roomsiswa.data.Siswa
import project.roomsiswa.repositori.OfflineRepositoriSiswa
import project.roomsiswa.repositori.RepositoriMapel
import project.roomsiswa.repositori.RepositoriPerilaku
import project.roomsiswa.repositori.RepositoriSiswa

class EntryViewModel(
    private val repositoriSiswa: RepositoriSiswa,
    private val repositoriMapel: RepositoriMapel,
    private val repositoriPerilaku: RepositoriPerilaku
): ViewModel(){

    /*
    * Berisi status Siswa saat ini
    */
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set
    var uiStateSiswaMapel by mutableStateOf(UIStateMapel())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa,
    ): Boolean {
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
                    && namamapel.isNotBlank() && guru.isNotBlank() && jam.isNotBlank()
                    && sanksi.isNotBlank() && penghargaan.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))

    }

    /*Fungsi untuk menyimpan data yang di-entry*/
    suspend fun saveSiswa() {
        if (validasiInput()) {
            repositoriSiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}
/*
* Mewakili Status Ui untuk Siswa
*/

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false,
)

data class UIStateMapel(
    val detailMapel: DetailMapel = DetailMapel(),
    val isEntryValid: Boolean = false,
)



data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = "",
    val namamapel : String = "",
    val guru : String = "",
    val jam : String = "",
    val sanksi : String = "",
    val penghargaan : String = "",
)
data class DetailMapel(
    val id : Int = 0,
    val namamapel : String = "",
    val guru : String = "",
    val jam : String = ""
)
data class DetailPerilaku(
    val id : Int = 0,
    val sanksi: String,
    val penghargaan: String
)

/*Fungsi untuk mengkonversi data input ke data dalam tabel sesuai jenis datanya*/
fun DetailSiswa.toSiswa(): Siswa = Siswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon,
)

fun DetailMapel.toSiswa(): Mapel = Mapel(
    id = id,
    namamapel = namamapel,
    guru = guru,
    jam = jam
)

fun DetailPerilaku.toSiswa(): Perilaku = Perilaku(
    id = id,
    sanksi = sanksi,
    penghargaan = penghargaan
)






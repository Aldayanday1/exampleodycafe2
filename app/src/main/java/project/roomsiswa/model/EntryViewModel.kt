package project.roomsiswa.model

import android.app.PictureInPictureUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.firstOrNull
import project.roomsiswa.data.Menu
import project.roomsiswa.data.Pesanan
import project.roomsiswa.repositori.RepositoriMenu
import project.roomsiswa.repositori.RepositoriPesanan

class EntryViewModel(
    private val repositoriMenu: RepositoriMenu,
    private val repositoriPesanan: RepositoriPesanan
): ViewModel() {

    /* ------------- MENU ------------ */

    /*
    * Berisi status Menu saat ini
    */
    var uiStateMenu by mutableStateOf(UIStateMenu())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInputMenu(uiState: DetailMenu = uiStateMenu.detailMenu): Boolean {
        return with(uiState) {
            idmenu != 0 && menu.isNotBlank() && harga.isNotBlank() && ketersediaan.isNotBlank() && kategori.isNotBlank()
        }
    }

    fun updateUiStateMenu(detailMenu: DetailMenu) {
        uiStateMenu = UIStateMenu(detailMenu = detailMenu, isEntryValid = validasiInputMenu(detailMenu))
    }

    // insert
    suspend fun saveMenu() {
        if (validasiInputMenu()) {
            repositoriMenu.insertMenu(uiStateMenu.detailMenu.toMenu())
        }
    }

    /* ------------- PESANAN ------------ */

    /*
    * Berisi status Pesanan saat ini
    */
    var uiStatePesanan by mutableStateOf(UIStatePesanan())
        private set

    /* Fungsi untuk memvalidasi input Pesanan */
    private fun validasiInputPesanan(uiState: DetailPesanan = uiStatePesanan.detailPesanan): Boolean {
        return with(uiState) {
            idpesanan != 0 && nama.isNotBlank() && detail.isNotBlank() && metode.isNotBlank() && tanggal.isNotBlank() && idmenuforeignkey != 0
        }
    }

    fun updateUiStatePesanan(detailPesanan: DetailPesanan) {
        uiStatePesanan = UIStatePesanan(
            detailPesanan = detailPesanan,
            isEntryValid = validasiInputPesanan(detailPesanan)
        )
    }

    // insert Pesanan
    suspend fun savePesanan() {
        if (validasiInputPesanan()) {
            repositoriPesanan.insertPesanan(uiStatePesanan.detailPesanan.toPesanan())
        }
    }
    fun getRepositoriMenu(): RepositoriMenu {
        return repositoriMenu
    }
    suspend fun populateIdMenuForeignKey(menuId: Int) {
        val menu = repositoriMenu.getMenuStream(menuId).firstOrNull()
        menu?.let {
            val detailPesanan = uiStatePesanan.detailPesanan.copy(idmenuforeignkey = it.idmenu)
            updateUiStatePesanan(detailPesanan)
        } ?: run {
            // Handle jika data dari idmenu tidak tersedia
            // Misalnya, menampilkan pesan error
            // Munculkan pesan alert atau ubah status untuk menunjukkan bahwa data kosong
            // Misalnya, dengan mengubah isEntryValid di UIStatePesanan menjadi false
            uiStatePesanan = UIStatePesanan(isEntryValid = false)
        }
    }


}

/* ------------- MENU ------------ */

// Mewakili Status Ui untuk Menu
data class UIStateMenu(
    val detailMenu: DetailMenu = DetailMenu(),
    val isEntryValid: Boolean = false
)
data class DetailMenu(
    val idmenu: Int? = null,
    val menu: String = "",
    val harga: String = "",
    val ketersediaan: String = "",
    val kategori: String = "",
)

// Fungsi untuk mengkonversi data input ke data dalam tabel Menu
fun DetailMenu.toMenu(): Menu = Menu(
    idmenu = idmenu ?: 0,
    menu = menu,
    harga = harga,
    ketersediaan = ketersediaan,
    kategori = kategori
)
// Fungsi untuk mengubah Menu menjadi UIStateMenu
fun Menu.toUiStateMenu(isEntryValid: Boolean = false): UIStateMenu = UIStateMenu(
    detailMenu = this.toDetailMenu(),
    isEntryValid = isEntryValid
)

// Fungsi untuk mengubah Menu menjadi DetailMenu
fun Menu.toDetailMenu(): DetailMenu = DetailMenu(
    idmenu = idmenu,
    menu = menu,
    harga = harga,
    ketersediaan = ketersediaan,
    kategori = kategori
)

/* ------------- PESANAN ------------ */

// Mewakili Status Ui untuk Pesanan
data class UIStatePesanan(
    val detailPesanan: DetailPesanan = DetailPesanan(),
    val isEntryValid: Boolean = false
)

data class DetailPesanan(
    val idpesanan: Int? = null,
    val nama: String = "",
    val detail: String = "",
    val metode: String = "",
    val tanggal: String = "",
    val idmenuforeignkey: Int? = null,
)

// Fungsi untuk mengkonversi data input ke data dalam tabel Pesanan
fun DetailPesanan.toPesanan(): Pesanan = Pesanan(
    idpesanan = idpesanan ?: 0,
    nama = nama,
    detail = detail,
    metode = metode,
    tanggal = tanggal,
    idMenuForeignKey = idmenuforeignkey ?: 0, // Sesuaikan dengan nilai yang diinginkan
)

// Fungsi untuk mengubah Pesanan menjadi UIStatePesanan
fun Pesanan.toUiStatePesanan(isEntryValid: Boolean = false): UIStatePesanan = UIStatePesanan(
    detailPesanan = this.toDetailPesanan(),
    isEntryValid = isEntryValid
)

// Fungsi untuk mengubah Pesanan menjadi DetailPesanan
fun Pesanan.toDetailPesanan(): DetailPesanan = DetailPesanan(
    idpesanan = idpesanan,
    nama = nama,
    detail = detail,
    metode = metode,
    tanggal = tanggal,
    idmenuforeignkey = idMenuForeignKey
)

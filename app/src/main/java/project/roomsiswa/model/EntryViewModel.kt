package project.roomsiswa.model

import android.app.PictureInPictureUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import project.roomsiswa.data.Menu
import project.roomsiswa.data.MenuDao
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
    private fun validasiInputPesanan(uiState: DetailPesanan = uiStatePesanan.detailPesanan, menuItems: List<Menu>): Boolean {
        return with(uiState) {
            idpesanan != 0 && nama.isNotBlank() && detail.isNotBlank() && metode.isNotBlank() && tanggal.isNotBlank() &&
                    menuItems.any { it.menu == idmenuforeignkey }
        }
    }

    fun updateUiStatePesanan(detailPesanan: DetailPesanan, menuItems: List<Menu>) {
        uiStatePesanan = UIStatePesanan(
            detailPesanan = detailPesanan,
            isEntryValid = validasiInputPesanan(detailPesanan, menuItems)
        )
    }

    // insert Pesanan
    suspend fun savePesanan(menuItems: List<Menu>) {
        if (validasiInputPesanan(menuItems = menuItems)) {
            repositoriPesanan.insertPesanan(uiStatePesanan.detailPesanan.toPesanan())
        }
    }

    /*--------DROPDOWN--------*/

    // Properti untuk daftar menu
    private val _menuItems = MutableStateFlow<List<Menu>>(emptyList())
    val menuItems: StateFlow<List<Menu>> = _menuItems

    init {
        // Panggil fungsi untuk mendapatkan daftar menu dari repositori
        getAllMenu()
    }

    // Fungsi untuk mendapatkan daftar menu dari repositori
    private fun getAllMenu() {
        viewModelScope.launch {
            repositoriMenu.getAllMenuStream().collect { menuList ->
                // Update daftar menu saat data berubah
                _menuItems.value = menuList
            }
        }
    }

    /*--------SEARCH MENU--------*/

    private val _searchResultMenu = MutableStateFlow<List<Menu>>(emptyList())
    val searchResult: StateFlow<List<Menu>> = _searchResultMenu.asStateFlow()

    fun searchMenu(query: String) {
        viewModelScope.launch {
            repositoriMenu.searchMenu(query).collect { menuList ->
                _searchResultMenu.value = menuList
            }
        }
    }

    /*--------SEARCH MENU--------*/

    private val _searchResultPesanan = MutableStateFlow<List<Pesanan>>(emptyList())
    val pesananResult: StateFlow<List<Pesanan>> = _searchResultPesanan.asStateFlow()

    fun searchPesanan(query: String) {
        viewModelScope.launch {
            repositoriPesanan.searchPesanan(query).collect { pesananList ->
                _searchResultPesanan.value = pesananList
            }
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
    val foto: String = "",
)

// Fungsi untuk mengkonversi data input ke data dalam tabel Menu
fun DetailMenu.toMenu(): Menu = Menu(
    idmenu = idmenu ?: 0,
    menu = menu,
    harga = harga,
    ketersediaan = ketersediaan,
    kategori = kategori,
    foto = foto
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
    kategori = kategori,
    foto = foto
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
    val idmenuforeignkey: String = "",
)

// Fungsi untuk mengkonversi data input ke data dalam tabel Pesanan
fun DetailPesanan.toPesanan(): Pesanan = Pesanan(
    idpesanan = idpesanan ?: 0,
    nama = nama,
    detail = detail,
    metode = metode,
    tanggal = tanggal,
    idMenuForeignKey = idmenuforeignkey, // Sesuaikan dengan nilai yang diinginkan
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

package project.roomsiswa.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import project.roomsiswa.data.Menu
import project.roomsiswa.repositori.RepositoriMenu
import project.roomsiswa.repositori.RepositoriPesanan
import project.roomsiswa.ui.halaman.ItemEditMenuDestination
import project.roomsiswa.ui.halaman.ItemEditPesananDestination

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriMenu: RepositoriMenu,
    private val repositoriPesanan: RepositoriPesanan

) : ViewModel(){
    var menuUiState by mutableStateOf(UIStateMenu())
        private set
    var pesananUiState by mutableStateOf(UIStatePesanan())
        private set

    private val editmenuId: Int = checkNotNull(savedStateHandle[ItemEditMenuDestination.editIdArg])
    private val editpesananId: Int = checkNotNull(savedStateHandle[ItemEditPesananDestination.editIdArg])

    // get (id)
    init {
        viewModelScope.launch {
            menuUiState = repositoriMenu.getMenuStream(editmenuId)
                .filterNotNull()
                .first()
                .toUiStateMenu(true)
            pesananUiState = repositoriPesanan.getPesananStream(editpesananId)
                .filterNotNull()
                .first()
                .toUiStatePesanan(true)
        }
    }

    suspend fun updateMenu(){
        if (validasiInputMenu(menuUiState.detailMenu)){
            repositoriMenu.updateMenu(menuUiState.detailMenu.toMenu())
        }
        else {
            println("Menu Data not valid")
        }
    }
    suspend fun updatePesanan(menuItems: List<Menu>) {
        if (validasiInputPesanan(pesananUiState.detailPesanan, menuItems)) {
            repositoriPesanan.updatePesanan(pesananUiState.detailPesanan.toPesanan())
        } else {
            println("Pesanan Data not valid")
        }
    }



    fun updateUiStateMenu(detailMenu: DetailMenu){
        menuUiState = UIStateMenu(
            detailMenu = detailMenu,
            isEntryValid = validasiInputMenu(detailMenu))
    }
    fun updateUiStatePesanan(detailPesanan: DetailPesanan, menuItems: List<Menu>) {
        pesananUiState = UIStatePesanan(
            detailPesanan = detailPesanan,
            isEntryValid = validasiInputPesanan(detailPesanan, menuItems),

        )
    }

    private fun validasiInputMenu(uiState: DetailMenu = menuUiState.detailMenu): Boolean {
        return with(uiState) {
            idmenu != null && menu.isNotBlank() && harga.isNotBlank() && ketersediaan.isNotBlank() && kategori.isNotBlank()
        }
    }
    private fun validasiInputPesanan(uiState: DetailPesanan = pesananUiState.detailPesanan, menuItems: List<Menu>): Boolean {
        return with(uiState) {
            idpesanan != 0 && nama.isNotBlank() && detail.isNotBlank() && metode.isNotBlank() && tanggal.isNotBlank()
                    && menuItems.any { it.menu == idmenuforeignkey }
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
}


































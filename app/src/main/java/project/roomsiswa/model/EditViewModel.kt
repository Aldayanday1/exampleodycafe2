package project.roomsiswa.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import project.roomsiswa.repositori.RepositoriMenu
import project.roomsiswa.repositori.RepositoriPesanan
import project.roomsiswa.ui.halaman.ItemEditDestination
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
    suspend fun updatePesanan(){
        if (validasiInputPesanan(pesananUiState.detailPesanan)){
            repositoriPesanan.updatePesanan(pesananUiState.detailPesanan.toPesanan())
        }
        else {
            println("Pesanan Data not valid")
        }
    }

    fun updateUiStateMenu(detailMenu: DetailMenu){
        menuUiState = UIStateMenu(
            detailMenu = detailMenu,
            isEntryValid = validasiInputMenu(detailMenu))
    }
    fun updateUiStatePesanan(detailPesanan: DetailPesanan){
        pesananUiState = UIStatePesanan(
            detailPesanan = detailPesanan,
            isEntryValid = validasiInputPesanan(detailPesanan))
    }

    private fun validasiInputMenu(uiState: DetailMenu = menuUiState.detailMenu): Boolean {
        return with(uiState) {
            idmenu != 0 && menu.isNotBlank() && harga.isNotBlank() && ketersediaan.isNotBlank() && kategori.isNotBlank()
        }
    }
    private fun validasiInputPesanan(uiState: DetailPesanan = pesananUiState.detailPesanan): Boolean {
        return with(uiState) {
            idpesanan != 0 && nama.isNotBlank() && detail.isNotBlank() && metode.isNotBlank() && tanggal.isNotBlank()
        }
    }
}


































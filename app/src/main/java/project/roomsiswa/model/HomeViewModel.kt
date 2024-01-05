package project.roomsiswa.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import project.roomsiswa.data.Menu
import project.roomsiswa.data.Pesanan
import project.roomsiswa.repositori.RepositoriMenu
import project.roomsiswa.repositori.RepositoriPesanan

class HomeViewModel(
    private val repositoriMenu: RepositoriMenu,
    private val repositoriPesanan: RepositoriPesanan

):ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // get all menu data (ASC = kategori & DESC = ketersediaan)
    val menuUiState: StateFlow<MenuUiState> = repositoriMenu.getAllMenuStream()
        .map { MenuUiState(listMenu = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = MenuUiState()
        )

    // get all menu data (ASC = nama)
    val pesananUiState: StateFlow<PesananUiState> = repositoriPesanan.getAllPesananStream()
        .map { PesananUiState(listPesanan = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = PesananUiState()
        )

    data class MenuUiState(
        val listMenu: List<Menu> = listOf()
    )
    data class PesananUiState(
        val listPesanan: List<Pesanan> = listOf()
    )
}
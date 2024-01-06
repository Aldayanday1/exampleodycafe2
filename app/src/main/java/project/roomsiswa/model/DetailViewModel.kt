package project.roomsiswa.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import project.roomsiswa.repositori.RepositoriMenu
import project.roomsiswa.repositori.RepositoriPesanan
import project.roomsiswa.ui.halaman.DetailsMenuDestination

class DetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoriMenu: RepositoriMenu,
    private val repositoriPesanan: RepositoriPesanan

) : ViewModel(){

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val detailId: Int = checkNotNull(savedStateHandle[DetailsMenuDestination.detailIdArg])

    // get id Menu
    val uiStateMenu: StateFlow<ItemDetailsMenuUiState> =
        repositoriMenu.getMenuStream(detailId)
            .filterNotNull()
            .map {
                ItemDetailsMenuUiState(detailMenu = it.toDetailMenu())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsMenuUiState()
            )

    // get id Pesanan
    val uiStatePesanan: StateFlow<ItemDetailsPesananUiState> =
        repositoriPesanan.getPesananStream(detailId)
            .filterNotNull()
            .map {
                ItemDetailsPesananUiState(detailPesanan = it.toDetailPesanan())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsPesananUiState()
            )

    // delete Menu
    suspend fun deleteMenuItem(){
        repositoriMenu.deleteMenu(uiStateMenu.value.detailMenu.toMenu())
    }

    // delete Pesanan
    suspend fun deletePesananItem(){
        repositoriPesanan.deletePesanan(uiStatePesanan.value.detailPesanan.toPesanan())
    }
}

data class ItemDetailsMenuUiState(
    val detailMenu: DetailMenu = DetailMenu(),
)

data class ItemDetailsPesananUiState(
    val detailPesanan: DetailPesanan = DetailPesanan()
)
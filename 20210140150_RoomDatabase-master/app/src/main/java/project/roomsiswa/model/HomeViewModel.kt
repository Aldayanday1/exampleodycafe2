package project.roomsiswa.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import project.roomsiswa.data.CombinedData
import project.roomsiswa.data.Siswa
import project.roomsiswa.repositori.RepositoriMapel
import project.roomsiswa.repositori.RepositoriSiswa

class HomeViewModel(
    private val repositoriSiswa: RepositoriSiswa,
    private val repositoriMapel: RepositoriMapel,

):ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = repositoriSiswa.getCombinedData()
        .map { listCombinedData -> HomeUiState(listCombinedData) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    data class HomeUiState(
        val listCombinedData: List<CombinedData> = listOf()
    )
}
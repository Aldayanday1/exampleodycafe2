package project.roomsiswa.model

import android.text.Spannable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import project.roomsiswa.AplikasiSiswa

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                aplikasiSiswa().container.repositoriMenu,
                aplikasiSiswa().container.repositoriPesanan
            )
        }
        initializer {
            EntryViewModel(
                aplikasiSiswa().container.repositoriMenu,
                aplikasiSiswa().container.repositoriPesanan
            )
        }
        initializer {
            DetailsViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoriMenu,
                aplikasiSiswa().container.repositoriPesanan
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoriMenu,
                aplikasiSiswa().container.repositoriPesanan
            )
        }
    }
}


/*
* Fungsi ekstensi query untuk objek [Application] dan mengembalikan sebuah
* instance dari [AplikasiSiswa].
*/

fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa)
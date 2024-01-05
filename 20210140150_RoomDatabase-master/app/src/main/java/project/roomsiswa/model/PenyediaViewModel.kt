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
            HomeViewModel(aplikasiSiswa().container.repositoriSiswa)
        }
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositoriSiswa)
        }
        initializer {
            DetailsViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoriSiswa,
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoriSiswa,
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
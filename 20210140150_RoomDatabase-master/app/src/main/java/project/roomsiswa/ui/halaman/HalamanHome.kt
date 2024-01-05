import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import project.roomsiswa.R
import project.roomsiswa.data.CombinedData
import project.roomsiswa.data.Mapel
import project.roomsiswa.data.Perilaku
import project.roomsiswa.data.Siswa
import project.roomsiswa.model.HomeViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.navigasi.DestinasiNavigasi
import project.roomsiswa.navigasi.SiswaTopAppBar


object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Simpan status UI dari ViewModel
    val uiStateSiswa by viewModel.homeUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        },
    ) { innerPadding ->
        BodyHome(
            itemSiswa = uiStateSiswa.listCombinedData,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onSiswaClick = onDetailClick
        )
    }
}

data class CombinedData(
    val siswa: Siswa,
    val mapel: Mapel?,
    val perilaku: Perilaku?
)

@Composable
fun BodyHome(
    itemSiswa: List<CombinedData>,
    modifier: Modifier = Modifier,
    onSiswaClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = itemSiswa, key = { it.siswa.id }) { combinedData ->
            DataSiswa(
                combinedData = combinedData,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onSiswaClick(combinedData.siswa.id) }
            )
            combinedData.mapel?.let { mapel ->
                Text(
                    text = "Mapel: ${mapel.namamapel}, Guru: ${mapel.guru}, Jam: ${mapel.jam}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            combinedData.perilaku?.let { perilaku ->
                Text(
                    text = "Sanksi: ${perilaku.sanksi}, Penghargaan: ${perilaku.penghargaan}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun DataSiswa(
    combinedData: CombinedData,
    modifier: Modifier = Modifier
) {
    val siswa = combinedData.siswa
    val mapel = combinedData.mapel ?: Mapel(0, "", "", "")
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                )
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mapel.namamapel,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mapel.guru,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mapel.jam,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

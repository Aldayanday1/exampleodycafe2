package project.roomsiswa.ui.halaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import project.roomsiswa.R
import project.roomsiswa.data.Pesanan
import project.roomsiswa.model.HomeViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiListPesanan : DestinasiNavigasi {
    override val route = "listpesanan"
    override val titleRes = R.string.list_pesanan
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesananListScreen(
    onDetailPesananListClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DestinasiListPesanan.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.alpha(0.5f),
            )
        },
    ){
            innerPadding ->

        val uiStatePesanan by viewModel.pesananUiState.collectAsState()

        //------- SEARCH PESANAN -------/
        val searchQueryState = remember { mutableStateOf("") }

        // filter = pemfilteran terhadap elemen yg ditampilkan (pesanan)
        //ignorecase true = mengabaikan kondisi dari huruf kapital / huruf kecilnya

        val filteredPesanan = uiStatePesanan.listPesanan.filter {
            it.idMenuForeignKey.contains(searchQueryState.value, ignoreCase = true)
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 15.dp,
                        bottom = 15.dp
                    ),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchQueryState.value,
                        onValueChange = { searchQueryState.value = it },
                        label = { Text("Search Pesanan") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                end = 35.dp,
                                start = 35.dp,
                                bottom = 10.dp
                            )
                    )
                }
                if (filteredPesanan.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 75.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.kucingsyedihpesanan),
                                    contentDescription = null, // Deskripsi konten, bisa dikosongkan jika tidak diperlukan
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .size(400.dp)
                                )
                            }
                        }
                    }
                } else {
                    items(filteredPesanan) { pesanan ->
                        DataListPesanan(
                            pesanan = pesanan,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp)
                                // perwakilan atas pemanggilan data (pesanan)
                                .clickable { onDetailPesananListClick(pesanan.idpesanan) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DataListPesanan(
    pesanan: Pesanan,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .size(width = 350.dp, height = 305.dp)
            .alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.idpesanan1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.idpesanan.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.nama1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.nama,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.nav_menu),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.idMenuForeignKey.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.detail1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.detail,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.metode1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.metode,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.tanggal1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = pesanan.tanggal,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}



package project.roomsiswa.ui.halaman

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import project.roomsiswa.R
import project.roomsiswa.data.Pesanan
import project.roomsiswa.model.DetailsViewModel
import project.roomsiswa.model.ItemDetailsPesananUiState
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.model.toPesanan
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DetailsPesananDestination : DestinasiNavigasi {
    override val route = "item_details_pesanan"
    override val titleRes = R.string.title_detail_pesanan
    const val detailIdArg = "itemId"
    val routeWithArgs = "$route/{$detailIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPesananScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel(factory = PenyediaViewModel.Factory) /** -> Penyedia View Model */
){
    val uiState = viewModel.uiStatePesanan.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Deklarasi variabel deleteConfirmationRequired
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DetailsPesananDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                modifier = Modifier.alpha(0.5f),
            )
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        uiState.value.detailPesanan.idpesanan?.let { id ->
                            navigateToEditItem(id)
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.alpha(0.8f),
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit_menu),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                FloatingActionButton(
                    onClick = {
                        // Mengatur status untuk menampilkan dialog konfirmasi
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.alpha(0.8f),
                    content = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                        )
                    }
                )
            }
        },
    ) { innerPadding ->

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            ItemPesananDetailBody(
                itemDetailsPesananUiState = uiState.value,
                onDelete = {
                    coroutineScope.launch {
                        viewModel.deletePesananItem()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                deleteConfirmationRequired = deleteConfirmationRequired,
                onDeleteCancel = {
                    deleteConfirmationRequired = false // Reset status deleteConfirmationRequired di sini
                }
            )
        }
    }
}

@Composable
private fun ItemPesananDetailBody(
    itemDetailsPesananUiState: ItemDetailsPesananUiState,
    onDelete: () -> Unit,
    deleteConfirmationRequired: Boolean,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        ItemPesananDetails(
            pesanan = itemDetailsPesananUiState.detailPesanan.toPesanan(),
            modifier = Modifier.fillMaxWidth()
        )

        if (deleteConfirmationRequired){
            DeletePesananConfirmationDialog(
                onDeleteConfirm = {
                    onDelete()
                },
                onDeleteCancel = {
                    onDeleteCancel() // Panggil onDeleteCancel untuk mereset status
                },
                deleteConfirmationRequired = deleteConfirmationRequired,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        LaunchedEffect(deleteConfirmationRequired) {
            if (!deleteConfirmationRequired) {
                onDeleteCancel()
            }
        }
    }
}

@Composable
fun ItemPesananDetails(
    pesanan : Pesanan, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .padding(top = 145.dp)
            .size(width = 350.dp, height = 315.dp)
            .alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            ItemPesananDetailsRow(
                labelResID = R.string.idpesanan1,
                itemDetail = pesanan.idpesanan.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemPesananDetailsRow(
                labelResID = R.string.nama1,
                itemDetail = pesanan.nama,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemPesananDetailsRow(
                labelResID = R.string.idmenu1,
                itemDetail = pesanan.idMenuForeignKey.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemPesananDetailsRow(
                labelResID = R.string.detail1,
                itemDetail = pesanan.detail,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemPesananDetailsRow(
                labelResID = R.string.metode1,
                itemDetail = pesanan.metode,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemPesananDetailsRow(
                labelResID = R.string.tanggal1,
                itemDetail = pesanan.tanggal,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }

    }
}

@Composable
fun ItemPesananDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
){
    Row (
        modifier = modifier
            .padding(top = 15.dp),
    ){
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeletePesananConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    deleteConfirmationRequired: Boolean, // Gunakan parameter baru sebagai trigger
    modifier: Modifier = Modifier
){
    if (deleteConfirmationRequired) { // Ubah penanganan tampilan dialog berdasarkan status
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(stringResource(id = R.string.attention))},
            text = { Text(text = stringResource(id = R.string.delete))},
            modifier = modifier,
            dismissButton = {
                TextButton(onClick = {
                    onDeleteCancel() // Panggil onDeleteCancel untuk mereset status
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            confirmButton = {
                TextButton(onClick = onDeleteConfirm) {
                    Text(text = stringResource(id = R.string.yes))
                }
            }
        )
    }
}
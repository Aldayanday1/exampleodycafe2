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
import project.roomsiswa.data.Menu
import project.roomsiswa.model.DetailsViewModel
import project.roomsiswa.model.ItemDetailsMenuUiState
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.model.toMenu
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DetailsMenuDestination : DestinasiNavigasi {
    override val route = "item_details_menu"
    override val titleRes = R.string.title_detail_menu
    const val detailIdArg = "itemId"
    val routeWithArgs = "$route/{$detailIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsMenuScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel(factory = PenyediaViewModel.Factory) /** -> Penyedia View Model */
){
    val uiState = viewModel.uiStateMenu.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Deklarasi variabel deleteConfirmationRequired
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DetailsMenuDestination.titleRes),
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
                        uiState.value.detailMenu.idmenu?.let { id ->
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

            ItemMenuDetailBody(
                itemDetailsMenuUiState = uiState.value,
                onDelete = {
                    coroutineScope.launch {
                        viewModel.deleteMenuItem()
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
private fun ItemMenuDetailBody(
    itemDetailsMenuUiState: ItemDetailsMenuUiState,
    onDelete: () -> Unit,
    deleteConfirmationRequired: Boolean,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        ItemMenuDetails(
            menu = itemDetailsMenuUiState.detailMenu.toMenu(),
            modifier = Modifier.fillMaxWidth()
        )

        if (deleteConfirmationRequired) {
            DeleteMenuConfirmationDialog(
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
fun ItemMenuDetails(
    menu : Menu, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .padding(top = 165.dp)
            .size(width = 350.dp, height = 275.dp)
            .alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            ItemMenuDetailsRow(
                labelResID = R.string.idmenu1,
                itemDetail = menu.idmenu.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemMenuDetailsRow(
                labelResID = R.string.menu1,
                itemDetail = menu.menu,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemMenuDetailsRow(
                labelResID = R.string.harga1,
                itemDetail = menu.harga,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemMenuDetailsRow(
                labelResID = R.string.ketersediaan1,
                itemDetail = menu.ketersediaan,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemMenuDetailsRow(
                labelResID = R.string.kategori1,
                itemDetail = menu.kategori,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }

    }
}

@Composable
fun ItemMenuDetailsRow(
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
private fun DeleteMenuConfirmationDialog(
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
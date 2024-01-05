package project.roomsiswa.ui.halaman

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import project.roomsiswa.R
import project.roomsiswa.model.EditViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object ItemEditPesananDestination : DestinasiNavigasi {
    override val route = "item_edit_pesanan"
    override val titleRes = R.string.title_edit_pesanan
    const val editIdArg = "itemId"
    val routeWithArgs = "$route/{$editIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditPesananScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CafeTopAppBar(
                title = stringResource(ItemEditPesananDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryPesananBody(
            uiStatePesanan = viewModel.pesananUiState,
            onPesananValueChange = viewModel::updateUiStatePesanan,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePesanan()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


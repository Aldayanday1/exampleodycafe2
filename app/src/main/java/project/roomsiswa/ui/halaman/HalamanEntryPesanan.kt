package project.roomsiswa.ui.halaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import project.roomsiswa.R
import project.roomsiswa.data.Menu
import project.roomsiswa.model.DetailPesanan
import project.roomsiswa.model.EntryViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.model.UIStatePesanan
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiPesananEntry: DestinasiNavigasi {
    override val route = "item_entry_pesanan"
    override val titleRes = R.string.title_entry_pesanan
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPesananScreen(
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    /** Modifier untuk Behavior -> agar Appbar Menyusut saat digulir kebawah*/
    modifier: Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutinScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mendapatkan daftar menu dari ViewModel
    val menuItems by viewModel.menuItems.collectAsState()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DestinasiPesananEntry.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        }
    ){ innerPadding ->
        EntryPesananBody(
            uiStatePesanan = viewModel.uiStatePesanan,
            onPesananValueChange = { detailPesanan -> viewModel.updateUiStatePesanan(detailPesanan, menuItems) },
            onSaveClick = {
                coroutinScope.launch {
                    viewModel.savePesanan(menuItems)
                    navigateBack()
                }
            },
            menuItems = menuItems, // Menggunakan menuItems dari parameter
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryPesananBody(
    uiStatePesanan: UIStatePesanan,
    onPesananValueChange: (DetailPesanan) -> Unit,
    onSaveClick: () -> Unit,
    menuItems: List<Menu>,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_Large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        FormInputPesanan(
            detailPesanan = uiStatePesanan.detailPesanan,
            onValueChange = onPesananValueChange,
            modifier = Modifier.fillMaxWidth(),
            menuItems = menuItems // Menggunakan menuItems dari parameter
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStatePesanan.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.btn_submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPesanan(
    detailPesanan: DetailPesanan,
    modifier: Modifier = Modifier,
    onValueChange: (DetailPesanan) -> Unit = {},
    enabled: Boolean = true,
    menuItems: List<Menu>, // Tambahkan parameter untuk menampung daftar menu
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){


        /** OutlinedTextField memerlukan String sbg nilai value*/
        OutlinedTextField(
            value = detailPesanan.idpesanan?.toString() ?: "",
            onValueChange = {
                onValueChange(
                    /** it = nilai yg dimasukkan */
                    if (it.isEmpty()) detailPesanan.copy(idpesanan = null)
                    else detailPesanan.copy(idpesanan = it.toIntOrNull())
                )
            },
            label = { Text(stringResource(R.string.idpesanan)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = detailPesanan.nama,
            onValueChange = {onValueChange(detailPesanan.copy(nama = it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPesanan.detail,
            onValueChange = {onValueChange(detailPesanan.copy(detail = it)) },
            label = { Text(stringResource(R.string.detail)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPesanan.metode,
            onValueChange = {onValueChange(detailPesanan.copy(metode = it)) },
            label = { Text(text = stringResource(R.string.metode)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPesanan.tanggal,
            onValueChange = {onValueChange(detailPesanan.copy(tanggal = it)) },
            label = { Text(text = stringResource(R.string.tanggal)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
//        OutlinedTextField(
//            value = detailPesanan.idmenuforeignkey?.toString() ?: "",
//            onValueChange = {
//                onValueChange(
//                    if (it.isEmpty()) detailPesanan.copy(idmenuforeignkey = null)
//                    else detailPesanan.copy(idmenuforeignkey = it.toIntOrNull())
//                )
//            },
//            label = { Text(stringResource(R.string.menu)) },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )

        var expanded by remember { mutableStateOf(false) }
        val selectedMenuId = detailPesanan.idmenuforeignkey

        OutlinedTextField(
            value = selectedMenuId?.toString() ?: "",
            onValueChange = {}, // Disable text input
            label = { Text(text = stringResource(R.string.menu)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            trailingIcon = {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // Gunakan daftar menuItems untuk mempopulasi DropdownMenuItem
                    for (menuItem in menuItems) {
                        DropdownMenuItem(
                            onClick = {
                                // Perbarui selectedMenuId dan tutup dropdown
                                onValueChange(detailPesanan.copy(idmenuforeignkey = menuItem.idmenu))
                                expanded = false
                            },
                            text = { Text(text = menuItem.menu) }
                        )
                    }
                }
            }
        )

        if (enabled){
            Text(
                text = stringResource(R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        Divider(
            thickness = dimensionResource(R.dimen.padding_small),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )

    }
}
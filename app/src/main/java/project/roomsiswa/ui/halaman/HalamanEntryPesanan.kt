package project.roomsiswa.ui.halaman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
                scrollBehavior = scrollBehavior,
                modifier = Modifier.alpha(0.5f),
            )
        }
    ){ innerPadding ->

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            EntryPesananBody(
                uiStatePesanan = viewModel.uiStatePesanan,
                onPesananValueChange = { detailPesanan ->
                    viewModel.updateUiStatePesanan(detailPesanan, menuItems) },
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
        OutlinedButton(
            onClick = onSaveClick,
            enabled = uiStatePesanan.isEntryValid,
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
                    border = BorderStroke(0.dp, Color.Transparent),  // Menghapus border
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray.copy(alpha = 0.8f),
                contentColor = Color.DarkGray.copy(alpha = 0.4f)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(id = R.string.btn_submit),
                color = Color.White
            )
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
    Card(
        modifier = modifier
            .padding(top = 15.dp)
            .size(width = 0.dp, height = 610.dp)
            .alpha(0.8f),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
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
                /** OutlinedTextField memerlukan String sbg nilai value*/
                OutlinedTextField(
                    value = detailPesanan.nama,
                    onValueChange = { onValueChange(detailPesanan.copy(nama = it)) },
                    label = { Text(stringResource(R.string.nama)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                Divider()

                var expanded by remember { mutableStateOf(false) }
                var selectedMenu: String? by remember { mutableStateOf(null) } // Menggunakan tipe data String nullable untuk menunjukkan pilihan yang kosong

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
                    ) {
                        Text(
                            text = selectedMenu ?: "Select Menu",
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(315.dp),
                    ) {
                        for (menuItem in menuItems) {
                            DropdownMenuItem(
                                onClick = {
                                    // Perbarui selectedMenu dan tutup dropdown
                                    selectedMenu = menuItem.menu
                                    expanded = false
                                    onValueChange(
                                        detailPesanan.copy(
                                            idmenuforeignkey = selectedMenu ?: ""
                                        )
                                    )
                                },
                                text = { Text(text = menuItem.menu) },
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = detailPesanan.detail,
                    onValueChange = { onValueChange(detailPesanan.copy(detail = it)) },
                    label = { Text(stringResource(R.string.detail)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = detailPesanan.metode,
                    onValueChange = { onValueChange(detailPesanan.copy(metode = it)) },
                    label = { Text(text = stringResource(R.string.metode)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = detailPesanan.tanggal,
                    onValueChange = { onValueChange(detailPesanan.copy(tanggal = it)) },
                    label = { Text(text = stringResource(R.string.tanggal)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                Spacer(modifier = Modifier
                    .padding(top = 0.dp, bottom = 0.dp)
                )

                Divider()

                if (enabled) {
                    Text(
                        text = stringResource(R.string.required_field),
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                    )
                }
        }
    }
}
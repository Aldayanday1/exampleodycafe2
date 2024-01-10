package project.roomsiswa.ui.halaman

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import project.roomsiswa.R
import project.roomsiswa.model.DetailMenu
import project.roomsiswa.model.EntryViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.model.UIStateMenu
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiMenuEntry: DestinasiNavigasi {
    override val route = "item_entry_menu"
    override val titleRes = R.string.title_entry_menu
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMenuScreen(
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutinScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DestinasiMenuEntry.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.alpha(0.5f)
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

            EntryMenuBody(
                uiStateMenu = viewModel.uiStateMenu,
                onMenuValueChange = viewModel::updateUiStateMenu,
                onSaveClick = {
                    coroutinScope.launch {
                        viewModel.saveMenu()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()

            )
        }
    }
}

@Composable
fun EntryMenuBody(
    uiStateMenu: UIStateMenu,
    onMenuValueChange: (DetailMenu) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_Large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        FormInputMenu(
            detailMenu = uiStateMenu.detailMenu,
            onValueChange = onMenuValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(
            onClick = onSaveClick,
            enabled = uiStateMenu.isEntryValid,
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
fun FormInputMenu(
    detailMenu: DetailMenu,
    modifier: Modifier = Modifier,
    onValueChange: (DetailMenu) -> Unit = {},
    enabled: Boolean = true
){
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .size(width = 350.dp, height = 510.dp)
            .alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ){
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Column (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ){
                /** OutlinedTextField memerlukan String sbg nilai value*/
                OutlinedTextField(
                    value = detailMenu.idmenu?.toString() ?: "",
                    onValueChange = {
                        onValueChange(
                            /** it = nilai yg dimasukkan */
                            if (it.isEmpty()) detailMenu.copy(idmenu = null)
                            else detailMenu.copy(idmenu = it.toIntOrNull())
                        )
                    },
                    label = { Text(stringResource(R.string.idmenu)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = detailMenu.menu,
                    onValueChange = {onValueChange(detailMenu.copy(menu = it)) },
                    label = { Text(stringResource(R.string.menu)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = detailMenu.harga,
                    onValueChange = {onValueChange(detailMenu.copy(harga = it)) },
                    label = { Text(stringResource(R.string.harga)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = detailMenu.ketersediaan,
                    onValueChange = {onValueChange(detailMenu.copy(ketersediaan = it)) },
                    label = { Text(text = stringResource(R.string.ketersediaan)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = detailMenu.kategori,
                    onValueChange = {onValueChange(detailMenu.copy(kategori = it)) },
                    label = { Text(text = stringResource(R.string.kategori)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                Spacer(modifier = Modifier.padding(top = 0.dp))
                Divider()

                if (enabled){
                    Text(
                        text = stringResource(R.string.required_field),
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
        }
    }
}
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import project.roomsiswa.R
import project.roomsiswa.model.DetailMapel
import project.roomsiswa.model.DetailSiswa
import project.roomsiswa.model.EntryViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.model.UIStateMapel
import project.roomsiswa.navigasi.DestinasiNavigasi
import project.roomsiswa.navigasi.SiswaTopAppBar

object DestinasiEntryMapel: DestinasiNavigasi {
    override val route = "item_entryMapel"
    override val titleRes = R.string.entry_siswamapel
}
@OptIn(ExperimentalMaterial3Api::class)@Composable
fun EntrySiswaScreenMapel(
    navigateBack: () -> Unit,
    modifier: Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutinScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }
    ){ innerPadding ->
        EntrySiswaBodyMapel(
            uiStateSiswaMapel = viewModel.uiStateSiswaMapel,
            onSiswaValueChangeMapel = viewModel::updateUiState,
            onSaveClick = {
                coroutinScope.launch {
                    viewModel.saveSiswa()
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

@Composable
fun EntrySiswaBodyMapel(
    uiStateSiswaMapel: UIStateMapel,
    onSiswaValueChangeMapel: (DetailMapel) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_Large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        FormInputSiswaMapel(
            detailMapel = uiStateSiswaMapel.detailMapel,
            onValueChangeMapel = onSiswaValueChangeMapel,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswaMapel.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.btn_submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswaMapel(
    detailMapel: DetailMapel,
    modifier: Modifier = Modifier,
    onValueChangeMapel: (DetailMapel) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = detailMapel.namamapel,
            onValueChange = { onValueChangeMapel(detailMapel.copy(namamapel = it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailMapel.guru,
            onValueChange = { onValueChangeMapel(detailMapel.copy(guru = it)) },
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailMapel.jam,
            onValueChange = { onValueChangeMapel(detailMapel.copy(jam = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(R.string.telpon)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
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
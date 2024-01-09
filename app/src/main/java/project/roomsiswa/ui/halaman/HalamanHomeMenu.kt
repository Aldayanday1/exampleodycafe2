package project.roomsiswa.ui.halaman

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import project.roomsiswa.R
import project.roomsiswa.data.Menu
import project.roomsiswa.model.HomeViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiMenu : DestinasiNavigasi{
    override val route = "menu"
    override val titleRes = R.string.welcome_menu
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    //------- SEARCH -------/
    val searchQueryState = remember { mutableStateOf("") }

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DestinasiMenu.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.alpha(0.5f),
            )
        },
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_Large)),
                horizontalAlignment = Alignment.End
            ){
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_Large))
                        .alpha(0.8f),
                ) {
                    FloatingActionButton(
                        onClick = navigateToHome,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.entry_pesanan)
                        )

                    }

                    Spacer(modifier = Modifier.padding(end = 10.dp))

                    FloatingActionButton(
                        onClick = navigateToItemEntry,
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.entry_pesanan)
                        )
                    }


                }
            }

        },
    ){
            innerPadding ->
        val uiStateMenu by viewModel.menuUiState.collectAsState()

        // filter = pemfilteran terhadap elemen yg ditampilkan (menu)
        //ignorecase true = mengabaikan kondisi dari huruf kapital / huruf kecilnya
        val filteredMenu = uiStateMenu.listMenu.filter {
            it.menu.contains(searchQueryState.value, ignoreCase = true)
        }



        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchQueryState.value,
                        onValueChange = { searchQueryState.value = it },
                        label = { Text("Search") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
                if (filteredMenu.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .alpha(0.8f)
                                    .padding(bottom = 150.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(180.dp) // Ubah ukuran sesuai dengan preferensi
                                        .clip(CircleShape)
                                        .alpha(0.8f),// Memotong gambar menjadi bentuk bulat
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.odycafe),
                                        contentDescription = null, // Deskripsi konten, bisa dikosongkan jika tidak diperlukan
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = 35.dp))
                                Text(
                                    text = stringResource(R.string.deskripsi_no_item),
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Cursive,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 25.sp,
                                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_Large))
                                )
                            }
                        }
                    }
                } else {
                    items(filteredMenu) { menu ->
                        DataMenu(
                            menu = menu,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp, vertical = 2.dp)
                                .clickable { onDetailClick(menu.idmenu) }
                        )
                    }
                }
            }
        }




//        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
//
//            Image(
//                painter = painterResource(id = R.drawable.esteh),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.FillBounds,
//            )
//
//            if (filteredMenu.isEmpty()) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 30.dp)
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier
//                            .alpha(0.8f)
//                            .padding(bottom = 150.dp)
//                    ) {
//                        Box(
//                            contentAlignment = Alignment.Center,
//                            modifier = Modifier
//                                .size(180.dp) // Ubah ukuran sesuai dengan preferensi
//                                .clip(CircleShape)
//                                .alpha(0.8f),// Memotong gambar menjadi bentuk bulat
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.odycafe),
//                                contentDescription = null, // Deskripsi konten, bisa dikosongkan jika tidak diperlukan
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        }
//                        Spacer(modifier = Modifier.padding(top = 35.dp))
//                        Text(
//                            text = stringResource(R.string.deskripsi_no_item),
//                            textAlign = TextAlign.Center,
//                            fontFamily = FontFamily.Cursive,
//                            fontWeight = FontWeight.Bold,
//                            style = MaterialTheme.typography.titleLarge,
//                            fontSize = 25.sp,
//                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_Large))
//                        )
//                    }
//                }
//            } else {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 35.dp),
//                    contentPadding = innerPadding,
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    item {
//                        OutlinedTextField(
//                            value = searchQueryState.value,
//                            onValueChange = { searchQueryState.value = it },
//                            label = { Text("Search") },
//                            leadingIcon = {
//                                Icon(
//                                    imageVector = Icons.Default.Search,
//                                    contentDescription = "Search Icon",
//                                    modifier = Modifier.size(24.dp) // Ukuran ikon bisa disesuaikan
//                                )
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                        )
//                    }
//
//                    items(filteredMenu) { menu ->
//                        DataMenu(
//                            menu = menu,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 30.dp, vertical = 2.dp)
//                                .clickable { onDetailClick(menu.idmenu) }
//                        )
//                    }
//                }
//
//            }
//        }
    }
}


@Composable
fun DataMenu(
    menu: Menu,
    modifier: Modifier = Modifier
){
    Card (
        modifier = modifier
            .padding(bottom = 16.dp)
            .size(width = 350.dp, height = 255.dp)
            .alpha(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ){
        Column (
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.idmenu1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = menu.idmenu.toString(),
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
                    text = stringResource(id = R.string.menu1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = menu.menu,
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
                    text = stringResource(id = R.string.harga1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = menu.harga,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight ,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.ketersediaan1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = menu.ketersediaan,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight ,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.kategori1),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = menu.kategori,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


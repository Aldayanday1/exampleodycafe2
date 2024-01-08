package project.roomsiswa.ui.halaman

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import project.roomsiswa.data.Pesanan
import project.roomsiswa.model.HomeViewModel
import project.roomsiswa.model.PenyediaViewModel
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiPesanan : DestinasiNavigasi {
    override val route = "pesanan"
    override val titleRes = R.string.welcome_pesanan
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesananScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CafeTopAppBar(
                title = stringResource(DestinasiPesanan.titleRes),
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
        val uiStatePesanan by viewModel.pesananUiState.collectAsState()

        // Menambahkan BoxWithConstraints di dalam Scaffold
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            // Memasukkan konten utama (BodyPesanan) di atas latar belakang
            BodyPesanan(
                itemPesanan = uiStatePesanan.listPesanan,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onPesananClick = onDetailClick
            )

//            Column(
//                modifier = Modifier
//                    .padding(top = 100.dp)
//                    .align(Alignment.TopCenter),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(dimensionResource(id = R.dimen.padding_Large))
//                        .alpha(0.8f),
//                ) {
//                    FloatingActionButton(
//                        onClick = navigateToItemEntry,
//                        shape = MaterialTheme.shapes.medium,
//                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = stringResource(R.string.entry_pesanan)
//                        )
//                    }
//                    FloatingActionButton(
//                        onClick = navigateToItemEntry,
//                        shape = MaterialTheme.shapes.medium
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Home,
//                            contentDescription = stringResource(R.string.entry_pesanan)
//                        )
//                    }
//                }
//            }
        }
    }
}

@Composable
fun BodyPesanan(
    itemPesanan: List<Pesanan>,
    modifier: Modifier = Modifier,
    onPesananClick: (Int) -> Unit = {}
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ){
        if (itemPesanan.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(0.8f)
                    .padding(bottom = 150.dp)
            ){
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
        } else {
            ListPesanan(
                itemPesanan = itemPesanan,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onItemPesananClick = { onPesananClick(it.idpesanan) }
            )
        }
    }
}


@Composable
fun ListPesanan(
    itemPesanan: List<Pesanan>,
    modifier: Modifier = Modifier,
    onItemPesananClick: (Pesanan) -> Unit
){
    LazyColumn(modifier = Modifier){
        items(items = itemPesanan, key = {it.idpesanan}){
                person ->
            DataPesanan(
                pesanan = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemPesananClick(person) }
            )
        }
    }
}

@Composable
fun DataPesanan(
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



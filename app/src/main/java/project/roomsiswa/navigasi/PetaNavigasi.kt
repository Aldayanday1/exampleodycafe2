package project.roomsiswa.navigasi

import android.icu.text.CaseMap.Title
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import project.roomsiswa.R
import project.roomsiswa.ui.halaman.DestinasiEntry
import project.roomsiswa.ui.halaman.DestinasiHome
import project.roomsiswa.ui.halaman.DestinasiPesanan
import project.roomsiswa.ui.halaman.DestinasiPesananEntry
import project.roomsiswa.ui.halaman.DestinasiStart
import project.roomsiswa.ui.halaman.DetailsDestination
import project.roomsiswa.ui.halaman.DetailsPesananDestination
import project.roomsiswa.ui.halaman.DetailsPesananScreen
import project.roomsiswa.ui.halaman.DetailsScreen
import project.roomsiswa.ui.halaman.EntryPesananScreen
import project.roomsiswa.ui.halaman.EntrySiswaScreen
import project.roomsiswa.ui.halaman.HomeScreen
import project.roomsiswa.ui.halaman.ItemEditDestination
import project.roomsiswa.ui.halaman.ItemEditPesananDestination
import project.roomsiswa.ui.halaman.ItemEditPesananScreen
import project.roomsiswa.ui.halaman.ItemEditScreen
import project.roomsiswa.ui.halaman.PesananScreen
import project.roomsiswa.ui.halaman.StartScreen

@Composable
fun SiswaApp(navController: NavHostController = rememberNavController()){
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
){
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiStart.route,
        modifier = Modifier
    ) {
        composable(DestinasiStart.route){
            StartScreen (
                onNextButtonMenuClicked = {navController.navigate(DestinasiHome.route)},
                onNextButtonPesananClicked = {navController.navigate(DestinasiPesanan.route)},
            )
        }

        /* ------------- NAV MENU ------------ */
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = {
                    navController.navigate("${DetailsDestination.route}/$it")
                },
            )
        }
        composable(DestinasiEntry.route){
            EntrySiswaScreen(navigateBack = { navController.popBackStack()}, modifier = Modifier)
        }
        composable(
            DetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestination.siswaIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditItem = {
                    navController.navigate("${ItemEditDestination.route}/$it")
                }
            )
        }
        composable(
            ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        /* ------------- NAV PESANAN ------------ */

        composable(DestinasiPesanan.route){
            PesananScreen(
                navigateToItemEntry = {navController.navigate(DestinasiPesananEntry.route)},
                onDetailClick = {
                    navController.navigate("${DetailsPesananDestination.route}/$it")
                },
            )
        }
        composable(DestinasiPesananEntry.route){
            EntryPesananScreen(
                navigateBack = { navController.popBackStack()},
                onNavigateUp = { navController.navigateUp() },
                modifier = Modifier
            )
        }
        composable(
            DetailsPesananDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsPesananDestination.detailIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailsPesananScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditItem = {
                    navController.navigate("${ItemEditPesananDestination.route}/$it")
                }
            )
        }
        composable(
            ItemEditPesananDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditPesananDestination.editIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditPesananScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
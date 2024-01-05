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
import project.roomsiswa.ui.halaman.DestinasiMenu
import project.roomsiswa.ui.halaman.DestinasiMenuEntry
import project.roomsiswa.ui.halaman.DestinasiPesanan
import project.roomsiswa.ui.halaman.DestinasiPesananEntry
import project.roomsiswa.ui.halaman.DestinasiStart
import project.roomsiswa.ui.halaman.DetailsMenuDestination
import project.roomsiswa.ui.halaman.DetailsMenuScreen
import project.roomsiswa.ui.halaman.DetailsPesananDestination
import project.roomsiswa.ui.halaman.DetailsPesananScreen
import project.roomsiswa.ui.halaman.EntryMenuScreen
import project.roomsiswa.ui.halaman.EntryPesananScreen
import project.roomsiswa.ui.halaman.ItemEditMenuDestination
import project.roomsiswa.ui.halaman.ItemEditMenuScreen
import project.roomsiswa.ui.halaman.ItemEditPesananDestination
import project.roomsiswa.ui.halaman.ItemEditPesananScreen
import project.roomsiswa.ui.halaman.MenuScreen
import project.roomsiswa.ui.halaman.PesananScreen
import project.roomsiswa.ui.halaman.StartScreen

@Composable
fun OdyCafeApp(navController: NavHostController = rememberNavController()){
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeTopAppBar(
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
){
    NavHost(
        navController = navController,
        startDestination = DestinasiStart.route,
        modifier = Modifier
    ) {
        composable(DestinasiStart.route){
            StartScreen (
                onNextButtonMenuClicked = {navController.navigate(DestinasiMenu.route)},
                onNextButtonPesananClicked = {navController.navigate(DestinasiPesanan.route)},
            )
        }

        /* ------------- NAV MENU ------------ */

        composable(DestinasiMenu.route){
            MenuScreen(
                navigateToItemEntry = {navController.navigate(DestinasiMenuEntry.route)},
                onDetailClick = {
                    navController.navigate("${DetailsMenuDestination.route}/$it")
                },
            )
        }
        composable(DestinasiMenuEntry.route){
            EntryMenuScreen(
                navigateBack = { navController.popBackStack()},
                onNavigateUp = { navController.navigateUp() },
                modifier = Modifier
            )
        }
        composable(
            DetailsMenuDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsMenuDestination.detailIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailsMenuScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditItem = {
                    navController.navigate("${ItemEditMenuDestination.route}/$it")
                }
            )
        }
        composable(
            ItemEditMenuDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditMenuDestination.editIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditMenuScreen(
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
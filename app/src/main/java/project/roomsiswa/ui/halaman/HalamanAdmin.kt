package project.roomsiswa.ui.halaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.roomsiswa.R
import project.roomsiswa.navigasi.CafeTopAppBar
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiAdmin : DestinasiNavigasi {
    override val route = "admin"
    override val titleRes = R.string.welcome
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navigateBack: () -> Unit,
    onNextButtonMenuClicked: () -> Unit,
    onNextButtonPesananListClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CafeTopAppBar(
            title = stringResource(DestinasiAdmin.titleRes),
            canNavigateBack = true,
            navigateUp = navigateBack,
            modifier = Modifier.alpha(0.5f)
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.esteh),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(200.dp))
                Column( // Added a nested Column for closer text placement
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Hi Admin !",
                        color = Color.DarkGray,
                        fontSize = 55.sp,
                        fontFamily = FontFamily.Cursive,
                    )
                }
                Spacer(modifier = Modifier.height(260.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = onNextButtonMenuClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.2f),
                            contentColor = Color.White.copy(alpha = 0.8f)
                        )
                    ) {
                        Text(stringResource(R.string.entry_menu), color = Color.DarkGray)
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = onNextButtonPesananListClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.2f),
                            contentColor = Color.White.copy(alpha = 0.8f)
                        )
                    ) {
                        Text(stringResource(R.string.list_pesanan), color = Color.DarkGray)
                    }
                }
            }
        }
    }
}


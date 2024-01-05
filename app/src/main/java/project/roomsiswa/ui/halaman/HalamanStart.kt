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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.roomsiswa.R
import project.roomsiswa.navigasi.DestinasiNavigasi

object DestinasiStart : DestinasiNavigasi {
    override val route = "start"
    override val titleRes = R.string.app_name
}

@Composable
fun StartScreen(
    onNextButtonMenuClicked: () -> Unit,
    onNextButtonPesananClicked: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        text = "Ody Cafe",
                        color = Color.DarkGray,
                        fontSize = 55.sp,
                        fontFamily = FontFamily.Cursive,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Memori Manis di Setiap Kesibukan",
                        color = Color.DarkGray,
                        fontSize = 20.sp,
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
                        onClick = onNextButtonPesananClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.2f),
                            contentColor = Color.White.copy(alpha = 0.8f)
                        )
                    ) {
                        Text(stringResource(R.string.idpesanan), color = Color.DarkGray)
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = onNextButtonMenuClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.2f),
                            /** Mengubah warna ke DarkGray dengan Transparansi 20%*/
                            contentColor = Color.White.copy(alpha = 0.8f)
                            /** Menambah transparansi*/
                        )
                    ) {
                        Text(stringResource(R.string.menu), color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

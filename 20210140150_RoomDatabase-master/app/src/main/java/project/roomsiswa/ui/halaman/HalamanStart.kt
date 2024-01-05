import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.roomsiswa.R
import project.roomsiswa.navigasi.DestinasiNavigasi
import project.roomsiswa.navigasi.SiswaTopAppBar

object DestinasiStart : DestinasiNavigasi {
    override val route = "start"
    override val titleRes = R.string.start_page_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanStart(
    onNextButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SiswaTopAppBar(
            title = stringResource(DestinasiStart.titleRes),
            canNavigateBack = false,
            navigateUp = {}
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_Large)))
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_Large)))
            Text(
                text = "Welcome to my ",
                color = Color.DarkGray,
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_Large)))
            Text(
                text = "Database Room",
                color = Color.DarkGray,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f, false),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onNextButtonClicked
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

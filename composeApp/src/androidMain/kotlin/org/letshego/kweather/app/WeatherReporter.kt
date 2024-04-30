import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import models.WeatherReport

@Composable
fun WeatherReporter(report: WeatherReport) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable { }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(report.current.condition.icon.replace("//", "https//"))
                    .scale(Scale.FIT)
                    .build(),
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
                    .width(80.dp)
                    .height(135.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "${report.current.condition.text} in ${report.location.name}", style = typography.h6)
                Text(text = "Temp: ${report.current.tempC} °C - Humidity: ${report.current.humidity} - Wind: ${report.current.windKPH} kph", style = typography.caption)
            }
        }
    }
}
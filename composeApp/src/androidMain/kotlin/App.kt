import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.WeatherReport

import org.jetbrains.compose.ui.tooling.preview.Preview

import viewmodel.WeatherReportViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(viewModel: WeatherReportViewModel) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val scope = CoroutineScope(Dispatchers.Default)
        var weatherReport: WeatherReport? by remember { mutableStateOf(null) }
        var error: String? by remember { mutableStateOf(null) }
        var query: String? by remember { mutableStateOf(null) }


        var isSearchActive by rememberSaveable { mutableStateOf(false) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        LaunchedEffect("LaunchedEffect") { // the key define when the block is relaunched
            // Your coroutine code here
            scope.launch {
                viewModel.weather.collect { value ->
                    value.data?.let {
                        weatherReport = value.data
                        showContent = true
                        println("got $value")
                        query = null
                    }
                    value.error?.let {
                        error = it
                        showContent = false;
                        println("got error $value")
                    }

                }
                println("flow is completed")
            }
        }
         Scaffold(

             topBar = {
                 Column(verticalArrangement = Arrangement.spacedBy((-1).dp)) {
                     TopBar(
                         onBackClick = {  },
                         scrollBehavior = scrollBehavior,
                     )
                     TopAppBarSurface(scrollBehavior = scrollBehavior) {
                         EmbeddedSearchBar(
                             onQueryChange = { },
                             isSearchActive = isSearchActive,
                             onActiveChanged = { isSearchActive = it },
                             onSearch = {
                                 query = it
                                 error = null
                                 isSearchActive = false
                                 viewModel.loadWeatherReport(it)
                             }
                         )
                     }
                     Column( modifier = Modifier
                         .padding(24.dp)
                         .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                         error?.let {  Text(it, color = Color.Red) }
                         Row(Modifier.clickable { }) {
                             Button(onClick = {
                                 showContent = false
                                 error = null
                                 viewModel.loadWeatherReport(location = query ?: "Nairobi")
                             }) {
                                 Text("Reload Weather")
                             }
                         }

                         AnimatedVisibility(showContent) {

                             weatherReport?.let { WeatherReporter(it) }
                         }
                     }
                 }
             }
        ) { _ ->
            // Search suggestions or results

        }
    }
}
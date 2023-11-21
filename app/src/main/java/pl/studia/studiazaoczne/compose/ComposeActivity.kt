package pl.studia.studiazaoczne.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.studia.studiazaoczne.R

class ComposeActivity : ComponentActivity() {

    val viewModel by viewModels<ComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeScreen(viewModel)
        }
    }
}

@Composable
fun ComposeScreen(viewModel: ComposeViewModel = ComposeViewModel()) {
    val state = viewModel.listSizeLiveData.observeAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        when (state.value) {
            is ComposeViewModel.ComposeState.Loading -> ShowLoading()
            is ComposeViewModel.ComposeState.ShowList -> ShowList(viewModel, state.value as ComposeViewModel.ComposeState.ShowList)
            null -> TODO()
        }

    }
}

@Composable
fun ShowLoading() {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ShowList(
    viewModel: ComposeViewModel,
    showListState: ComposeViewModel.ComposeState.ShowList,
) {
    Column {
        InputListSize() {
            viewModel.changeListSize(it)
        }
        SaveLongColumn(listSize = showListState.listSize)
    }
}


@Composable
private fun UnsaveLongList(name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0..1500)
            TopText()

        Text(text = "hello world$name ")
        Text(text = "hello world", fontSize = 16.sp)

    }
}

@Composable
private fun TopText() {
    Row {

        Image(
            painter = painterResource(id = R.drawable.kotek),
            contentDescription = "Ikona Top Text",
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "hello world", modifier = Modifier
                .width(150.dp)
                .height(70.dp)
                .background(Color.Red)

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputListSize(changeSize: (Int) -> Unit = {}) {
    val inputState = remember {
        mutableStateOf("")
    }
    Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            value = inputState.value,
            onValueChange = {
                inputState.value = it
            }, modifier = Modifier.width(100.dp)
        )
        Button(onClick = { changeSize.invoke(inputState.value.toIntOrNull() ?: 0) }) {
            Text(text = "zmień rozmiar")
        }
    }
}

@Composable
fun SaveLongColumn(listSize: Int) {
    LazyColumn(content = {
        for (i in 0..listSize)
            item { TopText() }

    })
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeScreen()
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingTopText() {
//    TopText()
//}
//
//@Preview(showBackground = true)
//@Composable
//fun InputPreview() {
//    InputListSize()
//}


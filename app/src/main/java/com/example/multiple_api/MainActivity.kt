package com.example.multiple_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.multiple_api.data.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunAPIsApp()
        }
    }
}

@Preview
@Composable
fun FunAPIsApp() {
    val scope = rememberCoroutineScope()
    var genderResult by remember { mutableStateOf<String?>(null) }
    var catFact by remember { mutableStateOf<String?>(null) }
    var dogImageUrl by remember { mutableStateOf<String?>(null) }
    var joke by remember { mutableStateOf<String?>(null) }
    var user by remember { mutableStateOf<String?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var inputName by remember { mutableStateOf(TextFieldValue("")) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3))
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "🌟 Just for Fun 🌟",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                // Gender API Section
                item {
                    SectionCard(title = "Guess Gender") {
                        OutlinedTextField(
                            value = inputName,
                            onValueChange = { inputName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.White.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .padding(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    val response = RetrofitInstance.api.getGender(inputName.text)
                                    genderResult =
                                        "${response.name} is ${response.gender} (Probability: ${response.probability})"
                                }
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Get Gender")
                        }
                        genderResult?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                // Cat Fact API Section
                item {
                    SectionCard(title = "Cat Fact") {
                        Button(
                            onClick = {
                                scope.launch {
                                    catFact = RetrofitInstance.api.getCatFact().fact
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                        ) {
                            Text("Get Cat Fact")
                        }
                        catFact?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                            )
                        }
                    }
                }

                // Dog Image API Section
                item {
                    SectionCard(title = "Random Dog Image") {
                        Button(
                            onClick = {
                                scope.launch {
                                    dogImageUrl = RetrofitInstance.api.getRandomDogImage().message
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                        ) {
                            Text("Get Dog Image")
                        }
                        dogImageUrl?.let {
                            SubcomposeAsyncImage(
                                model = it,
                                contentDescription = "Random Dog",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop,
                                loading = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.LightGray)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                // Random Joke API Section
                item {
                    SectionCard(title = "Joke") {
                        Button(
                            onClick = {
                                scope.launch {
                                    val response = RetrofitInstance.api.getRandomJoke()
                                    joke = "${response.setup}\n${response.punchline}"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Get Joke")
                        }
                        joke?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                // Random User API Section
                item {
                    SectionCard(title = "Random User") {
                        Button(
                            onClick = {
                                scope.launch {
                                    val response = RetrofitInstance.api.getRandomUser().results.first()
                                    user = "Name: ${response.name.first} ${response.name.last}"
                                    imageUrl = response.picture.large
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
                        ) {
                            Text("Get Random User")
                        }
                        user?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        imageUrl?.let {
                            SubcomposeAsyncImage(
                                model = it,
                                contentDescription = "Random User",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop,
                                loading = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.LightGray)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

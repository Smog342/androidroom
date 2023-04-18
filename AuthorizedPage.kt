package com.example.kotlinapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlinapp.ui.theme.KotlinAppTheme

class AuthorizedPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val intent = intent

                    intent.getStringExtra("username")?.let { Greeting(it) }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    val context: Context = LocalContext.current

    fun click(context: Context){

        val intent = Intent(context, ChangeProfileActivity::class.java)

        intent.putExtra("login", name)

        context.startActivity(intent)

    }

    Column(verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.background(Color.LightGray).fillMaxSize()){
        Button(onClick = { click(context) }) {
            Text(text = "$name")
        }
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinAppTheme {
        Greeting("Android")
    }
}
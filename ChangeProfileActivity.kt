package com.example.kotlinapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.kotlinapp.data.AppDatabase
import com.example.kotlinapp.ui.theme.KotlinAppTheme
import kotlinx.coroutines.launch

class ChangeProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent = getIntent()
        setContent {
            KotlinAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    intent.getStringExtra("login")?.let { ChangeProfile(name = it) }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ChangeProfile(name: String){

    var login by remember { mutableStateOf("") };
    var password by remember { mutableStateOf("") };
    var password2 by remember { mutableStateOf("") }
    val context = LocalContext.current;

    val rememberCoroutineScope = rememberCoroutineScope();

    fun Back(context: Context){

        val intent = Intent(context, MainActivity::class.java)

        intent.putExtra("login", name)

        context.startActivity(intent)

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        OutlinedTextField(value = login, placeholder = {Text(text = "Логин")}, onValueChange = {login = it})
        OutlinedTextField(value = password, placeholder = {Text(text = "Пароль")}, onValueChange = {password = it})
        OutlinedTextField(value = password2, placeholder = {Text(text = "Пароль")}, onValueChange = {password2 = it})
        Button(onClick = {

            if ((login == "") or (password == "") or (password2 == "")){

                Toast.makeText(
                    context,
                    "Пожалуйста введите все данные",
                    Toast.LENGTH_SHORT
                ).show()

            }else if (password != password2){

                Toast.makeText(
                    context,
                    "Введённые пароли не совпадают",
                    Toast.LENGTH_SHORT
                ).show()

            }else{

                val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
                ).build()

                rememberCoroutineScope.launch {

                    db.userDao().UpdateUser(name, login, password);

                    val intent = Intent(context, MainActivity::class.java)

                    intent.putExtra("login", name)

                    context.startActivity(intent)

                }

            }

        }) {
            Text("Сохранить")
        }
        Button(onClick = { Back(context) }) {
            Text(text = "Вернуться")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    KotlinAppTheme {
        Greeting2("Android")
    }
}
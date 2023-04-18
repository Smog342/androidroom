package com.example.kotlinapp

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.example.kotlinapp.data.AppDatabase
import com.example.kotlinapp.data.models.UserModel

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun RegistrationView() {
    var phoneNumber by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val applicationContext = LocalContext.current

     val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()
    val rememberCoroutineScope = rememberCoroutineScope();


    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            var job: Job? by remember {
                mutableStateOf(null)
            }
            Button(
                onClick = {
                    job = rememberCoroutineScope.launch {
                        if (phoneNumber == "" || username == "" || password == "" || confirmPassword == ""){
                            Toast.makeText(applicationContext, "Input Every Field.", Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                        if (password != confirmPassword){
                            Toast.makeText(applicationContext, "Pass and ConfirmPass field has different values", Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                        val user = db.userDao().findByNumber(phoneNumber)
                        if (user != null){
                            Toast.makeText(applicationContext, "User with that number already exists", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        db.userDao().insertAll(
                            UserModel(
                                uid = 0,
                                phoneNumber = phoneNumber,
                                username = username,
                                password = password
                            )
                        )

                        Toast.makeText(applicationContext, "Now go to the login page to login.", Toast.LENGTH_SHORT).show()
                    }

                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Register"
                )
            }
        }
    }
}

@Composable
fun AuthorizationView() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val applicationContext = LocalContext.current

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()
    val rememberCoroutineScope = rememberCoroutineScope();


    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },

            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Button(
                onClick = {
                          rememberCoroutineScope.launch {
                              val user = db.userDao().findByUsername(username)

                              if (user == null){
                                  Toast.makeText(applicationContext, "User does not exist", Toast.LENGTH_SHORT).show()
                                  return@launch
                              }

                              if (user.password != password){
                                  Toast.makeText(applicationContext, "Wrong password", Toast.LENGTH_SHORT).show()
                                  return@launch
                              }

                              Toast.makeText(applicationContext, "Welcome, $username", Toast.LENGTH_SHORT).show()

                              val intent = Intent(applicationContext, AuthorizedPage::class.java)
                              intent.putExtra("username", username)

                              applicationContext.startActivity(intent)
                          }
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Log In"
                )
            }
        }
    }
}


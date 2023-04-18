package com.example.kotlinapp

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object Register : TabItem("Registration", { RegistrationView() })
    object Auth : TabItem("Log In", { AuthorizationView() })
}
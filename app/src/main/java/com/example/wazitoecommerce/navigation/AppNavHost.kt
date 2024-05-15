package com.example.wazitoecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wazitoecommerce.ui.theme.screens.books.AddBooksScreen
import com.example.wazitoecommerce.ui.theme.screens.books.BooksScreen
import com.example.wazitoecommerce.ui.theme.screens.books.ViewBooksScreen
import com.example.wazitoecommerce.ui.theme.screens.home.HomeScreen
import com.example.wazitoecommerce.ui.theme.screens.home.ImportScreen


import com.example.wazitoecommerce.ui.theme.screens.login.LoginScreen

import com.example.wazitoecommerce.ui.theme.screens.signup.SignupScreen
import com.example.wazitoecommerce.ui.theme.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController:NavHostController = rememberNavController(),
    startDestination:String = SPLASH_SCREEN
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier){

        composable(LOGIN_URL){
            LoginScreen(navController = navController)
        }
        composable(SIGNUP_URL){
            SignupScreen(navController = navController)
        }
        composable(HOME_URL){
            HomeScreen(navController = navController)
        }
        composable(IMPORT_SCREEN){
            ImportScreen(navController = navController)
        }
        composable(BOOKS_SCREEN){
            BooksScreen(navController = navController)
        }
        composable(ADD_BOOKS_SCREEN){
            AddBooksScreen(navController = navController)
        }
        composable(VIEW_BOOKS_SCREEN){
            ViewBooksScreen(navController = navController)
        }
        composable(SPLASH_SCREEN){
            SplashScreen(navController = navController)
        }


    }
}
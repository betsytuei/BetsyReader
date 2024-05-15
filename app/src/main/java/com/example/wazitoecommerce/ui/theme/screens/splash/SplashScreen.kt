package com.example.wazitoecommerce.ui.theme.screens.splash


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


import com.example.wazitoecommerce.navigation.LOGIN_URL
import com.example.wazitoecommerce.ui.theme.WazitoECommerceTheme

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavHostController)
{
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        val coroutine = rememberCoroutineScope()
        coroutine.launch{
            delay(1000)
            navController.navigate(LOGIN_URL)

        }
        Image(painter = painterResource(id = com.example.wazitoecommerce.R.drawable.bookapp) ,
            contentDescription = "gallery",
            modifier = Modifier.size(height = 150.dp, width = 150.dp))

        Text(text = "Book Reader",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive
        )

    }
}
@Preview(showBackground = false)
@Composable
fun SplashScreenPreview(){
    WazitoECommerceTheme {
        SplashScreen(rememberNavController())
    }

}
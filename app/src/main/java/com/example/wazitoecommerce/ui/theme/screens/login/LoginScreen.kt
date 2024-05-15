package com.example.wazitoecommerce.ui.theme.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wazitoecommerce.R
import com.example.wazitoecommerce.data.AuthViewModel
import com.example.wazitoecommerce.navigation.HOME_URL
import com.example.wazitoecommerce.navigation.IMPORT_SCREEN
import com.example.wazitoecommerce.navigation.SIGNUP_URL
import com.example.wazitoecommerce.ui.theme.WazitoECommerceTheme
import com.example.wazitoecommerce.ui.theme.purplee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController:NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.login),
                contentDescription = "register",
                modifier = Modifier.size(120.dp))
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Login ",
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = email ,
            onValueChange = {email = it},
            placeholder = { Text(text = "Email")},
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = password ,
            onValueChange = {password = it},
            placeholder = { Text(text = "Password")},
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(30.dp))
        val context = LocalContext.current
        val authViewModel = AuthViewModel(navController, context)

        Button(onClick = { authViewModel.login(email, password)
            navController.navigate(HOME_URL)     },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(purplee),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp))
        {
            Text(text = "Login")

        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Dont Have An Account ? Sign Up.",
            fontSize = 25.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(SIGNUP_URL)
                   },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)


        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    WazitoECommerceTheme {
        LoginScreen(navController = rememberNavController())
    }
}
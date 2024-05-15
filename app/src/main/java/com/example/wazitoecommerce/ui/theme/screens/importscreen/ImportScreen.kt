package com.example.wazitoecommerce.ui.theme.screens.home

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wazitoecommerce.MainActivity

import com.example.wazitoecommerce.navigation.HOME_URL

import com.example.wazitoecommerce.ui.theme.WazitoECommerceTheme
import com.example.wazitoecommerce.ui.theme.lblue
import com.example.wazitoecommerce.ui.theme.screens.books.LibraryViewModel
import java.io.FileInputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportScreen(navController:NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
    ) {
        val mContext = LocalContext.current

        val context = LocalContext.current // Access context within a Composable

        val importBookLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
                uri?.let {
                    (context as MainActivity).contentResolver.openInputStream(uri)?.let { ips ->
                        LibraryViewModel.importBook(context, ips as FileInputStream)
                    }
                }
            }
        //TopAppBar
        TopAppBar(
            title = { Text(text = "Import Books", color = Color.White) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(lblue),
            navigationIcon = {
                IconButton(onClick = {  navController.navigate(HOME_URL)

                }) {
                    Icon(imageVector = Icons.Default.ArrowBack ,
                        contentDescription = "arrow back" ,
                        tint = Color.White)

                }

            }
            ,actions = {
                IconButton(onClick = { navController.navigate(HOME_URL) }) {
                    Icon(imageVector = Icons.Default.Home ,
                        contentDescription = "home" ,
                        tint = Color.White)

                }
                IconButton(onClick = {
                    val shareIntent=Intent(Intent.ACTION_SEND)
                    shareIntent.type="text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this is a cool content")
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share"))



                }) {
                    Icon(imageVector = Icons.Default.Share ,
                        contentDescription = "share" ,
                        tint = Color.White)

                }

            }
        )
        // End of topappbar
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            importBookLauncher.launch(arrayOf("application/epub+zip"))



        },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(lblue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp))
        {
            Text(text = "Import Books")

        }




        }
    }



@Composable
@Preview(showBackground = true)
fun ImportScreenPreview(){
    WazitoECommerceTheme {
        ImportScreen(navController = rememberNavController())
    }
}
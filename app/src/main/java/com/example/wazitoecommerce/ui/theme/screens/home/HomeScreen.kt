package com.example.wazitoecommerce.ui.theme.screens.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wazitoecommerce.R
import com.example.wazitoecommerce.navigation.ADD_BOOKS_SCREEN

import com.example.wazitoecommerce.navigation.BOOKS_SCREEN

import com.example.wazitoecommerce.navigation.IMPORT_SCREEN
import com.example.wazitoecommerce.navigation.LOGIN_URL
import com.example.wazitoecommerce.navigation.VIEW_BOOKS_SCREEN

import com.example.wazitoecommerce.ui.theme.WazitoECommerceTheme
import com.example.wazitoecommerce.ui.theme.lblue
import com.example.wazitoecommerce.ui.theme.purplee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController:NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val mContext = LocalContext.current
        //TopAppBar
        TopAppBar(
            title = { Text(text = "Home", color = Color.White) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(purplee),
            navigationIcon = {
                IconButton(onClick = { navController.navigate(LOGIN_URL)

                }) {
                    Icon(imageVector = Icons.Default.ArrowBack ,
                        contentDescription = "arrow back" ,
                        tint = Color.White)

                }

            }
            ,actions = {

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
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Card(modifier = Modifier.size(width = 160.dp, height = 120.dp))
            {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()
                        .clickable { navController.navigate(BOOKS_SCREEN)

                        }, contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.library),
                            contentDescription = "Amazon",
                            modifier = Modifier.size(80.dp))
                    }
                    Text(text = "My Books",
                        fontSize = 20.sp,
                        color = lblue,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Card(modifier = Modifier.size(width = 160.dp, height = 120.dp).clickable { navController.navigate(
                IMPORT_SCREEN)
                })
            {
                Column {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.importbook),
                            contentDescription = "Amazon",
                            modifier = Modifier.size(80.dp))
                    }
                    Text(text = "Import Books",
                        fontSize = 20.sp,
                        color = lblue,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
            }
        }


        Spacer(modifier = Modifier.height(30.dp))
        // End of row 1
        Row {
            Card(modifier = Modifier.size(width = 160.dp, height = 120.dp))
            {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()
                        .clickable { navController.navigate(ADD_BOOKS_SCREEN)
                            Toast.makeText(mContext,"Launching Add Books Screen",
                                Toast.LENGTH_SHORT).show()
                        }, contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.addbooks),
                            contentDescription = "Amazon",
                            modifier = Modifier.size(80.dp))
                    }
                    Text(text = "Add Books",
                        fontSize = 20.sp,
                        color = lblue,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Card(modifier = Modifier.size(width = 160.dp, height = 120.dp).clickable { navController.navigate(
                VIEW_BOOKS_SCREEN)
                })
            {
                Column {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.viewbooks),
                            contentDescription = "Amazon",
                            modifier = Modifier.size(80.dp))
                    }
                    Text(text = "View Books",
                        fontSize = 20.sp,
                        color = lblue,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
            }
        }



        // End of row 1
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    WazitoECommerceTheme {
        HomeScreen(navController = rememberNavController())
    }
}
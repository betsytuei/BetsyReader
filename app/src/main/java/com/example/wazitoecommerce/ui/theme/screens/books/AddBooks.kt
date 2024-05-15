package com.example.wazitoecommerce.ui.theme.screens.books



import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wazitoecommerce.R
import com.example.wazitoecommerce.data.ProductViewModel
import com.example.wazitoecommerce.navigation.VIEW_BOOKS_SCREEN
import com.example.wazitoecommerce.ui.theme.WazitoECommerceTheme
import com.example.wazitoecommerce.ui.theme.lblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBooksScreen(navController:NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Add Books",
            fontSize = 40.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)

        var productName by remember { mutableStateOf("") }
        var productQuantity by remember { mutableStateOf("") }
        var productPrice by remember { mutableStateOf("") }
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.addbookscreen),
                contentDescription = "register",
                modifier = Modifier.size(150.dp))
        }
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = productName ,
            onValueChange = {productName = it},
            placeholder = { Text(text = "Book Name")},

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = productQuantity ,
            onValueChange = {productQuantity = it},
            placeholder = { Text(text = "Author")},

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )



        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = productPrice ,
            onValueChange = {productPrice = it},
            placeholder = { Text(text = "Book Genre")},

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Spacer(modifier = Modifier.height(30.dp))



        //---------------------IMAGE PICKER START-----------------------------------//

        var modifier = Modifier
        ImagePicker(modifier,context, navController, productName.trim(), productQuantity.trim(), productPrice.trim())

        //---------------------IMAGE PICKER END-----------------------------------//



    }
}

@Composable
fun ImagePicker(modifier: Modifier = Modifier, context: Context,navController: NavHostController, name:String, quantity:String, price:String) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = modifier,) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.
            getBitmap(context.contentResolver,imageUri)
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Selected image")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), horizontalAlignment = Alignment.CenterHorizontally,) {
            Button(onClick = {
                imagePicker.launch("image/*")
            },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(lblue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp))
            {
                Text(text = "Select Image")

            }





            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                var productRepository = ProductViewModel(navController, context)

                productRepository.uploadProduct(name,quantity,price,imageUri!!)
                navController.navigate(VIEW_BOOKS_SCREEN)


            },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp))
            {
                Text(text = "Upload",
                    color = lblue)

            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddBooksScreenPreview(){
    WazitoECommerceTheme {
        AddBooksScreen(navController = rememberNavController())
    }
}
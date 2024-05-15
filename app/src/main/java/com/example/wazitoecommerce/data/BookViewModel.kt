package com.example.wazitoecommerce.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.wazitoecommerce.models.Book
import com.example.wazitoecommerce.navigation.LOGIN_URL
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class BookViewModel(var navController: NavHostController, var context: Context)
{
    var authViewModel:AuthViewModel
    var progress: ProgressDialog
    init {
        authViewModel = AuthViewModel(navController, context)
        if (!authViewModel.isLoggedIn()){
            navController.navigate(LOGIN_URL)
        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    fun uploadBook(book:String, author:String, genre:String, filePath:Uri){
        val bookId = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().getReference()
            .child("Book/$bookId")
        progress.show()
        storageRef.putFile(filePath).addOnCompleteListener{
            progress.dismiss()
            if (it.isSuccessful){
                // Save data to db
                storageRef.downloadUrl.addOnSuccessListener {
                    var imageUrl = it.toString()
                    var book = Book(book,author,genre,imageUrl,bookId)
                    var databaseRef = FirebaseDatabase.getInstance().getReference()
                        .child("Books/$bookId")
                    databaseRef.setValue(book).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this.context, "Upload error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun allBooks(
        book: MutableState<Book>,
        books: SnapshotStateList<Book>
    ): SnapshotStateList<Book> {
        progress.show()
        var ref = FirebaseDatabase.getInstance().getReference()
            .child("Books")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                books.clear()
                for (snap in snapshot.children){
                    var retrievedBook = snap.getValue(Book::class.java)
                    book.value = retrievedBook!!
                    books.add(retrievedBook)
                }
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB locked", Toast.LENGTH_SHORT).show()
            }
        })
        return books
    }

    fun deleteBook(bookId:String){
        var ref = FirebaseDatabase.getInstance().getReference()
            .child("Books/$bookId")
        ref.removeValue()
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }



}
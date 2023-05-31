package com.example.finalproject.model

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment
import com.example.finalproject.fragment.homeFragment.MainFragment
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

data class Book(    @DocumentId
                    var id:String,
                    val name:String,
                    val price:Double,
                    val rating: Double,
                    val description:String,
                    val image:String){
    constructor(  ) : this("", "name", 0.0, 0.0, "", "")
    companion object{
        var db: FirebaseFirestore = Firebase.firestore
        val COLLECTION_NAME = "books"

        fun addBookObject(book: Book) {
            db.collection(COLLECTION_NAME)
                .add(book)
                .addOnSuccessListener {
                    Log.e("me", it.id)
                    Log.e("me", "Book Added Successfully")

                }
                .addOnFailureListener {
                    Log.e("me", it.message.toString())
                }
        }

        fun getAllBooks(callback: (ArrayList<Book>) -> Unit) {
            val data = ArrayList<Book>()
            db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        Log.e("me", document.id)
                        Log.e("me", document.getString("name")!!)
                        val std = document.toObject(Book::class.java)
                        std.id = document.id

                        data.add(std)
                        Log.e("l", "Data length is: ${data.size}")
                    }
                    callback(data) // Pass the data to the callback
                }
                .addOnFailureListener { exception ->
                    Log.e("me", exception.message!!)
                    callback(data) // In case of failure, still pass the current data to the callback
                }
        }


        fun getBookByID(id: String, callback: (Book?) -> Unit) {
            db.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val book = documentSnapshot.toObject(Book::class.java)
                        callback(book)
                    } else {
                        callback(null)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("me", exception.message!!)
                    callback(null)
                }
        }


        fun deleteBookById(id: String)  {
            db.collection(COLLECTION_NAME)
                .document(id)
                .delete()
                .addOnSuccessListener {
                    Log.e("me","Deleted Successfully")
                }
                .addOnFailureListener {
                    Log.e("me",it.message!!)
                }
        }

        fun updateBookById(book: Book){
            db.collection(COLLECTION_NAME)
                .document(book.id)
                .set(book)
                .addOnSuccessListener {
                    Log.e("me","Book updated Successfully")
                }
                .addOnFailureListener {
                    Log.e("me",it.message!!)
                }
        }}
}
package com.example.finalproject.model

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Library(    @DocumentId
                        var id:String, val name:String, val description:String, val rating:Double,
                val location:String, val image:String){
    constructor(
        name: String,
        description: String,
        location: String,
        rating: Double = 0.0,
        image: String
    ) : this("", name, description, rating, location,image, )
    companion object{
        var db: FirebaseFirestore = Firebase.firestore
        val COLLECTION_NAME = "libraries"

        fun addLibraryObject(library: Library) {
            db.collection(COLLECTION_NAME)
                .add(library)
                .addOnSuccessListener {
                    Log.e("me", it.id)
                    Log.e("me", "Library Added Successfully")
                }
                .addOnFailureListener {
                    Log.e("me", it.message.toString())
                }
        }

        fun getAllBLibrary() {
            val data = ArrayList<Library>()
            db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        Log.e("me", document.id)
                        Log.e("me", document.getString("name")!!)
                        val std = document.toObject(Library::class.java) // empty constructor
                        std.id = document.id

                        data.add(std)
                    }
                }
                .addOnFailureListener {
                    Log.e("me", it.message!!)
                }
        }

        fun getLibraryByID(id: String) {
            db.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnSuccessListener {
                    Log.e("me", "Library is Found")
                }.addOnFailureListener {
                    Log.e("me", it.message!!)
                }
        }

        fun deleteLibraryById(id: String) {
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

        fun updateLibraryById(library: Library){
            db.collection(COLLECTION_NAME)
                .document(library.id)
                .set(library)
                .addOnSuccessListener {
                    Log.e("me","Library updated Successfully")
                }
                .addOnFailureListener {
                    Log.e("me",it.message!!)
                }
        }
    }
}

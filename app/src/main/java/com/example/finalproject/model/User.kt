package com.example.finalproject.model

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

data class User(@Suppress("unused")
                var token:String , val name:String, val email:String, val password:String,
                val dob:String, val location:String, var type:Int = 0){
    constructor() : this("", "", "", "", "", "", 0)

    companion object{
        var db: FirebaseFirestore = Firebase.firestore

        val COLLECTION_NAME = "users"

        fun addUserObject(user: User) {
            db.collection(COLLECTION_NAME)
                .add(user)
                .addOnSuccessListener {
                    Log.e("me", it.id)
                    Log.e("me", "User Added Successfully")
                }
                .addOnFailureListener {
                    Log.e("me", it.message.toString())
                }
        }


        fun getUserByID(id: String): User? {
            var user:User? = null
            db.collection(COLLECTION_NAME)
                .whereEqualTo("token", id).limit(1)
                .get()
                .addOnSuccessListener {
                    Log.e("auth",it.documents.size.toString())
                    if(it.documents.size>0){
                    Log.e("me", "${it.documents[0].data}user is Found")
                    user = it.documents[0].toObject()}
//
                }.addOnFailureListener {
                    Log.e("me", it.message!!)
                }
            return user
        }


        fun updateUserById(user: User){
            db.collection(COLLECTION_NAME)
                .document(user.token)
                .set(user)
                .addOnSuccessListener {
                    Log.e("me","User updated Successfully")
                }
                .addOnFailureListener {
                    Log.e("me",it.message!!)
                }
        }}
}



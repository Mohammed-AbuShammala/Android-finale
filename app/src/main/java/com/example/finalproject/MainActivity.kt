package com.example.finalproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.fragment.homeFragment.FavoriteFragment
import com.example.finalproject.fragment.homeFragment.MainFragment
import com.example.finalproject.fragment.homeFragment.ProfileFragment
import com.example.finalproject.model.Book
import com.example.finalproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQ_CAMERA = 100
        const val REQ_GALLERY = 200
        var userId = ""

        fun swipeFragment(activity: FragmentActivity, fragment: Fragment) {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment).commit()
        }

    }

    lateinit var db: FirebaseFirestore


    lateinit var imageView: ImageView
    lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var user : User? = User.getUserByID("RmUlrXDmd4fevxMiS3h2")

        val prefs = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        userId = prefs.getString("userId", "")!!
    }


    override fun onStart() {
        super.onStart()

        swipeFragment(this, MainFragment())
        db = Firebase.firestore
    }


    override fun onResume() {
        super.onResume()

        binding.btnHome.setOnClickListener {
            swipeFragment(this, MainFragment())
        }

        binding.btnMap.setOnClickListener {
            swipeFragment(this, MapsFragment())
        }

        binding.btnProfile.setOnClickListener {
            swipeFragment(this, ProfileFragment())
        }
    }


    //------------------------------------------------------------------------------------
    //camera and gallery buttons


    fun cameraBtn(imageView: ImageView) {
        this.imageView = imageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQ_CAMERA)
            } else
                openCamera()
        } else {
            openCamera()
        }
    }

    fun galleryBtn(imageView: ImageView) {
        this.imageView = imageView
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, REQ_GALLERY)
    }


    private fun openCamera() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, REQ_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK && data != null) {

            val bitmap = data.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)

        } else if (requestCode == REQ_GALLERY && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            imageView.setImageURI(uri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //-----------------------------------------------------------------------------------
    //options menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
                val prefs = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("hasSignedInBefore", false).apply()
                val i = Intent(this, UserSignActivity::class.java)
                startActivity(i)
            }
            R.id.exit -> {
                Toast.makeText(this, "Exited", Toast.LENGTH_SHORT).show()
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
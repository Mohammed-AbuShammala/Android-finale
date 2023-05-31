package com.example.finalproject.fragment.homeFragment

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.UserSignActivity
//import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
import com.example.finalproject.databinding.FragmentProfileBinding
import com.example.finalproject.databinding.FragmentSignInBinding
import com.example.finalproject.model.User
import java.util.*

private const val USER_ID = "userId"

class ProfileFragment : Fragment() {

    private var userId = MainActivity.userId
    private var isEditing = false

    lateinit var binding: FragmentProfileBinding
//    lateinit var db:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
//        db = DatabaseHelper(requireContext())

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        //hide camera and gallery buttons
        binding.btnAddImgFromCamera.visibility = View.INVISIBLE;
        binding.btnAddImgFromGallery.visibility = View.INVISIBLE;
        val prefs = this.requireContext().getSharedPreferences("MyPref",0)
        var userId = prefs.getString("userId", "")
        Log.e("profile", userId!!)

        val user : User? = User.getUserByID(userId)
        Log.e("profile", user.toString())
        if(user != null) {
            binding.tvName.setText(user.name)
            binding.tvEmail.setText(user.email)
            binding.tvDOB.setText(user.dob)
            binding.tvPassword.setText(user.password)
//        binding.personImg.setImageBitmap(profile.image)
        }


        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing

            //When pressed, the user will be given the ability to modify and save the modified data as well
            if (isEditing) {
                binding.btnEdit.text = "save"

                binding.tvName.isEnabled = true
                binding.tvEmail.isEnabled = true
                binding.tvDOB.isEnabled = true
                binding.tvPassword.isEnabled = true

                //Show camera and gallery buttons
                binding.btnAddImgFromCamera.visibility = View.VISIBLE;
                binding.btnAddImgFromGallery.visibility = View.VISIBLE;

            } else {
                //pressed the save btn
                binding.btnEdit.text = "edit"

                binding.tvName.isEnabled = false
                binding.tvEmail.isEnabled = false
                binding.tvDOB.isEnabled = false
                binding.tvPassword.isEnabled = false

                //hide camera and gallery buttons
                binding.btnAddImgFromCamera.visibility = View.INVISIBLE;
                binding.btnAddImgFromGallery.visibility = View.INVISIBLE;

                if (binding.tvName.text.toString().isNotEmpty() &&
                    binding.tvEmail.text.toString().isNotEmpty() &&
                    binding.tvDOB.text.toString().isNotEmpty() &&
                    binding.tvPassword.text.toString().isNotEmpty()
                ) {

                    val personName = binding.tvName.text.toString()
                    val personEmail = binding.tvEmail.text.toString()
                    val personDOB = binding.tvDOB.text.toString()
                    val personPassword = binding.tvPassword.text.toString()
                    val bitmap = (binding.personImg.drawable as BitmapDrawable).bitmap

//                    db.updateUser(userId, personName, personEmail, personPassword, personDOB, bitmap)
                } else
                    Toast.makeText(
                        context,
                        "Please make sure to fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }


        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as MainActivity).galleryBtn(binding.personImg)
        }


        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as MainActivity).cameraBtn(binding.personImg)
        }


        binding.tvDOB.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            // val formatter = SimpleDateFormat("d/M/yyyy EE", Locale.ENGLISH)
            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                binding.tvDOB.setText("$y/${m + 1}/$d")
            }, year, month, day)
            datePicker.show()

        }


        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, "logged out", Toast.LENGTH_SHORT).show()
            val prefs =
                requireContext().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("hasSignedInBefore", false).apply()
            val i = Intent(requireContext(), UserSignActivity::class.java)
            startActivity(i)
        }

    }


}
package com.example.finalproject.fragment.signFragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalproject.MainActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.UserSignActivity
//import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.FragmentSignUpBinding
import com.example.finalproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class SignUpFragment : Fragment() {


    lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            //The button that when pressed saves the information of the new user
            if (binding.tvName.text.isNotEmpty() && binding.tvPassword.text.isNotEmpty()
                && binding.tvRePassword.text.isNotEmpty() && binding.tvEmail.text.isNotEmpty()
                && binding.tvdob.text.isNotEmpty()
            ) {
                val username = binding.tvName.text.toString()
                val email = binding.tvEmail.text.toString()
                val pass = binding.tvPassword.text.toString()
                val rePass = binding.tvPassword.text.toString()
                val dob = binding.tvdob.text.toString()
                val bitmap = (binding.imgUser.drawable as BitmapDrawable).bitmap

                //To check whether the password in the first field is the same as in the second field
                if (pass == rePass && email != null) {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("auth", "createUserWithEmail:success")
                            val user = auth.currentUser
                           var createdUser : User = User(task.result.user!!.uid,username,email,pass,dob, ("31.527923, 34.449872"), 0);
                           var result = User.addUserObject(createdUser)

                            Log.d("auth", "Creating user result is: ${result.toString()}")

                            UserSignActivity.swipeFragment(requireActivity(), SignInFragment())

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("auth", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this.context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            }
                    }


//
                } else {
                    Toast.makeText(
                        context,
                        "Please verify that the password is correct",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Fill in The Required Fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as UserSignActivity).galleryBtn(binding.imgUser)
        }


        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as UserSignActivity).cameraBtn(binding.imgUser)
        }

        binding.tvdob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            // val formatter = SimpleDateFormat("d/M/yyyy EE", Locale.ENGLISH)
            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                binding.tvdob.setText("$y/${m + 1}/$d")
            }, year, month, day)
            datePicker.show()

        }

//******************************************************  signInLink  *********************************************
        // The text that the user will click on if they already have an account
        binding.singInLink.setOnClickListener {
            UserSignActivity.swipeFragment(requireActivity(), SignInFragment())
        }

    }

}

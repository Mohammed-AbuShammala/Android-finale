package com.example.finalproject.fragment.signFragment

import android.content.Intent
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
import com.example.finalproject.databinding.FragmentSignInBinding
import com.example.finalproject.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            if (binding.txtUserName.text.toString()
                    .isNotEmpty() && binding.txtPassword.text.toString().isNotEmpty()
            ) {
                val username = binding.txtUserName.text.toString()
                val password = binding.txtPassword.text.toString()


                var foundUser = false
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("atuh", "signInWithEmail:success")
                            val user = auth.currentUser
                            foundUser = true
                            //shared pref
                            val prefs = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                            val editor = prefs.edit()
                            editor.putBoolean("hasSignedInBefore", true).apply()
                            Log.d("auth", "add id = ${user!!.uid}")
                            editor.putString("userId", user!!.uid).apply()
                            Log.d("auth", "from SharedPreferneces ${prefs.getString("userId", "")}")
                            val i = Intent(this.requireContext(), MainActivity::class.java)
                            startActivity(i)
                            requireActivity().finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("auth", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                this.context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            if(!foundUser){
                                Toast.makeText(
                                    requireContext(),
                                    "the username or password your entered in incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }                        }
                    }




            } else
                Toast.makeText(context, "Please Fill in The Required Fields", Toast.LENGTH_SHORT)
                    .show()
        }

//************************************************** The event that occurs when you click on the Sign Up text ****************
        binding.btnSingup.setOnClickListener {
            UserSignActivity.swipeFragment(requireActivity(), SignUpFragment())
        }
    }


}
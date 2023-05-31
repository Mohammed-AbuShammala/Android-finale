package com.example.finalproject.fragment.homeFragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import com.example.finalproject.MainActivity
//import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentAddBookBinding
import com.example.finalproject.databinding.FragmentBorrowerBinding
import com.example.finalproject.model.Book
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class AddBookFragment : Fragment() {

    lateinit var binding: FragmentAddBookBinding
//    lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBookBinding.inflate(inflater, container, false)
//        db = DatabaseHelper(requireActivity())

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        binding.btnAdd.setOnClickListener {
            if (binding.bookName.text.toString()
                    .isNotEmpty() && binding.spinnerCategory.selectedItem.toString().isNotEmpty() &&
                binding.authorName.text.toString()
                    .isNotEmpty() && binding.spinnerLanguage.selectedItem.toString().isNotEmpty() &&
                binding.numberOfPages.text.toString()
                    .isNotEmpty() && binding.shelfNumber.text.toString().isNotEmpty() &&
                binding.NumberOfCopiesOfBooks.text.toString()
                    .isNotEmpty() && binding.releaseYear.text.toString().isNotEmpty() &&
                binding.description.text.toString().isNotEmpty()
            ) {

                val bookName = binding.bookName.text.toString()
                val category = binding.spinnerCategory.selectedItem.toString()
                val authorName = binding.authorName.text.toString()
                val language = binding.spinnerLanguage.selectedItem.toString()
                val pagesNum = binding.numberOfPages.text.toString().toInt()
                val shelfNumber = binding.shelfNumber.text.toString()
                val copiesNum = binding.NumberOfCopiesOfBooks.text.toString().toInt()
                val releaseYear = binding.releaseYear.text.toString().toInt()
                val description = binding.description.text.toString()

               Book.addBookObject(Book("",bookName,4.4,4.9,category,""))
                Toast.makeText(requireContext(),"Book added successfully",Toast.LENGTH_LONG).show()

            } else
                Toast.makeText(
                    context,
                    "Please make sure to fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
        }

//  When you press this button, it returns to the interface from which it came, which is the main and cancels the addition process
        binding.btnCancel.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(), MainFragment())
        }

        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as MainActivity).cameraBtn(binding.imgBook)
        }

        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as MainActivity).galleryBtn(binding.imgBook)
        }

    }

}

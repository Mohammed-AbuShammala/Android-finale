package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
import com.example.finalproject.databinding.FragmentBorrowerBinding
import com.example.finalproject.model.Book


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BOOK_ID = "bookId"

class BookDescriptionFragment : Fragment() {

    lateinit var binding: FragmentBookDescriptionBinding
//    lateinit var db:DatabaseHelper

    private var bookId: String? = null
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.bookId = it.getString(BOOK_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
//        db = DatabaseHelper(requireContext())



        return binding.root
    }


    override fun onResume() {
        super.onResume()

        //hide camera and gallery buttons
        binding.btnAddImgFromCamera.visibility = View.INVISIBLE;
        binding.btnAddImgFromGallery.visibility = View.INVISIBLE;

        //disable spinners
        binding.spinnerCategory.isEnabled = false
        binding.spinnerLanguage.isEnabled = false


        //the spinners lists
        val categoryArr: List<String> = resources.getStringArray(R.array.category).toList();
        val languageArr: List<String> = resources.getStringArray(R.array.language).toList();

//        val book = db.getBook(bookId!!)
        lateinit var book : Book
        Book.getBookByID(bookId!!) { cbBook ->
            if (cbBook != null) {
                book = cbBook

                binding.bookName.setText(book.name)
//        binding.spinnerCategory.setSelection(categoryArr.indexOf(book.category))
//        binding.authorName.setText(book.author)
//        binding.spinnerLanguage.setSelection(languageArr.indexOf(book.language))
//        binding.numberOfPages.setText(book.numberOfPages.toString())
//        binding.shelfNumber.setText(book.shelfNumber)
//        binding.NumberOfCopiesOfBooks.setText(book.numberOfCopies.toString())
//        binding.releaseYear.setText(book.releaseYear.toString())
                binding.description.setText(book.description)
//        binding.imgBook.setImageBitmap(book.image)

                Log.e("Book", book.toString())
            } else {
                // Book not found
                Log.e("Book", "Book not found")
            }
        }



        //Add the book to your favourites
////        var isFavorite = db.isFavorite(MainActivity.userId, bookId!!)
//
//        fun setFavoriteBtnText() {
//            if (isFavorite) {
//                binding.addToFavorite.text = "Remove from Favorite"
//            } else {
//                binding.addToFavorite.text = "Add to Favorite"
//            }
//        }


        //setting the starting text to the favorite btn
//        setFavoriteBtnText()


//        binding.addToFavorite.setOnClickListener {
//
//            if (!isFavorite) {
//                //add to favorite
//                if (db.insertFavorite(MainActivity.userId, bookId!!)) {
//                    Toast.makeText(requireContext(), "Added to Favorite", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(requireContext(), "error while adding to favorite", Toast.LENGTH_SHORT).show()
//                }
//            }else{
//                //remove from favorite
//                if(db.deleteFavorite(MainActivity.userId, bookId!!)) {
//                    Toast.makeText(requireContext(), "Removed to Favorite", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(requireContext(), "error while Removing from favorite", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            isFavorite = !isFavorite
//            setFavoriteBtnText()
//        }



        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as MainActivity).cameraBtn(binding.imgBook)
        }

        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as MainActivity).galleryBtn(binding.imgBook)
        }


        binding.imgBook.setOnClickListener {
            (requireActivity() as MainActivity).cameraBtn(binding.imgBook)
        }

    }


    companion object {
        fun newInstance(bookId: String) =
            BookDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(BOOK_ID, bookId)
                }
            }
    }
}
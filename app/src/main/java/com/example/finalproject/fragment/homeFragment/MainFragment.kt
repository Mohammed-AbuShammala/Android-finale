package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.MainActivity
import com.example.finalproject.adapter.BookAdapter
//import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentMainBinding
import android.widget.AdapterView
import com.example.finalproject.databinding.FragmentProfileBinding
import com.example.finalproject.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var db : FirebaseFirestore
    lateinit var books :ArrayList<Book>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        db = Firebase.firestore

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        var adapter :BookAdapter? =null
        Book.getAllBooks { books ->
            adapter = BookAdapter(books)
        binding.rvView.adapter = adapter
        }
        binding.rvView.layoutManager = GridLayoutManager(requireContext(), 2)

        //searching for book
        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                (binding.rvView.adapter as BookAdapter)//.search(s.toString())
            }

        })

        //searching using category
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (binding.spinner.selectedItem.toString() == "All") {
                    //get all books
//                    (binding.rvView.adapter as BookAdapter)//.searchCategory("")
                }else{
//                    (binding.rvView.adapter as BookAdapter)//.searchCategory(binding.spinner.selectedItem.toString())
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }

        binding.btnBookAdd.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(), AddBookFragment())

        }

        binding.root.setOnClickListener {

//            MainActivity.swipeFragment(requireActivity(), BookDescriptionFragment())
        }

    }

}
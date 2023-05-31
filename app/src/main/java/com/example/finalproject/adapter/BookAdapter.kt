package com.example.finalproject.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
//import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.BookCardViewBinding
import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment
//import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment
import com.example.finalproject.model.Book


class BookAdapter(var data: ArrayList<Book>): RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    lateinit var context: Context
    private var initialData = data

    class MyViewHolder(val cardViewBinding: BookCardViewBinding): RecyclerView.ViewHolder(cardViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding : BookCardViewBinding
                = BookCardViewBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardViewBinding.apply {
            txtBookName.text = data[position].name
            Log.e("adtr", "${data[position].name}")
//            txtAuthorName.text = data[position].author

        }


        holder.cardViewBinding.root.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Delete book")
                setMessage("Are you sure that you want to delete this book?")
                setPositiveButton("Yse"){
                        _, _ ->
//                    if(DatabaseHelper(context).deleteBook(data[position].id)){
//                        data.removeAt(position)
//                        notifyDataSetChanged()
//                    }

                }
                setCancelable(true)
                setNegativeButton("No"){ dialogInterface: DialogInterface, _ ->
                    dialogInterface.cancel()
                }
                create()
                show()
            }
            true
        }

        holder.cardViewBinding.root.setOnClickListener {
            Book.getBookByID(data[position].id) { book ->
                if (book != null) {
                    // Book exists, do something with it
                    Log.e("Book", book.toString())
                } else {
                    // Book not found
                    Log.e("Book", "Book not found")
                }
            }
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

                .replace(R.id.fragmentContainerView, BookDescriptionFragment.newInstance(data[position].id)).commit()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

//    fun search(text : String){
////        val newArray = initialData.filter { book ->
//////            book.name.contains(text) || book.author.startsWith(text)
////        }
////        data = newArray as ArrayList<Book>
//        notifyDataSetChanged()
//    }

//    fun searchCategory(category: String){
//        val newArray = initialData.filter { book ->
////            book.category.contains(category)
//        }
//        data = newArray as ArrayList<Book>
//        notifyDataSetChanged()
//    }



}
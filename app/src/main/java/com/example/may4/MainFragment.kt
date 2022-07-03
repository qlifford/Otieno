package com.example.may4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.may4.database.AppDatabase
import com.example.may4.database.ProductFromDatabase
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL


class MainFragment : androidx.fragment.app.Fragment() {
    var categoriesRecyclerView: RecyclerView? = null
    var recyclerView: RecyclerView? = null
    var searchButton: Button? = null
    var searchTerm: EditText? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val categories = listOf(
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
            "shoes",
        )
        categoriesRecyclerView?.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
                false
            )
            adapter = CategoriesAdapter(categories)
            progressBar!!.visibility = View.GONE
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton?.setOnClickListener {
            doAsync {
                val db = Room.databaseBuilder(
                    requireActivity().applicationContext,
                    AppDatabase::class.java, "database_name"
                ).build()
                val productsFromDatabase = db.productDao().searchFor("${searchTerm?.text}")
                val products = productsFromDatabase.map {
                    Products(
                        it.title,
                        "https://png.pngtree.com/png-clipart/20190604/original/pngtree-restaurant-waiter-male-suit-png-image_1267605.jpg",
                        it.price,
                        true
                    )
                }
                uiThread {
                    recyclerView?.apply {
                        layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 2)
                        adapter = ProductsAdapter(products)
                        progressBar?.visibility = View.GONE
                    }
                }
            }
        }
    }
}

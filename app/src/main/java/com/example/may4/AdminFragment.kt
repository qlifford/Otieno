package com.example.may4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.may4.database.AppDatabase
import com.example.may4.database.ProductFromDatabase
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AdminFragment : androidx.fragment.app.Fragment() {

    var categoriesRecyclerView: RecyclerView? = null
    var recyclerView: RecyclerView? = null
    var btnSearch: Button? = null
    var edtSearch: EditText? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? { val root = inflater.inflate(R.layout.fragment_admin, container, false)

        val categories = listOf(
            "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes", "shoes",)
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

        btnSearch?.setOnClickListener {
            doAsync {
                val db = Room.databaseBuilder(
                    requireActivity().applicationContext,
                    AppDatabase::class.java, "database_name"
                ).build()
                val productsFromDatabase = db.productDao().searchFor("${edtSearch?.text}")
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
                    }
                    progressBar?.visibility = View.GONE
                }
            }
        }
    }
}


   /* var button: Button? = null
    var productTitle: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator)


        button?.setOnClickListener {
            val title = productTitle!!.text
            d("god","oh pressed!:) with text of $title")
        }

    }
}*/
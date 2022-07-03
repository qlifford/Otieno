package com.example.may4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.may4.cart.CartActivity
import com.example.may4.database.AppDatabase
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var navView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var frameLayout: FrameLayout? = null
    var progressBar: ProgressBar? = null
    var categoriesRecyclerView: RecyclerView? = null
    var boolean: Boolean? = null
    private var search: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawerLayout)
        frameLayout = findViewById(R.id.frameLayout)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)


        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MainFragment())
            .commit()

        navView!!.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MainFragment())
                        .commit()
                }
                R.id.actionAdmin -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AdminFragment())
                        .commit()
                }
            }
            it.isChecked = true
            drawerLayout!!.closeDrawers()

            true
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        }
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
        )
        categoriesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = CategoriesAdapter(categories)
            progressBar?.visibility = View.GONE

        }

        doAsync {
            val db = Room.databaseBuilder(
                this@MainActivity!!.applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

            val productFromDatabase = db.productDao().getAll()

            val products = productFromDatabase.map {
                Products(
                    it.title,
                    "https://www.trustedreviews.com/wp-content/uploads/sites/54/2020/05/Samsung-Galaxy-Z-Flip-Flex-Mode-920x518.jpg",
                    it.price,
                    true
                )
            }
            uiThread {
                recyclerView?.apply {
                    layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(this@MainActivity, 2)
                    adapter = ProductsAdapter(products)
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId
            == R.id.ActionCart) {
            startActivity(Intent(this, CartActivity::class.java))

            return true
        }

    super.onOptionsItemSelected(item)
        drawerLayout!!.openDrawer(GravityCompat.START)
        return true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }
}



//logout profile
/*class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private var hFragment:Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


         actionBar = supportActionBar!!
         actionBar.title = "Back"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            binding.emailTil.text = email

        }else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }

}*/
//
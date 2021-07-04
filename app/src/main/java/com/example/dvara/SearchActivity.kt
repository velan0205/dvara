package com.example.dvara

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvara.databinding.ActivitySearchBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding

    private lateinit var database: DatabaseReference

    lateinit var mobileSearchAdapter: MobileSearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference

        mobileSearchAdapter = MobileSearchAdapter()

        val layoutManager1 = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        with(binding.recyclerView) {
            adapter = mobileSearchAdapter

            layoutManager = layoutManager1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu?.findItem(R.id.searchView_MenuMain)
        val searchView: SearchView = item?.actionView as SearchView



        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@SearchActivity, newText.toString(), Toast.LENGTH_LONG).show()
                // adapter.filter.filter(newText)
                 firebaseUserSearch(newText.toString())
                //database.orderByChild("mobile_list").startAt(newText).endAt(newText + "\uf8ff")
                return false
            }
        })

        return true
    }

    private fun firebaseUserSearch(searchText: String) {
        // My top posts by number of stars
        val myTopPostsQuery = database.child("mobile_list").child(searchText)
            .orderByChild("starCount")


        // mobileSearchAdapter.setAdapter(firebaseRecyclerAdapter)
    }

}
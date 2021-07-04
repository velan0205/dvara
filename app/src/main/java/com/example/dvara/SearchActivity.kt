package com.example.dvara

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvara.databinding.ActivitySearchBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding

    private lateinit var database: DatabaseReference
    lateinit var listener: ChildEventListener
    var searchText = ""

    lateinit var mobileSearchAdapter: MobileSearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.getReference("mobile_list")

        mobileSearchAdapter = MobileSearchAdapter()

        val layoutManager1 = LinearLayoutManager(this)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.adapter = mobileSearchAdapter
        binding.recyclerView.layoutManager = layoutManager1

        listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val mobile = snapshot.getValue<Mobile>()
                mobileSearchAdapter.addData(mobile!!)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

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
                database.orderByChild("mobileNumber").startAt(searchText)
                    .removeEventListener(listener)
                searchText = newText!!
                if (!newText.isNullOrEmpty()){
                    firebaseUserSearch(newText.toString())} else{
                    mobileSearchAdapter.clear()
                }
                return false
            }
        })

        return true
    }

    private fun firebaseUserSearch(searchText: String) {
        mobileSearchAdapter.clear()
        database.orderByChild("mobileNumber").equalTo(searchText).limitToFirst(1)
            .get().addOnSuccessListener {
                for (result in it.children) {
                    val mobile = result.getValue<Mobile>()
                    mobileSearchAdapter.addData(mobile!!)
                }
            }
    }

}
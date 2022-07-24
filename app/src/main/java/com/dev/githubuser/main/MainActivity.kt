package com.dev.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.R
import com.dev.githubuser.adapter.UserAdapter
import com.dev.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.findUser()

        viewModel.listUser.observe(this, { user ->
            setUsersData(user)
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        // Search View
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Masukkan Kata" // Hint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.findUser(query) // cari user
                searchView.clearFocus() // Hapus Duplikat Kata
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    private fun setUsersData(listItem: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()

        listUser.clear()
        for (i in listItem.indices) {
            val user = listItem[i]
            listUser.add(user)
        }

        val adapter = UserAdapter(listUser, 1)

        with(binding) {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvUser.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
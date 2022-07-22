package com.dev.githubuser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listUser = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findUser()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        // Search View
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Masukkan Kata" // Hint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query) // cari user
                searchView.clearFocus() // Hapus Duplikat Kata
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    // cari user
    private fun findUser(username: String = "Fardal") {
        val client = ApiConfig.getApiService().findUsers(username)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                showLoading(true)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    showLoading(false)
                    if (responseBody != null) {
                        setUsersData(responseBody.items)
                    }
                } else {
                    Log.e("asd", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}")
            }
        })
    }

    fun setUsersData(listItem: List<ItemsItem>) {
        listUser.clear()
        for (i in listItem.indices) {
            val user = User(username = listItem[i].login, photo = listItem[i].avatarUrl)
            listUser.add(user)
        }

        val adapter = UserAdapter(listUser)

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

//    private val listUser: ArrayList<User>
//        get() {
//            val dataName = resources.getStringArray(R.array.name)
//            val dataUsername = resources.getStringArray(R.array.username)
//            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
//            val dataLocation = resources.getStringArray(R.array.location)
//            val dataCompany = resources.getStringArray(R.array.company)
//            val dataRepository = resources.getStringArray(R.array.repository)
//            val dataFollowing = resources.getStringArray(R.array.following)
//            val dataFollowers = resources.getStringArray(R.array.followers)
//
//            val listUser = ArrayList<User>()
//            for (i in dataName.indices) {
//                val user = User(dataName[i], dataUsername[i], dataPhoto.getResourceId(i, -1),
//                    dataLocation[i], dataRepository[i], dataCompany[i],
//                    dataFollowers[i], dataFollowing[i])
//                listUser.add(user)
//            }
//            dataPhoto.recycle()
//
//            return listUser
//        }
}
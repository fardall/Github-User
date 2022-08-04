package com.dev.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.githubuser.R
import com.dev.githubuser.adapter.UserAdapter
import com.dev.githubuser.databinding.ActivityMainBinding
import com.dev.githubuser.favorite.FavoriteActivity
import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.settings.SettingPreferences
import com.dev.githubuser.settings.SettingsActivity
import com.dev.githubuser.settings.SettingsViewModel
import com.dev.githubuser.settings.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = obtainViewModel(this, pref)

        settingsViewModel.getThemeSettings()?.observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        viewModel.findUser()

        viewModel.listUser.observe(this, { listUser ->
            if (listUser.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.tvNoData.visibility = View.GONE
            }
            setUsersData(listUser)

        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    private fun obtainViewModel(activity: AppCompatActivity, preferences: SettingPreferences): SettingsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref = preferences)
        return ViewModelProvider(activity, factory)[SettingsViewModel::class.java]
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val toSettings = Intent(this, SettingsActivity::class.java)
                startActivity(toSettings)
                true
            }
            R.id.favorite -> {
                val toFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(toFavorite)
                true
            }
            else -> true
        }
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
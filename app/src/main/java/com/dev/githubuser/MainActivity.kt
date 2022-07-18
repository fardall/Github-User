package com.dev.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UserAdapter(listUser)

        with(binding) {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvUser.adapter = adapter
        }
    }

    private val listUser: ArrayList<User>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataFollowers = resources.getStringArray(R.array.followers)

            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(dataName[i], dataUsername[i], dataPhoto.getResourceId(i, -1),
                    dataLocation[i], dataRepository[i], dataCompany[i],
                    dataFollowers[i], dataFollowing[i])

                listUser.add(user)
            }
            dataPhoto.recycle()

            return listUser
        }
}
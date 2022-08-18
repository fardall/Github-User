package com.dev.githubuser.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.githubuser.detail.DetailViewModel
import com.dev.githubuser.domain.UserUseCase
import com.dev.githubuser.favorite.FavoriteViewModel
import com.dev.githubuser.di.Injection
import com.dev.githubuser.followers.FollowersViewModel
import com.dev.githubuser.following.FollowingViewModel
import com.dev.githubuser.main.MainViewModel

class ViewModelFactory private constructor(private val useCase: UserUseCase, private val pref: SettingPreferences?) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreferences?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideUseCase(application), pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(useCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(useCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(useCase) as T
            }
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(useCase) as T
            }
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(useCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }


}
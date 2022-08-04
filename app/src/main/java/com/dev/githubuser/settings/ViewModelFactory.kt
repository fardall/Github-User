package com.dev.githubuser.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.githubuser.detail.DetailViewModel
import com.dev.githubuser.favorite.FavoriteViewModel
import com.dev.githubuser.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val mApplication: Application, private val pref: SettingPreferences?) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreferences?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
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
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mApplication) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }


}
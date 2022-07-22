package com.dev.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var name: String? = null,
    var username: String? = null,
    var photo: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var followers: String? = null,
    var following: String? = null
) : Parcelable

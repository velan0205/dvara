package com.example.dvara

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Mobile(
    val upSpeed: String? = null,
    val timeStamp: String? = null,
    val mobileNumber: String? = null
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
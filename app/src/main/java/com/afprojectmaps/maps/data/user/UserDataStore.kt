package com.afprojectmaps.maps.data.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.afprojectmaps.maps.data.user.model.User
import com.afprojectmaps.maps.data.user.model.mapUser

class UserDataStore {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    private val userId = auth.currentUser?.uid

    fun get(
        onSuccess:(User) -> Unit
    ){
        userId ?: return

        database.reference.child("users")
            .child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.mapUser()
                    onSuccess(user)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun updateCountInterstitialAds(count: Int) {
        userId ?: return

        database.reference.child("users")
            .child(userId)
            .child("countInterstitialAds")
            .setValue(count)
    }

    fun updateCountInterstitialAdsClick(count: Int){
        userId ?: return

        database.reference.child("users")
            .child(userId)
            .child("countInterstitialAdsClick")
            .setValue(count)
    }

    fun updateCountRewardedAds(count: Int) {
        userId ?: return

        database.reference.child("users")
            .child(userId)
            .child("countRewardedAds")
            .setValue(count)
    }

    fun updateCountRewardedAdsClick(count: Int){
        userId ?: return
        database.reference.child("users")
            .child(userId)
            .child("countRewardedAdsClick")
            .setValue(count)
    }
}
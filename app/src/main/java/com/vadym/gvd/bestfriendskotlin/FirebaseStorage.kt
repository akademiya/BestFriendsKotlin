package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vadym.gvd.bestfriendskotlin.holly_days.HollyDayEntity

private const val DB_URL = "https://tf-prayer.firebaseio.com/"
class FirebaseStorage(private val context: Context) {

    private val hollyDayRef: DatabaseReference = Firebase.database(DB_URL).getReference("HollyDays")

    fun saveDaysToFirebase(hollyDays: List<HollyDayEntity>) {
        hollyDays.forEach { day ->
            day.id += hollyDayRef.push().key
            day.id.let {
                hollyDayRef.child(it.toString()).setValue(day)
            }
        }
    }

    fun listHollyDaysFromFB(callback: (List<HollyDayEntity>) -> Unit) {
        val hollyDaysList = mutableListOf<HollyDayEntity>()

        hollyDayRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hollyDaysList.clear()
                for (personSnapshot in dataSnapshot.children) {
                    val dayModel = personSnapshot.getValue(HollyDayEntity::class.java)
                    if (dayModel != null) {
                        hollyDaysList.add(dayModel)
                    }
                }
                callback(hollyDaysList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun updateValueOfDay(id: String, newDay: String) {
        hollyDayRef.child(id).child("day").setValue(newDay)
    }
}
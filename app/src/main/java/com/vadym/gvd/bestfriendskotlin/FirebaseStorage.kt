package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vadym.gvd.bestfriendskotlin.holy_days.HolyDayEntity

private const val DB_URL = "https://tf-prayer.firebaseio.com/"
class FirebaseStorage(private val context: Context) {

    private val hollyDayRef: DatabaseReference = Firebase.database(DB_URL).getReference("HollyDays")
    private val infoMessage: DatabaseReference = Firebase.database(DB_URL).getReference("InfoMessage")

    fun saveDaysToFirebase(holyDays: List<HolyDayEntity>) {
        holyDays.forEach { day ->
            day.id += hollyDayRef.push().key
            day.id.let {
                hollyDayRef.child(it.toString()).setValue(day)
            }
        }
    }

    fun listHollyDaysFromFB(callback: (List<HolyDayEntity>) -> Unit) {
        val holyDaysList = mutableListOf<HolyDayEntity>()

        hollyDayRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                holyDaysList.clear()
                for (personSnapshot in dataSnapshot.children) {
                    val dayModel = personSnapshot.getValue(HolyDayEntity::class.java)
                    if (dayModel != null) {
                        holyDaysList.add(dayModel)
                    }
                }
                callback(holyDaysList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun updateValueOfDay(id: String, newDay: String) {
        hollyDayRef.child(id).child("day").setValue(newDay)
    }

    fun infoMessageFromFB(callback: (String) -> Unit) {
//        infoMessage.push().key.let {
//            infoMessage.child(it.toString()).setValue("Some message")
//        }
        infoMessage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val message = childSnapshot.getValue(String::class.java)
                    callback(message.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error: ${error.message}")
            }
        })
    }
}
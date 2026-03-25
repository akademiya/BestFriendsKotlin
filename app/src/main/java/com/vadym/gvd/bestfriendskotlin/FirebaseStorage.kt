package com.vadym.gvd.bestfriendskotlin

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vadym.gvd.bestfriendskotlin.holy_days.HolyDayEntity
import java.util.Locale

private const val DB_URL = "https://tf-prayer.firebaseio.com/"

class FirebaseStorage {

    // ✅ Singleton — один екземпляр на весь додаток
    companion object {
        val instance: FirebaseStorage by lazy { FirebaseStorage() }
    }

    private val hollyDayRef: DatabaseReference =
        Firebase.database(DB_URL).getReference("HollyDays")
    private val infoMessageRef: DatabaseReference =
        Firebase.database(DB_URL).getReference("InfoMessage")

    // ✅ Зберігає активний listener щоб можна було його зняти
    private var hollyDaysListener: ValueEventListener? = null

    fun saveDaysToFirebase(holyDays: List<HolyDayEntity>) {
        holyDays.forEach { day ->
            val key = hollyDayRef.push().key ?: return@forEach
            day.id = key
            hollyDayRef.child(key).setValue(day)
        }
    }

    // ✅ addListenerForSingleValueEvent замість addValueEventListener —
    //    дані зчитуються один раз, не тримає відкрите з'єднання постійно
    fun listHollyDaysFromFB(callback: (List<HolyDayEntity>) -> Unit) {
        hollyDayRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = snapshot.children.mapNotNull {
//                    it.getValue(HolyDayEntity::class.java)
//                }
                val list = snapshot.children.mapNotNull { child ->
                    val id    = child.child("id").getValue(String::class.java)
                    val title = child.child("title").getValue(String::class.java)
                    val day   = child.child("day").getValue(String::class.java)
                    if (day != null) HolyDayEntity(id, title, day) else null
                }
                callback(list)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    // ✅ Якщо потрібні realtime-оновлення (наприклад для календаря)
    fun observeHollyDays(callback: (List<HolyDayEntity>) -> Unit) {
        hollyDaysListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull {
                    it.getValue(HolyDayEntity::class.java)
                }
                callback(list)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        }
        hollyDayRef.addValueEventListener(hollyDaysListener!!)
    }

    // ✅ Знімає realtime-listener щоб уникнути витоків пам'яті
    fun removeHollyDaysObserver() {
        hollyDaysListener?.let { hollyDayRef.removeEventListener(it) }
        hollyDaysListener = null
    }

    fun updateValueOfDay(id: String, newDay: String) {
        hollyDayRef.child(id).child("day").setValue(newDay)
    }

    fun infoMessageFromFB(callback: (String?) -> Unit) {
        infoMessageRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val locale = Locale.getDefault().language
                val message = snapshot.child(locale).getValue(String::class.java)
                    ?: snapshot.child("en").getValue(String::class.java)

                callback(message?.takeIf { it.isNotBlank() })
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}
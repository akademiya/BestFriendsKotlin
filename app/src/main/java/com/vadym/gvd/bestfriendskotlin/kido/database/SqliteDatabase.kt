package com.vadym.gvd.bestfriendskotlin.kido.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vadym.gvd.bestfriendskotlin.kido.Person
import java.util.*

class SqliteDatabase private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PERSON_TABLE = ("CREATE TABLE $TABLE_PERSONS($KEY_ID INTEGER PRIMARY KEY,$KEY_PERSON_NAME TEXT,$KEY_PERSON_DESCRIPTION TEXT)")
        db?.execSQL(CREATE_PERSON_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONS")
        onCreate(db)
    }

    fun listPerson(): List<Person> {
        val sql = "select * from $TABLE_PERSONS"
        val db = this.readableDatabase
        val storePersons = ArrayList<Person>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val description = cursor.getString(2)
                storePersons.add(Person(id, name, description))

            } while (cursor.moveToNext())
        }
        cursor.close()
        return storePersons
    }

    fun addPerson(person: Person) {
        val values = ContentValues()
        values.put(KEY_PERSON_NAME, person.personName)
        values.put(KEY_PERSON_DESCRIPTION, person.personDescription)
        val db = this.writableDatabase

        db.insert(TABLE_PERSONS, null, values)
        db.close()
    }

    fun updatePerson(person: Person) {
        val values = ContentValues()
        values.put(KEY_PERSON_NAME, person.personName)
        values.put(KEY_PERSON_DESCRIPTION, person.personDescription)
        val db = this.readableDatabase
        db.update(TABLE_PERSONS, values, "$KEY_ID=?", arrayOf(person.personId.toString()))
    }

    fun deletePerson(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PERSONS, "$KEY_ID =?", arrayOf(id.toString()))
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "person"
        val TABLE_PERSONS = "persons"

        val KEY_ID = "_id"
        val KEY_PERSON_NAME = "personname"
        val KEY_PERSON_DESCRIPTION = "persondescription"

        private var instance: SqliteDatabase? = null
        fun getInstance(context: Context): SqliteDatabase {
            if (instance == null) {
                instance = SqliteDatabase(context)
            }
            return instance!!
        }

    }

}
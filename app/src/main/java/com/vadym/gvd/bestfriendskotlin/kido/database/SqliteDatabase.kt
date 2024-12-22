package com.vadym.gvd.bestfriendskotlin.kido.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.vadym.gvd.bestfriendskotlin.kido.Person
import java.io.ByteArrayOutputStream

class SqliteDatabase private constructor(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PERSON_TABLE = ("CREATE TABLE $TABLE_PERSONS($KEY_ID INTEGER PRIMARY KEY,$KEY_PERSON_NAME TEXT,$KEY_PERSON_DESCRIPTION TEXT,$KEY_PERSON_PHOTO BLOB,$KEY_POSITION INTEGER)")
        db?.execSQL(CREATE_PERSON_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < DATABASE_VERSION) {
            if (!isFieldExist(TABLE_PERSONS, KEY_PERSON_PHOTO)) {
                db.execSQL("ALTER TABLE $TABLE_PERSONS ADD COLUMN $KEY_PERSON_PHOTO BLOB DEFAULT NULL;")
            }
        } else {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONS")
            onCreate(db)
        }
    }

    @SuppressLint("Recycle")
    private fun isFieldExist(tableName: String, fieldName: String): Boolean {
        var isExist = false
        val db = this.writableDatabase
        val res = db.rawQuery("PRAGMA table_info($tableName)", null)
        res.moveToFirst()
        do {
            val currentColumn = res.getString(1)
            if (currentColumn == fieldName) {
                isExist = true
            }
        } while (res.moveToNext())
        return isExist
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
                val photo = cursor.getBlob(3)
                val position = Integer.parseInt(cursor.getString(4))
                storePersons.add(Person(id, name, description, convertByteArrayToBitmap(photo), position))

            } while (cursor.moveToNext())
        }
        cursor.close()
        return storePersons.sortedBy { it.personPosition }
    }

    fun addPerson(person: Person) {
        val values = ContentValues()
        values.put(KEY_PERSON_NAME, person.personName)
        values.put(KEY_PERSON_DESCRIPTION, person.personDescription)
        values.put(KEY_POSITION, person.personPosition)
        values.put(KEY_PERSON_PHOTO, person.personPhoto?.let { convertImageToByteArray(it) })
        val db = this.writableDatabase

        db.insert(TABLE_PERSONS, null, values)
        db.close()
    }

    fun updatePerson(person: Person) {
        val values = ContentValues()
        values.put(KEY_PERSON_NAME, person.personName)
        values.put(KEY_PERSON_DESCRIPTION, person.personDescription)
        values.put(KEY_PERSON_PHOTO, person.personPhoto?.let { convertImageToByteArray(it) })
        val db = this.readableDatabase
        db.update(TABLE_PERSONS, values, "$KEY_ID=?", arrayOf(person.personId.toString()))
    }

    fun updateSortPosition(person: Person) {
        val db = this.readableDatabase
        val values = ContentValues()
        val columns = arrayOf(KEY_ID, KEY_PERSON_NAME, KEY_PERSON_DESCRIPTION, KEY_POSITION)

        values.put(KEY_POSITION, person.personPosition)
        db.query(TABLE_PERSONS, columns, null, null, null, null, KEY_POSITION).close()
        db.update(TABLE_PERSONS, values, "$KEY_ID=?", arrayOf(person.personId.toString()))
    }

    fun deletePerson(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PERSONS, "$KEY_ID =?", arrayOf(id.toString()))
    }

    private fun convertImageToByteArray(image: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        return stream.toByteArray()
    }

    private fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "person"
        val TABLE_PERSONS = "persons"

        val KEY_ID = "_id"
        val KEY_PERSON_NAME = "personname"
        val KEY_PERSON_DESCRIPTION = "persondescription"
        val KEY_PERSON_PHOTO = "personphoto"
        val KEY_POSITION = "position"

        private var instance: SqliteDatabase? = null
        fun getInstance(context: Context): SqliteDatabase {
            if (instance == null) {
                instance = SqliteDatabase(context)
            }
            return instance!!
        }

    }

}
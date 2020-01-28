package com.vadym.gvd.bestfriendskotlin.condition.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vadym.gvd.bestfriendskotlin.condition.Condition

class ConditionSqlDB(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONDITION_TABLE = ("CREATE TABLE $TABLE_CONDITIONS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_LIDER TEXT,"
                + "$KEY_DURATION TEXT,"
                + "$KEY_TODAY TEXT,"
                + "$KEY_CONDITION TEXT,"
                + "$KEY_PUB_GOAL TEXT,"
                + "$KEY_PER_GOAL TEXT,"
                + "$KEY_POSITION INTEGER)")
        db?.execSQL(CREATE_CONDITION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            if (!isFieldExist(TABLE_CONDITIONS, KEY_POSITION))
                db.execSQL("ALTER TABLE $TABLE_CONDITIONS ADD COLUMN $KEY_POSITION INTEGER;")
        } else {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_CONDITIONS")
            onCreate(db)
        }
    }

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

    fun listConditions(): List<Condition> {
        val sql = "select * from $TABLE_CONDITIONS"
        val db = this.readableDatabase
        val storeConditions = ArrayList<Condition>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val lider = cursor.getString(1)
                val duration = cursor.getString(2)
                val today = cursor.getString(3)
                val condition = cursor.getString(4)
                val pubGoal = cursor.getString(5)
                val perGoal = cursor.getString(6)
                val position = Integer.parseInt(cursor.getString(7))
                storeConditions.add(Condition(
                        conditionId = id,
                        lider = lider,
                        duration = duration,
                        today = today,
                        condition = condition,
                        pubGoal = pubGoal,
                        perGoal = perGoal,
                        conditionPosition = position)
                )

            } while (cursor.moveToNext())
        }
        cursor.close()
        return storeConditions.sortedBy { it.conditionPosition }
    }

    fun addCondition(condition: Condition) {
        val values = ContentValues()
        values.put(KEY_LIDER, condition.lider)
        values.put(KEY_DURATION, condition.duration)
        values.put(KEY_TODAY, condition.today)
        values.put(KEY_CONDITION, condition.condition)
        values.put(KEY_PUB_GOAL, condition.pubGoal)
        values.put(KEY_PER_GOAL, condition.perGoal)
        values.put(KEY_POSITION, condition.conditionPosition)
        val db = this.writableDatabase

        db.insert(TABLE_CONDITIONS, null, values)
        db.close()
    }

    fun updateCondition(condition: Condition) {
        val values = ContentValues()
        values.put(KEY_LIDER, condition.lider)
        values.put(KEY_DURATION, condition.duration)
        values.put(KEY_CONDITION, condition.condition)
        values.put(KEY_PUB_GOAL, condition.pubGoal)
        values.put(KEY_PER_GOAL, condition.perGoal)
        val db = this.readableDatabase
        db.update(TABLE_CONDITIONS, values, "$KEY_ID=?", arrayOf(condition.conditionId.toString()))
    }

    fun updateSortPosition(condition: Condition) {
        val db = this.readableDatabase
        val values = ContentValues()
        val columns = arrayOf(KEY_ID, KEY_LIDER, KEY_DURATION, KEY_POSITION)

        values.put(KEY_POSITION, condition.conditionPosition)
        db.query(TABLE_CONDITIONS, columns, null, null, null, null, KEY_POSITION).close()
        db.update(TABLE_CONDITIONS, values, "$KEY_ID=?", arrayOf(condition.conditionId.toString()))
    }

    fun deleteCondition(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CONDITIONS, "$KEY_ID =?", arrayOf(id.toString()))
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "condition"
        val TABLE_CONDITIONS = "conditions"

        val KEY_ID = "_id"
        val KEY_LIDER = "lider"
        val KEY_DURATION = "duration"
        val KEY_TODAY = "from_day"
        val KEY_LEFTOVER = "leftover"
        val KEY_CONDITION = "condition"
        val KEY_PUB_GOAL = "pub_goal"
        val KEY_PER_GOAL = "per_goal"
        val KEY_POSITION = "position"

        private var instance: ConditionSqlDB? = null

        fun getInstance(context: Context): ConditionSqlDB {
            if (instance == null) {
                instance = ConditionSqlDB(context)
            }
            return instance!!
        }
    }

}
package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vadym.gvd.bestfriendskotlin.treelife.TreeRow

// ─── Data class ──────────────────────────────────────────────────────────────

data class TreeRow(
    val id: Int = 1,
    val stage: Int = 1,           // 1–7
    val level: Int = 1,           // 1=індивідуальний, 2=батьки, 3=дідусь
    val coins: Int = 0,
    val dedicationConfirmed: Boolean = false,
    val lastRegressionCheck: String = "" // "yyyy-MM"
)

// ─── DB ──────────────────────────────────────────────────────────────────────

class TreeOfLifeDB private constructor(ctx: Context)
    : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE (
                $COL_ID                 INTEGER PRIMARY KEY,
                $COL_STAGE              INTEGER NOT NULL DEFAULT 1,
                $COL_LEVEL              INTEGER NOT NULL DEFAULT 1,
                $COL_COINS              INTEGER NOT NULL DEFAULT 0,
                $COL_DEDICATION         INTEGER NOT NULL DEFAULT 0,
                $COL_LAST_REG_CHECK     TEXT    NOT NULL DEFAULT ''
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    // ─── Read ─────────────────────────────────────────────────────────────

    fun getTree(): TreeRow? {
        val cursor = readableDatabase.query(TABLE, null, "$COL_ID=1", null, null, null, null)
        return cursor.use {
            if (!it.moveToFirst()) return null
            TreeRow(
                id                  = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                stage               = it.getInt(it.getColumnIndexOrThrow(COL_STAGE)),
                level               = it.getInt(it.getColumnIndexOrThrow(COL_LEVEL)),
                coins               = it.getInt(it.getColumnIndexOrThrow(COL_COINS)),
                dedicationConfirmed = it.getInt(it.getColumnIndexOrThrow(COL_DEDICATION)) == 1,
                lastRegressionCheck = it.getString(it.getColumnIndexOrThrow(COL_LAST_REG_CHECK)) ?: ""
            )
        }
    }

    // ─── Write ────────────────────────────────────────────────────────────

    fun insertDefaultTree() {
        writableDatabase.insertWithOnConflict(
            TABLE, null,
            ContentValues().apply {
                put(COL_ID, 1)
                put(COL_STAGE, 1)
                put(COL_LEVEL, 1)
                put(COL_COINS, 0)
                put(COL_DEDICATION, 0)
                put(COL_LAST_REG_CHECK, "")
            },
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun updateStage(newStage: Int) {
        writableDatabase.update(TABLE,
            ContentValues().apply { put(COL_STAGE, newStage.coerceIn(1, 7)) },
            "$COL_ID=1", null)
    }

    fun updateCoins(newCoins: Int) {
        writableDatabase.update(TABLE,
            ContentValues().apply { put(COL_COINS, newCoins) },
            "$COL_ID=1", null)
    }

    /** Викликається адміном або після QR-скану посвячення */
    fun confirmDedication() {
        writableDatabase.update(TABLE,
            ContentValues().apply { put(COL_DEDICATION, 1) },
            "$COL_ID=1", null)
    }

    fun updateLastRegressionCheck(month: String) {
        writableDatabase.update(TABLE,
            ContentValues().apply { put(COL_LAST_REG_CHECK, month) },
            "$COL_ID=1", null)
    }

    // ─── Singleton ────────────────────────────────────────────────────────

    companion object {
        private const val DB_NAME    = "tree_of_life.db"
        private const val DB_VERSION = 1
        private const val TABLE      = "tree"
        private const val COL_ID               = "id"
        private const val COL_STAGE            = "stage"
        private const val COL_LEVEL            = "level"
        private const val COL_COINS            = "coins"
        private const val COL_DEDICATION       = "dedication_confirmed"
        private const val COL_LAST_REG_CHECK   = "last_regression_check"

        @Volatile private var instance: TreeOfLifeDB? = null
        fun getInstance(ctx: Context) =
            instance ?: synchronized(this) {
                instance ?: TreeOfLifeDB(ctx.applicationContext).also { instance = it }
            }
    }
}
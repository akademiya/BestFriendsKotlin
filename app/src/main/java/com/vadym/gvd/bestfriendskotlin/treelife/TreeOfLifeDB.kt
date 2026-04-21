package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate

// ─── Data class ──────────────────────────────────────────────────────────────

data class TreeRow(
    val id: Int = 1,
    val level: Int = 1,                  // розблокований рівень: 1/2/3
    val stageIndividual: Int = 1,
    val stageFamily: Int = 1,
    val stageGenerational: Int = 1,
    val coins: Int = 0,
    val dedicationConfirmed: Boolean = false,
    val lastRegressionCheck: String = "",
    val stageIndividualDate: String = "",   // дата відкриття стадії "yyyy-MM-dd"
    val stageFamilyDate: String = "",
    val stageGenerationalDate: String = ""
) {
    fun stageForLevel(treeLevel: TreeLevel) = when (treeLevel) {
        TreeLevel.INDIVIDUAL   -> stageIndividual
        TreeLevel.FAMILY       -> stageFamily
        TreeLevel.GENERATIONAL -> stageGenerational
    }

    fun stageDateForLevel(treeLevel: TreeLevel): LocalDate? {
        val raw = when (treeLevel) {
            TreeLevel.INDIVIDUAL   -> stageIndividualDate
            TreeLevel.FAMILY       -> stageFamilyDate
            TreeLevel.GENERATIONAL -> stageGenerationalDate
        }
        return if (raw.isBlank()) null
        else runCatching { LocalDate.parse(raw) }.getOrNull()
    }
}

// ─── DB ──────────────────────────────────────────────────────────────────────

class TreeOfLifeDB private constructor(ctx: Context)
    : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $T (
                $ID                 INTEGER PRIMARY KEY,
                $LEVEL              INTEGER NOT NULL DEFAULT 1,
                $STAGE_IND          INTEGER NOT NULL DEFAULT 1,
                $STAGE_FAM          INTEGER NOT NULL DEFAULT 1,
                $STAGE_GEN          INTEGER NOT NULL DEFAULT 1,
                $COINS              INTEGER NOT NULL DEFAULT 0,
                $DEDICATION         INTEGER NOT NULL DEFAULT 0,
                $LAST_REG           TEXT    NOT NULL DEFAULT '',
                $STAGE_IND_DATE TEXT NOT NULL DEFAULT '',
                $STAGE_FAM_DATE TEXT NOT NULL DEFAULT '',
                $STAGE_GEN_DATE TEXT NOT NULL DEFAULT ''
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        if (old < 3) {
            // Додаємо нові колонки без втрати даних
            db.execSQL("ALTER TABLE $T ADD COLUMN $STAGE_IND_DATE TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE $T ADD COLUMN $STAGE_FAM_DATE TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE $T ADD COLUMN $STAGE_GEN_DATE TEXT NOT NULL DEFAULT ''")
        }
    }

    // ─── Read ─────────────────────────────────────────────────────────────

    fun getTree(): TreeRow? {
        val c = readableDatabase.query(T, null, "$ID=1", null, null, null, null)
        return c.use {
            if (!it.moveToFirst()) return null
            TreeRow(
                id                  = it.getInt(it.getColumnIndexOrThrow(ID)),
                level               = it.getInt(it.getColumnIndexOrThrow(LEVEL)),
                stageIndividual     = it.getInt(it.getColumnIndexOrThrow(STAGE_IND)),
                stageFamily         = it.getInt(it.getColumnIndexOrThrow(STAGE_FAM)),
                stageGenerational   = it.getInt(it.getColumnIndexOrThrow(STAGE_GEN)),
                coins               = it.getInt(it.getColumnIndexOrThrow(COINS)),
                dedicationConfirmed = it.getInt(it.getColumnIndexOrThrow(DEDICATION)) == 1,
                lastRegressionCheck = it.getString(it.getColumnIndexOrThrow(LAST_REG)) ?: "",
                stageIndividualDate = it.getString(it.getColumnIndexOrThrow(STAGE_IND_DATE)) ?: "",
 stageFamilyDate     = it.getString(it.getColumnIndexOrThrow(STAGE_FAM_DATE)) ?: "",
 stageGenerationalDate = it.getString(it.getColumnIndexOrThrow(STAGE_GEN_DATE)) ?: ""
            )
        }
    }

    // ─── Write ────────────────────────────────────────────────────────────

    fun insertDefaultTree() {
        writableDatabase.insertWithOnConflict(
            T, null,
            ContentValues().apply {
                put(ID, 1); put(LEVEL, 1)
                put(STAGE_IND, 1); put(STAGE_FAM, 1); put(STAGE_GEN, 1)
                put(COINS, 0); put(DEDICATION, 0); put(LAST_REG, "")
            },
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun updateStageForLevel(treeLevel: TreeLevel, newStage: Int) {
        val stageCol = when (treeLevel) {
            TreeLevel.INDIVIDUAL   -> STAGE_IND
            TreeLevel.FAMILY       -> STAGE_FAM
            TreeLevel.GENERATIONAL -> STAGE_GEN
        }
        val dateCol = when (treeLevel) {
            TreeLevel.INDIVIDUAL   -> STAGE_IND_DATE
            TreeLevel.FAMILY       -> STAGE_FAM_DATE
            TreeLevel.GENERATIONAL -> STAGE_GEN_DATE
        }
        writableDatabase.update(T,
            ContentValues().apply {
                put(stageCol, newStage.coerceIn(1, 7))
                put(dateCol, LocalDate.now().toString())   // "yyyy-MM-dd"
            },
            "$ID=1", null)
    }

    // ─── Singleton ────────────────────────────────────────────────────────

    companion object {
        private const val DB_NAME    = "tree_of_life.db"
        private const val DB_VERSION = 3
        private const val T          = "tree"
        private const val ID         = "id"
        private const val LEVEL      = "level"
        private const val STAGE_IND  = "stage_individual"
        private const val STAGE_FAM  = "stage_family"
        private const val STAGE_GEN  = "stage_generational"
        private const val COINS      = "coins"
        private const val DEDICATION = "dedication_confirmed"
        private const val LAST_REG = "last_regression_check"
        private const val STAGE_IND_DATE = "stage_individual_date"
        private const val STAGE_FAM_DATE = "stage_family_date"
        private const val STAGE_GEN_DATE = "stage_generational_date"

        @Volatile private var instance: TreeOfLifeDB? = null
        fun getInstance(ctx: Context) =
            instance ?: synchronized(this) {
                instance ?: TreeOfLifeDB(ctx.applicationContext).also { instance = it }
            }
    }
}
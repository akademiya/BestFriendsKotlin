package com.vadym.gvd.bestfriendskotlin.kido


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.CursorWindow
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.*
import com.vadym.gvd.bestfriendskotlin.kido.Chronometer.nextBeep
import com.vadym.gvd.bestfriendskotlin.kido.adapter.PersonAdapter
import com.vadym.gvd.bestfriendskotlin.kido.database.SqliteDatabase
import kotlinx.android.synthetic.main.view_kido.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.reflect.Field
import java.nio.ByteBuffer
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


class PersonView : MainActivity() {
    private lateinit var allPerson: List<Person>
    private lateinit var listPersonEmpty: RelativeLayout
    private lateinit var database: SqliteDatabase
    private lateinit var adapter: PersonAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private var addingPersonPhoto: ImageView? = null
    private var imgUri: Uri? = null
    private var imgBitmap: Bitmap? = null
    private var imgByte: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        toolbarButtonMenu()
        initializ()
        showOrHideFab()
        chronometer()
        tracker().setScreenName("Kido for Person")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
    }

    private fun toolbarButtonMenu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initializ() {
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        fab.setOnClickListener { addTaskDialog() }
        listPersonEmpty = findViewById(R.id.list_kido_empty)

        itemTouchHelper = ItemTouchHelper(touchHelperCallback()).apply {
            attachToRecyclerView(rv_list_kido)
        }

        rv_list_kido.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_list_kido.setHasFixedSize(true)

        database = SqliteDatabase.getInstance(this)
        allPerson = database.listPerson()

        if (allPerson.isNotEmpty()) {
            rv_list_kido.visibility = View.VISIBLE
            adapter = PersonAdapter(
                    personList = allPerson.sortedBy { it.personPosition },
                    context = this,
                    database = database,
                    onMoveItemTouch = { viewHolder -> onStartDrag(viewHolder) })
            rv_list_kido.adapter = adapter
        } else {
            rv_list_kido.visibility = View.GONE
            listPersonEmpty.visibility = View.VISIBLE
        }
    }

    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.item_edit_list_person, null)

        val nameField = subView.findViewById<EditText>(R.id.create_person_name)
        val descriptionFiled = subView.findViewById<EditText>(R.id.create_person_description)
        addingPersonPhoto = subView.findViewById<ImageView>(R.id.choose_person_img)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.add_new_person)
        builder.setView(subView)
        builder.create()

        addingPersonPhoto?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                }
            }
        }


        builder.setPositiveButton(R.string.add_person) { _, _ ->
            val name = nameField.text.toString()
            val description = descriptionFiled.text.toString()

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_SHORT).show()
            } else {
                imgByte = imgByte ?: ContextCompat.getDrawable(baseContext, R.drawable.ic_person)!!.toBitmap().toByteAr()

                val newPerson = Person(name, description, imgByte, allPerson.lastIndex + 1)
                database.addPerson(newPerson)

                restartActivity(this)
            }
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            val contentURI = data.data
            imgUri = contentURI

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                val bytes = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
                addingPersonPhoto?.setImageBitmap(bitmap)
                imgBitmap = bitmap

                val iStream = contentURI?.let { contentResolver.openInputStream(it) }
                imgByte = iStream?.readBytes()

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@PersonView, "Photo can`t saved. Please, sent screenshot to author about problem with upload person photo", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun chronometer() {
        val mp = MediaPlayer.create(this, R.raw.tongil)
        val userTime = findViewById<EditText>(R.id.user_time)
        var millisecondsUserTime = 0L

        chronometer.text = DateUtils.formatElapsedTime(0)

        start.setOnClickListener {
            millisecondsUserTime = if (userTime.text.toString().isEmpty()) 0 else userTime.text.toString().toLong()
            millisecondsUserTime = TimeUnit.MINUTES.toMillis(millisecondsUserTime)
            userTime.visibility = View.GONE
            chronometer.visibility = View.VISIBLE
            Chronometer.base = SystemClock.elapsedRealtime()
            Chronometer.start()
            userTime.isFocused.let {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(userTime.windowToken , 0)
            }
        }

        Chronometer.setOnTick {
            val now = SystemClock.elapsedRealtime()
            val elapsedMillis = now - Chronometer.base

            chronometer.text = DateUtils.formatElapsedTime(elapsedMillis / 1000)
            if (elapsedMillis < nextBeep)
                return@setOnTick

            when {
                elapsedMillis >= millisecondsUserTime -> { mp.start() }
//                elapsedMillis >= 2400000 -> {
//                    Toast.makeText(this, resources.getString(R.string.min_40), Toast.LENGTH_SHORT).show()
//                    mp.start()
//                }
//                elapsedMillis >= 1260000 -> {
//                    Toast.makeText(this, resources.getString(R.string.min_21), Toast.LENGTH_SHORT).show()
//                    mp.start()
//                    nextBeep = 2400000
//                }
//                elapsedMillis >= 720000 -> {
//                    Toast.makeText(this, resources.getString(R.string.min_12), Toast.LENGTH_SHORT).show()
//                    mp.start()
//                    nextBeep = 1260000
//                }
//                elapsedMillis >= 420000 -> {
//                    Toast.makeText(this, resources.getString(R.string.min_7), Toast.LENGTH_SHORT).show()
//                    mp.start()
//                    nextBeep = 720000
//                }
//                elapsedMillis >= 180000 -> {
//                    Toast.makeText(this, resources.getString(R.string.min_3), Toast.LENGTH_SHORT).show()
//                    mp.start()
//                    nextBeep = 420000
//                }
            }
        }

        reset.setOnClickListener {
            Chronometer.stop()
            mp.stop()
            mp.reset()
            userTime.text.clear()
            userTime.visibility = View.VISIBLE
            chronometer.visibility = View.GONE
            chronometer.text = DateUtils.formatElapsedTime(0)
            restartActivity(this)
        }
    }

    private fun showOrHideFab() {
        rv_list_kido.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    private fun onStartDrag(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun touchHelperCallback() = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): Int {
            val dragFlags: Int = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
            val swipeFlags: Int = ItemTouchHelper.ACTION_STATE_DRAG
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            drop(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {}
    }

    fun drop(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(allPerson, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(allPerson, i, i - 1)
            }
        }

        allPerson.forEachIndexed { index, current ->
            current.personPosition = index
            database.updateSortPosition(current)
        }
    }
}
package com.vadym.gvd.bestfriendskotlin.kido

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kido.Chronometer.nextBeep
import com.vadym.gvd.bestfriendskotlin.kido.adapter.PersonAdapter
import com.vadym.gvd.bestfriendskotlin.kido.adapter.PersonAdapterListener
import com.vadym.gvd.bestfriendskotlin.kido.database.SqliteDatabase
import com.vadym.gvd.bestfriendskotlin.restartActivity
import java.io.File
import java.util.Collections


class PersonView : MainActivity(), PersonAdapterListener {
    private val PERMISSION_REQUEST_CODE = 101
    private val PICK_IMAGE_REQUEST_CODE = 102
    private lateinit var allPerson: List<Person>
    private lateinit var listPersonEmpty: RelativeLayout
    private lateinit var database: SqliteDatabase
    private lateinit var adapter: PersonAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var fab: FloatingActionButton
    private lateinit var start: Button
    private lateinit var stop: Button
    private lateinit var listKido: RecyclerView
    private lateinit var chronometer: TextView

    private lateinit var uploadPhoto: ImageView
    private var isImgSelected = false
    private var isImgEdit = false
    private lateinit var selectedPerson: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        fab = findViewById(R.id.fab)
        start = findViewById(R.id.start)
        stop = findViewById(R.id.stop)
        listKido = findViewById(R.id.rv_list_kido)
        chronometer = findViewById(R.id.chronometer)
        val adContainer: AdView = findViewById(R.id.adView)
        val adDivider: View = findViewById(R.id.adDivider)

        toolbarButtonMenu()
        initializ()
        showOrHideFab()
        chronometer()

        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }
    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun initializ() {
        fab.setOnClickListener { addTaskDialog() }
        listPersonEmpty = findViewById(R.id.list_kido_empty)

        itemTouchHelper = ItemTouchHelper(touchHelperCallback()).apply {
            attachToRecyclerView(listKido)
        }

        listKido.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listKido.setHasFixedSize(true)

        database = SqliteDatabase.getInstance(this)
        allPerson = try {
            database.listPerson()
        } catch (e: Exception) {
            e.printStackTrace()
            clearStorageAppDialog()
            emptyList<Person>()
        }

        if (allPerson.isNotEmpty()) {
            listKido.visibility = View.VISIBLE
            adapter = PersonAdapter(
                    personList = allPerson.sortedBy { it.personPosition },
                    context = this,
                    database = database,
                    onMoveItemTouch = { viewHolder -> onStartDrag(viewHolder) },
                listener = this)
            listKido.adapter = adapter
        } else {
            listKido.visibility = View.GONE
            listPersonEmpty.visibility = View.VISIBLE
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.item_edit_list_person, null)

        val nameField = subView.findViewById<EditText>(R.id.create_person_name)
        val descriptionFiled = subView.findViewById<EditText>(R.id.create_person_description)
        val buttonSelectPhoto = subView.findViewById<Button>(R.id.btn_select_photo)
        uploadPhoto = subView.findViewById(R.id.upload_img_person)

        buttonSelectPhoto.setOnClickListener {
            if (checkPermission()) {
                openGallery()
            } else {
                requestPermission()
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.add_new_person)
        builder.setView(subView)
        builder.create()

        builder.setPositiveButton(R.string.add_person) { _, _ ->
            val name = nameField.text.toString()
            val description = descriptionFiled.text.toString()
            @Suppress("DEPRECATION")
            val personPhoto = if (::uploadPhoto.isInitialized && isImgSelected) {
                imageViewToBitmap(uploadPhoto)
            } else {
                drawableToBitmap(resources.getDrawable(R.drawable.empty_person))
            }

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_SHORT).show()
            } else {
                val newPerson = Person(name, description, personPhoto, allPerson.lastIndex + 1)
                database.addPerson(newPerson)

                restartActivity(this)
            }
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE
        )
    }

    override fun onSelectPhoto(person: Person) {
        selectedPerson = person
        isImgEdit = true
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        @Suppress("DEPRECATION")
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onPhotoUpdated(person: Person, newBitmap: Bitmap) {
        adapter.updatePersonPhoto(person, newBitmap)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        @Suppress("DEPRECATION")
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                if (!isImgEdit) {
                    uploadPhoto.setImageURI(selectedImageUri)
                    isImgSelected = true
                } else {
                    @Suppress("DEPRECATION")
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    onPhotoUpdated(selectedPerson, bitmap)
                    isImgEdit = false
                }
            }
        }
    }

    private fun imageViewToBitmap(imageView: ImageView): Bitmap {
        return (imageView.drawable as BitmapDrawable).bitmap
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 100 // Default width
        val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 100 // Default height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun chronometer() {
        val mp = MediaPlayer.create(this, R.raw.ton)
        chronometer.text = DateUtils.formatElapsedTime(0)

        start.setOnClickListener {
            Chronometer.base = SystemClock.elapsedRealtime()
            Chronometer.start()
        }

        Chronometer.setOnTick {
            val now = SystemClock.elapsedRealtime()
            val elapsedMillis = now - Chronometer.base

            chronometer.text = DateUtils.formatElapsedTime(elapsedMillis / 1000)
            if (elapsedMillis < nextBeep)
                return@setOnTick

            when {
                elapsedMillis >= 2400000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_40), Toast.LENGTH_SHORT).show()
                    mp.start()
                }
                elapsedMillis >= 1260000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_21), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 2400000
                }
                elapsedMillis >= 720000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_12), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 1260000
                }
                elapsedMillis >= 420000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_7), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 720000
                }
                elapsedMillis >= 180000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_3), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 420000
                }
            }
        }

        stop.setOnClickListener {
            Chronometer.stop()
            chronometer.text = DateUtils.formatElapsedTime(0)
            mp.stop()
        }
    }

    private fun showOrHideFab() {
        listKido.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    private fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    @Suppress("DEPRECATION")
    private fun touchHelperCallback() = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags: Int = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
            val swipeFlags: Int = ItemTouchHelper.ACTION_STATE_DRAG
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            drop(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
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


    private fun clearStorageAppDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.view_message, null)

        val nameField = subView.findViewById<TextView>(R.id.dialog_message)
        nameField.setText(R.string.technical_message)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.clear_storage_app)
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton(R.string.clear_storage_app_button) { _, _ ->
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${packageName}")
            }
            startActivity(intent)
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }
}
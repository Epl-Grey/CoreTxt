package com.example.coretxt

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val GALLERY = 1
    private val CAMERA = 2

    lateinit var photoBtn: ImageButton
    lateinit var galleryBtn: ImageButton


    lateinit var messageList: ListView
    lateinit var sendBtn: Button
    lateinit var editText: EditText

    lateinit var MsgList: ArrayList<MsgView>
    lateinit var msgAdapter: msgSimpleAdapter

    lateinit var answerList: ArrayList<answerView>
    lateinit var answerAdapter: answerSimpleAdapter

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoBtn = findViewById(R.id.photoBtn)
        galleryBtn = findViewById(R.id.galleryBtn)


        messageList = findViewById(R.id.messgeList)
        sendBtn = findViewById(R.id.sendBtn)
        editText = findViewById(R.id.edt)

        MsgList = java.util.ArrayList<MsgView>()
        msgAdapter = msgSimpleAdapter(this, MsgList)

        answerList = java.util.ArrayList<answerView>()
        answerAdapter = answerSimpleAdapter(this, answerList)

        imageView = findViewById(R.id.showImage)

        sendBtn.setOnClickListener {
            var textMSg: String = editText.text.toString()
            println(textMSg)
            addMessage(textMSg)
//            messageList.adapter = answerAdapter
        }

        photoBtn.setOnClickListener { takePhotoFromCamera() }

        galleryBtn.setOnClickListener {
            choosePhotoFromGallary()
        }

    }

    fun addMessage(text: String){
        MsgList.add(MsgView(text))
        answerList.add(answerView(text))
        msgAdapter = msgSimpleAdapter(this, MsgList)
        answerAdapter = answerSimpleAdapter(this, answerList)

        messageList.adapter = msgAdapter
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    imageView.setImageBitmap(bitmap)
                    recognizeBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val imageView: ImageView = findViewById(R.id.showImage)
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
            recognizeBitmap(bitmap)
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    fun recognizeBitmap(bmp: Bitmap){
        val text = RestApi.sendImage(bmp)
        addMessage(text)
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
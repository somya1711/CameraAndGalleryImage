package com.example.cameraandgalleryimage

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var picture: ImageView? = null
    private var upload: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picture = findViewById(R.id.img_profile_pic)

        upload = findViewById(R.id.upload_btn)
        upload?.setOnClickListener(this)


    }

    private fun selectImage(context: Context) {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")

        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item].equals("Take Photo")) {
                val takePicture =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item].equals("Choose from Gallery")) {
                val pickPhoto = Intent()
                pickPhoto.setType("image/*")
                pickPhoto.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(pickPhoto, 1)
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss()
            }
        })
        builder.show()

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    picture?.setImageBitmap(selectedImage)
                }
                1 -> if (resultCode == Activity.RESULT_OK && data != null) {

                    val imageData: Uri? = data.data;
                    picture?.setImageURI(imageData)

                }
            }
        }
    }

    override fun onClick(v: View?) {
        selectImage(this)
    }
}

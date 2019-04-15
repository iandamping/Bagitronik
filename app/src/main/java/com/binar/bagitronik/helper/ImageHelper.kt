package com.binar.bagitronik.helper

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import com.binar.bagitronik.helper.Constant.maxWidth
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class ImageHelper {
    companion object {
        fun bitmapToFile(ctx: Context, bitmap: Bitmap?): File {
            val f = File(ctx.cacheDir, "image_uploads")
            f.createNewFile()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val fos = FileOutputStream(f)
            fos.write(byteArray)
            fos.flush()
            fos.close()
            return f
        }

        fun getBitmapFromGallery(ctx: Context?, path: Uri?): Bitmap? {
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = ctx?.applicationContext?.contentResolver?.query(path, filePathColum, null, null, null)
            cursor?.moveToFirst()

            val columnIndex: Int? = cursor?.getColumnIndex(filePathColum.get(0))
            val picturePath = columnIndex?.let { cursor.getString(it) }
            cursor?.close()

            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(picturePath, options)
            options.inSampleSize = calculateSampleSize(options, maxWidth, maxWidth)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(picturePath, options)
        }

        fun decodeSampledBitmapFromFile(imageFile: File, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, options)
            options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false

            var scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90F)
            } else if (orientation == 3) {
                matrix.postRotate(180F)
            } else if (orientation == 8) {
                matrix.postRotate(270F)
            }
            scaledBitmap =
                    Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
            return scaledBitmap
        }

        private fun calculateSampleSize(options: BitmapFactory.Options, requiredWidth: Int, requiredHeight: Int): Int {
            val height = options.outHeight
            val widht = options.outWidth
            var inSampleSize = 1

            if (height > requiredHeight || widht > requiredHeight) {
                val halfHeight = height / 2
                val halfWidth = widht / 2

                while ((halfHeight / inSampleSize) >= requiredHeight && (halfWidth / inSampleSize) >= requiredWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

    }
}
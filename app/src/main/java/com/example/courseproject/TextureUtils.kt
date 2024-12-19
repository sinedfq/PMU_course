package com.example.courseproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.opengl.GLES20.*
import android.opengl.GLUtils
import java.io.InputStream


class TextureUtils {
    companion object {
        fun loadTexture(texture: InputStream): Int {
            val textureHandle = IntArray(1)
            glGenTextures(1, textureHandle, 0)

            val options = BitmapFactory.Options()
            options.inScaled = false

            var bitmap = BitmapFactory.decodeStream(texture, Rect(0, 0, 0, 0), options)

            val m = Matrix()
            m.preScale(1f, -1f)
            if (bitmap != null) {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, false)
            }

            glBindTexture(GL_TEXTURE_2D, textureHandle[0])

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)

            bitmap?.recycle()

            return textureHandle[0]
        }
    }
}
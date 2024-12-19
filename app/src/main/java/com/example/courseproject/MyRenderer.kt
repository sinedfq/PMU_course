package com.example.courseproject

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyRenderer(ctx: Context) : GLSurfaceView.Renderer {
    private val objects: MutableList<IGLObject> = mutableListOf()
    private val bgColorR: Float = 0.0f
    private val bgColorG: Float = 0.0f
    private val bgColorB: Float = 0.0f
    private val bgColorA: Float = 1.0f

    init {
        val baseShader = Utils.inputStreamToString(ctx.assets.open("shaders/base_shader.vert"))
        val colorShader = Utils.inputStreamToString(ctx.assets.open("shaders/color_shader.frag"))
        val textureShader = Utils.inputStreamToString(ctx.assets.open("shaders/texture_shader.frag"))
        val flameShader = Utils.inputStreamToString(ctx.assets.open("shaders/flame_shader.frag"))

        val table = GLObject.fromInputStream(
            baseShader,
            textureShader,
        ctx.assets.open("models/table.obj"),
        ctx.assets.open("models/table.jpg")
        )
        table.setPosition(0f, -1.94f, -9.5f)
        table.setScale(2f, 2f, 2f)
        table.rotateY = 90f

        val candle = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/candle.obj"),
            ctx.assets.open("models/candle.jpg")
        )
        candle.setPosition(0.5f, -0.45f, -9f)
        candle.setScale(1f, 1f, 1f)

        val glass = GLObject.fromInputStream(
            baseShader,
            colorShader,
            ctx.assets.open("models/glass.obj")
        )
        glass.setPosition(1.2f, -0.49f, -9f)
        glass.setScale(3f, 3f, 3f)

        val apple = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/apple.obj"),
            ctx.assets.open("models/apple.png")
        )
        apple.setPosition(0.9f, -0.48f, -9f)
        apple.setScale(0.2f, 0.2f, 0.2f)

        val Watermelon = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/Watermelon_SF.obj"),
            ctx.assets.open("models/Watermelon.jpg")
        )
        Watermelon.setPosition(0.1f, -0.550f, -10f)
        Watermelon.setScale(0.25f, 0.2f, 0.25f)

        val banana = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/banana.obj"),
            ctx.assets.open("models/banana.jpg")
        )
        banana.setPosition(-0.4f, -0.49f, -10f)
        banana.setScale(2.5f, 2.5f, 2.5f)
        banana.rotateY = 270f

        val orange = GLObject.fromInputStream(
            baseShader,
            textureShader,
            ctx.assets.open("models/orange.obj"),
            ctx.assets.open("models/orange.jpg")
        )
        orange.setPosition(-0.7f, -0.32f, -8.5f)
        orange.setScale(0.75f, 0.75f, 0.75f)
        orange.rotateX = 270f

        val flame = GLObject.fromInputStream(
            baseShader,
            flameShader,
            ctx.assets.open("models/flame.obj"),
            ctx.assets.open("models/flame.jpg")
        )
        flame.setPosition(0.5f, -0.92f, -9f)
        flame.setScale(0.13f, 0.2f, 0.25f)

        objects.add(glass)
        objects.add(apple)
        objects.add(Watermelon)
        objects.add(banana)
        objects.add(orange)
        objects.add(flame)
        objects.add(candle)
        objects.add(table)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        for (obj in objects) {
            obj.onSurfaceCreated(gl, config)
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        for (obj in objects) {
            obj.onSurfaceChanged(gl, width, height)
        }
    }

    override fun onDrawFrame(gl: GL10) {
        glClearColor(bgColorR, bgColorG, bgColorB, bgColorA)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        val candle = (objects[objects.size - 2] as GLObject)

        for (obj in objects) {
            obj.setLightDirection(candle.x + 0.3f - obj.x, candle.y + 1f - obj.y, candle.z - obj.z)
            obj.onDrawFrame(gl)
        }
    }
}
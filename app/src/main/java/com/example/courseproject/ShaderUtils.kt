package com.example.courseproject

import android.opengl.GLES20
import android.util.Log

class ShaderUtils {
    companion object {
        private fun loadShader(shaderType: Int, shaderSource: String): Int {
            val shader = GLES20.glCreateShader(shaderType)
            if (shader == 0) {
                return shader
            }

            GLES20.glShaderSource(shader, shaderSource)
            GLES20.glCompileShader(shader)

            val compiled = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
            if (compiled[0] != 0) {
                return shader
            }

            return 0
        }

        fun createProgram(vertexSource: String, fragmentSource: String): Int {
            val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)
            val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource)

            var program = GLES20.glCreateProgram()
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)

            GLES20.glLinkProgram(program)
            val linkStatus = intArrayOf(GLES20.GL_FALSE)
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] == GLES20.GL_TRUE) {
                return program
            }

            return 0
        }
    }
}
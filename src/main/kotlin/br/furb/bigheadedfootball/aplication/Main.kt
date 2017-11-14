package br.furb.bigheadedfootball.aplication

import java.awt.BorderLayout
import java.awt.Cursor
import javax.media.opengl.GLCanvas
import javax.media.opengl.GLCapabilities
import javax.swing.JFrame
import javax.swing.WindowConstants


class Main : JFrame() {

    var world: World = World()

    init {

        title = "Polygon Paint"
        setBounds(300, 250, 400, 400 + 22)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()

        val glCaps = GLCapabilities()
        glCaps.redBits = 8
        glCaps.blueBits = 8
        glCaps.greenBits = 8
        glCaps.alphaBits = 8
        cursor = Cursor(Cursor.HAND_CURSOR)
        val canvas = GLCanvas(glCaps)
        add(canvas, BorderLayout.CENTER)
        canvas.addGLEventListener(world)

        canvas.requestFocus()
    }
}

/**
 * Inicialização do kotlin
 */
fun main(args: Array<String>) {
    Main().isVisible = true
}
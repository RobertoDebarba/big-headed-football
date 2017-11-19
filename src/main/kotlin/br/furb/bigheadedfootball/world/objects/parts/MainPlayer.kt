package br.furb.bigheadedfootball.world.objects.parts

import br.furb.bigheadedfootball.common.glDrawable
import br.furb.bigheadedfootball.world.components.Color
import br.furb.bigheadedfootball.world.components.Point
import br.furb.bigheadedfootball.world.components.PointCommom
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.concurrent.thread

class MainPlayer : Player(), KeyListener {
    override var color: Color = Color.RED
    private val pressed = HashSet<Int>()

    init {
        transformation.translation(25.0, 0.0, 60.0)
        thread {
            while (true) {

                if (conditionRefresh()) {
                    refreshValues()
                    glDrawable {
                        display()
                    }
                }
                Thread.sleep(70)
            }
        }
    }

    private fun refreshValues() {
        val originPoint = transformation.transformPoint(PointCommom.neutralPoint())
        val destinedZPoint = transformation.transformPoint(Point(0.0, 0.0, -0.6))
        val positiveMoveZPoint = destinedZPoint.diff(originPoint)

        if (pressed.contains(UP))
            transformation.translation(positiveMoveZPoint)

        if (pressed.contains(DOWN))
            transformation.translation(positiveMoveZPoint.inverted())

        val radians = 0.2
        if (pressed.contains(LEFT))
            rotation(radians)

        if (pressed.contains(RIGHT))
            rotation(-radians)
    }

    private fun conditionRefresh(): Boolean {
        return pressed.contains(UP) ||
                pressed.contains(DOWN) ||
                pressed.contains(LEFT) ||
                pressed.contains(RIGHT)
    }


    override fun keyTyped(p0: KeyEvent) {}

    private val UP = 38
    private val DOWN = 40
    private val LEFT = 37
    private val RIGHT = 39
    override fun keyPressed(e: KeyEvent) {
        println(e.keyCode)
        pressed.add(e.keyCode)

    }

    override fun keyReleased(e: KeyEvent) {
        pressed.remove(e.keyCode)
    }

    private fun rotation(radians: Double) {
        transformWithCenterBBox {
            transformation.rotateY(radians)
        }
    }

    private fun transformWithCenterBBox(block: () -> Unit) {
        val center = PointCommom.neutralPoint()
        val centerPoint = transformation.transformPoint(center)
        val inverted = centerPoint.inverted()

        transformation.translation(inverted)
        block()
        transformation.translation(centerPoint)
    }

}
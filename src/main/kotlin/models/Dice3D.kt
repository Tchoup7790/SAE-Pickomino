package fr.baptiste.test3d.model

import com.interactivemesh.jfx.importer.col.ColModelImporter
import iut.info1.pickomino.data.DICE
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.AmbientLight
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.SceneAntialiasing
import javafx.scene.SubScene
import javafx.scene.effect.Bloom
import javafx.scene.paint.Color
import javafx.scene.transform.Rotate
import javafx.util.Duration
import kotlin.random.Random

class Dice3D : Group() {
    val view = Group()
    private val transformX = Rotate(270.0, Rotate.X_AXIS)
    private val transformY = Rotate(0.0, Rotate.Y_AXIS)
    var select = false
    var frozen = SimpleBooleanProperty(false)
    lateinit var actualSide : DICE

    init {
        this.setSide(DICE.worm)

        val importer = ColModelImporter()
        importer.read("assets/3D/Dés/dés3d.dae")

        val light = AmbientLight()
        light.color = Color.WHITE

        view.children.addAll(importer.import)
        view.children.add(light)
        view.transforms.addAll(transformX, transformY)

        val camera = PerspectiveCamera(true)
        camera.translateZ = -6.0

        val subScene = SubScene(view, 50.0, 50.0, true, SceneAntialiasing.BALANCED)
        subScene.camera = camera

        this.children.add(subScene)
    }

    private fun aleatoire() : Pair<Double, Double> {
        return Pair(Random.nextDouble(400.0, 900.0), Random.nextDouble(400.0, 900.0))
    }

    private fun animate(aleatoireX:Double, aleatoireY:Double, endX:Double, endY:Double) {
        var timelineX = Timeline(
            KeyFrame(Duration.ZERO, KeyValue(this.transformX.angleProperty(), 0)),
            KeyFrame(
                Duration.seconds(2.3),
                KeyValue(this.transformX.angleProperty(), aleatoireX)
            )
        )
        var timelineY = Timeline(
            KeyFrame(Duration.ZERO, KeyValue(this.transformY.angleProperty(), 0)),
            KeyFrame(
                Duration.seconds(2.3),
                KeyValue(this.transformY.angleProperty(), aleatoireY)
            )
        )

        timelineX.play()
        timelineY.play()

        timelineX = Timeline(
            KeyFrame(
                Duration.ZERO,
                KeyValue(this.transformX.angleProperty(), aleatoireX)
            ),
            KeyFrame(
                Duration.seconds(2.3),
                KeyValue(this.transformX.angleProperty(), endX)
            )
        )
        timelineY = Timeline(
            KeyFrame(
                Duration.ZERO,
                KeyValue(this.transformY.angleProperty(), aleatoireY)
            ),
            KeyFrame(
                Duration.seconds(2.3),
                KeyValue(this.transformY.angleProperty(), endY)
            )
        )

        timelineX.play()
        timelineY.play()
    }

    fun setSide(side : DICE){
        val (aleatoireX, aleatoireY) = aleatoire()
        if(side == DICE.d1) {
            this.animate(aleatoireX, aleatoireY, 0.0, 0.0)
            this.actualSide = DICE.d1
        }
        if (side == DICE.d2){
            this.animate(aleatoireX, aleatoireY, 270.0, 0.0)
            this.actualSide = DICE.d2
        }
        if (side == DICE.d3){
            this.animate(aleatoireX, aleatoireY, 0.0, 90.0)
            this.actualSide = DICE.d3
        }
        if (side == DICE.d4){
            this.animate(aleatoireX, aleatoireY, 0.0, 270.0)
            this.actualSide = DICE.d4
        }
        if (side == DICE.d5){
            this.animate(aleatoireX, aleatoireY, 90.0, 0.0)
            this.actualSide = DICE.d5
        }
        if (side == DICE.worm){
            this.animate(aleatoireX, aleatoireY, 0.0, 180.0)
            this.actualSide = DICE.worm
        }
    }

    fun reset() {
        this.frozen = SimpleBooleanProperty(false)
        this.setSide(DICE.worm)
        this.setSelected(false)
    }

    fun setSelected(selected : Boolean) {
        if(selected) {
            this.effect = Bloom()
            this.select = true
        } else {
            this.effect = null
            this.select = false
        }
    }
}
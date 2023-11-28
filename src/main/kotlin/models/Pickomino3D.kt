package models

import com.interactivemesh.jfx.importer.col.ColModelImporter
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.*
import javafx.scene.paint.Color
import javafx.scene.transform.Rotate
import javafx.util.Duration

class Pickomino3D(var number: Int) : Group() {
    val view = Group()
    private val transformY = Rotate(90.0, Rotate.Y_AXIS)
    var select = false
    var frozen = SimpleBooleanProperty(false)
    var back = false

    init {
        val importer = ColModelImporter()
        importer.read("assets/3D/Pickomino/pickomino3d-$number.dae")

        val light = AmbientLight()
        light.color = Color.WHITE

        view.children.addAll(importer.import)
        view.children.add(light)
        view.transforms.addAll(transformY)

        val camera = PerspectiveCamera(true)
        camera.translateZ = -5.0

        val subScene = SubScene(view, 75.0, 150.0, true, SceneAntialiasing.BALANCED)
        subScene.camera = camera

        this.children.add(subScene)
    }

    private fun animate(e1:Int, e2:Int) {
        Timeline(
            KeyFrame(Duration.ZERO, KeyValue(this.transformY.angleProperty(), e1)),
            KeyFrame(
                Duration.seconds(2.3),
                KeyValue(this.transformY.angleProperty(), e2)
            )
        ).play()
    }

    fun setReturned(back : Boolean) {
        this.back = back
        if(back) {
            Timeline(
                KeyFrame(Duration.ZERO, KeyValue(this.frozen, true)),
                KeyFrame(Duration.seconds(2.3), KeyValue(this.frozen, false))
            ).play()
            this.animate(90, 270)
        }
        else this.animate(270, 90)
    }
}
package vues

import fr.baptiste.test3d.model.Dice3D
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.transform.Rotate
import javafx.scene.transform.Scale
import javafx.util.Duration

class Loading(font : Font): BorderPane() {
    init{
        val vbox = VBox()
        val diceAnimation = Dice3D()
        val title = Label("Connexion en cours")

        var transformXanime = Rotate(270.0, Rotate.X_AXIS)
        var transformYanime = Rotate(0.0, Rotate.Y_AXIS)

        title.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

        diceAnimation.view.transforms.addAll(transformXanime, transformYanime)

        diceAnimation.scaleX = 1.5
        diceAnimation.scaleY = 1.5
        diceAnimation.scaleZ = 1.5

        val timeline = Timeline(
            KeyFrame(Duration.ZERO, KeyValue(transformXanime.angleProperty(), 0)),
            KeyFrame(Duration.ZERO, KeyValue(transformYanime.angleProperty(), 0)),
            KeyFrame(Duration.seconds(1.0), KeyValue(transformXanime.angleProperty(), 360)),
            KeyFrame(Duration.seconds(1.0), KeyValue(transformYanime.angleProperty(), 360))
        )
        timeline.cycleCount = Timeline.INDEFINITE
        timeline.play()

        vbox.children.addAll(diceAnimation, title)
        vbox.alignment = Pos.CENTER
        vbox.spacing = 50.0

        this.center = vbox
    }
}
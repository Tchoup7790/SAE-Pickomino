package controllers

import javafx.animation.ScaleTransition
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.util.Duration


class ControllerButtonView(): EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        when (event.eventType) {
            MouseEvent.MOUSE_ENTERED -> {
                val scaleTransition = ScaleTransition(Duration.millis(200.0), (event.source as Button))
                scaleTransition.fromX = 1.0
                scaleTransition.fromY = 1.0
                scaleTransition.toX = 1.2
                scaleTransition.toY = 1.2

                scaleTransition.play()
            }

            MouseEvent.MOUSE_CLICKED -> {
                var scaleTransition = ScaleTransition(Duration.millis(200.0), (event.source as Button))
                scaleTransition.fromX = 1.2
                scaleTransition.fromY = 1.2
                scaleTransition.toX = 1.0
                scaleTransition.toY = 1.0

                scaleTransition.play()

                scaleTransition = ScaleTransition(Duration.millis(200.0), (event.source as Button))
                scaleTransition.fromX = 1.0
                scaleTransition.fromY = 1.0
                scaleTransition.toX = 1.2
                scaleTransition.toY = 1.2

                scaleTransition.play()
            }
            MouseEvent.MOUSE_EXITED -> {
                val scaleTransition = ScaleTransition(Duration.millis(200.0), (event.source as Button))
                scaleTransition.fromX = 1.2
                scaleTransition.fromY = 1.2
                scaleTransition.toX = 1.0
                scaleTransition.toY = 1.0

                scaleTransition.play()
            }
        }
    }
}
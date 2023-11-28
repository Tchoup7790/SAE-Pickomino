package controllers

import fr.baptiste.test3d.model.Dice3D
import iut.info1.pickomino.data.STATUS
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.effect.Bloom
import javafx.scene.input.MouseEvent
import javafx.util.Duration
import models.GameManager
import vues.MainVue

class ControleurSourisDe(private val vue: MainVue, private val gm : GameManager): EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        if(!(event.source as Dice3D).frozen.value){
            if (event.eventType == MouseEvent.MOUSE_ENTERED) {
                for (child in this.vue.GameViewVue.listeDe.indices) {
                    if (this.vue.GameViewVue.listeDe[child].actualSide == (event.source as Dice3D).actualSide) {
                        Timeline(
                            KeyFrame(Duration.ZERO, KeyValue(this.vue.GameViewVue.listeDe[child].view.translateZProperty(), 0)),
                            KeyFrame(
                                Duration.seconds(0.2),
                                KeyValue(this.vue.GameViewVue.listeDe[child].view.translateZProperty(), -1)
                            ),
                        ).play()
                    }
                }
            }
            if (event.eventType == MouseEvent.MOUSE_EXITED) {
                for (child in this.vue.GameViewVue.listeDe.indices) {
                    if (this.vue.GameViewVue.listeDe[child].actualSide == (event.source as Dice3D).actualSide) {
                        Timeline(
                            KeyFrame(Duration.ZERO, KeyValue(this.vue.GameViewVue.listeDe[child].view.translateZProperty(), -1)),
                            KeyFrame(
                                Duration.seconds(0.2), KeyValue(this.vue.GameViewVue.listeDe[child].view.translateZProperty(), 0)
                            ),
                        ).play()
                    }
                }
            }
            if (event.eventType == MouseEvent.MOUSE_CLICKED) {
                if(this.gm.getGameStatus() != STATUS.KEEP_DICE) return
                val dice = (event.source as Dice3D)
                if(dice.actualSide in this.gm.getActualPlayer().keptDices) return
                for (child in this.vue.GameViewVue.listeDe.indices) {
                    if (this.vue.GameViewVue.listeDe[child].actualSide == dice.actualSide && !this.vue.GameViewVue.listeDe[child].select) {
                        this.vue.GameViewVue.listeDe[child].setSelected(!this.vue.GameViewVue.listeDe[child].select)
                    }
                }

                this.gm.keepDice(dice.actualSide)
            }
        }
    }
}
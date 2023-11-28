package controllers

import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.util.Duration
import models.GameManager
import vues.MainVue
import java.lang.IndexOutOfBoundsException


class ControleurSouris3D(private val vue: MainVue, private val gm : GameManager): EventHandler<MouseEvent>{
    override fun handle(event: MouseEvent) {
        if(this.gm.getGameStatus() in listOf(STATUS.ROLL_DICE, STATUS.ROLL_DICE_OR_TAKE_PICKOMINO))
        Timeline(
             KeyFrame(Duration.ZERO, KeyValue(vue.GameViewVue.btn.disableProperty(), true)),
             KeyFrame(Duration.seconds(2.3), KeyValue(vue.GameViewVue.btn.disableProperty(), false))
        ).play()

        val dices = gm.rollDices()
        var side = 0

        for (child in vue.GameViewVue.listeDe.indices) {
            try {
                if(!vue.GameViewVue.listeDe[child].select) {
                    vue.GameViewVue.listeDe[child].setSide(dices[side])
                    side++
                }
            } catch(e : IndexOutOfBoundsException) {
                return
            }

            Timeline(
             KeyFrame(Duration.ZERO, KeyValue(this.vue.GameViewVue.listeDe[child].frozen, true)),
             KeyFrame(Duration.seconds(2.3), KeyValue(this.vue.GameViewVue.listeDe[child].frozen, false))
            ).play()
        }
    }
}
package controllers

import javafx.event.EventHandler
import javafx.scene.control.RadioButton
import javafx.scene.input.MouseEvent
import models.GameManager
import vues.MainVue

class ControllerPlayerNumber(private val vue: MainVue, private val gm : GameManager): EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        if(event.eventType == MouseEvent.MOUSE_CLICKED){
            when(event.source){
                vue.playerNumberVue.backBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background_Main, vue.indexVue)
                }
                is RadioButton -> {
                    vue.playerNumberVue.checkPlayerNumber()
                }
                vue.playerNumberVue.gameStart -> {
                    vue.children.clear()
                    vue.playerNumberVue.lancementPartie()
                    vue.GameViewVue.setPlayerList(vue.playerNumberVue.listeJoueur)
                    this.gm.newGame(this.vue.GameViewVue.players)
                    vue.children.addAll(vue.background, vue.GameViewVue)
                }
            }
        }
    }
}
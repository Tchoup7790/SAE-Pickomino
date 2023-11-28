package controllers

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import vues.MainVue

class ControllerChangeView(vue: MainVue, stage: Stage? = null):EventHandler<MouseEvent> {
    private val vue = vue
    private val stage = stage
    override fun handle(event: MouseEvent) {
        if(event.eventType == MouseEvent.MOUSE_CLICKED){
            when (event.source) {
                vue.end.backBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background_Main, vue.indexVue)
                }
                vue.indexVue.playBtn -> {
                    vue.children.clear()
                    vue.playerNumberVue.checkPlayerNumber()
                    vue.children.addAll(vue.background, vue.playerNumberVue)
                }
                vue.indexVue.optionBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background, vue.optionVue)
                }
                vue.indexVue.rulesBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background, vue.rulesVue)
                    vue.rulesVue.checkDisableButton()
                }
                vue.indexVue.exitBtn -> {
                    val alert = Alert(Alert.AlertType.CONFIRMATION)
                    alert.title = "Au Revoir"
                    alert.headerText = "Êtes vous sûr de vouloir quitter le jeu ?"

                    val result = alert.showAndWait()
                    if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                        stage?.close()
                    }
                }
            }
        }
    }
}
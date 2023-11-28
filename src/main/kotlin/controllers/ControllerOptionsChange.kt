package controllers

import javafx.event.EventHandler
import javafx.scene.control.CheckBox
import javafx.scene.input.MouseEvent
import vues.MainVue

class ControllerOptionsChange(vue: MainVue): EventHandler<MouseEvent> {
    val vue = vue
    override fun handle(event: MouseEvent) {
        if(event.eventType == MouseEvent.MOUSE_CLICKED){
            when(event.source) {
                vue.optionVue.backBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background_Main, vue.indexVue)
                }
                is CheckBox -> vue.optionVue.checkBox()
            }
            //TODO: CHANGEMENT DES OPTIONS
        }
    }
}
package controllers

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import vues.MainVue

class ControllerRuleReader(vue: MainVue): EventHandler<MouseEvent> {
    val vue = vue
    override fun handle(event: MouseEvent) {
        if(event.eventType == MouseEvent.MOUSE_CLICKED){
            when(event.source) {
                vue.rulesVue.leftBtn -> vue.rulesVue.rulePage--
                vue.rulesVue.rightBtn -> vue.rulesVue.rulePage++
                vue.rulesVue.backBtn -> {
                    vue.children.clear()
                    vue.children.addAll(vue.background_Main, vue.indexVue)
                }
            }
            vue.rulesVue.checkDisableButton()
            vue.rulesVue.center = vue.rulesVue.rulesImages[vue.rulesVue.rulePage]
        }
    }
}
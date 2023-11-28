package vues

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import vue.Index

class MainVue(background_Main: ImageView, background: ImageView): StackPane() {
    val fontBangers = Font.loadFont("file:assets/font/Bangers.ttf", 60.0)
    val background_Main = background_Main
    val background = background
    val indexVue = Index(fontBangers)
    val rulesVue = Rules(fontBangers)
    val optionVue = Options(fontBangers)
    var GameViewVue = GameView(fontBangers)
    var playerNumberVue = PlayerNumber(fontBangers)
    val loading = Loading(fontBangers)
    val end = EndGame(fontBangers)

    init {
        this.children.addAll(background_Main, loading)
    }

    fun setControllerBtnView(eventHandler: EventHandler<MouseEvent>, btn : Button){
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
        btn.addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler)
        btn.addEventHandler(MouseEvent.MOUSE_EXITED, eventHandler)
    }

    fun setControllerBtnView(eventHandler: EventHandler<MouseEvent>, check : CheckBox){
        check.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
    }

    fun setControllerBtnView(eventHandler: EventHandler<MouseEvent>, check : RadioButton){
        check.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
    }

    fun fixeControllerDe(eventHandler : EventHandler<MouseEvent>){
        GameViewVue.btn.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
    }

    fun fixeControllerHoverDe(eventHandler : EventHandler<MouseEvent>){
        for(child in this.GameViewVue.listeDe.indices){
            GameViewVue.listeDe[child].addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler)
            GameViewVue.listeDe[child].addEventHandler(MouseEvent.MOUSE_EXITED, eventHandler)
            GameViewVue.listeDe[child].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
        }
    }

    fun fixeControllerHoverPickomino(eventHandler : EventHandler<MouseEvent>){
        for(child in GameViewVue.listePickomino.indices){
            GameViewVue.listePickomino[child].addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler)
            GameViewVue.listePickomino[child].addEventHandler(MouseEvent.MOUSE_EXITED, eventHandler)
            GameViewVue.listePickomino[child].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler)
        }
    }
}
package vue

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.BoxBlur
import javafx.scene.effect.DropShadow
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font



class Index(fontBangers : Font): BorderPane() {
    val playBtn =  Button("Jouer")
    val rulesBtn = Button("Règles")
    val optionBtn = Button("Options")
    val exitBtn =  Button("Quitter")

    init{
        val vBoxCenter = VBox()
        val hBoxTop = HBox()
        val dropShadow = DropShadow()
        val title = Label("Pickomino: The Game")
        val name = Label("Made by Xx_TheSteveDestroyer44_xX")
        val btnStyle = "-fx-background-color: #f5f5f5; -fx-border-width: 0;-fx-font-family:'${fontBangers.family}';-fx-font-size: 26;-fx-text-fill: #F6C00C;"

        playBtn.style = btnStyle
        playBtn.setPrefSize(300.0, 50.0)

        rulesBtn.style = btnStyle
        rulesBtn.setPrefSize(300.0, 50.0)

        optionBtn.style = btnStyle
        optionBtn.setPrefSize(300.0, 50.0)

        exitBtn.style = btnStyle
        exitBtn.setPrefSize(300.0, 50.0)

        title.style = "-fx-font-family:'${fontBangers.family}' ; -fx-font-size: 80;-fx-text-fill: #F6C00C;"
        dropShadow.color = Color.WHITE
        title.effect = dropShadow

        name.style = "-fx-font-family:'${fontBangers.family}';-fx-font-size: 14;-fx-text-fill: #FFFFFF"

        hBoxTop.children.add(title)
        hBoxTop.padding = Insets(40.0, 0.0, 0.0, 0.0)
        hBoxTop.alignment = Pos.CENTER

        vBoxCenter.children.addAll(playBtn, rulesBtn, optionBtn, exitBtn)
        vBoxCenter.spacing = 35.0
        vBoxCenter.alignment = Pos.CENTER

        this.top = hBoxTop
        this.center = vBoxCenter
        this.bottom = name
        this.padding = Insets(20.0)
    }
}
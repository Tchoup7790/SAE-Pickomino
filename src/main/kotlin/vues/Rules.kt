package vues

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class Rules(font:Font): BorderPane() {
    private val font = font
    val backBtn = Button("Retour")
    val leftBtn = Button("Page précédente")
    val rightBtn = Button("Page suivante")
    var rulesImages = emptyList<ImageView>()
    var rulePage = 0
    init{
        val title = Label("Règles")
        val hBoxTop = HBox()
        val hBoxBottom = HBox()
        val vBoxRight = VBox()
        val vBoxLeft = VBox()

        for(i in 1..8){
            var image = ImageView(Image("file:assets/img/regles/regle"+i+".png"))
            image.fitHeight = 550.0
            image.fitWidth = 500.0
            rulesImages += image
        }
        leftBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        leftBtn.setPrefSize(200.0, 50.0)
        leftBtn.alignment = Pos.CENTER

        rightBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        rightBtn.setPrefSize(200.0, 50.0)
        rightBtn.alignment = Pos.CENTER

        title.style = "-fx-font-family:'${font.family}' ; -fx-font-size: 60;-fx-text-fill: #F6C00C;"

        vBoxRight.children.add(rightBtn)
        vBoxRight.alignment = Pos.CENTER

        vBoxLeft.children.add(leftBtn)
        vBoxLeft.alignment = Pos.CENTER

        hBoxTop.children.add(title)
        hBoxTop.padding = Insets(40.0, 0.0, 0.0, 0.0)
        hBoxTop.alignment = Pos.CENTER

        backBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        backBtn.setPrefSize(200.0, 50.0)
        backBtn.alignment = Pos.CENTER

        hBoxBottom.children.add(backBtn)
        hBoxBottom.alignment = Pos.CENTER
        hBoxBottom.padding = Insets(0.0, 0.0, 40.0, 0.0)

        this.center = rulesImages.get(rulePage)
        this.top = hBoxTop
        this.bottom = hBoxBottom
        this.left =vBoxLeft
        this.right = vBoxRight
    }

    fun checkDisableButton(){
        if(this.rulePage == 7){
            rightBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${this.font.family}';-fx-font-size: 22;-fx-text-fill: #D9D9D9;"
            rightBtn.isDisable = true
        }else{
            rightBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            rightBtn.isDisable = false
        }
        if(this.rulePage == 0){
            leftBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${this.font.family}';-fx-font-size: 22;-fx-text-fill: #D9D9D9;"
            leftBtn.isDisable = true
        }else{
            leftBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            leftBtn.isDisable = false
        }
    }
}
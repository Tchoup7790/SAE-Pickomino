package vues

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class PlayerNumber(font: Font): BorderPane() {
    private val font = font
    val backBtn = Button("Retour")
    val radio2 = RadioButton("2 joueurs")
    val radio3 = RadioButton("3 joueurs")
    val radio4 = RadioButton("4 joueurs")
    private val joueur1 = TextField("Joueur 1")
    private val joueur2 = TextField("Joueur 2")
    private val joueur3 = TextField("Joueur 3")
    private val joueur4 = TextField("Joueur 4")
    var listeJoueur = emptyList<String>()
    private val vBoxText = VBox()
    val gameStart = Button("LANCER LA PARTIE")
    init{
        val hBoxBottom= HBox()
        val hBoxRadio = HBox()
        val title = Label("Choix des joueurs")
        val hBoxTop = HBox()
        val vBoxCenter = VBox()
        val toggle = ToggleGroup()

        radio2.toggleGroup = toggle
        radio2.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        radio2.isSelected = true

        radio3.toggleGroup = toggle
        radio3.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

        radio4.toggleGroup = toggle
        radio4.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

        joueur1.maxWidth = 300.0
        joueur1.style = "-fx-background-color: F5F5F5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F6C00C;"

        joueur2.maxWidth = 300.0
        joueur2.style = "-fx-background-color: F5F5F5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F6C00C;"

        joueur3.maxWidth = 300.0
        joueur3.style = "-fx-background-color: F5F5F5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F6C00C;"

        joueur4.maxWidth = 300.0
        joueur4.style = "-fx-background-color: F5F5F5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F6C00C;"

        title.style = "-fx-font-family:'${font.family}' ; -fx-font-size: 60;-fx-text-fill: #F6C00C;"

        hBoxTop.children.add(title)
        hBoxTop.padding = Insets(40.0, 0.0, 0.0, 0.0)
        hBoxTop.alignment = Pos.CENTER

        backBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        backBtn.setPrefSize(200.0, 50.0)
        backBtn.alignment = Pos.CENTER

        gameStart.style = "-fx-background-color: #f5f5f5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 26;-fx-text-fill: #F6C00C;"
        gameStart.setPrefSize(200.0, 50.0)
        gameStart.alignment = Pos.CENTER

        hBoxRadio.children.addAll(radio2, radio3, radio4)
        hBoxRadio.alignment = Pos.CENTER
        hBoxRadio.spacing = 50.0

        hBoxBottom.children.addAll(backBtn, gameStart)
        hBoxBottom.alignment = Pos.CENTER
        hBoxBottom.padding = Insets(0.0, 0.0, 40.0, 0.0)

        vBoxText.children.addAll(joueur1, joueur2, joueur3, joueur4)
        vBoxText.alignment = Pos.CENTER
        vBoxText.spacing = 50.0

        vBoxCenter.children.addAll(hBoxRadio, vBoxText)
        vBoxCenter.alignment = Pos.CENTER
        vBoxCenter.spacing = 100.0

        this.top = hBoxTop
        this.center = vBoxCenter
        this.bottom = hBoxBottom
    }

    fun checkPlayerNumber(){
        if(this.radio2.isSelected){
            this.joueur3.isDisable = true
            this.joueur4.isDisable = true
        }else if(this.radio3.isSelected){
            this.joueur3.isDisable = false
            this.joueur4.isDisable = true
        }else if(this.radio4.isSelected){
            this.joueur3.isDisable = false
            this.joueur4.isDisable = false
        }
    }

    fun lancementPartie(){
        for(text in vBoxText.children){
            if(!text.isDisable){
                this.listeJoueur += (text as TextField).text
            }
        }
    }
}
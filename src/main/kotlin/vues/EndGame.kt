package vues

import fr.baptiste.test3d.model.Dice3D
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.transform.Rotate
import javafx.util.Duration
import models.Player

class EndGame(font: Font): BorderPane() {
    private var players = emptyList<Player>()
    private val font = font
    private val winner = Label()
    private val vBoxScore = VBox()
    val backBtn = Button("Menu")
    init{
        val vboxTitle = VBox()
        val vboxCenter = VBox()
        val hboxScore = HBox()
        val title = Label("Le gagnant est :")
        val score = Label("Score :")

        title.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 36;-fx-text-fill: #F5F5F5;"

        score.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 28;-fx-text-fill: #F6C00C;"

        winner.style = "-fx-font-family:'${font.family}' ; -fx-font-size: 60;-fx-text-fill: #F6C00C;"

        vboxTitle.children.addAll(title ,winner)
        vboxTitle.alignment = Pos.CENTER
        vboxTitle.spacing = 50.0

        hboxScore.children.addAll(score, vBoxScore)
        hboxScore.alignment = Pos.CENTER
        hboxScore.spacing = 50.0

        backBtn.style = "-fx-background-color: #f5f5f5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 26;-fx-text-fill: #F6C00C;"
        backBtn.setPrefSize(200.0, 50.0)
        backBtn.alignment = Pos.CENTER

        vBoxScore.children.add(score)
        vBoxScore.spacing = 25.0

        vboxCenter.children.addAll(vboxTitle, hboxScore, backBtn)
        vboxCenter.alignment = Pos.CENTER
        vboxCenter.spacing = 200.0

        this.center = vboxCenter
    }

    fun setPlayersScore(list: List<Player>){
        this.players = list

        players.sortedBy { p -> p.score }

        this.winner.text = players[0].name

        for(player in players.indices){
            var number = Label((player + 1).toString())
            number.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            number.minWidth = 20.0
            var name = Label(players[player].name)
            name.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            name.minWidth = 250.0
            name.alignment = Pos.BASELINE_CENTER
            var score = Label("score : "+(players[player].score).toString())
            score.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            score.minWidth = 50.0
            score.alignment = Pos.BASELINE_CENTER

            var hbox = HBox(number, name, score)

            vBoxScore.children.addAll(hbox)
        }
    }
}
package vues

import fr.baptiste.test3d.model.Dice3D
import models.Pickomino3D
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import models.Player

class GameView(font: Font): BorderPane() {
    private val font = font
    var players = emptyList<Player>()
    val btn = Button("Lancer de Dés")

    val listeDe : Array<Dice3D> = arrayOf(Dice3D(), Dice3D(), Dice3D(), Dice3D(), Dice3D(), Dice3D(), Dice3D(), Dice3D())

    val listePickomino : MutableList<Pickomino3D> = mutableListOf(
        Pickomino3D(21),
        Pickomino3D(22), Pickomino3D(23), Pickomino3D(24), Pickomino3D(25), Pickomino3D(26),
        Pickomino3D(27), Pickomino3D(28),
        Pickomino3D(29), Pickomino3D(30), Pickomino3D(31), Pickomino3D(32), Pickomino3D(33), Pickomino3D(34), Pickomino3D(35), Pickomino3D(36)
    )

    private var hBoxPlayers = listOf<HBox>(HBox() ,HBox(), HBox(), HBox())
    private val vBoxRight = VBox()
    private val vBoxLeft = VBox()

    private val actualPlayerName = Label()

    private val status = Label()
    init {
        val title = Label("Pickomino: The Game")
        val hBoxTop = HBox()
        val hHautDe = HBox(listeDe[0], listeDe[1], listeDe[2], listeDe[3])
        val hBasDe = HBox(listeDe[4], listeDe[5], listeDe[6], listeDe[7])
        val hHautPickomino = HBox(listePickomino[0], listePickomino[1], listePickomino[2], listePickomino[3], listePickomino[4], listePickomino[5], listePickomino[6], listePickomino[7])
        val hBasPickomino = HBox(listePickomino[8], listePickomino[9], listePickomino[10], listePickomino[11], listePickomino[12], listePickomino[13], listePickomino[14], listePickomino[15])
        val listeDe = VBox(hHautDe, hBasDe)
        val listePickomino = VBox(hHautPickomino, hBasPickomino)
        val centerContainer = HBox(btn)
        val centerElt = VBox(listePickomino, listeDe)
        val hBoxBottom = HBox()
        val vBoxBottom = VBox()

        title.style = "-fx-font-family:'${font.family}' ; -fx-font-size: 60;-fx-text-fill: #F6C00C;"

        hBoxTop.children.add(title)
        hBoxTop.padding = Insets(40.0, 0.0, 0.0, 0.0)
        hBoxTop.alignment = Pos.CENTER

        hHautDe.spacing = 2.0
        hHautDe.alignment = Pos.CENTER

        hBasDe.spacing = 2.0
        hBasDe.alignment = Pos.CENTER

        listeDe.spacing = 2.0
        listeDe.alignment = Pos.CENTER

        hHautPickomino.spacing = 2.0
        hHautPickomino.alignment = Pos.CENTER

        hBasPickomino.spacing = 2.0
        hBasPickomino.alignment = Pos.CENTER

        listePickomino.spacing = 2.0
        listePickomino.alignment = Pos.CENTER

        centerContainer.alignment = Pos.CENTER

        centerElt.spacing = 20.0
        centerElt.alignment = Pos.CENTER

        vBoxBottom.children.addAll(actualPlayerName, btn, status)
        vBoxBottom.alignment = Pos.CENTER
        vBoxBottom.spacing = 25.0

        status.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        actualPlayerName.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

        btn.style = "-fx-background-color: #f5f5f5; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 26;-fx-text-fill: #F6C00C;"
        btn.setPrefSize(200.0, 50.0)
        btn.alignment = Pos.CENTER

        hBoxBottom.children.add(vBoxBottom)
        hBoxBottom.alignment = Pos.CENTER
        hBoxBottom.padding = Insets(0.0, 0.0, 40.0, 0.0)

        vBoxLeft.alignment = Pos.CENTER
        vBoxLeft.spacing = 300.0

        vBoxRight.alignment = Pos.CENTER
        vBoxRight.spacing = 300.0

        this.padding = Insets(20.0)
        this.bottom = hBoxBottom
        this.center = centerElt
        this.left = vBoxLeft
        this.right = vBoxRight
        this.top = hBoxTop
    }

    fun setPlayerList(players : List<String>) {
        for (i in players.indices) {
            this.players += Player(players[i], i)
            var name = Label(this.players[i].name)
            name.style =
                "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            var score = Label("score : " + this.players[i].score)
            score.style =
                "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

            var vBoxPlayer = VBox()
            vBoxPlayer.children.addAll(name, score)

            vBoxPlayer.alignment = Pos.CENTER
            vBoxPlayer.spacing = 30.0
            this.hBoxPlayers[i].children.addAll(
                this.players[i].pickominos[this.players[i].pickominos.size - 1],
                vBoxPlayer
            )

            this.actualPlayerName.text = this.players[0].name
            this.status.text = "Lancer les dés"

            if (i == 0 || i == 2) {
                this.vBoxLeft.children.add(this.hBoxPlayers[i])
            } else {
                this.vBoxRight.children.add(this.hBoxPlayers[i])
            }
        }
    }

    fun update(actualPlayer: Player, status : String, accessiblePickos : List<Int>, stackTops : List<Int>, players:List<Player>){
        this.status.text = status
        this.actualPlayerName.text = actualPlayer.name
        this.players = players
        for(player in players) {
            println("Joueur: ${player.name} | id: ${player.id} | score : ${player.score}")
            val name = Label(this.players[player.id].name)
            name.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            val score = Label("score : " + this.players[player.id].score)
            score.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

            val vBoxPlayer = VBox()
            vBoxPlayer.children.addAll(name, score)

            this.hBoxPlayers[player.id].children.clear()
            this.hBoxPlayers[player.id].children.addAll(
                this.players[player.id].pickominos[this.players[player.id].pickominos.size - 1],
                vBoxPlayer
            )

            if(stackTops[actualPlayer.id] in accessiblePickos) {
                val picko = this.listePickomino.find { p -> p.number == stackTops[actualPlayer.id] }
                if(picko != null) {
                    actualPlayer.rmPickomino(picko)
                    var index = this.listePickomino.indexOfLast { p -> p.number < picko.number }
                    if(index < 0) index = 0
                    this.listePickomino.add(index, picko)
                }
            }

            for(i in this.listePickomino) {
                if(i.number !in accessiblePickos && i.number !in stackTops) {
                    if(!i.back) {
                        i.setReturned(true)
                    }
                } else if(i.back || i.opacity == 0.0) {
                    i.setReturned(false)
                    i.opacity = 1.0
                }
            }
        }
    }

}
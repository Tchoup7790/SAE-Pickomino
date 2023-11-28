package models

import exceptions.*
import listener.status.StatusChangeListener
import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.Pickomino
import iut.info1.pickomino.data.STATUS
import javafx.scene.control.Alert
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import listener.player.PlayerChangeListener
import java.net.ConnectException
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class GameManager {
    val connectionManager = ConnectionManager(this)
    private lateinit var connect : Connector
    private var gameid : Int? = null
    private var gamekey : Int? = null
    private var players : List<Player> = listOf()
    var dices : List<DICE> = listOf()
    private var pickominos : MutableSet<Pickomino> = mutableSetOf()
    private var disabledPickominos : MutableSet<Pickomino> = mutableSetOf()
    private val statusObservers = mutableListOf<StatusChangeListener>()
    private var actualStatus : STATUS by Delegates.observable(STATUS.GAME_FINISHED) { _, _, status ->
        this.statusObservers.forEach { observer ->
            observer.handle(status)
        }
    }
    private val playerObservers = mutableListOf<PlayerChangeListener>()
    private var player : Player? by Delegates.observable(null) { _, _, player ->
        this.playerObservers.forEach { observer ->
            observer.handle(player)
        }
    }

    val labeledStatus = mapOf(
        STATUS.ROLL_DICE to "Lancez les dés",
        STATUS.ROLL_DICE_OR_TAKE_PICKOMINO to "Lancez les dés ou prenez un pickomino",
        STATUS.KEEP_DICE to "Sélectionnez le(s) dé(s) à garder",
        STATUS.TAKE_PICKOMINO to "Prenez un pickomino",
        STATUS.GAME_FINISHED to "Partie terminée !",
    )

    init {
        try {
            this.connectToServer()
        } catch(e : Throwable) {
            val alert = Alert(Alert.AlertType.ERROR, "Impossible d'établir une connexion avec le serveur !")
            alert.showAndWait()
            exitProcess(0)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun connectToServer() {
        if(this.connectionManager.connectState != CONNECTIONSTATUS.READY) {
            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                throw throwable
            }
            // Tentative de reconnection
            GlobalScope.launch(exceptionHandler) {
                this@GameManager.connect = this@GameManager.connectionManager.connect()
            }
        }
    }

    private fun isGameActive() : Boolean {
        return (this.gameid != null && this.gamekey != null) // Renvois true s'il y a un identifiant et une clé vers une partie.
    }

    fun newGame(players:List<Player>) : Boolean {
        val isGameActive = try {
            this.isGameActive()
        } catch(e : ConnectException) {
            return false
        }
        if(isGameActive) return false

        val (a, b) = this.connect.newGame(players.size) // Création de la partie + récupération des identifiants
        this.gameid = a
        this.gamekey = b
        this.players = players

        this.updateGameStatus()
        this.updatePlayer()

        return true // Partie crée avec succès
    }

    fun getGameStatus() : STATUS {
        if(!this.isGameActive()) throw GameNotFound() // Renvois une erreur s'il y a aucune partie
        return this.connect.gameState(this.gameid!!, this.gamekey!!).current.status
    }

    private fun updateGameStatus() {
        this.actualStatus = this.getGameStatus()
    }

    private fun updatePlayer() {
        val np = this.getActualPlayer()
        if(this.player?.id == np.id) return
        this.player = np
    }

    fun getActualPlayer(): Player {
        if (!this.isGameActive()) throw GameNotFound()
        if (this.players.isEmpty()) throw NoSuchElementException()
        val playerId = this.connect.gameState(this.gameid!!, this.gamekey!!).current.player
        return players.find { p -> p.id == playerId } ?: throw PlayerNotFound()
    }

    fun rollDices() : List<DICE> {
        if(!this.isGameActive()) throw GameNotFound()
        if(getGameStatus() != STATUS.ROLL_DICE && getGameStatus() != STATUS.ROLL_DICE_OR_TAKE_PICKOMINO) throw WrongStatus()
        this.dices = this.connect.rollDices(this.gameid!!, this.gamekey!!)
        this.updateGameStatus()
        this.updatePlayer()
        return this.dices
    }

    fun debugRollDices(dices:List<DICE>) : List<DICE> {
        if(!this.isGameActive()) throw GameNotFound()
        if(getGameStatus() != STATUS.ROLL_DICE && getGameStatus() != STATUS.ROLL_DICE_OR_TAKE_PICKOMINO) throw WrongStatus()
        this.dices = this.connect.choiceDices(this.gameid!!, this.gamekey!!, dices)
        this.updateGameStatus()
        this.updatePlayer()
        return this.dices
    }

    fun keepDice(dice:DICE) : Boolean {
        if(!this.isGameActive()) throw GameNotFound()
        if(getGameStatus() != STATUS.KEEP_DICE) throw WrongStatus()
        val player = this.getActualPlayer()
        val valid = this.connect.keepDices(this.gameid!!, this.gamekey!!, dice)
        player.keptDices = this.connect.gameState(this.gameid!!, this.gamekey!!).current.keptDices
        this.updateGameStatus()
        this.updatePlayer()
        return valid
    }

    fun pickPickomino(pickomino: Int) : Boolean {
        if(!this.isGameActive()) throw GameNotFound()
        if(getGameStatus() != STATUS.TAKE_PICKOMINO && getGameStatus() != STATUS.ROLL_DICE_OR_TAKE_PICKOMINO) throw WrongStatus()
        val player = getActualPlayer()
        val valid = this.connect.takePickomino(this.gameid!!, this.gamekey!!, pickomino)
        if(valid) {
            this.updateGameStatus()
            this.updatePlayer()
        }
        return valid
    }

    fun getAccessiblePickos() : List<Int> {
        if(!this.isGameActive()) throw GameNotFound()
        return this.connect.gameState(this.gameid!!, this.gamekey!!).accessiblePickos()
    }

    fun getPickosStackTops() : List<Int> {
        if(!this.isGameActive()) throw GameNotFound()
        return this.connect.gameState(this.gameid!!, this.gamekey!!).pickosStackTops()
    }

    fun getPlayersScore() : List<Int> {
        if(!this.isGameActive()) throw GameNotFound()
        return this.connect.gameState(this.gameid!!, this.gamekey!!).score()
    }

    fun getPlayers() : List<Player> {
        return this.players
    }

    fun getFinalScore() : List<Int> {
        if(!this.isGameActive()) throw GameNotFound()
        if(getGameStatus() != STATUS.GAME_FINISHED) throw WrongStatus()
        return this.connect.finalScore(this.gameid!!, this.gamekey!!)
    }

    fun stopGame() {
        if(!this.isGameActive()) throw GameNotFound()
        if(this.getGameStatus() != STATUS.GAME_FINISHED) throw WrongStatus()
        this.gameid = null
        this.gamekey = null
    }

    fun resetPickominos() {
        this.pickominos = mutableSetOf(
            Pickomino(21, 1),
            Pickomino(22, 1),
            Pickomino(23, 1),
            Pickomino(24, 1),
            Pickomino(25, 2),
            Pickomino(26, 2),
            Pickomino(27, 2),
            Pickomino(28, 2),
            Pickomino(29, 3),
            Pickomino(30, 3),
            Pickomino(31, 3),
            Pickomino(32, 3),
            Pickomino(33, 4),
            Pickomino(34, 4),
            Pickomino(35, 4),
            Pickomino(36, 4)
        )
        this.disabledPickominos = mutableSetOf()
    }

    fun addStatusChangeListener(observer: StatusChangeListener) {
        this.statusObservers.add(observer)
    }

    fun removeStatusChangeListener(observer: StatusChangeListener) {
        this.statusObservers.remove(observer)
    }

    fun addPlayerChangeListener(observer: PlayerChangeListener) {
        this.playerObservers.add(observer)
    }

    fun removePlayerChangeListener(observer: PlayerChangeListener) {
        this.playerObservers.remove(observer)
    }
}
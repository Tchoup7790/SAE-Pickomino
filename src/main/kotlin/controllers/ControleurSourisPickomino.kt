package controllers

import exceptions.WrongStatus
import models.Pickomino3D
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.exceptions.BadPickominoChosenException
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.util.Duration
import models.GameManager
import vues.MainVue

class ControleurSourisPickomino(private val vue: MainVue, private val gm : GameManager): EventHandler<MouseEvent> {
    override fun handle(event: MouseEvent) {
        if(!(event.source as Pickomino3D).frozen.value){
            when (event.eventType) {
                MouseEvent.MOUSE_ENTERED -> {
                    for (child in this.vue.GameViewVue.listePickomino.indices) {
                        if (this.vue.GameViewVue.listePickomino[child].number == (event.source as Pickomino3D).number) {
                            Timeline(
                                KeyFrame(
                                    Duration.ZERO,
                                    KeyValue(this.vue.GameViewVue.listePickomino[child].view.translateZProperty(), 0)
                                ),
                                KeyFrame(
                                    Duration.seconds(0.2),
                                    KeyValue(this.vue.GameViewVue.listePickomino[child].view.translateZProperty(), -0.5)
                                ),
                            ).play()
                        }
                    }
                }
                MouseEvent.MOUSE_EXITED -> {
                    for (child in this.vue.GameViewVue.listePickomino.indices) {
                        if (this.vue.GameViewVue.listePickomino[child].number == (event.source as Pickomino3D).number) {
                            Timeline(
                                KeyFrame(
                                    Duration.ZERO,
                                    KeyValue(this.vue.GameViewVue.listePickomino[child].view.translateZProperty(), -0.5)
                                ),
                                KeyFrame(
                                    Duration.seconds(0.2),
                                    KeyValue(this.vue.GameViewVue.listePickomino[child].view.translateZProperty(), 0)
                                ),
                            ).play()
                        }
                    }
                }
                MouseEvent.MOUSE_CLICKED -> {
                    if(this.gm.getGameStatus() !in listOf(STATUS.TAKE_PICKOMINO, STATUS.ROLL_DICE_OR_TAKE_PICKOMINO)) return
                    try {
                        val player = this.gm.getActualPlayer()
                        val players = this.gm.getPlayers()
                        val picko = (event.source as Pickomino3D)
                        val stackTops = this.gm.getPickosStackTops()
                        val accessibles = this.gm.getAccessiblePickos()

                        if(picko.number in stackTops) {
                            val index = stackTops.indexOf(picko.number)
                            val valid = this.gm.pickPickomino(picko.number)
                            if(valid) players[index].givePickoTo(picko, player)
                        } else {
                            if(picko.number in accessibles) {
                                val valid = this.gm.pickPickomino(picko.number)
                                if(valid) player.takePickomino(picko)
                            }
                        }
                    } catch(e : BadPickominoChosenException) {
                        println("badPicko")
                    } catch(e : WrongStatus) {
                        println("WrongStatus")
                    }
                    this.vue.GameViewVue.update(this.gm.getActualPlayer(), this.gm.labeledStatus[this.gm.getGameStatus()]?:"Unknow", this.gm.getAccessiblePickos(), this.gm.getPickosStackTops(), this.gm.getPlayers())
                }
            }
        }
    }
}
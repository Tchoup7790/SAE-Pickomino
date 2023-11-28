package listener.status

import iut.info1.pickomino.data.STATUS
import javafx.application.Platform
import models.GameManager
import vues.GameView
import vues.MainVue
import vues.PlayerNumber

class StatusChangeHandler(private val mv: MainVue, private val gm : GameManager) : StatusChangeListener {
    override fun handle(status: STATUS) {

        this.mv.GameViewVue.btn.isDisable = (status !in listOf(STATUS.ROLL_DICE, STATUS.ROLL_DICE_OR_TAKE_PICKOMINO))
        this.mv.GameViewVue.update(this.gm.getActualPlayer(), this.gm.labeledStatus[this.gm.getGameStatus()]?:"Unknow", this.gm.getAccessiblePickos(), this.gm.getPickosStackTops(), this.gm.getPlayers())

        if(status == STATUS.GAME_FINISHED) {
            Platform.runLater {
                this.mv.children.clear()
                this.mv.children.addAll(this.mv.background_Main, this.mv.end)
                /*this.mv.GameViewVue = GameView(this.mv.fontBangers)
                this.mv.playerNumberVue = PlayerNumber(this.mv.fontBangers)
                this.mv.children.addAll(this.mv.background, this.mv.indexVue)*/
            }
        }
    }
}
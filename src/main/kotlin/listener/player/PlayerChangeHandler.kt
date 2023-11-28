package listener.player

import models.GameManager
import models.Player
import vues.MainVue

class PlayerChangeHandler(private val mv : MainVue, private val gm : GameManager) : PlayerChangeListener {
    override fun handle(player: Player?) {
        if(player == null) return
        player.keptDices = listOf()
        this.mv.GameViewVue.update(this.gm.getActualPlayer(), this.gm.labeledStatus[this.gm.getGameStatus()]?:"Unknow", this.gm.getAccessiblePickos(), this.gm.getPickosStackTops(), this.gm.getPlayers())
        for(i in this.mv.GameViewVue.listeDe) {
            i.reset()
        }
    }
}
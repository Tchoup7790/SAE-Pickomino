package listener.connectionstatus

import iut.info1.pickomino.data.STATUS
import javafx.application.Platform
import javafx.scene.control.Label
import models.CONNECTIONSTATUS
import models.GameManager
import vues.GameView
import vues.MainVue

class ConnectionStatusChangeHandler(private val mv : MainVue, private val gm : GameManager) : ConnectionStatusChangeListener {
    override fun handle(status: CONNECTIONSTATUS) {
        Platform.runLater {
            if(status == CONNECTIONSTATUS.READY && this.mv.children.contains(this.mv.loading)) {
                this.mv.children.clear()
                this.mv.children.addAll(this.mv.background_Main, this.mv.indexVue)
            } else if(this.mv.children.contains(this.mv.indexVue)) {
                this.mv.children.clear()
                this.mv.children.addAll(this.mv.background_Main, this.mv.loading)
            }
        }
    }
}
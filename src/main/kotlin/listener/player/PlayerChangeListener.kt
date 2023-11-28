package listener.player

import models.Player

interface PlayerChangeListener {
    fun handle(player : Player?)
}
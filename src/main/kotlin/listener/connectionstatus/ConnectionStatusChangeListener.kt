package listener.connectionstatus

import iut.info1.pickomino.data.STATUS
import models.CONNECTIONSTATUS

interface ConnectionStatusChangeListener {
    fun handle(status : CONNECTIONSTATUS)
}
package listener.status

import iut.info1.pickomino.data.STATUS

interface StatusChangeListener {
    fun handle(status : STATUS)
}
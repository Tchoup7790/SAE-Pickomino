package models

import exceptions.ConnectionImpossible
import io.ktor.client.network.sockets.*
import iut.info1.pickomino.Connector
import kotlinx.coroutines.delay
import listener.connectionstatus.ConnectionStatusChangeListener
import java.net.ConnectException
import java.net.SocketException
import java.nio.channels.UnresolvedAddressException
import kotlin.properties.Delegates

class ConnectionManager(private val gm : GameManager) {
    private val interval = 2000L
    private val MAXTRIES = 30
    private val connectionStatusObservers = mutableListOf<ConnectionStatusChangeListener>()
    var connectState : CONNECTIONSTATUS by Delegates.observable(CONNECTIONSTATUS.CONNECTING) { _, _, status ->
        this.connectionStatusObservers.forEach { observer ->
            observer.handle(status)
        }
    }

    private suspend fun reconnect() : Connector? {
        var tries = 0
        var connector : Connector? = null
        while(this.connectState != CONNECTIONSTATUS.READY) {
            if(tries >= this.MAXTRIES) return null
            tries++
            this.connectState = CONNECTIONSTATUS.CONNECTING
            try {
                connector = getConnector()
            } catch(e : Exception) {
                if(this.connectState == CONNECTIONSTATUS.INVALID_ADDRESS) {
                    println("invalid")
                    throw ConnectionImpossible()
                }
            }
            delay(interval)
        }
        return connector
    }

    private fun getConnector() : Connector {
        try {
            val c = Connector.factory("172.26.82.76", "8080", true)
            this.connectState = CONNECTIONSTATUS.READY
            return c
        } catch(e: ConnectException) {
            // Impossible d'établir une connexion vers le serveur (dans ce cas ça peut être lié au vpn)
            this.connectState = CONNECTIONSTATUS.OFFLINE
        } catch(e: UnresolvedAddressException) {
            // Impossible de trouver un chemin vers le serveur, l'adresse est invalide
            this.connectState = CONNECTIONSTATUS.INVALID_ADDRESS
        } catch(e: ConnectTimeoutException) {
            // Impossible d'établir une connexion vers le serveur, le serveur ne répond pas
            this.connectState = CONNECTIONSTATUS.SERVER_NOT_RESPONDING
        } catch(e: SocketException) {
            // Pas de connection sur l'appareil
            this.connectState = CONNECTIONSTATUS.NETWORK_UNREACHABLE
        }
        throw ConnectionImpossible()
    }

    suspend fun connect() : Connector {
        return try {
            getConnector()
        } catch (e: Exception) {
            return reconnect() ?: throw ConnectionImpossible()
        }
    }

    fun addStatusChangeListener(observer: ConnectionStatusChangeListener) {
        this.connectionStatusObservers.add(observer)
    }

    fun removeStatusChangeListener(observer: ConnectionStatusChangeListener) {
        this.connectionStatusObservers.remove(observer)
    }
}
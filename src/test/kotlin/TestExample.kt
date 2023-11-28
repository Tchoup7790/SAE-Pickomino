import org.junit.jupiter.api.Test
import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.exceptions.BadPickominoChosenException
import iut.info1.pickomino.exceptions.BadStepException
import iut.info1.pickomino.exceptions.DiceAlreadyKeptException
import iut.info1.pickomino.exceptions.DiceNotInRollException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TestNewGame {
    val connect = Connector.factory("172.26.82.76", "8080", true)

    @ParameterizedTest
    @MethodSource("nbPlayersProvider")
    //test plusieurs cas de nbPlayers qui ne devraient renvoyé des erreurs en l'occurence (-1, -1)
    fun testNewGame(nbPlayers : Int, res : Pair<Int, Int>) {
        assertEquals(res, connect.newGame(nbPlayers))
    }
    companion object {
        @JvmStatic
        fun nbPlayersProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(-1, Pair(-1, -1)),
                Arguments.of(0, Pair(-1, -1)),
                Arguments.of(1, Pair(-1, -1)),
                Arguments.of(5, Pair(-1, -1)),
            )
        }
    }
}


class TestTakePickomino {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    @Test
    //test prendre pickomino que l'on peut prendre
    fun testTakePickomino1() {
        // On fait en sorte que le joueur 1 prenne un pickomino en l'occurence le 25.
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d4))
        connect.keepDices(id, key, DICE.worm)
        // Il prend le 25
        connect.takePickomino(id, key, 25)
        // on vérifie que le joueur 1 a bien pris le pickomino 25
        assertEquals(25, connect.gameState(id, key).pickosStackTops()[0])
    }
    @Test
    //test prendre pickomino qu'on ne peut pas prendre
    fun testTakePickomino2() {
        // On fait en sorte que le joueur 1 prenne un pickomino en l'occurence le 25.
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d4))
        connect.keepDices(id, key, DICE.worm)
        // Il prend le 21
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 21)
        }
    }
    @Test
    //test prendre pickomino alors que le joueur a "passé" son tour
    fun testTakePickomino3() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.worm))
        assertThrows<BadStepException> {
            connect.takePickomino(id, key, 35)
        }
    }

    @Test
    //test prendre pickomino qui a été retourner/cacher
    fun testTakePickomino4() {
        //on fait passer/perdre le tour du premier joueur
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        //on fait choisir le picckomino 36 qui est le dernier qui a donc été retourné par le joueur 1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 36)
        }
    }
    @Test
    //test prendre un pickomino que l'on peut prendre a un joueur
    fun testTakePickomino5() {
        //on fait choisir le pickomino 36 au joueur1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        connect.takePickomino(id, key, 36)
        //on fait en sorte que le joueur 2 prenne le pickomino 36 de l'autre joueur
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        connect.takePickomino(id, key, 36)
    }
    @Test
    //test prendre un pickomino que l'on ne peut pas prendre a un joueur
    fun testTakePickomino6() {
        // on fait choisir le pickomino 36 au joueur 1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        connect.takePickomino(id, key, 36)
        // on fait passer/perdre le tour du joueur 2
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        // on fait choisir le pickomino 25 au joueur 1
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d4))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id, key, 25)
        // on fait choisir le pickomino 36 au joueur 2
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        assertThrows<BadPickominoChosenException> {
            connect.takePickomino(id, key, 36)
        }
    }
    @Test
    //test si le joueur ne peut bien pas prendre de pickomino d'une certaine valeur si il ne possède pas de ver
    fun testTakePickomino7() {
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        assertThrows<BadStepException> {
            connect.takePickomino(id, key, 32)
        }
    }
}

class TestfinalScore {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    //fonction qui décompose le numéro du pickomino en plusieurs valeurs de dés
    fun decompositionPickomino(nb : Int) : List<DICE> {
        val tabDice = listOf<DICE>(DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.worm)
        var remaining = nb
        var tab : List<DICE> = listOf()
        var dice = 5
        while (remaining != 0) {
            if (remaining - dice >= 0) {
                remaining -= dice
                tab += tabDice[dice-1]
            } else {
                dice -= 1
            }
        }
        return tab
    }

    //fonction qui fait prendre au jouer dont c'est le tour le pickomino désigné
    fun choosePickomino(nb : Int) {
        val tab = decompositionPickomino(nb)
        if ((nb % 5) == 0) {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
        } else {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
            connect.choiceDices(id, key, listOf(tab.last()))
            connect.keepDices(id, key, tab.last())
        }
        connect.takePickomino(id, key, nb)
    }

    //fonction qui fait prendre les pickomino désigné aux joueurs
    fun choosePickominos(tab : List<Int>) {
        for (i in 0 until tab.size) {
            choosePickomino(tab[i])
        }
    }

    @ParameterizedTest
    @MethodSource("scoreProvider")
    //test si le joueur as passer son tour il retourne bien le pickomino de la plus forte valeur
    fun testFinalScore(pickominoTab:List<Int>, scoreTab : List<Int>) {
        choosePickominos(pickominoTab)
        assertEquals(connect.finalScore(id, key), scoreTab)
    }
    companion object {
        @JvmStatic
        fun scoreProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(listOf(36, 21, 35, 22, 34, 23, 33, 24, 32, 25, 31, 26, 30, 27, 29, 28), listOf(28, 12)),
                Arguments.of(listOf(35, 30, 28, 26, 29, 33, 31, 22, 23, 36, 24, 25, 34, 27, 32, 21), listOf(21, 19)),
                Arguments.of(listOf(23, 25, 28, 31, 32, 21, 35, 27, 24, 29, 26, 30, 34, 33, 22, 36), listOf(18, 22)),
                Arguments.of(listOf(28, 31, 22, 27, 23, 33, 25, 34, 35, 26, 36, 30, 29, 21, 24, 32), listOf(18, 22)),
                Arguments.of(listOf(34, 22, 32, 35, 27, 24, 23, 31, 25, 33, 28, 21, 36, 26, 30, 29), listOf(21, 19))
            )
        }
    }
}

class TestRendPickomino {

    //fonction qui décompose le numéro du pickomino en plusieurs valeurs de dés
    fun decompositionPickomino(nb : Int) : List<DICE> {
        val tabDice = listOf<DICE>(DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.worm)
        var remaining = nb
        var tab : List<DICE> = listOf()
        var dice = 5
        while (remaining != 0) {
            if (remaining - dice >= 0) {
                remaining -= dice
                tab += tabDice[dice-1]
            } else {
                dice -= 1
            }
        }
        return tab
    }

    //fonction qui fait prendre au jouer dont c'est le tour le pickomino désigné
    fun choosePickomino(nb : Int) {
        val tab = decompositionPickomino(nb)
        if ((nb % 5) == 0) {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
        } else {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
            connect.choiceDices(id, key, listOf(tab.last()))
            connect.keepDices(id, key, tab.last())
        }
        connect.takePickomino(id, key, nb)
    }

    //fonction qui fait prendre les pickomino désigné aux joueurs
    fun choosePickominos(tab : List<Int>) {
        for (i in 0 until tab.size) {
            choosePickomino(tab[i])
        }
    }

    //fonction qui fait en sorte que le joueur courant perde
    fun joueurPerd() {
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d1)
    }

    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second
    @Test
    // test si le joueur as passer son tour il rend bien le dernier Pickomino de sa pile
    fun testRendPickomino1() {
        // on fait choisir le pickomino 36 au joueur 1
        choosePickomino(36)
        // on fait passer/perdre le tour du joueur 2
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        // on fait passer/perdre le tour du joueur 1
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        //on regarde si le pickomino 36 est bien de retour dans les pickomino accesibles
        assertEquals(36, connect.gameState(id, key).accessiblePickos().last())
    }
    @ParameterizedTest
    @MethodSource("pickominoProvider")
    //test si le joueur as passer son tour il retourne bien le pickomino de la plus forte valeur
    fun testRendPickomino2(pickominoTab:List<Int>, pickominoValeurPlus : Int) {
        choosePickominos(pickominoTab)
        joueurPerd()
        assertEquals(pickominoValeurPlus, connect.gameState(id, key).accessiblePickos().last())
    }
    companion object {
        @JvmStatic
        fun pickominoProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(listOf(36, 35, 32, 34), 32),
                Arguments.of(listOf(36, 35, 32, 33, 34), 33),
                Arguments.of(listOf(36, 35, 34, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22), 23)
            )
        }
    }
}

class TestKeepDices {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    @Test
    // test que l'on a bien pris le dés que l'on a pris
    fun testKeepDices1() {
        // on fait choisir les vers au joueur 1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        // on vérifie que le dernier dés qui as été pris est bien le vers
        assertEquals(DICE.worm, connect.gameState(id, key).current.keptDices.last())
    }
    @Test
    // test que l'on ne peut pas prendre un dés qui n'est pas présent sur les dés proposés
    fun testKeepDices2() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        assertThrows<DiceNotInRollException> {
            connect.keepDices(id, key, DICE.d2)
        }
    }
    @Test
    // test que l'on ne peut pas prendre une valeur de dés que l'on as déja prise a un tour précédent
    fun testKeepDices3() {
        //on fait cchoisir le vers au joueur 1
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        //on vérifie qu'il ne peut plus le choisir
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        assertThrows<DiceAlreadyKeptException> {
            connect.keepDices(id, key, DICE.worm)
        }
    }
}
class TestChoiceDices {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    @Test
    // verifie que les dés choisi sont bien les dés qui sont sortit
    fun testChoiceDices1() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        assertEquals(listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1), connect.gameState(id, key).current.rolls.subList(0, 8))
    }
}
class TestRollDices {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    @Test
    // test que le nombre de dés diminus de la bonne valeur quand on en prends
    fun testRollDices1() {
        connect.rollDices(id, key)
        connect.keepDices(id, key, connect.gameState(id, key).current.rolls.last())
        val size = 8 - connect.gameState(id, key).current.keptDices.size
        assertEquals(size, connect.rollDices(id, key).size)
    }
}

class TestAccessiblePickos {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    @Test
    fun testAccessiblePickos1() {
        // On fait en sorte que le joueur 1 prenne un pickomino en l'occurence le 25.
        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d4, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.d1, DICE.d1, DICE.d4))
        connect.keepDices(id, key, DICE.worm)
        // Il prend le 25
        connect.takePickomino(id, key, 25)
        assertEquals(listOf(21, 22, 23, 24, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36), connect.gameState(id, key).accessiblePickos())
    }
}

class Status {
    val connect = Connector.factory("172.26.82.76", "8080", true)
    val identification = connect.newGame(2)
    val id = identification.first
    val key = identification.second

    //fonction qui décompose le numéro du pickomino en plusieurs valeurs de dés
    fun decompositionPickomino(nb : Int) : List<DICE> {
        val tabDice = listOf<DICE>(DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.worm)
        var remaining = nb
        var tab : List<DICE> = listOf()
        var dice = 5
        while (remaining != 0) {
            if (remaining - dice >= 0) {
                remaining -= dice
                tab += tabDice[dice-1]
            } else {
                dice -= 1
            }
        }
        return tab
    }

    //fonction qui fait prendre au jouer dont c'est le tour le pickomino désigné
    fun choosePickomino(nb : Int) {
        val tab = decompositionPickomino(nb)
        if ((nb % 5) == 0) {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
        } else {
            connect.choiceDices(id, key, tab)
            connect.keepDices(id, key, DICE.worm)
            connect.choiceDices(id, key, listOf(tab.last()))
            connect.keepDices(id, key, tab.last())
        }
        connect.takePickomino(id, key, nb)
    }

    //fonction qui fait prendre les pickomino désigné aux joueurs
    fun choosePickominos(tab : List<Int>) {
        for (i in 0 until tab.size) {
            choosePickomino(tab[i])
        }
    }

    //fonction qui fait en sorte que le joueur courant perde
    fun joueurPerd() {
        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))
        connect.keepDices(id, key, DICE.d1)
    }

    @Test
    //on test si on prends tout les pickomino le status de la partie est bien GAME_FINISHED
    fun testStatusGameFinished() {
        choosePickominos(listOf(36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21))
        assertEquals(STATUS.GAME_FINISHED, connect.gameState(id, key).current.status)
    }

    @Test
    //on test le status TAKE_PICKOMINO
    fun testStatusTakePickomino() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key,DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key,DICE.d1)
        assertEquals(STATUS.TAKE_PICKOMINO, connect.gameState(id, key).current.status)
    }

    @Test
    //on test le status KEEP_DICE
    fun testKeepDice() {
        connect.choiceDices(id, key, listOf(DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.worm))
        assertEquals(STATUS.KEEP_DICE, connect.gameState(id, key).current.status)
    }

    @Test
    //on test le status ROLL_DICE_OR_TAKE_PICKOMINO
    fun testStatusRollDiceOrTakePickomino() {
        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d1))
        connect.keepDices(id, key,DICE.worm)
        assertEquals(STATUS.ROLL_DICE_OR_TAKE_PICKOMINO, connect.gameState(id, key).current.status)
    }

    @Test
    //on test le status ROLL_DICE
    fun testRollDice() {
        connect.choiceDices(id, key, listOf(DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.d2, DICE.worm))
        connect.keepDices(id, key, DICE.d2)
        assertEquals(STATUS.ROLL_DICE, connect.gameState(id, key).current.status)
    }
}
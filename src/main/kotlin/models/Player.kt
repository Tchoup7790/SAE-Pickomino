package models

import iut.info1.pickomino.data.DICE

class Player(name:String, id:Int) {
    var name:String
        private set
    var id:Int
        private set
    var score:Int = 0
        private set
    var keptDices : List<DICE> = listOf()
    var pickominos : MutableList<Pickomino3D> = mutableListOf(Pickomino3D(0))
    init {
        this.name=name
        this.id=id
    }

    fun takePickomino(pickomino: Pickomino3D) {
        this.score += pickomino.number
        this.pickominos.add(pickomino)
    }

    fun rmPickomino(pickomino: Pickomino3D) {
        this.score -= pickomino.number
        this.pickominos.remove(pickomino)
    }

    fun givePickoTo(pickomino: Pickomino3D, player: Player) {
        this.rmPickomino(pickomino)
        player.takePickomino(pickomino)
    }
}
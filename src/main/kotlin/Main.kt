import controllers.*
import controllers.ControleurSouris3D
import controllers.ControleurSourisDe
import controllers.ControleurSourisPickomino
import javafx.application.Application
import javafx.application.ConditionalFeature
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.Stage
import listener.connectionstatus.ConnectionStatusChangeHandler
import listener.player.PlayerChangeHandler
import listener.status.StatusChangeHandler
import models.GameManager
import vues.MainVue
import kotlin.system.exitProcess

class Main: Application() {
    private val minWidth = 1280.0
    private val minHeight = 720.0

    override fun start(stage: Stage) {
        if (!Platform.isSupported(ConditionalFeature.SCENE3D)){
            val alert = Alert(Alert.AlertType.ERROR, "3D not supported on your device")
            alert.showAndWait()
            exitProcess(0)
        }

        // Background images creation + adaptation with screen size
        val backgroundIndex = ImageView(Image("file:assets/img/Illustration_main.jpg"))
        backgroundIndex.fitWidthProperty().bind(stage.widthProperty())
        backgroundIndex.fitHeightProperty().bind(stage.heightProperty())

        val backgroundMain = ImageView(Image("file:assets/img/Back_simple.jpg"))
        backgroundMain.fitWidthProperty().bind(stage.widthProperty())
        backgroundMain.fitHeightProperty().bind(stage.heightProperty())

        // Instancing main vue
        val root = MainVue(backgroundIndex, backgroundMain)

        // Instancing game manager
        val gm = GameManager()

        // Add custom handler for listeners
        gm.connectionManager.addStatusChangeListener(ConnectionStatusChangeHandler(root, gm))
        gm.addStatusChangeListener(StatusChangeHandler(root, gm))
        gm.addPlayerChangeListener(PlayerChangeHandler(root, gm))

        // Creating Music Media Player (disable music when exception is catch)
        try {
            val bgMusic = Media(javaClass.getResource("/audio/bg-music.mp3")?.toString() ?: "")
            val musicPlayer = MediaPlayer(bgMusic)
            musicPlayer.volumeProperty().bind(root.optionVue.sliderM.valueProperty())
            musicPlayer.cycleCount = MediaPlayer.INDEFINITE
            musicPlayer.play()
        } catch(e:Exception) {
            println("music not supported")
            root.optionVue.checkM.isSelected = false
            root.optionVue.checkM.isDisable = true
            root.optionVue.sliderM.isDisable = true
        }

        // Set controllers
        root.setControllerBtnView(ControllerChangeView(root), root.indexVue.playBtn)
        root.setControllerBtnView(ControllerChangeView(root), root.indexVue.optionBtn)
        root.setControllerBtnView(ControllerChangeView(root), root.indexVue.rulesBtn)
        root.setControllerBtnView(ControllerChangeView(root, stage), root.indexVue.exitBtn)

        root.setControllerBtnView(ControllerButtonView(), root.indexVue.playBtn)
        root.setControllerBtnView(ControllerButtonView(), root.indexVue.optionBtn)
        root.setControllerBtnView(ControllerButtonView(), root.indexVue.rulesBtn)
        root.setControllerBtnView(ControllerButtonView(), root.indexVue.exitBtn)

        root.setControllerBtnView(ControllerButtonView(), root.rulesVue.rightBtn)
        root.setControllerBtnView(ControllerButtonView(), root.rulesVue.leftBtn)
        root.setControllerBtnView(ControllerButtonView(), root.rulesVue.backBtn)

        root.setControllerBtnView(ControllerRuleReader(root), root.rulesVue.rightBtn)
        root.setControllerBtnView(ControllerRuleReader(root), root.rulesVue.leftBtn)
        root.setControllerBtnView(ControllerRuleReader(root), root.rulesVue.backBtn)

        root.setControllerBtnView(ControllerButtonView(), root.optionVue.backBtn)
        root.setControllerBtnView(ControllerOptionsChange(root), root.optionVue.backBtn)
        root.setControllerBtnView(ControllerOptionsChange(root), root.optionVue.checkM)
        root.setControllerBtnView(ControllerOptionsChange(root), root.optionVue.checkFX)

        root.setControllerBtnView(ControllerButtonView(), root.playerNumberVue.backBtn)
        root.setControllerBtnView(ControllerButtonView(), root.playerNumberVue.gameStart)
        root.setControllerBtnView(ControllerPlayerNumber(root, gm), root.playerNumberVue.gameStart)
        root.setControllerBtnView(ControllerPlayerNumber(root, gm), root.playerNumberVue.backBtn)
        root.setControllerBtnView(ControllerPlayerNumber(root, gm), root.playerNumberVue.radio2)
        root.setControllerBtnView(ControllerPlayerNumber(root, gm), root.playerNumberVue.radio3)
        root.setControllerBtnView(ControllerPlayerNumber(root, gm), root.playerNumberVue.radio4)

        root.setControllerBtnView(ControllerButtonView(), root.GameViewVue.btn)
        root.fixeControllerDe(ControleurSouris3D(root, gm))
        root.fixeControllerHoverDe(ControleurSourisDe(root, gm))
        root.fixeControllerHoverPickomino(ControleurSourisPickomino(root, gm))

        root.setControllerBtnView(ControllerChangeView(root), root.end.backBtn)
        root.setControllerBtnView(ControllerButtonView(), root.end.backBtn)

        // Creating scene and configuring the stage
        val scene = Scene(root)
        stage.minWidth = this.minWidth
        stage.minHeight = this.minHeight
        stage.title="Pickomino: The Game"
        stage.scene=scene
        stage.show()
    }
}


fun main() {
    Application.launch(Main::class.java)
}
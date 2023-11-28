package vues

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.Slider
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class Options(font: Font):BorderPane() {
    private val font = font
    val backBtn = Button("Retour")
    val title = Label("Options")
    val checkFX = CheckBox("Volume FX")
    val checkM = CheckBox("Volume Musique")
    private val labelFXS = Label("Volume FX :")
    private val labelMS = Label("Volume Musique :")
    val sliderVFX = Slider()
    val sliderM = Slider()
    init{
        val hBoxBottom = HBox()
        val hBoxTop = HBox()
        val centerVBox = VBox()
        val checkHBox = HBox()
        val sliderHBox = HBox()

        title.style = "-fx-font-family:'${font.family}' ; -fx-font-size: 60;-fx-text-fill: #F6C00C;"

        labelMS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        checkFX.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        labelFXS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        checkM.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"

        checkHBox.children.addAll(checkM, checkFX)
        checkHBox.alignment = Pos.CENTER
        checkHBox.spacing = 100.0

        sliderM.isDisable = false
        sliderVFX.isDisable = false

        checkM.isSelected = true
        checkFX.isSelected = true

        sliderHBox.children.addAll(labelMS, sliderM, labelFXS, sliderVFX)
        sliderHBox.alignment = Pos.CENTER
        sliderHBox.spacing = 20.0

        hBoxTop.children.add(title)
        hBoxTop.padding = Insets(40.0, 0.0, 0.0, 0.0)
        hBoxTop.alignment = Pos.CENTER

        backBtn.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
        backBtn.setPrefSize(200.0, 50.0)
        backBtn.alignment = Pos.CENTER

        hBoxBottom.children.add(backBtn)
        hBoxBottom.alignment = Pos.CENTER
        hBoxBottom.padding = Insets(0.0, 0.0, 40.0, 0.0)

        centerVBox.children.addAll(checkHBox, sliderHBox)
        centerVBox.spacing = 100.0
        centerVBox.translateY = -50.0
        centerVBox.alignment = Pos.CENTER

        this.bottom = hBoxBottom
        this.center = centerVBox
        this.top = hBoxTop
    }

    fun checkBox(){
        if(!this.checkM.isSelected){
            labelMS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #D9D9D9;"
            sliderM.isDisable = true
        }else{
            labelMS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            sliderM.isDisable = false
        }
        if(!this.checkFX.isSelected){
            labelFXS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #D9D9D9;"
            sliderVFX.isDisable = true
        }else{
            labelFXS.style = "-fx-background-color: transparent; -fx-border-width: 0;-fx-font-family:'${font.family}';-fx-font-size: 22;-fx-text-fill: #F5F5F5;"
            sliderVFX.isDisable = false
        }
    }
}
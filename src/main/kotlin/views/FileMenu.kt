package main.kotlin.views

import javax.inject.Inject

import javafx.fxml.FXML
import javafx.scene.control.Menu
import javafx.stage.Stage

abstract class FileMenu: Menu() {
    @Inject
    lateinit var pStage: Stage

    init {

    }

    @FXML
    fun new() {}

    @FXML
    fun open() {}

    @FXML
    fun save() {}

    @FXML
    fun print() {}
}
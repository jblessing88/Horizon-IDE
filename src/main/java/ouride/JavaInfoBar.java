package main.java.ouride;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class JavaInfoBar extends InfoBar{
	
	public JavaInfoBar(OurIDE ide){
		this.ide = ide;
		guideButtons = new Button[]{new Button("Class"), new Button("Method"), new Button("If/Else"), new Button("Switch"), new Button("Loop"), new Button("Variable"), new Button("Output"), new Button("Equation"), new Button("UML Diagram")};
		guideInfoPanels = new InfoPanel[]{new JavaClassInfoPanel(ide, this), new JavaMethodInfoPanel(ide, this), new JavaIfElseInfoPanel(ide, this), new JavaSwitchInfoPanel(ide, this), new JavaLoopInfoPanel(ide, this), new JavaVariableInfoPanel(ide, this), new JavaOutputInfoPanel(ide, this), new JavaEquationInfoPanel(ide, this), new JavaUMLInfoPanel(ide,this)};
		guideBar = new ToolBar();
		for(int i = 0; i < guideButtons.length; i++){
			guideButtons[i].setOnAction(this);
		}
		this.getChildren().addAll(guideBar);
	}
}
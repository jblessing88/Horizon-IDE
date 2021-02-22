package main.java.ouride;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class JavaUMLInfoPanel extends InfoPanel {

	JavaUMLInfoPanel(OurIDE ide, InfoBar bar){
		this.ide = ide;
		this.bar = bar;
		super.setStyle("-fx-border-color:rgb(0,0,0);");
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		Label temp = new Label("Coming Soon");
		temp.setPrefSize(700, 100);
		temp.setAlignment(Pos.CENTER);
		super.add(temp,0,0);
		ide.addInfoPanel(this);
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

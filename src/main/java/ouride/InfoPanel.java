package main.java.ouride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;

public abstract class InfoPanel extends GridPane implements EventHandler<ActionEvent> {
	protected TextFlow info;
	protected Button add, cancel;
	protected Label x;
	protected OurIDE ide;
	protected ScrollPane pane;
	protected OurTab activeTab;
	protected InfoBar bar;
	
	public abstract void load();
	public abstract void handle(ActionEvent e);
	
	public void close(){
		if(activeTab != null){
			activeTab.cancel();
		}
		//remove InfoPanel code
		ide.removeInfoPanel();
	}
}

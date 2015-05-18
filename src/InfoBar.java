package ouride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToolBar;

public abstract class InfoBar extends VBox implements EventHandler<ActionEvent> {
	protected Button[] guideButtons;
	protected InfoPanel[] guideInfoPanels;
	protected ToolBar guideBar;
	protected OurIDE ide;
	
	public void generateButtons(){
		guideBar.getItems().addAll(guideButtons);
	}

	public void handle(ActionEvent e){
		for(int i = 0; i < guideButtons.length; i++){
			if(e.getSource() == guideButtons[i]){
				guideInfoPanels[i].load();
				break;
			}
		}
	}

	public void add(InfoPanel info){
		if(this.getChildren().contains(info)){
			remove();
		}
		else{
			remove();
			this.getChildren().addAll(info);
		}
	}

	public void remove(){
		this.getChildren().retainAll(guideBar);
	}
}


package ouride;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JavaSwitchInfoPanel extends InfoPanel {
		private Label defaultLabel, casesLabel;
		private ComboBox<Integer> cases;
		private CheckBox defaultYN;
		public JavaSwitchInfoPanel(OurIDE ide, InfoBar bar){
			this.bar = bar;
			this.ide = ide;
			activeTab = null;
			//add button
			add = new Button("Add");
			add.setPrefSize(75, 25);
			add.setOnAction(this);
			//cancel button
			cancel = new Button("Cancel");
			cancel.setPrefSize(75,25);
			cancel.setOnAction(this);
			//x button
			x = new Label("\u2715");
			x.setPrefSize(20,30);
			x.setOnMouseClicked(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					close();
				}
				
			});
			x.setStyle("-fx-cursor:hand;");
			//default input
			defaultLabel = new Label("Default? y/n:");
			defaultLabel.setPrefSize(100,25);
			defaultYN = new CheckBox();
			defaultYN.setPrefSize(75,25);
			//case input
			casesLabel = new Label("Number of Cases:");
			casesLabel.setPrefSize(100,25);
			cases = new ComboBox<Integer>();
			cases.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
			cases.setPrefSize(75,25);
			cases.getSelectionModel().selectFirst();
			cases.setEditable(true);
			//description
			ArrayList<Text> text = new ArrayList<Text>();
			info = new TextFlow();
			//file read code here
			text.add(new Text("An "));
			Text temp = new Text("switch");
			temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
			text.add(temp);
			text.add(new Text(" statement is similar to an if/else statement, however the key difference is that while if/else statements only lead to one action being done by the program, a switch statement can have many different possible actions done.\n\nSwitch statements have a certain "));
			temp = new Text("number of cases");
			temp.setStyle("-fx-font-weight:bold;");
			text.add(temp);
			text.add(new Text(" that get tested against some past variable. In a switch statement, the variable gets compared to every case (each case has some sort of constant variable). If the case matches the variable, that case is the action that will be done by the program, similar to an if/else statement.\n\n"));
			temp = new Text("Default");
			temp.setStyle("-fx-font-weight:bold;");
			text.add(temp);
			text.add(new Text(" is a special case that works identical to an “else” statement, where if all the other cases do not succeed, the default case becomes the one being done by the program."));
			info.getChildren().addAll(text);
		    pane = new ScrollPane();
			pane.setFitToWidth(true);
			pane.setContent(info);
			pane.setPrefHeight(100);
			super.setPrefHeight(140);
			RowConstraints row1 = new RowConstraints();
			RowConstraints row2 = new RowConstraints();
			RowConstraints row3 = new RowConstraints();
			RowConstraints row4 = new RowConstraints();
			RowConstraints buffer = new RowConstraints();
			ColumnConstraints column1 = new ColumnConstraints();
			ColumnConstraints column2 = new ColumnConstraints();
			ColumnConstraints column3 = new ColumnConstraints();
			ColumnConstraints column4 = new ColumnConstraints();
			ColumnConstraints column5 = new ColumnConstraints();
			ColumnConstraints column6 = new ColumnConstraints();
			ColumnConstraints buffer1 = new ColumnConstraints();
			buffer.setPrefHeight(10);
			buffer1.setPrefWidth(10);
			row1.setPrefHeight(x.getPrefHeight());
			row4.setPrefHeight(add.getPrefHeight());
			row2.setPrefHeight(cases.getPrefHeight());
			row3.setPrefHeight(super.getPrefHeight()-(row1.getPrefHeight()+row2.getPrefHeight()+row4.getPrefHeight())-20);
			column6.setPrefWidth(x.getPrefWidth());
			column5.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
			column4.setPrefWidth(cancel.getPrefWidth());
			column3.setPrefWidth(cases.getPrefWidth());
			column2.setPrefWidth(casesLabel.getPrefWidth()+20);
			column1.setPrefWidth(300);
			//GridPane.setHgrow(pane, Priority.SOMETIMES);
			//GridPane.setHgrow(cases,Priority.SOMETIMES);
			//GridPane.setHgrow(casesLabel,Priority.SOMETIMES);
			buffer1.setHgrow(Priority.ALWAYS);
			column2.setHalignment(HPos.RIGHT);
			row2.setValignment(VPos.CENTER);
			super.getColumnConstraints().setAll(buffer1,column1,column2,column3,column4,column5,column6,buffer1);
			super.getRowConstraints().setAll(buffer,row1,row2,row3,buffer);
			pane.setPrefWidth(column1.getPrefWidth());
			super.setStyle("-fx-border-color:rgb(0,0,0);");
		}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,4);
		super.add(pane,1,1);
		super.add(casesLabel,2,2);
		super.add(cases,3,2);
		super.add(defaultLabel,2,3);
		super.add(defaultYN,3,3);
		super.add(cancel,4,4);
		GridPane.setColumnSpan(add,2);
		super.add(add,5,4);
		super.add(x,6,1);
		
		//code to add Panel to guide area
		ide.addInfoPanel(this);
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==add){
			cases.setStyle("-fx-border-radius: 1px");
			if(activeTab!=null){
				activeTab.cancel();
			}
			activeTab = ide.lastActiveTab();
			String text = "switch( ){\n";
			try{
				int a = Integer.parseInt(cases.getValue()+"");
				if(a<0){
					throw new NumberFormatException();
				}
				else{
					for(int i = 0; i < a; i++){
						text = text + "case : \n\tbreak;\n";
					}
					if(defaultYN.isSelected()){
						text = text + "default: \n\tbreak;\n";
					}
					text = text + "}";
					activeTab.oneClick(text);
				}
			}
			catch(NumberFormatException ex){
				//use popover for error display
				cases.setStyle("-fx-border-color: red");
			}
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

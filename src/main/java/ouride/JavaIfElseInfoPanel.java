package main.java.ouride;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JavaIfElseInfoPanel extends InfoPanel {
	private ChoiceBox<String> conditional;
	private Label conditionalLabel;

	public JavaIfElseInfoPanel(OurIDE ide, InfoBar bar){
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
		//loop input
		conditional = new ChoiceBox<String>();
		conditional.getItems().addAll("if","else if","else");
		conditional.getSelectionModel().selectFirst();
		conditional.setPrefSize(75, 25);
		conditionalLabel = new Label("Conditional Type:  ");
		conditionalLabel.setPrefSize(100, 25);
		conditionalLabel.setAlignment(Pos.BASELINE_RIGHT);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("An "));
		Text temp = new Text("if/else");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" statement is a way for a program to carry out certain assignments based off of particular conditions. There are three conditional types: if, else if, and else.\n\nAn \""));
		temp = new Text("if");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("\" conditional type sets the first condition of a set of conditions. So by using an \"if\" statement, the code will carry out an action if it agrees with the conditions that are in the parenthesis.\n\nAn \""));
		temp = new Text("else if");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("\" is the second set of conditions that a program must follow in order to carry out a certain action. There can be multiple \"else if\" statements in a single set of conditions. You can only use an \"else if\" statement if it is immediately after an \"if\" statement or another \"else if\" statement.\n\nAn \""));
		temp = new Text("else");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("\" statement is the last set of conditions that tells the program to do a certain action if it does not agree with the conditions of the \"if\" or \"else if\" statement.\n\nHere is a psuedocode example:\n\tIf  x = 2, do action A.\n\tElse if  x > 2, do action B.\n\tElse, do action C."));
		info.getChildren().addAll(text);
	    pane = new ScrollPane();
		pane.setFitToWidth(true);
		pane.setContent(info);
		pane.setPrefHeight(100);
		super.setPrefHeight(140);
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();
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
		row2.setPrefHeight(super.getPrefHeight()-(x.getPrefHeight()+add.getPrefHeight())-20);
		row3.setPrefHeight(add.getPrefHeight());
		column6.setPrefWidth(x.getPrefWidth());
		column5.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column4.setPrefWidth(cancel.getPrefWidth());
		column3.setPrefWidth(conditional.getPrefWidth());
		column2.setPrefWidth(conditionalLabel.getPrefWidth());
		column1.setPrefWidth((super.getPrefWidth()-(column5.getPrefWidth()-column4.getPrefWidth()-column3.getPrefWidth()-column2.getPrefWidth()-column6.getPrefWidth())));
		//GridPane.setHgrow(pane, Priority.SOMETIMES);
		//GridPane.setHgrow(conditional, Priority.SOMETIMES);
		//GridPane.setHgrow(conditionalLabel, Priority.SOMETIMES);
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
		
		GridPane.setRowSpan(pane,3);
		super.add(pane,1,1);
		super.add(conditionalLabel,2,2);
		super.add(conditional,3,2);
		super.add(cancel,4,3);
		GridPane.setColumnSpan(add,2);
		super.add(add,5,3);
		super.add(x,6,1);
		
		//code to add Panel to guide area
		ide.addInfoPanel(this);
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==add){
			if(activeTab!=null){
				activeTab.cancel();
			}
			activeTab = ide.lastActiveTab();
			String first = "";
			String last = "";
			if(conditional.getValue()=="if"){
				first = "if( ){\n";
				last = "\n}";
			}
			else if(conditional.getValue()=="else if"){
				first = "else if( ) {\n";
				last = "\n}";
			}
			else{
				first = "else {\n";
				last = "\n}";
			}
			activeTab.twoClick(first, last);
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

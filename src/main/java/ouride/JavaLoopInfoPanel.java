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

public class JavaLoopInfoPanel extends InfoPanel {
	private ChoiceBox<String> loop;
	private Label loopLabel;

	public JavaLoopInfoPanel(OurIDE ide, InfoBar bar){
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
		loop = new ChoiceBox<String>();
		loop.getItems().addAll("for","while","do while");
		loop.getSelectionModel().selectFirst();
		loop.setPrefSize(75, 25);
		loopLabel = new Label("Loop Type:  ");
		loopLabel.setPrefSize(100, 25);
		loopLabel.setAlignment(Pos.BASELINE_RIGHT);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("A "));
		Text temp = new Text("loop");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" is a way of repeating lines of code more than one time. The chunk of code that is contained within the loop will be executed again and again until the condition required by the loop is met. For example, you could set up a loop to print out all even numbers between 1-10. The code that gets executed each time the loop is run will print out an even number, with each time the loop goes through, the next even number will print out. This goes on until the number 10 is printed, meaning the loop has reached the end.\n\nA "));
		temp = new Text("for loop");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" allows you to write a loop that needs to be executed a specific number of times. A for loop usually involves a counter of some kind. It is important to remember that a for loop is best used for when you know how many times a particular task needs to be repeated. The for loop takes three arguments, separated by semi colons. The first argument is the what to start the counter (example: int x = 0;), the second argument is the exit condition (x < 10;), and the third argument is the incrementation, meaning the counter eithers goes up or down (x = x +1;).\n\nPutting this together, we can make a for loop to run 3 times:\n\tfor(int x = 0; x < 3; x= x+1)\nSo the loop starts the first time with the counter (x) at 0, and it will end when the counter reaches 3, and each time the loop goes through, the counter is added by 1.\n\nA "));
		temp = new Text("while loop");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" allows you to repeat a task until a certain condition is met. These are used for when you do not know how many times the loop needs to go through, but you know when the reason for the repetition to stop.\n\nA psuedocode example of a while loop could be :\n\tWhile user has not pressed ‘q’, do action A\n\nA "));
		temp = new Text("do while loop");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" works the same as a while loop, the only difference is a do while loop is guaranteed to execute one time, but a while loop does not need to execute at all. This is because a while loop checks the condition at the beginning of the loop, whereas a do while loop checks the condition at the end of the loop.\n\nA psuedocode example of a do while loop could be:\n\tDo action A while user has not pressed ‘q’"));
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
		column3.setPrefWidth(loop.getPrefWidth());
		column2.setPrefWidth(loopLabel.getPrefWidth());
		column1.setPrefWidth((super.getPrefWidth()-(column5.getPrefWidth()-column4.getPrefWidth()-column3.getPrefWidth()-column2.getPrefWidth()-column6.getPrefWidth())));
		//GridPane.setHgrow(pane, Priority.SOMETIMES);
		//GridPane.setHgrow(loop, Priority.SOMETIMES);
		//GridPane.setHgrow(loopLabel, Priority.SOMETIMES);
		buffer1.setHgrow(Priority.ALWAYS);
		column2.setHalignment(HPos.RIGHT);
		row2.setValignment(VPos.CENTER);
		super.getColumnConstraints().setAll(buffer1,column1,column2,column3,column4,column5,column6,buffer1);
		super.getRowConstraints().setAll(buffer,row1,row2,row3,buffer);
		pane.setPrefWidth(column1.getPrefWidth());
		super.setStyle("-fx-border-color: rgb(0,0,0);");
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,3);
		super.add(pane,1,1);
		super.add(loopLabel,2,2);
		super.add(loop,3,2);
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
			if(loop.getValue()=="for"){
				first = "for( ; ; ){";
				last = "}";
			}
			else if(loop.getValue()=="while"){
				first = "while( ){";
				last = "}";
			}
			else{
				first = "do {";
				last = "}while( );";
			}
			activeTab.twoClick(first, last);
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

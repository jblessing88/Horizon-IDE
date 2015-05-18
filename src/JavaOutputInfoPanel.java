package ouride;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JavaOutputInfoPanel extends InfoPanel {

	private RadioButton print, println;
	private ToggleGroup prints;
	
	public JavaOutputInfoPanel(OurIDE ide, InfoBar bar){
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
		//print selection
		print = new RadioButton("Print");
		println = new RadioButton("Println");
		prints = new ToggleGroup();
		print.setToggleGroup(prints);
		println.setToggleGroup(prints);
		print.setSelected(true);
		print.setPrefSize(100, 25);
		println.setPrefSize(100,25);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("An "));
		Text temp = new Text("output");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" is what shows up to you when the program has run. An output can be made by either using a print statement or a println (print line) statement.\n\nA "));
		temp = new Text("print");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" statement tells the program what to show you once the code has run in the output area, or if you run the program in a command prompt, it will show up in the terminal or console window.\nAn example of this would be:\n\tprint(\"Hello World!\");\nAfter running this code, the statement Hello World! will show up as the output (what gets shown to you either in the output area or terminal or console window).\n\nA "));
		temp = new Text("println");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" statement is the same as a print statement except whereas a print statement will print everything you tell it to, a println statement will separate the outputs by creating new lines.\nAn example of this would be:\n\tprintln(\"Hello\");\n\tprint(\"World\");\nAfter running this code, the output would look like this:\nHello\nWorld"));
		info.getChildren().addAll(text);
	    pane = new ScrollPane();
		pane.setFitToWidth(true);
		pane.setContent(info);
		pane.setPrefHeight(100);
		
		/*ArrayList<ColumnConstraints> gridH = new ArrayList<ColumnConstraints>();
		ArrayList<RowConstraints> gridV = new ArrayList<RowConstraints>();
		for(int i = 0; i < 20; i++){
			ColumnConstraints c = new ColumnConstraints();
			RowConstraints r = new RowConstraints();
			c.setPercentWidth(5);
			r.setPercentHeight(5);
			gridH.add(c);
			gridV.add(r);
		}
		super.getColumnConstraints().setAll(gridH);
		super.getRowConstraints().setAll(gridV);*/
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
		ColumnConstraints buffer1 = new ColumnConstraints();
		buffer.setPrefHeight(10);
		buffer1.setPrefWidth(10);
		row1.setPrefHeight(x.getPrefHeight());
		row4.setPrefHeight(add.getPrefHeight());
		row2.setPrefHeight(print.getPrefHeight());
		row3.setPrefHeight(super.getPrefHeight()-(row1.getPrefHeight()+row2.getPrefHeight()+row4.getPrefHeight())-20);
		column5.setPrefWidth(x.getPrefWidth());
		column4.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column3.setPrefWidth(cancel.getPrefWidth());
		column2.setPrefWidth(println.getPrefWidth()+40);
		column1.setPrefWidth((super.getPrefWidth()-(column5.getPrefWidth()-column4.getPrefWidth()-column3.getPrefWidth()-column2.getPrefWidth()-column1.getPrefWidth())));
		//GridPane.setHgrow(pane, Priority.SOMETIMES);
		//GridPane.setHgrow(print, Priority.SOMETIMES);
		buffer1.setHgrow(Priority.ALWAYS);
		column2.setHalignment(HPos.CENTER);
		super.getColumnConstraints().setAll(buffer1,column1,column2,column3,column4,column5,buffer1);
		super.getRowConstraints().setAll(buffer,row1,row2,row3,row4,buffer);
		pane.setPrefWidth(column1.getPrefWidth());
		super.setStyle("-fx-border-color: rgb(0,0,0);");
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,4);
		super.add(pane,1,1);
		super.add(print,2,2);
		super.add(println,2,3);
		super.add(cancel,3,4);
		GridPane.setColumnSpan(add,2);
		super.add(add,4,4);
		super.add(x,5,1);
		/*GridPane.setRowSpan(pane, 20);
		GridPane.setColumnSpan(pane,9);
		super.add(pane,0,0);
		
		GridPane.setRowSpan(print, 4);
		GridPane.setColumnSpan(print,4);
		super.add(print,11,4);
		
		GridPane.setRowSpan(println,4);
		GridPane.setColumnSpan(println,4);
		super.add(println,11,11);
		
		GridPane.setRowSpan(cancel,3);
		GridPane.setColumnSpan(cancel,2);
		super.add(cancel,16,16);
		
		GridPane.setRowSpan(x,2);
		GridPane.setColumnSpan(x,2);
		super.add(x,19,1);
		
		GridPane.setRowSpan(add,3);
		GridPane.setColumnSpan(add,2);
		super.add(add,18,16);*/
		
		//code to add Panel to Guide Area
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
			if(print.isSelected())
				activeTab.oneClick("System.out.print(\"\");");
			else 
				activeTab.oneClick("System.out.println(\"\");");
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

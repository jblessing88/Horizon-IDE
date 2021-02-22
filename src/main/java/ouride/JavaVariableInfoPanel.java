package main.java.ouride;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JavaVariableInfoPanel extends InfoPanel {
	
	private ChoiceBox<String> type;
	private Label typeLabel;
	private CheckBox array;
	private Label arrayLabel;
	private TextField name;
	private Label nameLabel;
	private Label scopeLabel;
	private ChoiceBox<String> scope;

	public JavaVariableInfoPanel(OurIDE ide, InfoBar bar){
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
		//scope input
		scope = new ChoiceBox<String>();
		scope.getItems().addAll("default","private","public","protected");
		scope.getSelectionModel().selectFirst();
		scope.setPrefSize(75,25);
		scopeLabel = new Label("Variable Scope:");
		scopeLabel.setPrefSize(100,25);
		//type input
		type = new ChoiceBox<String>();
		type.getItems().addAll("int","double","boolean","String","char","float","long","short","byte","Other");
		type.getSelectionModel().selectFirst();
		type.setPrefSize(75,25);
		typeLabel = new Label("Variable Type:");
		typeLabel.setPrefSize(100, 25);
		//name input
		name = new TextField();
		//name.setPrefSize(75, 25);
		name.setMaxSize(75, 25);
		nameLabel = new Label("Variable Name:");
		nameLabel.setPrefSize(100, 25);
		//array input
		array = new CheckBox();
		array.setPrefSize(75,25);
		arrayLabel = new Label("Is Array? y/n:");
		arrayLabel.setPrefSize(100, 25);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("A "));
		Text temp = new Text("variable");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" provides you with a named storage that your programs can manipulate. Each variable has a specific type, which determines the size and layout of the variable’s memory, the range of values that can be stored within that memory, and the set of operations that can be applied to the variable. Remember, you must declare all variables before they can be used:\n\n\tdata type variable [=value][, variable [=value];\n\nTo declare multiple variables, separate each with a comma. The following declares three integers named a, b, and c:\n\n\tint a, b, c;\n\n"));
		temp = new Text("Variable scope");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" determines the restrictions of each variable. "));
		temp = new Text("Public");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables can be seen by the class it is in, and by any class that uses the class. "));
		temp = new Text("Protected");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables may only be seen by the class that owns the variable, the package the class belongs to, and any of its subclasses (anything that extends it). "));
		temp = new Text("Default");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" (no modifier) can be seen by the class that owns it, or any packages it belongs to. "));
		temp = new Text("Private");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables can only be seen the class it is in.\n\n"));
		temp = new Text("Variable type");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" describes the kind of variable.\n\t"));
		temp = new Text("int");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are integer values (whole numbers between -2^31 and 2^31-1).\n\t\tExample: int i = 254\n\t"));
		temp = new Text("double");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are basically numbers that can contain decimal values.\n\t\tExample: double a = 23.89\n\t"));
		temp = new Text("boolean");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are essentially a true or false statement.\n\t\tExample: boolean one = true\n\t"));
		temp = new Text("String");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are statements that can contain words, numbers or symbols.\n\t\tExample: string a = “My dog turned 3 years old!”\n\t"));
		temp = new Text("char");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are any individual characters on a keyboard.\n\t\tExample: char letterA = ‘A’\n\t"));
		temp = new Text("float");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are numbers with a fractional part (similar to a double, except it can be a much larger number).\n\t\tExample: float a1 = 23534.083740283\n\t"));
		temp = new Text("long");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are numbers that can be much larger or smaller than a regular int variable.\n\t\tExample: long a = 10000000000, long b = -20000000000\n\t"));
		temp = new Text("short");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are numbers between -32,768 and 32,767.\n\t\tExample: short s = 10000, short r = -20000\n\t"));
		temp = new Text("byte");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are numbers between -128 and 127.\n\t\tExample: byte a = 100, byte b = -50\n\t"));
		temp = new Text("Other");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" variables are objects that act as a placeholder for a variable that is a type of any kind of class. You can replace the object with any kind of class you want it to be.\n\n"));
		temp = new Text("Variable name");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" is simply what you decide to name the variable. For example, if you wanted to store the integer 100, you can store it as int a. With naming the variables, you do not need to repeatedly put in the same values whenever you want to use them, just call the name you gave the variable.\n\n"));
		temp = new Text("Is Array");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" is asking you if want to put the values into a container object that holds each number of values of a single type.\n An psuedocode example of this would be:\n\tAn array of 5 elements stores the numbers 2, 4, 6, 8, and 10.\n\tThe first element of the array is the number 2, the second element is the number 4, and so on."));
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
		row2.setPrefHeight((super.getPrefHeight()-(row1.getPrefHeight()-row4.getPrefHeight()))/2-40);
		row3.setPrefHeight(row2.getPrefHeight());
		column6.setPrefWidth(x.getPrefWidth());
		column5.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column4.setPrefWidth(cancel.getPrefWidth());
		column3.setPrefWidth(array.getPrefWidth());
		column2.setPrefWidth(arrayLabel.getPrefWidth()+20);
		column1.setPrefWidth(300);
		//GridPane.setHgrow(pane, Priority.SOMETIMES);
		//GridPane.setHgrow(arrayLabel,Priority.SOMETIMES);
		//GridPane.setHgrow(array,Priority.SOMETIMES);
		buffer1.setHgrow(Priority.ALWAYS);
		column2.setHalignment(HPos.RIGHT);
		super.getColumnConstraints().setAll(buffer1,column1,column2,column3,column4,column5,column6,buffer1);
		super.getRowConstraints().setAll(buffer,row1,row2,row3,row4,buffer);
		pane.setPrefWidth(column1.getPercentWidth());
		super.setStyle("-fx-border-color: rgb(0,0,0);");
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
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,4);
		super.add(pane,1,1);
		super.add(scopeLabel,2,1);
		super.add(scope,3,1);
		super.add(typeLabel,2,2);
		super.add(type,3,2);
		super.add(nameLabel,2,3);
		super.add(name,3,3);
		super.add(arrayLabel,2,4);
		super.add(array,3,4);
		super.add(x,6,1);
		super.add(cancel,4,4);
		GridPane.setColumnSpan(add,2);
		super.add(add,5,4);
		/*GridPane.setRowSpan(pane, 20);
		GridPane.setColumnSpan(pane,9);
		super.add(pane,0,0);
		
		GridPane.setRowSpan(scopeLabel, 3);
		GridPane.setColumnSpan(scopeLabel,4);
		super.add(scopeLabel,10,1);
		GridPane.setRowSpan(scope, 3);
		GridPane.setColumnSpan(scope,4);
		super.add(scope,13,1);
		
		GridPane.setRowSpan(typeLabel, 3);
		GridPane.setColumnSpan(typeLabel,4);
		super.add(typeLabel,10,5);
		GridPane.setRowSpan(type, 3);
		GridPane.setColumnSpan(type,4);
		super.add(type,13,5);
		
		GridPane.setRowSpan(nameLabel, 3);
		GridPane.setColumnSpan(nameLabel,4);
		super.add(nameLabel,10,9);
		GridPane.setRowSpan(name, 3);
		GridPane.setColumnSpan(name,4);
		super.add(name,13,9);
		
		GridPane.setRowSpan(arrayLabel, 3);
		GridPane.setColumnSpan(arrayLabel,4);
		super.add(arrayLabel,10,13);
		GridPane.setRowSpan(array, 3);
		GridPane.setColumnSpan(array,4);
		super.add(array,13,13);

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
			String text = "";
			if(scope.getValue() != "default"){
				text += scope.getValue() + " ";
			}
			if(type.getValue() != "Other"){
				text += type.getValue();
			}
			else{
				text += "Object";
			}
			if(array.isSelected()){
				text += "[]";
			}
			if(name.getText()==""){
				text += " " + name.getText();
			}
			else{
				text += " temp";
			}
			activeTab.oneClick(text);
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

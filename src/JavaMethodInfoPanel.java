package ouride;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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

public class JavaMethodInfoPanel extends InfoPanel {
	
	private ChoiceBox<String> type;
	private Label typeLabel;
	private CheckBox array;
	private Label arrayLabel;
	private TextField name;
	private Label nameLabel;
	private Label scopeLabel;
	private ChoiceBox<String> scope;
	private Label abstractLabel;
	private CheckBox abstractYN;
	private ComboBox<Integer> parameters;
	private Label parameterLabel;

	public JavaMethodInfoPanel(OurIDE ide, InfoBar bar){
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
		scope.getItems().addAll("public","private","protected");
		scope.getSelectionModel().selectFirst();
		scope.setPrefSize(100,25);
		scopeLabel = new Label("Method Scope:");
		scopeLabel.setPrefSize(125,25);
		//type input
		type = new ChoiceBox<String>();
		type.getItems().addAll("void","byte","short","int","long","double","float","boolean","char","String","Other");
		type.setPrefSize(100,25);
		typeLabel = new Label("Return Type:");
		typeLabel.setPrefSize(125, 25);
		type.getSelectionModel().selectFirst();
		//abstract input
		abstractYN = new CheckBox();
		abstractYN.setPrefSize(100,25);
		abstractLabel = new Label("Abstract? y/n:");
		abstractLabel.setPrefSize(125, 25);
		//name input
		name = new TextField();
		//name.setPrefSize(75, 25);
		name.setMaxSize(100, 25);
		nameLabel = new Label("Method Name:");
		nameLabel.setPrefSize(125, 25);
		//array input
		array = new CheckBox();
		array.setPrefSize(100,25);
		arrayLabel = new Label("Is Array? y/n:");
		arrayLabel.setPrefSize(125, 25);
		//parameter input
		parameterLabel = new Label("Number of Parameters:");
		parameterLabel.setPrefSize(125, 25);
		parameters = new ComboBox<Integer>();
		parameters.getItems().addAll(0,1,2,3,4,5);
		parameters.getSelectionModel().selectFirst();
		parameters.setEditable(true);
		parameters.setPrefSize(100, 25);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("A "));
		Text temp = new Text("method");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" is a group of statements that are put together in order to perform an operation or action. The only required elements of a method declaration are the method’s return type, name, a pair of parentheses, (), and a body between braces, {}.The scope of a method determines the access restrictions for that method. There are three possible scopes:\n\t"));
		temp = new Text("Public");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" methods can be seen or used by the class it is in, and by any class that uses the class.\n\t"));
		temp = new Text("Private");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" methods can only be seen or used by the class it is in.\n\t"));
		temp = new Text("Protected");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" methods can only be seen or used by the class that owns the method, the package the class belongs to, and any of its subclasses.\n\nThe"));
		temp = new Text(" return type");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" is the data type for the value returned by the method, or "));
		temp = new Text("void");
		temp.setStyle("-fx-font-style:italic;");
		text.add(temp);
		text.add(new Text(" if the method does not return a value. The are multiple return types:\n\t"));
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
		temp = new Text("Is return type Array");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text(" is asking you if the values being returned are grouped together in an array, meaning there are multiple values of the same type in one container object.\n\n"));
		temp = new Text("Number of parameter");
		temp.setStyle("-fx-font-weight:bold");
		text.add(temp);
		text.add(new Text(" is asking you for the number of parameters you would like to use in the method. Parameters are the variables that are listed as part of a method declaration. Each parameter must have a unique name and defined data type."));
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
		ColumnConstraints column7 = new ColumnConstraints();
		ColumnConstraints column8 = new ColumnConstraints();
		ColumnConstraints buffer1 = new ColumnConstraints();
		buffer.setPrefHeight(20);
		buffer1.setPrefWidth(16);
		row1.setPrefHeight(x.getPrefHeight());
		row4.setPrefHeight(add.getPrefHeight());
		row2.setPrefHeight((super.getPrefHeight()-(row1.getPrefHeight()-row4.getPrefHeight()))/2-20);
		row3.setPrefHeight(row2.getPrefHeight());
		column8.setPrefWidth(x.getPrefWidth());
		column7.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column6.setPrefWidth(cancel.getPrefWidth());
		column4.setPrefWidth(abstractLabel.getPrefWidth()+10);
		column5.setPrefWidth(abstractYN.getPrefWidth());
		column3.setPrefWidth(array.getPrefWidth());
		column2.setPrefWidth(arrayLabel.getPrefWidth()+20);
		column1.setPrefWidth(300);
		//GridPane.setHgrow(arrayLabel,Priority.SOMETIMES);
		//GridPane.setHgrow(abstractYN, Priority.SOMETIMES);
		buffer1.setHgrow(Priority.ALWAYS);
		column2.setHalignment(HPos.RIGHT);
		column4.setHalignment(HPos.RIGHT);
		super.getColumnConstraints().setAll(buffer1,column1,column2,column3,column4,column5,column6,column7,column8,buffer1);
		super.getRowConstraints().setAll(buffer,row1,row2,row3,row4,buffer);
		pane.setPrefWidth(column1.getPercentWidth());
		
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
		super.setStyle("-fx-border-color:rgb(0,0,0);");
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,4);
		super.add(pane,1,1);
		super.add(scopeLabel,2,1);
		super.add(scope,3,1);
		super.add(typeLabel,2,4);
		super.add(type,3,4);
		super.add(nameLabel,2,2);
		super.add(name,3,2);
		super.add(arrayLabel,2,3);
		super.add(array,3,3);
		super.add(parameterLabel,4,2);
		super.add(parameters,5,2);
		super.add(abstractLabel,4,3);
		super.add(abstractYN,5,3);
		super.add(x,8,1);
		super.add(cancel,6,4);
		GridPane.setColumnSpan(add,2);
		super.add(add,7,4);
		ide.addInfoPanel(this);
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == add){
			parameters.setStyle("-fx-border-radius: 1px");
			if(activeTab!=null){
				activeTab.cancel();
			}
			activeTab = ide.lastActiveTab();
			String first = "";
			String last = "";
			first = first + scope.getValue();
			if(abstractYN.isSelected()){
				first = first + " abstract";
			}
			if(type.getValue() != "Other"){
				first = first + " " + type.getValue();
			}
			else{
				first = first + " Object";
			}
			if(array.isSelected()&&type.getValue() != "void"){
				first = first + "[]";
			}
			first = first + " " + name.getText() + "(";
			try{
				int a = Integer.parseInt(parameters.getValue()+"");
				if(a<0){
					throw new NumberFormatException();
				}
				else{
					for(int i = 0; i < a - 1; i++){
						first = first + ", ";
					}
					first = first + "){";
				}
				if(abstractYN.isSelected()){
					first = first + ";\n";
					activeTab.oneClick(first);
				}
				else{
					last = "\n}";
					activeTab.twoClick(first, last);
				}
			}
			catch(NumberFormatException ex){
				//use popover for error display
				parameters.setStyle("-fx-border-color: red");
			}
			
		}
		else if(e.getSource()==cancel){
			close();
		}
	}

}

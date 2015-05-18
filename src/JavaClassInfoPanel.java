package ouride;

import java.awt.Font;
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

public class JavaClassInfoPanel extends InfoPanel {

	private CheckBox implement;
	private Label implementLabel;
	private CheckBox extend;
	private Label extendLabel;
	private TextField name;
	private Label nameLabel;
	private Label scopeLabel;
	private ChoiceBox<String> scope;
	private Label abstractLabel;
	private CheckBox abstractYN;

	public JavaClassInfoPanel(OurIDE ide, InfoBar bar){
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
		scope.setPrefSize(75,25);
		scopeLabel = new Label("Class Scope:");
		scopeLabel.setPrefSize(100,25);
		//type input
		implement = new CheckBox();
		implement.setPrefSize(75,25);
		implementLabel = new Label("Implements? y/n:");
		implementLabel.setPrefSize(100, 25);
		//abstract input
		abstractYN = new CheckBox();
		abstractYN.setPrefSize(75,25);
		abstractLabel = new Label("Abstract? y/n:");
		abstractLabel.setPrefSize(100, 25);
		//name input
		name = new TextField();
		//name.setPrefSize(75, 25);
		name.setMaxSize(75, 25);
		nameLabel = new Label("Class Name:");
		nameLabel.setPrefSize(100, 25);
		//array input
		extend = new CheckBox();
		extend.setPrefSize(75,25);
		extendLabel = new Label("Extends? y/n:");
		extendLabel.setPrefSize(100, 25);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("A "));
		Text temp = new Text("class");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" is a template definition of a method and variable in a particular kind of object. This makes an object a specific instance of a class, where it contains actual values instead of just variables.\n\n"));
		temp = new Text("Class scope ");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("determines who has access to the actual class. There are three possible scopes: Public, Private and Protected. A "));
		temp = new Text("public");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" class can be accessed by anyone. A "));
		temp = new Text("private");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" class is hidden from everyone except for the class that creates it.  If a class is private, they should only be used as an “inner-class”. A "));
		temp = new Text("protected");
		temp.setStyle("-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" class can be accessed by any class inside of the package and its subclasses (a package can be thought of as a group of directories, and subclasses are classes that get extended from other classes).\n\nA "));
		temp = new Text("class name ");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("is used as the name for the class, which is the template itself.\n\n"));
		temp = new Text("Extends ");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("is a term used to explain a particular relationship between classes. An extended class has all of the same methods and variables of the original class that is being extended. So in a sense, a particular class is receiving the same terms (variables and methods) from another class. You can only extend to one class, however.\n\nTo "));
		temp = new Text("implement ");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("an interface, a particular class receives methods and constant variables from some interface.  You can think of an interface as a class with only abstract methods. You can implement as many interfaces as you would like, too. To do this, simply separate the interfaces with commas in the code.\n\nIf a class is "));
		temp = new Text("abstract ");
		temp.setStyle("-fx-font-weight:bold;");
		text.add(temp);
		text.add(new Text("it may or may not include abstract methods. An abstract method is a method that is declared, but has no implementation. Abstract classes cannot be instantiated, but they can be subclassed however."));
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
		buffer1.setPrefWidth(10);
		row1.setPrefHeight(x.getPrefHeight());
		row4.setPrefHeight(add.getPrefHeight());
		row2.setPrefHeight((super.getPrefHeight()-(row1.getPrefHeight()-row4.getPrefHeight()))/2-20);
		row3.setPrefHeight(row2.getPrefHeight());
		column8.setPrefWidth(x.getPrefWidth());
		column7.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column6.setPrefWidth(cancel.getPrefWidth());
		column4.setPrefWidth(abstractLabel.getPrefWidth()+10);
		column5.setPrefWidth(abstractYN.getPrefWidth());
		column3.setPrefWidth(extend.getPrefWidth());
		column2.setPrefWidth(extendLabel.getPrefWidth()+20);
		column1.setPrefWidth(300);
		buffer1.setHgrow(Priority.ALWAYS);
		//GridPane.setHgrow(extendLabel,Priority.SOMETIMES);
		//GridPane.setHgrow(abstractYN, Priority.SOMETIMES);
		column2.setHalignment(HPos.RIGHT);
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
		super.add(implementLabel,2,4);
		super.add(implement,3,4);
		super.add(nameLabel,2,2);
		super.add(name,3,2);
		super.add(extendLabel,2,3);
		super.add(extend,3,3);
		super.add(abstractLabel,4,3);
		super.add(abstractYN,5,3);
		super.add(x,8,1);
		super.add(cancel,6,4);
		GridPane.setColumnSpan(add,2);
		super.add(add,7,4);
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
			String first = "";
			String last = "\n}";
			first = first + scope.getValue();
			if(abstractYN.isSelected())
				first = first + " abstract"; 
			first = first + " class " + name.getText();
			if(extend.isSelected())
				first = first + " extends";
			if(implement.isSelected()){
				first = first + " implements";
			}
			first = first + "{\n";
			activeTab.twoClick(first, last);
		}
		else if(e.getSource()==cancel){
			close();
		}
	}
}

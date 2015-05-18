package ouride;

import java.util.ArrayList;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
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

public class JavaEquationInfoPanel extends InfoPanel {
	private TextField equation;
	private Button preview;
	private EquationEditor ee = new JavaEquationEditor();

	public JavaEquationInfoPanel(OurIDE ide, InfoBar bar){
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
		//preview button
		preview = new Button("Preview");
		preview.setPrefSize(75,25);
		preview.setOnAction(this);
		//equation input
		equation =  new TextField();
		equation.setPromptText("Put Mathematical Equation Here...");
		equation.setPrefSize(400, 25);
		//description
		ArrayList<Text> text = new ArrayList<Text>();
		info = new TextFlow();
		//file read code here
		text.add(new Text("The "));
		Text temp = new Text("Equation Editor");
		temp.setStyle("-fx-font-weight:bold;-fx-underline:true;");
		text.add(temp);
		text.add(new Text(" is a text field in which you can put an equation into it and it has a preview button to show what it looks like in code.\nTo demonstrate, by putting the following into the text field:\n\tx^2\nThe preview would show:\n\tmath.pow(x,2)"));
		info.getChildren().addAll(text);
		pane = new ScrollPane();
		pane.setFitToWidth(true);
		pane.setContent(info);
		pane.setPrefHeight(100);
		super.setPrefHeight(140);
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();
		RowConstraints buffer1 = new RowConstraints();
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		ColumnConstraints column4 = new ColumnConstraints();
		ColumnConstraints column5 = new ColumnConstraints();
		ColumnConstraints column6 = new ColumnConstraints();
		ColumnConstraints buffer = new ColumnConstraints();
		ColumnConstraints buffer2 = new ColumnConstraints();
		buffer.setPrefWidth(40);
		buffer1.setPrefHeight(10);
		buffer2.setPrefWidth(10);
		row1.setPrefHeight(x.getPrefHeight());
		row2.setPrefHeight(super.getPrefHeight()-(x.getPrefHeight()+add.getPrefHeight())-20);
		row3.setPrefHeight(add.getPrefHeight());
		column6.setPrefWidth(x.getPrefWidth());
		column5.setPrefWidth(add.getPrefWidth()-x.getPrefWidth());
		column4.setPrefWidth(cancel.getPrefWidth());
		column3.setPrefWidth(preview.getPrefWidth());
		column2.setPrefWidth(equation.getPrefWidth());
		column1.setPrefWidth(300);
		//GridPane.setHgrow(equation, Priority.SOMETIMES);
		column2.setHalignment(HPos.RIGHT);
		buffer2.setHgrow(Priority.ALWAYS);
		row2.setValignment(VPos.CENTER);
		super.getColumnConstraints().setAll(buffer2,column1,buffer,column2,column3,column4,column5,column6,buffer2);
		super.getRowConstraints().setAll(buffer1,row1,row2,row3,buffer1);
		pane.setPrefWidth(column1.getPrefWidth());
		super.setStyle("-fx-border-color: rgb(0,0,0);");
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.getChildren().clear();
		
		GridPane.setRowSpan(pane,3);
		super.add(pane,1,1);
		super.add(equation,3,2);
		super.add(preview,4,3);
		super.add(cancel,5,3);
		GridPane.setColumnSpan(add,2);
		super.add(add,6,3);
		super.add(x,7,1);
		ide.addInfoPanel(this);
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == add){
			if(activeTab!=null){
				activeTab.cancel();
			}
			activeTab = ide.lastActiveTab();
			activeTab.oneClick(ee.changeEquation(equation.getText()));
		}
		else if (e.getSource() == preview){
			Dialogs.create()
		              .nativeTitleBar()
		              .title("Equation Preview")
		              .message(ee.changeEquation(equation.getText()))
		              .showInformation();
		}
		else if (e.getSource() == cancel){
			close();
		}
	}

}

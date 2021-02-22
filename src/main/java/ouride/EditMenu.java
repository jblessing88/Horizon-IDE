package main.java.ouride;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class EditMenu extends Menu implements EventHandler<ActionEvent>{

	private MenuItem cut;
	private MenuItem copy;
	private MenuItem undo;
	private MenuItem redo;
	private MenuItem paste;
	private MenuItem selectAll;
	private MenuItem refactor;
	private MenuItem settings;
	private Robot robot;
	private OurIDE ide;
	public EditMenu(OurIDE a){
		super("Edit");
        
        System.setProperty("java.awt.headless", "false");
        
		cut = new MenuItem("Cut");
		cut.setOnAction(this);
		cut.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		copy = new MenuItem("Copy");
		copy.setOnAction(this);
		copy.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		undo = new MenuItem("Undo");
		undo.setOnAction(this);
		undo.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		redo = new MenuItem("Redo");
		redo.setOnAction(this);
		redo.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		paste = new MenuItem("Paste");
		paste.setOnAction(this);
		paste.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		selectAll = new MenuItem("Select All");
		selectAll.setOnAction(this);
		selectAll.setOnMenuValidation(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		refactor = new MenuItem("Refactor");
		refactor.setOnAction(this);
		
		settings = new MenuItem("Settings");
		settings.setOnAction(this);
		
		//Set shortcut displays based off operating system
		/*if(System.getProperty("os.name").indexOf("Mac") >= 0){
			cut.setAccelerator(KeyCombination.keyCombination("Meta+X"));
			copy.setAccelerator(KeyCombination.keyCombination("Meta+C"));
			undo.setAccelerator(KeyCombination.keyCombination("Meta+Z"));
			redo.setAccelerator(KeyCombination.keyCombination("Meta+Y"));
			paste.setAccelerator(KeyCombination.keyCombination("Meta+V"));
			selectAll.setAccelerator(KeyCombination.keyCombination("Meta+A"));
			refactor.setAccelerator(KeyCombination.keyCombination("Meta+H"));
		}
		else{
			cut.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
			copy.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
			undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
			redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
			paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
			selectAll.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
			refactor.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
		}*/
		cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
		undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
		redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));
		paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN));
		selectAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
		refactor.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
		
		this.getItems().addAll(cut,copy,undo,redo,paste,selectAll,refactor,settings);
		try {
			robot = new Robot();
			robot.setAutoDelay(10);
			robot.setAutoWaitForIdle(true);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ide = a;
	}
	
	public void cut(){//simulates control+x or command+x depending on operating system
		if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_X);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_X);
		}
	}
	
	public void copy(){//simulates control+c or command+c depending on operating system
		if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_C);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_C);
		}
	}
	
	public void undo(){//simulates control+z or command+z depending on operating system
		/*if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_Z);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_Z);
		}*/
		ide.lastActiveTab().undo();
	}
	
	public void redo(){//simulates control+y or command+y depending on operating system
		/*if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_Y);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_Y);
		}*/
		ide.lastActiveTab().redo();
	}
	
	public void paste(){//simulates control+v or command+v depending on operating system
		if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_V);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
		}
	}
	
	public void refactor(){
		final ArrayList<Integer> indexList = new ArrayList<Integer>();
        final HBox hbox = new HBox();
        final Region spacer = new Region();
        final Label findLabel = new Label("Find : ");
        final TextField findTextField = new TextField("");
        final Label refactorLabel = new Label("Replace With : ");
        final TextField refactorTextField = new TextField("");
        final Button refactorButton = new Button("Refactor!");
        final Button close = new Button("x");
        final TextArea textArea = ide.getTabbedArea().getActiveTab().getTextArea();

        refactorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	String toFind = findTextField.getText();
    			String toReplace = refactorTextField.getText();
    			String edit = textArea.getText();
    			if(!toFind.isEmpty()){
    				edit = edit.replace(toFind, toReplace);
    				ide.lastActiveTab().storeUndo();
    			}
    			textArea.setText(edit);
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                ide.removeFromCenter(hbox);
            }
        });

        HBox.setHgrow(spacer, Priority.ALWAYS);
        close.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.getChildren().addAll(findLabel, findTextField, refactorLabel, refactorTextField, refactorButton, spacer, close);
        ide.addToCenter(hbox);
		//ide.lastActiveTab().refactor();
	}
	
	public void selectAll(){//simulates control+a or command+a depending on operating system
		if(System.getProperty("os.name").indexOf("Mac") >= 0){
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_A);
		}
		else{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
		}
	}
	
	public void settings(){
		ide.openSettings();
	}

	@Override
	public void handle(ActionEvent event) {//determines which button was pressed by user and sends to appropriate method
		// TODO Auto-generated method stub
			if(event.getSource() == cut){
				cut();
			}
			else if(event.getSource() == copy){
				copy();
			}
			else if(event.getSource() == undo){
				undo();
			}
			else if(event.getSource() == redo){
				redo();
			}
			else if(event.getSource() == paste){
				paste();
			}
			else if(event.getSource() == refactor){
				refactor();
			}
			else if(event.getSource() == selectAll){
				selectAll();
			}
			else if(event.getSource() == settings){
				settings();
			}
	}
}

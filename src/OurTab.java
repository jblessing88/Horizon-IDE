package ouride;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class OurTab extends Tab{
	
	private TextArea textArea;
	private String path;
	private boolean saved;
	private boolean prevSaved;
	private boolean autoIndent;
	private ColorScheme colorScheme;
	private int click;
	private int pos1, pos2;
	private String first, second;
	private LinkedList<String> undo,redo;
	private LinkedList<Integer> undoPos, redoPos;
	private String undoCompare;
	private int undoSave;
	private boolean backspacing, typing, stored, wasUndo;
	
	public OurTab(String path){
		super(path.substring(path.lastIndexOf(System.getProperty("file.separator"))+1));
		wasUndo = false;
		prevSaved = true;
		saved = true;
		backspacing = false;
		undoCompare = "";
		typing = false;
		stored = false;
		this.setPath(path);
		textArea = new TextArea();
		undo = new LinkedList<String>();
		undoPos = new LinkedList<Integer>();
		redo = new LinkedList<String>();
		redoPos = new LinkedList<Integer>();
		undoSave = -1;
		try {
			File file = new File(path);
			Scanner scan = new Scanner(file);
			String text = "";
			while(scan.hasNextLine()){
				text = text + scan.nextLine()+"\n";
			}
			textArea.setText(text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				// TODO Auto-generated method stub
				if(saved){
					setText(getText()+"*");
					saved = false;
				}
				if(!wasUndo){
					redo.clear();
					redoPos.clear();
				}
				wasUndo = false;
			}});
		textArea.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				// TODO Auto-generated method stub
				KeyCodeCombination combo = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
				KeyCodeCombination comboSave = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
				KeyCodeCombination comboRedo = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
				boolean line = false;
				boolean space = false;
				if(textArea.getCaretPosition()!=0){
					line = textArea.getText(textArea.getCaretPosition()-1, textArea.getCaretPosition()).equalsIgnoreCase("\n");
					space = textArea.getText(textArea.getCaretPosition()-1, textArea.getCaretPosition()).equalsIgnoreCase(" ");
				}
				if(combo.match(e)){
					undo();
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(comboSave.match(e)){
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(comboRedo.match(e)){
					redo();
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(e.getCode()==KeyCode.SPACE && !space){
					System.out.println("UNDO");
					backspacing = false;
					//undo.add(textArea.getText());
					//undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = false;
				}
				else if(e.getCode()==KeyCode.BACK_SPACE && !backspacing){
					backspacing = true;
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					System.out.println("BACKSPACING");
					typing = false;
					stored = false;
				}
				else if(e.getCode()==KeyCode.ENTER && !line){
					System.out.println("ENTER");
					backspacing = false;
					//undo.add(textArea.getText());
					//undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = false;
				}
				else if(e.getCode()!=KeyCode.BACK_SPACE && backspacing){
					System.out.println("FINISHED");
					backspacing = false;
					typing = false;
					stored = false;
				}
				else if((e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT||e.getCode()==KeyCode.UP||e.getCode()==KeyCode.DOWN) && typing){
					System.out.println("ARROWS");
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = true;
					backspacing = false;
				}
				else if(e.getCode()!=KeyCode.ENTER && e.getCode()!=KeyCode.BACK_SPACE && e.getCode()!=KeyCode.SPACE && e.getCode()!=KeyCode.CONTROL && !(e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT||e.getCode()==KeyCode.UP||e.getCode()==KeyCode.DOWN)){
					typing = true;
					backspacing = false;
				}
				
				if(typing && !stored){
					System.out.println("HERE");
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					stored = true;
				}
			}
			
		});
		textArea.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(typing){
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = true;
					backspacing = false;
				}
			}
		});
		/*textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent event) {
	            if (event.getCode() == KeyCode.ENTER&&autoIndent) {
	              autoIndent();
	            }
	        }
	    });*/
		this.setContent(textArea);
	}
	
	public OurTab(int num){
		super("Untitled" + num);
		path = "Untitled" + num;
		wasUndo = false;
		saved = true;
		prevSaved = false;
		backspacing = false;
		undoCompare = "";
		typing = false;
		stored = false;
		undo = new LinkedList<String>();
		undoPos = new LinkedList<Integer>();
		redo = new LinkedList<String>();
		redoPos = new LinkedList<Integer>();
		undoSave = -1;
		textArea = new TextArea();
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				// TODO Auto-generated method stub
				if(!getText().contains("*")){
					setText(getText()+"*");
					saved = false;
				}
				if(!wasUndo){
					redo.clear();
					redoPos.clear();
				}
				wasUndo = false;
			}});
		textArea.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				// TODO Auto-generated method stub
				KeyCodeCombination combo = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
				KeyCodeCombination comboSave = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
				KeyCodeCombination comboRedo = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
				boolean line = false;
				boolean space = false;
				if(textArea.getCaretPosition()!=0){
					line = textArea.getText(textArea.getCaretPosition()-1, textArea.getCaretPosition()).equalsIgnoreCase("\n");
					space = textArea.getText(textArea.getCaretPosition()-1, textArea.getCaretPosition()).equalsIgnoreCase(" ");
				}
				if(combo.match(e)){
					undo();
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(comboSave.match(e)){
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(comboRedo.match(e)){
					redo();
					stored = false;
					typing = false;
					backspacing = false;
				}
				else if(e.getCode()==KeyCode.SPACE && !space){
					System.out.println("UNDO");
					backspacing = false;
					//undo.add(textArea.getText());
					//undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = false;
				}
				else if(e.getCode()==KeyCode.BACK_SPACE && !backspacing){
					backspacing = true;
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					System.out.println("BACKSPACING");
					typing = false;
					stored = false;
				}
				else if(e.getCode()==KeyCode.ENTER && !line){
					System.out.println("ENTER");
					backspacing = false;
					//undo.add(textArea.getText());
					//undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = false;
				}
				else if(e.getCode()!=KeyCode.BACK_SPACE && backspacing){
					System.out.println("FINISHED");
					backspacing = false;
					typing = false;
					stored = false;
				}
				else if((e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT||e.getCode()==KeyCode.UP||e.getCode()==KeyCode.DOWN) && typing){
					System.out.println("ARROWS");
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = true;
					backspacing = false;
				}
				else if(e.getCode()!=KeyCode.ENTER && e.getCode()!=KeyCode.BACK_SPACE && e.getCode()!=KeyCode.SPACE && e.getCode()!=KeyCode.CONTROL && !(e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT||e.getCode()==KeyCode.UP||e.getCode()==KeyCode.DOWN)){
					typing = true;
					backspacing = false;
				}
				
				if(typing && !stored){
					System.out.println("HERE");
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					stored = true;
				}
			}
			
		});
		textArea.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(typing){
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					typing = false;
					stored = true;
					backspacing = false;
				}
			}
		});
		this.setContent(textArea);
	}

	public void addText(String startText, String endText, int a, int b){
		String text  = textArea.getText();
		String temp = text.substring(0,a);
		text = temp + startText + text.substring(a);
		temp = text.substring(0,b + startText.length());
		text = temp + endText + text.substring(b + startText.length());
		textArea.setText(text);
	}
	
	public void addText(String input, int a){
		String text = textArea.getText();
		String temp = text.substring(0,a);
		text = temp + input + text.substring(a);
		textArea.setText(text);
	}

	public void autoIndent(){
		Scanner indenter = new Scanner(textArea.getText());
		String output = "";
		boolean ignore = false;
		int tabs = 0;
		String check1, check2;
		while(indenter.hasNextLine()){
			check1 = indenter.nextLine();
			if(indenter.hasNextLine()){
				check2 = indenter.nextLine();
				if(check1.contains("if")&&(check1.contains("{")||check2.contains("{"))){
					if(check1.indexOf("if")>0){
						if((check1.charAt(check1.indexOf("if")-1)==' ' || check1.charAt(check1.indexOf("if")-1)=='{')&&(check1.charAt(check1.indexOf("if")+2)==' ' || check1.charAt(check1.indexOf("if")+2)=='(')){
							ignore = true;
						}
					}
				}
				if(check1.contains("else if")&&(check2.contains("{")||check1.contains("{"))){
					
				}
			}
			else{
				
			}
			output = output+"\n";
		}
	}
	
	/*public void find(){
		final Stage input = new Stage();
		final FlowPane pane = new FlowPane();
		final TextArea find = new TextArea();
		final Button ok = new Button("Find");
		final Button next = new Button("Find Next");
	    ok.setOnAction(new EventHandler<ActionEvent>(){ public void handle(ActionEvent e) {if(e.getSource()==ok){
			pane.getChildren().remove(ok);
			pane.getChildren().add(next);
			String toFind = find.getText();
			String edit = textArea.getText();
			textArea.selectRange(edit.indexOf(toFind, 0),edit.indexOf(toFind, 0) + toFind.length());
		}}});
		next.setOnAction(new EventHandler<ActionEvent>(){ public void handle(ActionEvent e) {if(e.getSource()==next){
			String toFind = find.getText();
			String edit = textArea.getText();
			if(edit.indexOf(toFind,textArea.getCaretPosition())>textArea.getCaretPosition())
				textArea.selectRange(edit.indexOf(toFind,textArea.getCaretPosition()),edit.indexOf(toFind,textArea.getCaretPosition())+toFind.length());
			else
				input.close();
		}}});
		find.setPrefSize(140, 30);
		pane.getChildren().addAll(new Label("Find: "),find, ok);
		pane.autosize();
		Scene scene = new Scene(pane);
		input.setTitle("Find");
		input.setScene(scene);
		input.requestFocus();
		input.show();
	}*/
	
	public String getPath() {
		return path;
	}

	public boolean hasBeenSaved(){
		return prevSaved;
	}
	
	public void setPath(String path) {
		this.path = path;
		changeSaved();
		setText(path.substring(path.lastIndexOf(System.getProperty("file.separator"))+1));
	}
	
	public String getContentText(){
		return textArea.getText();
	}
	
	public TextArea getTextArea(){
		return textArea;
	}
	
	public boolean getSaved(){
		return saved;
	}
	
	public void changeSaved(){
		if(saved){
			
		}
		else{
			saved = true;
			if(getText().contains("*"))
				super.setText(getText().substring(0,getText().length()-1));
			undoSave = undo.size();
			undoCompare = textArea.getText();
			prevSaved = true;
			if(typing){
				undo.add(textArea.getText());
				undoPos.add(textArea.getCaretPosition());
				stored = true;
				backspacing = false;
			}
		}
	}
	
	public void cancel(){
		textArea.setOnMouseClicked(null);
	}
	
	public void twoClick(String a, String b){
		click = 0;
		first = a;
		second = b;
		textArea.requestFocus();
		textArea.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(click == 0){
					pos1 = textArea.getCaretPosition();
					click++;
				}
				else if(click == 1){
					pos2 = textArea.getCaretPosition();
					undo.add(textArea.getText());
					undoPos.add(textArea.getCaretPosition());
					addText(first,second,pos1,pos2);
					textArea.setOnMouseClicked(null);
				}
			}

		});
	}
	
	public void oneClick(String a){
		first = a;
		textArea.requestFocus();
		textArea.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pos1 = textArea.getCaretPosition();
				undo.add(textArea.getText());
				undoPos.add(textArea.getCaretPosition());
				addText(first,pos1);
				textArea.setOnMouseClicked(null);
			}
		});
	}
	
	public void update(ColorScheme c, Settings s){
		colorScheme = c;
		autoIndent = true;
		textArea.setStyle(c.getBackgroundCSS());
	}
	
	public void undo(){
		if(undo.peekLast()!=null){
			System.out.println("PRE = " + undo.size());
			wasUndo = true;
			String text = undo.pollLast();
			int pos = undoPos.pollLast();
			redo.add(textArea.getText());
			redoPos.add(textArea.getCaretPosition());
			textArea.setText(text);
			textArea.positionCaret(pos);
			System.out.println("POST = " + undo.size());
			if(undo.size()==undoSave && super.getText().contains("*") && undoCompare.equals(textArea.getText())){
				super.setText(getText().substring(0,getText().length()-1));
				saved = true;
			}
			else if(!super.getText().contains("*")){
				setText(getText()+"*");
				saved = false;
			}
			if(undo.size()==0 && undoSave == -1 && hasBeenSaved() && getText().contains("*")){
				super.setText(getText().substring(0,getText().length()-1));
				saved = true;
			}
			if(undo.size()==0 && undoSave == -1 && !hasBeenSaved() && getText().contains("*")){
				super.setText(getText().substring(0,getText().length()-1));
				saved = false;
			}
		}
		/*else{
			if(!hasBeenSaved()&&super.getText().contains("*")){
				super.setText(getText().substring(0,getText().length()-1));
			}
		}*/
	}
	
	public void redo(){
		if(redo.peek()!=null){
			System.out.println("REDO");
			String text = redo.pollLast();
			wasUndo = true;
			int pos = redoPos.pollLast();
			undo.add(textArea.getText());
			undoPos.add(textArea.getCaretPosition());
			textArea.setText(text);
			textArea.positionCaret(pos);
			if(undo.size()==undoSave && super.getText().contains("*") &&  undoCompare.equals(textArea.getText())){
				super.setText(getText().substring(0,getText().length()-1));
				saved = true;
			}
			else if(!super.getText().contains("*")){
				setText(getText()+"*");
				saved = false;
			}
			if(undo.size()==0 && undoSave == -1 && hasBeenSaved() && getText().contains("*")){
				super.setText(getText().substring(0,getText().length()-1));
				saved = true;
			}
		}
	}
	
	public void storeUndo(){
		undo.add(textArea.getText());
		undoPos.add(textArea.getCaretPosition());
	}
}

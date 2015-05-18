package ouride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public class CodeMenu extends Menu implements EventHandler<ActionEvent>{

	private MenuItem compile;
	private MenuItem run;
	private OurIDE ide;
	public CodeMenu(OurIDE a){
		super("Code");
		compile = new MenuItem("Compile");
		compile.setOnAction(this);
		run = new MenuItem("Run");
		run.setOnAction(this);
		this.getItems().addAll(compile,run);
		ide = a;
	}
	
	public void compile(){
		ide.compile();
	}
	public void run(){
		ide.run();
	}
	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == compile){
			compile();
		}
		else if(e.getSource() == run){
			run();
		}
	}

}

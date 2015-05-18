package ouride;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class OutputArea extends VBox implements EventHandler<KeyEvent>{
	private TextArea output;
	private Button collapse;
	private Button expand;
	private OutputStream in;
	private OutReaderThread out;
	private ErrReaderThread err;
	private ProcessThread end;
	private Process running;
	private HBox box;
	private boolean collapsed;
	private String stream;
	private boolean forced = false;
	private GridPane notifications;
	private Label not1,not2,not3,not4;
	private byte[] obuf, ebuf;
	
	public OutputArea() throws IOException{
		super();
		collapsed = false;
		box = new HBox();
		collapse = new Button("\u25bc");
		expand = new Button("\u25b2");
		output = new TextArea();
		notifications = new GridPane();
		Label buffer = new Label();
		buffer.setPrefWidth(1);
		buffer.setStyle("-fx-border-color:rgb(0,0,0);");
		Label buffer1 = new Label();
		buffer1.setPrefWidth(1);
		buffer1.setStyle("-fx-border-color:rgb(0,0,0);");
		Label buffer2 = new Label();
		buffer2.setPrefWidth(1);
		buffer2.setStyle("-fx-border-color:rgb(0,0,0);");
		not1 = new Label();
		not2 = new Label();
		not3 = new Label();
		not4 = new Label();
		not1.setMinWidth(100);
		not2.setMinWidth(500);
		not3.setMinWidth(100);
		not4.setMinWidth(50);
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		ColumnConstraints column4 = new ColumnConstraints();
		ColumnConstraints column5 = new ColumnConstraints();
		ColumnConstraints column6 = new ColumnConstraints();
		ColumnConstraints column7 = new ColumnConstraints();
		column1.setPrefWidth(100);
		column2.setPrefWidth(1);
		column3.setPrefWidth(500);
		column3.setHgrow(Priority.ALWAYS);
		column4.setPrefWidth(1);
		column5.setPrefWidth(100);
		column6.setPrefWidth(1);
		column7.setPrefWidth(50);
		notifications.getColumnConstraints().setAll(column1,column2,column3,column4,column5,column6,column7);
		notifications.add(not1, 0, 0);
		notifications.add(buffer, 1, 0);
		notifications.add(not2, 2, 0);
		notifications.add(buffer1, 3, 0);
		notifications.add(not3, 4, 0);
		notifications.add(buffer2, 5, 0);
		notifications.add(not4, 6, 0);
		collapse.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                collapse();
            }
        });
		expand.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                expand();
            }
        });
		//this.setStyle("-fx-background-color: #C0C0C0;");
		this.setAlignment(Pos.CENTER_RIGHT);
		
		box.setAlignment(Pos.CENTER_RIGHT);
		Label label = new Label("Output");
		box.getChildren().addAll(label,collapse);
		output.setWrapText(true);
		output.setOnKeyPressed(this);
		this.getChildren().addAll(box,output,notifications);

	}
	
	public void setNotification1(String a){
		not1.setText(a);
	}
	public void setNotification2(String a){
		not2.setText(a);
	}
	public void setNotification3(String a){
		not3.setText(a);
	}
	public void setNotification4(String a){
		not4.setText(a);
	}
	
	public void collapse(){
		if(!collapsed){
			box.getChildren().remove(collapse);
			box.getChildren().add(expand);
			this.getChildren().remove(output);
			collapsed = true;
		}
	}
	
	public void expand(){
		if(collapsed){
			box.getChildren().remove(expand);
			box.getChildren().add(collapse);
			this.getChildren().remove(notifications);
			this.getChildren().add(output);
			this.getChildren().add(notifications);
			collapsed = false;
		}
	}
	
	private void setInputStream(OutputStream in){
		this.in = in;
	}
	
	public void setProcess(Process p){
		if(out!=null){
			out.stop();
		}
		if(err!=null){
			err.stop();
		}
		if(end!=null){
			end.stop();
		}
		if(running!=null){
			forced = true;
			running.destroy();
		}
		stream = "";
		out = new OutReaderThread(p.getInputStream());
		err = new ErrReaderThread(p.getErrorStream());
		end = new ProcessThread(p);
		running = p;
		out.start();
		err.start();
		end.start();
		output.clear();
		setNotification2("  Beginning Operation");
		setInputStream(p.getOutputStream());
	}
	
	public TextArea getArea(){
		return output;
	}
	 class ProcessThread extends Thread {
		 Process p;
		 
		 ProcessThread(Process process) {
	            p = process;
	     }
		 
		 public void run(){
			 boolean done = true;
			 while(done){
				 Platform.runLater(new Runnable() {
				        public void run() {
				        	try{
								 p.exitValue();
								 if(!forced){
									 while(p.getInputStream().available()!=0){
										 
									 }
									 out.stop();
									 while(p.getErrorStream().available()!=0){
									
									 }
								     err.stop();
								     if(running!=null)
										 running.destroy();
									 running = null;
									 setNotification2("  Operation Complete");
									 in = null;
									 stop();
									 forced = true;
								 }
								 forced = false;		 
							 }
							 catch(IllegalThreadStateException e){
								
							 } catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        }
				    });
				 try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
	 }
	
	 class OutReaderThread extends Thread {
	        InputStream pi;

	        OutReaderThread(InputStream pi) {
	            this.pi = pi;
	        }

	        public void run() {
	        	Scanner scan = new Scanner(pi);
	        		try{
	        			while(true){
	        				obuf = new byte[pi.available()];
	        				final int len = pi.read(obuf);
	        				if(len==-1){
	        					break;
	        				}
	        				Platform.runLater(new Runnable(){
	        					public void run(){
	        						output.appendText(new String(obuf,0,len));
	        						stream = stream + new String(obuf,0,len);
	        					}
	        				});
	        				try {
	    						sleep(10);
	    					} catch (InterruptedException e) {
	    						// TODO Auto-generated catch block
	    						e.printStackTrace();
	    					}
	        			}
	        		}
	        		catch(IOException e){
	        			e.printStackTrace();
	        		}
				}
	        }
	 class ErrReaderThread extends Thread {
	        InputStream pi;

	        ErrReaderThread(InputStream pi) {
	            this.pi = pi;
	        }

	        public void run() {
	        	Scanner scan = new Scanner(pi);
	        		try{
	        			while(true){
	        				ebuf = new byte[pi.available()];
	        				final int len = pi.read(ebuf);
	        				if(len==-1){
	        					break;
	        				}
	        				Platform.runLater(new Runnable(){
	        					public void run(){
	        						output.appendText(new String(ebuf,0,len));
	        						stream = stream + new String(ebuf,0,len);
	        					}
	        				});
	        				try {
	    						sleep(10);
	    					} catch (InterruptedException e) {
	    						// TODO Auto-generated catch block
	    						e.printStackTrace();
	    					}
	        			}
	        		}
	        		catch(IOException e){
	        			e.printStackTrace();
	        		}
				}
	        }

	@Override
	public void handle(KeyEvent arg0) {
		// TODO Auto-generated method stub
		KeyCodeCombination combo = new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN);
		if(arg0.getCode().equals(KeyCode.ENTER)&&in!=null){
			String input;
			if(stream.length()==0)
				input = output.getText();
			else
				input = output.getText().substring(stream.length());
			try {
				in.write(input.getBytes());
				in.write("\n".getBytes());
				in.flush();
				stream = stream + input;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stream != null){
			if(!arg0.isShortcutDown()){
				if(output.getCaretPosition() < stream.length()){
					output.positionCaret(stream.length() + 1);
				}
				if(arg0.getCode().equals(KeyCode.BACK_SPACE) && output.getCaretPosition() < stream.length()){
					arg0.consume();
				}
			}
			else{
				if(!arg0.getCode().equals(KeyCode.CONTROL) && !arg0.getCode().equals(KeyCode.META)){
					if(!combo.match(arg0)){
						if(output.getCaretPosition() < stream.length()){
							output.positionCaret(stream.length() + 1);
						}
						if(arg0.getCode().equals(KeyCode.BACK_SPACE) && output.getCaretPosition() < stream.length()){
							arg0.consume();
						}
					}
				}
			}
		}
	}
}

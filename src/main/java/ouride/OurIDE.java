package main.java.ouride;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Scanner;

public class OurIDE extends Application {
    private BorderPane borderPane;
    private JavaCompiler comp;
    private OurFileManager fileManager;
    private OurMenuBar bar;
    OurTabbedArea tabs;//fix settings, change to using getters
    private OurThread thread;
    private OutputArea text;
	private Settings settings;
	private VBox centerArea;
    private VBox menuGuidesBar;
	private String workspace;
    private String installDirec;
    private InfoBar infoBar;
    boolean lostFocus = false;
    private FocusThread focusing;
    private Scene scene;
    
	@Override
    public void init(){
        workspace = "";
        installDirec = "";
        String sep = System.getProperty("file.separator");
        File file = new File(System.getProperty("user.home") + sep + "Horizon" + sep + "Recovery");
        if(!file.exists()){
            file.mkdirs();
        }
        installDirec = file.getParentFile().getAbsolutePath();

        file = new File(installDirec + sep + "_ws.txt");
        Scanner scan = null;
        try{
            scan = new Scanner(file);
            if(scan.hasNext()){
                File workspaceLoc = new File(scan.next());
                if(workspaceLoc.exists())
                    workspace = workspaceLoc.getAbsolutePath();
            }
        } catch(FileNotFoundException fnfe){

        }
    }
	
	@Override
    public void start(final Stage primaryStage) throws Exception {
        if(workspace.isEmpty())
            syncWorkspace();

        bar				= new OurMenuBar(this);
        borderPane		= new BorderPane();
		centerArea		= new VBox();
        menuGuidesBar   = new VBox();
        comp			= new JavaCompiler(this);
        fileManager		= new OurFileManager(workspace, this);
        scene           = new Scene(borderPane, 900 ,700);
		settings		= new Settings(this);
        tabs			= new OurTabbedArea(this);
        text			= new OutputArea();
        thread			= new OurThread(this);
        infoBar         = new JavaInfoBar(this);

        if(System.getProperty("os.name").contains("Mac")){
            //bar.setUseSystemMenuBar(true);
        }

        tabs.addTab();
        infoBar.generateButtons();
		centerArea.getChildren().add(tabs);
		centerArea.setVgrow(tabs, Priority.ALWAYS);

        menuGuidesBar.getChildren().addAll(bar, infoBar);
        
        borderPane.setLeft(fileManager);
        borderPane.setCenter(centerArea);
        borderPane.setTop(menuGuidesBar);
        borderPane.setBottom(text);
        

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(925);
        primaryStage.setMinHeight(750);
        primaryStage.setTitle("Horizon");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent arg0) {
                if(getTabsSize() > 1){
                    if(!bar.getFileMenu().close())
                        arg0.consume();
                }
            }
        });
        focusing = new FocusThread(primaryStage);
        focusing.start();
	}
	public FileMenu getFileMenu(){
		return bar.getFileMenu();
	}
	
	public void removeFromCenter(Node node){
        centerArea.getChildren().remove(node);
    }

    public void addToCenter(Node node){
        centerArea.getChildren().clear();
        centerArea.getChildren().addAll(node, tabs);
        borderPane.setCenter(centerArea);
    }

    public void removeInfoPanel(){
        infoBar.remove();
    }

    public void addInfoPanel(InfoPanel info){
        infoBar.add(info);
    }
    
    @Override
    public void stop(){
        thread.stop();
        focusing.stop();
        System.exit(0);
    }

    public void syncWorkspace(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Initialize Workspace");
        info.setContentText("Please select a Workspace to store all of your programs!");
        info.show();

        {
            DirectoryChooser dirc = new DirectoryChooser();
            dirc.setTitle("Choose a Workspace!");
            File file = dirc.showDialog(null);
            if (file == null)
                System.exit(0);
            workspace = file.getAbsolutePath();
        }

        File file = new File(installDirec + System.getProperty("file.separator") + "_ws.txt");
        if(!file.exists()){
            file = new File(installDirec, "_ws.txt");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                //put something
            }
        }
            
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(workspace);
        } catch(IOException ioe){
            //put something
        } finally{
            try{
                bufferedWriter.close();
                fileWriter.close();
            } catch(IOException ioe){
                //put something
            }
        }
    }

    public Scene getScene(){
	return scene;
    }

    public String getWorkspace(){
        return workspace;
    }

    public String getInstallDirec(){
        return installDirec;
    }
    
    public OutputArea getOutputArea(){
        return text;
    }
	
	public Settings getSettings(){
		return settings;
	}
   
	public OurTabbedArea getTabbedArea(){
		return tabs;
	}
 
    public OurFileManager getFileManager(){
        return fileManager;
    }
    
    public void createNewTab(){
        tabs.addTab();
    }
    
    public void createNewTab(String a){
        tabs.addTab(a);
    }
    
    public void openSettings(){	
    	settings.open();
    }
	
    public OurTab lastActiveTab(){
        return tabs.getActiveTab();
    }

    public OurTab nextActiveTab(int a){
        tabs.getSelectionModel().select(a);
        return tabs.getActiveTab();
    }

    public int getTabsSize(){
        return tabs.getTabs().size();
    }

    public void expandAll(){
		fileManager.expand();
		text.expand();
    }
    
    public void collapseAll(){
		fileManager.collapse();
		text.collapse();
    }
    
    public void compile(){
    	if(tabs.getActiveTab().hasBeenSaved()){
    		String compilePath = tabs.getActiveTab().getPath();
    		compilePath = compilePath.substring(0,compilePath.lastIndexOf(System.getProperty("file.separator")));
    		comp.compile(compilePath);
    	}
    	else{
    		text.getArea().setText("Please save selected file before compiling");
    	}
    }
    
    public void run(){
    	if(tabs.getActiveTab().hasBeenSaved()){
    		String runPath = tabs.getActiveTab().getPath();
    		runPath = runPath.substring(0,runPath.lastIndexOf(".java"));
    		comp.run(runPath);
    	}
    	else{
    		text.getArea().setText("Please save and compile selected file before running");
    	}
    }
    public void update(ColorScheme c){
    	tabs.update(c, settings);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    class FocusThread extends Thread{
    	Stage program;
    	public FocusThread(Stage s){
    		program = s;
    	}
    	public void run(){
   			 while(true){
				 Platform.runLater(new Runnable() {
				        public void run() {
				        	if(!program.isFocused()){
				        		lostFocus = true;
				        	}
				        	else{
				        		if(lostFocus){
				        			fileManager.refresh();
				        			lostFocus = false;
				        		}
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
}

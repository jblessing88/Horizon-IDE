package main.java.ouride;

import java.io.*;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

public class FileMenu extends Menu implements EventHandler<ActionEvent>{

    private MenuItem newFile;
    private MenuItem openFile;
    private MenuItem saveFile;
    private MenuItem saveAsFile;
    private MenuItem saveAllFile;
    private MenuItem printFile;
    private MenuItem closeFile;
    private OurIDE ide;
    
    public FileMenu(OurIDE a){
        super("File");
        
        newFile = new MenuItem("New");
        openFile = new MenuItem("Open");
        saveFile = new MenuItem("Save");
        saveAsFile = new MenuItem("Save As");
        saveAllFile = new MenuItem("Save All");
        printFile = new MenuItem("Print");
        closeFile = new MenuItem("Close");
        ide = a;
        
        newFile.setOnAction(this);
        openFile.setOnAction(this);
        saveFile.setOnAction(this);
        saveAsFile.setOnAction(this);
        saveAllFile.setOnAction(this);
        printFile.setOnAction(this);
        closeFile.setOnAction(this);
        
        if(System.getProperty("os.name").contains("Mac")){
            newFile.setAccelerator(KeyCombination.keyCombination("Meta+n"));
            openFile.setAccelerator(KeyCombination.keyCombination("Meta+o"));
            saveFile.setAccelerator(KeyCombination.keyCombination("Meta+s"));
            saveAsFile.setAccelerator(KeyCombination.keyCombination("Meta+Shift+s"));
            saveAllFile.setAccelerator(KeyCombination.keyCombination("Meta+Alt+s"));
            printFile.setAccelerator(KeyCombination.keyCombination("Meta+p"));
            closeFile.setAccelerator(KeyCombination.keyCombination("Meta+q"));
        }
        else{
            newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
            openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
            saveFile.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
            saveAsFile.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+s"));
            saveAllFile.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+s"));
            printFile.setAccelerator(KeyCombination.keyCombination("Ctrl+p"));
            closeFile.setAccelerator(KeyCombination.keyCombination("Ctrl+q"));
        }
        
        this.getItems().addAll(newFile, openFile, saveFile, saveAsFile, saveAllFile, printFile, closeFile);
    }
    
    public void newF(){
        ide.createNewTab();
    }
    
    public void openF(){
        FileChooser filec = new FileChooser();
        filec.setTitle("Open File");
        List<File> listFile = filec.showOpenMultipleDialog(null);
        if(listFile == null)
            return;
        for(File file : listFile){
            if(file.exists())
                ide.createNewTab(file.getAbsolutePath());
            else{
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("File Error");
                error.setContentText(file.getAbsolutePath() + " could not be opened.");
                error.show();
            }
        }
    }

    public void saveOption(OurTab tab, String text){
        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Save File");
        confirm.setContentText("Do you want to save " + tab.getPath() + "!");

        confirm.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> result = confirm.showAndWait();

        if(result.get() == btnYes){
            saveF(tab, text);
        }
    }
    
    public void saveF(OurTab tab, String text){
        if(!tab.hasBeenSaved()){
            saveAsF(tab, text);
        }
        else{
            if(!tab.getSaved()){
                File file = new File(tab.getPath());
                boolean exit;
                try{
                    exit = file.exists();
                } catch(SecurityException se){
                    Alert exception = new Alert(Alert.AlertType.ERROR);
                    exception.setTitle("Security Warning");
                    exception.setContentText("This file cannot be accessed due to denied read access.");

                    // Create expandable Exception.
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    se.printStackTrace(pw);
                    String exceptionText = sw.toString();

                    Label label = new Label("The exception stacktrace was:");

                    TextArea textArea = new TextArea(exceptionText);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setVgrow(textArea, Priority.ALWAYS);
                    GridPane.setHgrow(textArea, Priority.ALWAYS);

                    GridPane expContent = new GridPane();
                    expContent.setMaxWidth(Double.MAX_VALUE);
                    expContent.add(label, 0, 0);
                    expContent.add(textArea, 0, 1);

                    // Set expandable Exception into the dialog pane.
                    exception.getDialogPane().setExpandableContent(expContent);

                    exception.showAndWait();
                    exit = false;
                }
                if(exit){
                    FileWriter fileWriter = null;
                    BufferedWriter bufferedWriter = null;
                    
                    try{
                        fileWriter = new FileWriter(file.getAbsoluteFile());
                        bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(text);
                        tab.changeSaved();
                    } catch (FileNotFoundException fnfe){
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("File Not Found");
                        confirm.setContentText("The file does not exist. Please select another file location to save to.");

                        Optional<ButtonType> result = confirm.showAndWait();
                        if(result.get() == ButtonType.OK){
                            saveAsF(tab, text);
                        }
                    } catch(IOException ioe){
                        //put something
                    } finally{
                        try {
                            bufferedWriter.close();
                            fileWriter.close();
                        } catch (IOException ioe) {
                            //put something
                        }
                    }
                }
            }
        }
    }
    
    public void saveAsF(OurTab tab, String text){
        boolean create = false;
        FileChooser filec = new FileChooser();
        filec.setTitle("Saving" + tab.getPath());
        filec.setInitialFileName(tab.getPath());
        filec.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAVA files (*.java)", "*.java"));
        filec.setInitialDirectory(new File(ide.getWorkspace()));
        File file = filec.showSaveDialog(null);
        if(file == null)
            return;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            System.out.println("hello");
           // if(!closing){
                if(!tab.hasBeenSaved()){
                    tab.setPath(file.getAbsolutePath());
                }
                else{
                    create = true;
                }
          //  }
            
        } catch(FileNotFoundException fnfe){
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("File Not Found");
            confirm.setContentText("The file could not be found. Please select a different file.");

            Optional<ButtonType> result = confirm.showAndWait();
            if(result.get() == ButtonType.OK){
                saveAsF(tab, text);
            }
        } catch (IOException ioe){
            //put something
        } finally{
            try{
                bufferedWriter.close();
                fileWriter.close();
            } catch(IOException ioe){
                //put something
            }
        }
        if(create){
            ide.createNewTab(file.getAbsolutePath());
        }
    }
    
    public void saveAllF(){
        for(int i = 1; i < ide.getTabsSize(); i++){
            OurTab tab = ide.nextActiveTab(i);
            //if(tab.hasBeenSaved()){
            saveF(tab, tab.getContentText());
            //}
            //else{
            //    saveAsF(tab, tab.getContentText(), closing);
            //}
        }
    }
    
    public final void printF(){
    }
    
    public boolean close(){
    	boolean isNotSaved = true;
    	for(int i = 1; i < ide.getTabsSize(); i++){
    		if(!((OurTab)ide.getTabbedArea().getTabs().get(i)).getSaved()){
    			isNotSaved = false;
    		}
    	}
        if(ide.getTabsSize() > 1 && !isNotSaved){
            ButtonType btnYes = new ButtonType("Yes");
            ButtonType btnNo = new ButtonType("No");

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Save File");
            confirm.setContentText("Do you want to save all of your files!");

            confirm.getButtonTypes().setAll(btnYes, btnNo, ButtonType.CANCEL);

            Optional<ButtonType> result = confirm.showAndWait();

            if(result.get() == btnYes){
                saveAllF();
            } else if(result.get() == ButtonType.CANCEL) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void handle(ActionEvent t) {
        if(t.getSource() == newFile){
            newF();
        }
        else if(t.getSource() == openFile){
            openF();
        }
        else if(t.getSource() == saveFile){
            saveF(ide.lastActiveTab(), ide.lastActiveTab().getContentText());
        }
        else if(t.getSource() == saveAsFile){
            saveAsF(ide.lastActiveTab(), ide.lastActiveTab().getContentText());
        }
        else if(t.getSource() == saveAllFile){
            saveAllF();
        }
        else if(t.getSource() == printFile){
            printF();
        }
        else if(t.getSource() == closeFile){
            if(close())
            	System.exit(0);
        }
    }
}

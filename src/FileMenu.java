package ouride;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.print.PageLayout;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

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
                Action select = Dialogs.create()
                        .nativeTitleBar()
                        .title("File Error")
                        .message(file.getAbsolutePath() + " could not be opened.")
                        .showError();
            }
        }
    }

    public void saveOption(OurTab tab, String text){
        Action[] buttons = {Dialog.Actions.YES, Dialog.Actions.NO};
        Action select = Dialogs.create()
              .actions(buttons)
              .nativeTitleBar()
              .title("Save File")
              .message("Do you want to save " + tab.getPath() + "!")
              .showConfirm();

        if(select == Dialog.Actions.YES){
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
                    Action secure = Dialogs.create()
                            .nativeTitleBar()
                            .title("Security Warning")
                            .message("This file cannot be accessed due to denied read access.")
                            .showException(se);
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
                        Action[] buttons = {Dialog.Actions.CANCEL, Dialog.Actions.OK};
                        Action select = Dialogs.create()
                              .actions(buttons)
                              .nativeTitleBar()
                              .title("File Not Found")
                              .message("The file does not exist. Please select another file location to save to.")
                              .showError();

                        if(select == Dialog.Actions.OK){
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
            Action[] buttons = {Dialog.Actions.CANCEL, Dialog.Actions.OK};
            Action select = Dialogs.create()
                  .actions(buttons)
                  .nativeTitleBar()
                  .title("File Not Found")
                  .message("The file could not be found. Please select a different file.")
                  .showError();

            if(select == Dialog.Actions.OK){
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
        //Text text = ide.lastActiveTab().getContentText();
        //text.setWrapText(true);
        //Printer printer = Printer.getDefaultPrinter();
        //PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        //double scaleX = pageLayout.getPrintableWidth() / text.getBoundsInParent().getWidth();
        //double scaleY = pageLayout.getPrintableHeight() / text.getBoundsInParent().getHeight();
        //pageLayout.
        //text.getTransforms().add(new Scale(scaleX, scaleY));
        //text.setStyle("-fx-font-size:12;");
 
        /*Text text = setText(ide.lastActiveTab().getContentText());

        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = job.getPrinter();
        if (job != null) {
            
            boolean success = job.showPrintDialog(null);
            if (success) {
                job.printPage(text);
                job.endJob();
            }
        }*/
    }
    
    public boolean close(){
    	boolean isNotSaved = true;
    	for(int i = 1; i < ide.getTabsSize(); i++){
    		if(!((OurTab)ide.getTabbedArea().getTabs().get(i)).getSaved()){
    			isNotSaved = false;
    		}
    	}
        if(ide.getTabsSize() > 1 && !isNotSaved){
            Action[] buttons = {Dialog.Actions.YES, Dialog.Actions.NO, Dialog.Actions.CANCEL};
            Action select = Dialogs.create()
                  .actions(buttons)
                  .nativeTitleBar()
                  .title("Save File")
                  .message("Do you want to save all of your files!")
                  .showConfirm();

            if(select == Dialog.Actions.YES){
                saveAllF();
            }
            else if(select == Dialog.Actions.CANCEL){
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

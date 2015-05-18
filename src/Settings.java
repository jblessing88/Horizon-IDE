package ouride;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Settings {
    final String path = "config.txt";
    private String fontType;
    private int fontSize;
    private Scheme scheme;
    private boolean wordWrap;
    private boolean autoComplete;
    private OurIDE ide;
    private Stage stage;
    private String delimiter;

    public Settings(OurIDE aIDE){
	stage = new Stage();
        ide = aIDE;
        if(System.getProperty("os.name").contains("Windows")){
            delimiter = "\\";
        }
        else{
            delimiter = "/";
                }
        //importFonts();
        try{
            Scanner scanner = new Scanner(new File(path));
            int counter = 1;

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String variable = line.substring(line.indexOf(":") + 1);
                switch(line.substring(0, line.indexOf(":"))){
                    case "[font type]":
                        fontType = variable;
                        break;
                    case "[font size]":
                        fontSize = Integer.parseInt(variable);
                        break;
                    case "[scheme]":
                        scheme = (variable.equals("DESERT")) ? Scheme.DESERT : Scheme.PLAIN;
                        break;
                    case "[word wrap]":
                        wordWrap = (variable.equals("true")) ? true : false;
                        break;
                    case "[auto complete]":
                        autoComplete = (variable.equals("true")) ? true : false;
                        break;
                }
                counter++;
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    /*private void importFonts(){
        Font.loadFont(Settings.class.getResource(".." + delimiter + "libs" + delimiter + "fonts" + delimiter + "Inconsolata" + delimiter + "Inconsolata-g.ttf").toExternalForm(), 10);
    }*/

    public void setAll(String aFontType, int aFontSize, Scheme aScheme, boolean aWordWrap, boolean aAutoComplete){
        fontType = aFontType;
        fontSize = aFontSize;
        scheme = aScheme;
        wordWrap = aWordWrap;
        autoComplete = aAutoComplete;

        set();
    }

    public void set(){
        try{
            PrintWriter writer = new PrintWriter(path);
            writer.println("[font type]:" + fontType);
            writer.println("[font size]:" + fontSize);
            writer.println("[scheme]:" + scheme);
            writer.println("[word wrap]:" + wordWrap);
            writer.println("[auto complete]:" + autoComplete);
            writer.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(){
	    ObservableList<Tab> list = ide.tabs.getTabs();
        if(scheme == Scheme.DESERT){
            ide.getScene().getStylesheets().removeAll("default.css");
            ide.getScene().getStylesheets().add("dessert.css");
        }
        else{
            ide.getScene().getStylesheets().removeAll("dessert.css");
            //ide.getScene().getStylesheets().add("libs" + delimiter + "css" + delimiter + "default.css");
        }
        for(Tab x : list){
                        try{
                                if(x.getText().equals("\u2715" + "   "))
                                        continue;

                                TextArea textArea = (TextArea) x.getContent();

                                textArea.setStyle("-fx-font-family: " + fontType + ";-fx-font-size: " + fontSize + ";");



                                textArea.setWrapText(wordWrap);
                        }catch(Exception e){
                                System.out.println("Unable to find a text area for a tab");
                        }
        }
    }

    public String getFont(){
        return fontType;
    }

    public int getFontSize(){
        return fontSize;
    }

    public Scheme getScheme(){
        return scheme;
    }

    public boolean getWordWrap(){
        return wordWrap;
    }

    public boolean getAutoComplete(){
        return autoComplete;
    }

    public void open(){
	final Settings settings = this;
        final Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");

        GridPane grid = new GridPane();
        Label fontLabel = new Label("Font : ");
        Label fontSizeLabel = new Label("Font Size : ");
        Label schemeLabel = new Label("Scheme : ");
        Label wordWrapLabel = new Label("Word Wrap : ");
        Label autoCompleteLabel = new Label("Auto Complete : ");

        List<String> fontList = Font.getFontNames();
        final ComboBox<String> fonts = new ComboBox<String>();
        for(String a : fontList){
            fonts.getItems().add(a);
        }
        fonts.setValue(settings.getFont());
	fonts.setDisable(true);


        final ComboBox<Integer> fontSize = new ComboBox<Integer>();
        fontSize.getItems().addAll(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 32, 64, 72);
        fontSize.setValue(settings.getFontSize());
	fontSize.setDisable(true);

        final ComboBox<String> scheme = new ComboBox<String>();
        scheme.getItems().addAll("Default", "Desert");
        if(settings.getScheme() == Scheme.DESERT)
            scheme.setValue("Desert");
        else
            scheme.setValue("Default");

        final CheckBox wordWrap = new CheckBox();
        wordWrap.setSelected(settings.getWordWrap());

        final CheckBox autoComplete = new CheckBox();
        autoComplete.setSelected(settings.getAutoComplete());

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            Scheme temp;
            if(scheme.getValue().equals("Desert"))
                temp = Scheme.DESERT;
            else
                temp = Scheme.PLAIN;
            settings.setAll(fonts.getValue(), fontSize.getValue(), temp, wordWrap.isSelected(), autoComplete.isSelected());
            settings.update();
            settingsStage.close();
            }
        });
        Button apply = new Button("Apply");
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scheme temp;
                if(scheme.getValue().equals("Desert"))
                    temp = Scheme.DESERT;
                else
                    temp = Scheme.PLAIN;
                settings.setAll(fonts.getValue(), fontSize.getValue(), temp, wordWrap.isSelected(), autoComplete.isSelected());
                settings.update();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                settingsStage.close();
            }
        });

        grid.add(fontLabel, 0, 0, 3, 1);
        grid.add(fontSizeLabel, 0, 1, 3, 1);
        grid.add(schemeLabel, 0, 2, 3, 1);
        grid.add(wordWrapLabel, 0, 3, 3, 1);
        grid.add(autoCompleteLabel, 0, 4, 3, 1);

        grid.add(fonts, 4, 0);
        grid.add(fontSize, 4, 1);
        grid.add(scheme, 4, 2);
        grid.add(wordWrap, 4, 3);
        grid.add(autoComplete, 4, 4);

        grid.add(submit, 5, 6);
        grid.add(apply, 6, 6);
        grid.add(cancel, 7, 6);

        settingsStage.setScene(new Scene(grid));
        settingsStage.show();	    
    }

}

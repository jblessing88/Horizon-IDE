package main.java.ouride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class HelpMenu extends Menu implements EventHandler<ActionEvent>{
    private MenuItem aboutHorizon;
    private MenuItem survey;
    private MenuItem help;

    public HelpMenu(){
        this.setText("Help");

        aboutHorizon = new MenuItem("About Horizon");
        aboutHorizon.setOnAction(this);
        this.getItems().add(aboutHorizon);

        survey = new MenuItem("Survey");
        survey.setOnAction(this);
        this.getItems().add(survey);

        help = new MenuItem("Help");
        help.setOnAction(this);
        this.getItems().add(help);
    }

    public void aboutHorizon(){
        String about = "HorizonIDE is a basic Integrated Development Environment (IDE) with additional features for better integration for new or inexperienced users into a software development world. The nature and intent of this program is to assist new or inexperienced programmers in learning the syntax and knowledge of multiple programming languages. The program will run on multiple operating systems (Mac, Windows, Linux, etc.) in order for the project to expand to a greater user base.";

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("About Horizon");
        info.setContentText(about);
        info.show();
    }

    public void survey(){
//        final Rating rating = new Rating();
//        final TextArea errors = new TextArea();
//        final TextArea comments = new TextArea();
//        final Action submit = new AbstractAction("Submit"){
//            {ButtonBar.setType(this, ButtonType.OK_DONE);}
//
//            @Override
//            public void execute(ActionEvent ae) {
//
//                Dialogs.create()
//                        .nativeTitleBar()
//                        .title("Confirmation")
//                        .message("Thank You for your advice!!")
//                        .showInformation();
//
//                ((Dialog) ae.getSource()).hide();
//            }
//        };
//        Dialog survey = new Dialog(null, "Survey", false, true);
//
//        final GridPane grid = new GridPane();
//        grid.setVgap(10);
//        RowConstraints row1 = new RowConstraints();
//        RowConstraints row2 = new RowConstraints();
//        RowConstraints row3 = new RowConstraints();
//        ColumnConstraints column1 = new ColumnConstraints();
//        ColumnConstraints column2 = new ColumnConstraints();
//
//        row1.setPrefHeight(rating.getPrefHeight());
//        row2.setPrefHeight(errors.getPrefHeight());
//        row3.setPrefHeight(comments.getPrefHeight());
//        column1.setPrefWidth(160);
//        column2.setPrefWidth(comments.getPrefHeight());
//
//        grid.getColumnConstraints().setAll(column1, column2);
//        grid.getRowConstraints().setAll(row1,row2,row3);
//
//        grid.add(new Label("Please Rate HorizonIDE!"),0,0);
//        grid.add(rating,1,0);
//        grid.add(new Label("Any Errors Found?"),0,1);
//        grid.add(errors,1,1);
//        grid.add(new Label("Any Suggestions?"),0,2);
//        grid.add(comments,1,2);
//
//        survey.setResizable(false);
//        survey.setIconifiable(false);
//        survey.setContent(grid);
//        survey.getActions().addAll(submit, Dialog.Actions.CANCEL);
//        survey.show();
    }

    public void help(){
//        Dialogs.create()
//                .nativeTitleBar()
//                .title("User Manual")
//                .message("Coming Soon")
//                .showInformation();
    }
    @Override
    public void handle(ActionEvent e){
        if(e.getSource() == aboutHorizon){
            aboutHorizon();
        }
        else if(e.getSource() == survey){
            survey();
        }
        else if(e.getSource() == help){
            help();
        }
    }
}

package ouride;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
//import sample.OurIDE;

import java.util.ArrayList;


public class ViewMenu extends Menu implements EventHandler<ActionEvent>{
    private MenuItem find;
    private MenuItem collapseAll;
    private MenuItem expandAll;
    private OurIDE ide;
    private int position;
    private String toFind;

    public ViewMenu(OurIDE aIDE){
        ide = aIDE;
        position = 0;

        this.setText("View");

        find = new MenuItem("Find");
        find.setOnAction(this);
        this.getItems().add(find);

        collapseAll = new MenuItem("Collapse All");
        collapseAll.setOnAction(this);
        this.getItems().add(collapseAll);

        expandAll = new MenuItem("Expand All");
        expandAll.setOnAction(this);
        this.getItems().add(expandAll);

        if(System.getProperty("os.name").indexOf("Mac") >= 0){
            find.setAccelerator(KeyCombination.keyCombination("Meta+F"));
        }
        else{
            find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        }
    }

    public void find(){
        final ArrayList<Integer> indexList = new ArrayList<Integer>();
        final HBox hbox = new HBox();
        final Region spacer = new Region();
        final Label findLabel = new Label("Find : ");
        final TextField findTextField = new TextField("");
        final Button findButton = new Button("Find!");
        final Button close = new Button("x");
        final Button previous = new Button("<");
        final Button next = new Button(">");
        final TextArea textArea = ide.getTabbedArea().getActiveTab().getTextArea();
        

        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if((textArea.getText().indexOf(findTextField.getText()) == -1) || (textArea.getText().equals(""))){
                    hbox.getChildren().removeAll(previous, next);
                    return;
                }
                toFind = findTextField.getText();
                int temp = 0;
                indexList.clear();
                position = 0;
                while(textArea.getText().indexOf(findTextField.getText(), temp) > -1){
                    indexList.add(textArea.getText().indexOf(findTextField.getText(), temp));
                    temp = textArea.getText().indexOf(findTextField.getText(), temp) + 1;
                }

                hbox.getChildren().removeAll(previous, next, spacer, close);
                hbox.getChildren().addAll(previous, next, spacer, close);
                textArea.requestFocus();
                textArea.positionCaret(indexList.get(position));
                textArea.selectRange(indexList.get(position),indexList.get(position)+toFind.length());
            }
        });

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                position++;
                if(indexList.size() - 1 < position){
                    position = 0;
                }
                textArea.requestFocus();
                textArea.positionCaret(indexList.get(position));
                textArea.selectRange(indexList.get(position),indexList.get(position)+toFind.length());
            }
        });

        previous.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                position--;
                if(position < 0){
                    position = indexList.size() - 1;
                }
                textArea.requestFocus();
                textArea.positionCaret(indexList.get(position));
                textArea.selectRange(indexList.get(position),indexList.get(position)+toFind.length());
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
        hbox.getChildren().addAll(findLabel, findTextField, findButton, spacer, close);
        ide.addToCenter(hbox);
  }

	public void collapseAll(){
		ide.collapseAll();
	}

    /*public void collapseFileManager(){
        //ide.getFileManager().getRoot().setExpanded(false);
        //treeRecursion(ide.getFileManager().getRoot(), false);
    }*/

	public void expandAll(){
		ide.expandAll();
	}

   /* public void expandFileManager(){
        //ide.getFileManager().getRoot().setExpanded(true);
        //treeRecursion(ide.getFileManager().getRoot(), true);
    }*/

    /*private void treeRecursion(TreeItem<String> treeItem, boolean expand){
        for(TreeItem<String> a : treeItem.getChildren()){
            treeRecursion(a, expand);
            a.setExpanded(expand);
        }
    }*/

    @Override
    public void handle(ActionEvent e){
        if(e.getSource() == find){
            find();
        }
        else if(e.getSource() == collapseAll){
            collapseAll();
        }
        else if(e.getSource() == expandAll){
            expandAll();
        }
    }
}

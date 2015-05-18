package ouride;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
//import sample.OurIDE;

import java.io.File;

public class OurFileManager extends VBox{
    String path;
    final OurIDE ide;
	String delimiter;    
	TreeView<String> fileManager;
	Button collapse;
	Button expand;
	HBox titleBar;
	Label title;
	Region spacer;
	boolean collapsed;

    public OurFileManager(String aPath, OurIDE aIDE){
        ide = aIDE;
        path = aPath;
		collapse = new Button("\u25c0");
		expand = new Button("\u25ba");
		fileManager = new TreeView<String>();
		title = new Label("File Manager");
		titleBar = new HBox();
		spacer = new Region();
		collapsed = false;

		titleBar.setHgrow(spacer, Priority.ALWAYS);
		titleBar.setPadding(new Insets(5, 5, 5, 5));
		titleBar.getChildren().addAll(title, spacer, collapse);

		collapse.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				collapse();
			}
		});

		expand.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				expand();
			}
		});

		if(System.getProperty("os.name").contains("Windows")){
			delimiter = "\\";
		}
		else{
			delimiter = "/";
		}

		this.createList();
		this.getChildren().addAll(titleBar, fileManager);
		this.setVgrow(fileManager, Priority.ALWAYS);
    }

    private TreeItem<String> buildList(String fileLocation){
        File[] list = new File(fileLocation).listFiles();
		TreeItem<String> tree;
		if(fileLocation.equals(path))
			tree = new TreeItem<String>("Workspace");
		else
			tree = new TreeItem<String>(fileLocation.substring(fileLocation.lastIndexOf(delimiter) + 1));

        for(File x : list){
            try{
                if(x.isDirectory() && !x.isHidden())
                    tree.getChildren().add(buildList(x.getAbsolutePath()));
                else if(!x.isHidden() && x.getAbsolutePath().substring(x.getAbsolutePath().lastIndexOf(".")).equals(".java")){
                    String name = x.getPath().substring(x.getPath().lastIndexOf(delimiter) + 1);
                    TreeItem<String> temp = new TreeItem<String>(name);
                    tree.getChildren().add(temp);

                }

            }
            catch(Exception e){
                e.getMessage();
            }
        }

        tree.setExpanded(false);
        return tree;
    }

	public void collapse(){
		if(collapsed == false){
			this.getChildren().remove(fileManager);
			titleBar.getChildren().removeAll(title, spacer, collapse);
			titleBar.getChildren().add(expand);
			collapsed = true;
		}
	}

	public void expand(){
		if(collapsed == true){
			this.getChildren().add(fileManager);
			titleBar.getChildren().removeAll(expand);
			titleBar.getChildren().addAll(title, spacer, collapse);
			collapsed = false;
		}
	}

    public void createList(){
        fileManager.setRoot(this.buildList(path));
        fileManager.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> stringTreeItem2) {
				try{
					if(stringTreeItem2 != null){
						if(stringTreeItem2.getValue().indexOf(".java") > - 1){
							//Note to self: Make it, if its highlighted then open, else highlight. This can solve the double click issue.
							if(stringTreeItem2.getValue().substring(stringTreeItem2.getValue().lastIndexOf(".java")).equals(".java")){
								String filePath = findPath(stringTreeItem2, "");
									filePath = path + delimiter + filePath.substring(filePath.indexOf(delimiter) + 1);
									try{
										ide.createNewTab(filePath);
									}
									catch(Exception e){
										System.out.println(e.getMessage());
									}
							}
						}
					}
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
            }
        });
    }

    private String findPath(TreeItem<String> treeItem, String treePath){
        if(treeItem.getValue() != null){
            if(treeItem.getValue().indexOf(".java") > -1)
                treePath = treeItem.getValue();
            else
                treePath = treeItem.getValue() + delimiter + treePath;

            if(treeItem.getParent() == null)
                return treePath;

            treePath = findPath(treeItem.getParent(), treePath);
        }
        return treePath;
    }

    public void setPath(String a){
        path = a;
    }
    
    public void refresh(){
    	this.getChildren().remove(fileManager);
    	fileManager = new TreeView<String>();
    	createList();
    	this.getChildren().add(fileManager);
    	this.setVgrow(fileManager, Priority.ALWAYS);
    }
}

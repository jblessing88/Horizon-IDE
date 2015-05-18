package ouride;

import com.sun.javafx.scene.control.skin.LabelSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;



public class OurTabbedArea extends TabPane implements EventHandler<Event>{
	private int untitled;
	private Tab tab = new Tab("\u2715");
	private OurIDE ide;
	public OurTabbedArea(OurIDE ide){
		super();
		this.ide = ide;
		untitled = 1;
		tab.setClosable(false);
		this.getTabs().add(tab);
		//tab.setStyle("-fx-background-insets: 0 10 0 0;-fx-cursor:hand;");
		tab.setTooltip(new Tooltip("Close All Tabs"));
		tab.setOnSelectionChanged(this);
	}
	
	private void setClosableButton(final Tab tab){
        final StackPane closeBtn = new StackPane(){
                @Override
                protected void layoutChildren() {
                        super.layoutChildren();
                        getParent().setStyle("-fx-content-display:right;");
                }
        };
        closeBtn.getChildren().add(new Label("\u2715"));
        closeBtn.setStyle("-fx-cursor:hand;");
        closeBtn.visibleProperty().bind(tab.selectedProperty());
        closeBtn.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent paramT) {
                		getSelectionModel().selectNext();
                		if(!((OurTab)tab).getSaved()){
                			ide.getFileMenu().saveOption((OurTab)(tab), ((OurTab)tab).getContentText());
                		}
                		OurTabbedArea.this.getTabs().remove(tab); 
                }
        });
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean isSelected) {
                        if(isSelected){
                                tab.setGraphic(closeBtn);
                        }else{
                                tab.setGraphic(null);
                        }
                }
        });
	}
	
	public OurTab getActiveTab(){
		for(int i = 0; i < this.getTabs().size(); i++)
			if(this.getTabs().get(i).isSelected())
				return (OurTab) this.getTabs().get(i);
		return null;
	}
	
	public void addTab(){
		final OurTab a = new OurTab(untitled);
		a.setClosable(false);
		setClosableButton(a);
		this.getTabs().addAll(a);
		this.getSelectionModel().select(a);
		untitled++;
	}
	
	public void addTab(String path){
		final OurTab a = new OurTab(path);
		a.setClosable(false);
		setClosableButton(a);
		boolean clone = false;
		OurTab b, c = a;
		for(int i = 1; i < getTabs().size(); i++){
			b= (OurTab)getTabs().get(i);
			if(b.getPath().equals(a.getPath())){
				clone = true;
				c = (OurTab)getTabs().get(i);
			}
		}	
		if(clone){
			getSelectionModel().select(c);
		}
		else{
			this.getTabs().addAll(a);
			this.getSelectionModel().select(a);
		}
	}
	
	public void removeTab(OurTab tab){
		this.getTabs().remove(tab);
	}

	@Override
	public void handle(Event arg0) {
			// TODO Auto-generated method stub
			tab.setClosable(false);
			
			if (tab.isSelected()&&getTabs().size()>1) {
				Tab tab1;
				for(int i = 1; i < getTabs().size();i++){
					tab1 = getTabs().get(i);
					if(!((OurTab)tab1).getSaved())
						ide.getFileMenu().saveOption((OurTab)(tab1), ((OurTab)tab1).getContentText());
				}
					getTabs().retainAll(tab);
			}	
	}
	
	public void update(ColorScheme c, Settings s){
		//setStyle("-fx-background: rgb(0,0,0);");
		//setStyle(c.getBackgroundCSS());
		/*for(int i = 0; i < getTabs().size();i++){
			if(this.getTabs().get(i)==tab){
				
			}
			else{
				((OurTab) this.getTabs().get(i)).update(c,s);
			}
		}*/
	}
}

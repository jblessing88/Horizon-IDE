package ouride;

import javafx.scene.control.MenuBar;
import ouride.CodeMenu;
import ouride.EditMenu;
import ouride.FileMenu;
import ouride.HelpMenu;
import ouride.OurIDE;
import ouride.ViewMenu;

public class OurMenuBar extends MenuBar{
    
    private FileMenu fmenu;
    private EditMenu emenu;
    private ViewMenu vmenu;
    private CodeMenu cmenu;
    private HelpMenu hmenu;
    private OurIDE ide = new OurIDE();
    
    public OurMenuBar(OurIDE a){
        
        ide = a;
        fmenu = new FileMenu(ide);
        emenu = new EditMenu(ide);
        vmenu = new ViewMenu(ide);
        cmenu = new CodeMenu(ide);
        hmenu = new HelpMenu();
        
        getMenus().addAll(fmenu, emenu, cmenu, vmenu, hmenu);
    }
    
    public FileMenu getFileMenu(){
        return fmenu;
    }
}

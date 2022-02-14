package uepb.agendamentoconsultas.ui.models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ModelMenu {
    private String icon, name;
    private MenuType type;
    
    public ModelMenu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public ModelMenu() {
    }
    
    public static enum MenuType{
        TITLE, MENU, EMPTY;
    }
    
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }
    
    public Icon toIcon(){
        return new ImageIcon(this.getClass().getResource("/icons/" + this.getIcon()));
    }
    
}

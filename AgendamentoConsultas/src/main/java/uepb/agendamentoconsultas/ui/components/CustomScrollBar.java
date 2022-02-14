package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public final class CustomScrollBar extends JScrollBar{
    
    private java.awt.Dimension sizeBar;
    private java.awt.Color colorBar;
    
    public CustomScrollBar(){
        this.sizeBar = new java.awt.Dimension(8, 8);
        this.colorBar = new java.awt.Color(48, 144, 216);
        
        setUI(new uepb.agendamentoconsultas.ui.models.CustomScrollBarUI());
        setPreferredSize(sizeBar);
        setForeground(colorBar);
        setBackground(java.awt.Color.WHITE);
    }

    public Dimension getSizeBar() {
        return sizeBar;
    }

    public void setSizeBar(Dimension sizeBar) {
        setPreferredSize(sizeBar);
        this.sizeBar = sizeBar;
        repaint();
    }

    public Color getColorBar() {
        return colorBar;
    }

    public void setColorBar(Color colorBar) {
        setForeground(colorBar);
        this.colorBar = colorBar;
        repaint();
    }
    
    
    
}

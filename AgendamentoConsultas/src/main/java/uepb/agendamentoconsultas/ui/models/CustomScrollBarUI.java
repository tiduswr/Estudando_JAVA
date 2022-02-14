package uepb.agendamentoconsultas.ui.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI{
    private final int THUMB_SIZE = 40;
    private Color barColor = new Color(240, 240, 240);
    
    @Override
    protected Dimension getMaximumThumbSize() {
        if(scrollbar.getOrientation() == JScrollBar.VERTICAL){
            return new Dimension(0, THUMB_SIZE);
        }else{
            return new Dimension (THUMB_SIZE, 0);
        }
    }
    
    @Override
    protected Dimension getMinimumThumbSize() {
        if(scrollbar.getOrientation() == JScrollBar.VERTICAL){
            return new Dimension(0, THUMB_SIZE);
        }else{
            return new Dimension (THUMB_SIZE, 0);
        }
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ScrollBarButton();
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ScrollBarButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        int orientation, size, x, y, width, height;
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        orientation = scrollbar.getOrientation();
        
        if(orientation == JScrollBar.VERTICAL){
            size = r.width / 2;
            x = r.x + ((r.width - size) / 2);
            y = r.y;
            width = size;
            height = r.height;
        }else{
            size = r.height / 2;
            y = r.y + ((r.height - size) / 2);
            x = 0;
            width = r.width;
            height = size;
        }
        
        g2.setColor(barColor);
        g2.fillRect(x, y, width, height);
    }
    
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g;
        
        int x = r.x;
        int y = r.y;
        int width = r.width;
        int height = r.height;
        
        if(scrollbar.getOrientation() == JScrollBar.VERTICAL){
            y += 8;
            height -= 16;
        }else{
            x += 8;
            width -= 16;
        }
        
        g2.setColor(scrollbar.getForeground());
        g2.fillRoundRect(x, y, width, height, 10, 10);
    }
    
    private final class ScrollBarButton extends JButton{
        public ScrollBarButton(){
            setBorder(BorderFactory.createEmptyBorder());
        }
        
        @Override
        public void paint(Graphics grphcs){
            
        }
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }
    
}

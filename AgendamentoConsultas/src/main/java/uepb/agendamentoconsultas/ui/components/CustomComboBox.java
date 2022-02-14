package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public final class CustomComboBox<E> extends JComboBox<E>{
    
    private String lblText;
    private Color lineColor;
    private Color labelColor;
    private Color popupMouseOverColor;
    private Font labelFont;
    private boolean mouseOver;
    
    public CustomComboBox(){
        lblText = "Label";
        this.lineColor  = new Color(3, 155, 216);
        this.labelColor = new Color(150,150,150);
        this.popupMouseOverColor = new Color(240,240,240);
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(15, 3, 5, 3));
        this.setUI(new ComboUI(this));
        
        setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(5,5,5,5));
                if(isSelected){
                    c.setBackground(popupMouseOverColor);
                }
                return c;
            }
        });
    }
    
    private class ComboUI extends BasicComboBoxUI{
        
        private final Animator animator; // TimingFramework
        private boolean animateHintText, show;
        private float location;
        private CustomComboBox combo;
        
        public ComboUI(CustomComboBox combo){
            animateHintText = true;
            this.combo = combo;
            
            addMouseListener(new MouseAdapter(){
        
                @Override
                public void mouseEntered(MouseEvent m){
                    mouseOver = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent m){
                    mouseOver = false;
                    repaint();
                }

            });
            addPopupMenuListener(new PopupMenuListener(){
            
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent pme){
                    arrowButton.setBackground(getLineColor());
                }
                
                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent pme){
                    arrowButton.setBackground(getLabelColor());
                }
                
                @Override
                public void popupMenuCanceled(PopupMenuEvent pme){
                    arrowButton.setBackground(getLabelColor());
                }
                
            });
            addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(!isFocusOwner()){
                        if(getSelectedIndex() == -1){
                            showing(true);
                        }else{
                            showing(false);
                        }
                    }
                }
            });
            addFocusListener(new FocusAdapter(){

                @Override
                public void focusGained(FocusEvent e) {
                    showing(false);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    showing(true);
                }
            });
            TimingTarget target = new TimingTargetAdapter(){
                @Override
                public void begin() {
                    animateHintText = getSelectedIndex() == -1;
                }
                @Override
                public void timingEvent(float fraction) {
                    location = fraction;
                    repaint();
                }
            };
            animator = new Animator(300, target);
            animator.setResolution(0);
            animator.setAcceleration(0.5f);
            animator.setDeceleration(0.5f);
        
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            
        }
        
        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }
        
        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup p = new BasicComboPopup(combo){
                @Override
                protected JScrollPane createScroller() {
                    list.setFixedCellHeight(30);
                    JScrollPane s = new JScrollPane(list);
                    s.setBackground(Color.WHITE);
                    CustomScrollBar sb = new CustomScrollBar();
                    sb.setForeground(lineColor);
                    s.setVerticalScrollBar(sb);
                    return s;
                }
            };
            p.setBorder(new LineBorder(lineColor,1));
            return p;
        }
        
        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            if(mouseOver){
                g2.setColor(lineColor);
            }else{
                g2.setColor(labelColor);
            }

            g2.fillRect(2, height - 1, width - 4, 1);
            createHintText(g2);
            createLineStyle(g2);
            g2.dispose();
        }
        
        private void createHintText(Graphics2D g2){
            Insets in = getInsets();
            g2.setColor(combo.getLabelColor());
            g2.setFont(combo.getFont());
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(combo.getLblText(), g2);
            double height = getHeight() - in.top - in.bottom;
            double textY = (height - r2.getHeight()) / 2;
            double size;
            if(animateHintText){
                if(show){
                    size = 18 * (1 - location);
                }else{
                    size = 18 * location;
                }
            }else{
                size = 18;
            }
            g2.drawString(combo.getLblText(), in.right, (int)(in.top + textY + ft.getAscent() - size));
        }

        private void createLineStyle(Graphics2D g2){
            if(isFocusOwner()){
                double size;
                double width = getWidth() - 4;
                int height = getHeight();
                g2.setColor(lineColor);

                if(show){
                    size = width * (1 - location);
                }else{
                    size = width * location;
                }
                double x = (width - size) / 2;
                g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
            }
        }
        
        private void showing(boolean action){
            if(animator.isRunning()){
                animator.stop();
            }else{
                location = 1;
            }
            animator.setStartFraction(1f - location);
            show = action;
            location = 1f - location;
            animator.start();
        }
        
        private final class ArrowButton extends JButton{
            
            public ArrowButton(){
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5,5,5,5));
                setBackground(getLabelColor());
            }
            
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                int width, height, size, x, y;
                
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                width = getWidth();
                height = getHeight();
                size = 10;
                x = (width - size) / 2;
                y = (height - size) / 2 + 2;
                int px[] = {x, x+size, x+size / 2};
                int py[] = {y, y, y+ size};
                g2.setColor(getBackground());
                g2.fillPolygon(px, py, px.length);
                g2.dispose();
            }
        }
        
    }

    public String getLblText() {
        return lblText;
    }

    public void setLblText(String lblText) {
        this.lblText = lblText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }

    public Color getPopupMouseOverColor() {
        return popupMouseOverColor;
    }

    public void setPopupMouseOverColor(Color popupMouseOverColor) {
        this.popupMouseOverColor = popupMouseOverColor;
    }
    
}

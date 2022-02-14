package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public final class CustomButton extends JButton{
    
    private AnimationStyle animationHover, animationPress;
    private ButtonStyle style = ButtonStyle.PRIMARY;
    private ButtonColor currentStyle = new ButtonColor(ButtonStyle.PRIMARY);
    private int round = 5;
    
    public CustomButton(){
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(8,8,8,8));
        setForeground(Color.white);
        initAnimation();
        addMouseListener(new MouseAdapter(){
        
            @Override
            public void mouseEntered(MouseEvent e) {
                animationHover.start(currentStyle.backgroundHover, style.backgroundHover);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                animationHover.start(currentStyle.backgroundHover, style.background);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                animationPress.start(currentStyle.background, style.backgroundPress);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                animationPress.start(currentStyle.background, style.background);
            }
            
        });
    }

    public ButtonStyle getStyle() {
        return style;
    }

    public void setStyle(ButtonStyle style) {
        if(this.style != style) {
            this.style = style;
            animationHover.stop();
            animationPress.stop();
            currentStyle.changeStyle(style);
            setForeground(style.foreground);
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }
    
    private void initAnimation(){
        animationHover = new AnimationStyle(300, currentStyle, "backgroundHover");
        animationHover.addTarget(new TimingTargetAdapter(){
        
            @Override
            public void timingEvent(float fraction) {
                repaint();
            }
            
        });
        animationPress = new AnimationStyle(200, currentStyle, "background");
        animationPress.addTarget(new TimingTargetAdapter(){
        
            @Override
            public void timingEvent(float fraction) {
                repaint();
            }
        
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        int x,y, width, height;
        Area area;
        Graphics2D g2;
        
        g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        x = 0;
        y = 0;
        width = getWidth();
        height = getHeight();
        area = new Area(new RoundRectangle2D.Double(x, y, width, height, round, round));
        g2.setColor(currentStyle.background);
        g2.fill(area);
        area.subtract(new Area(new RoundRectangle2D.Double(x, y, width, height - 2, round, round)));
        g2.setColor(currentStyle.backgroundHover);
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }
    
    public enum ButtonStyle{
        PRIMARY(new Color(0, 172, 126), new Color(238,238,238), new Color(2,11,82), new Color(3,205,151)), 
        SECONDARY(new Color(203, 209, 219), new Color(58,70,81), new Color(8, 92,108), new Color(230, 239, 255)), 
        DESTRUCTIVE(new Color(255,138,48), new Color(238,238,238), new Color(198, 86, 0), new Color(255,161,90));
        
        private ButtonStyle(Color background, Color foreground, Color backgroundHover, Color backgroundPress){
            this.background = background;
            this.foreground = foreground;
            this.backgroundHover = backgroundHover;
            this.backgroundPress = backgroundPress;
        }
        
        private Color background, foreground, backgroundHover, backgroundPress;
    }
    
    protected class ButtonColor{
        private Color background, foreground, backgroundHover, backgroundPress;
        
        public ButtonColor(ButtonStyle b){
            changeStyle(b);
        }
        
        public ButtonColor(){}
        
        public Color getBackground() {
            return background;
        }

        public void setBackground(Color background) {
            this.background = background;
        }

        public Color getForeground() {
            return foreground;
        }

        public void setForeground(Color foreground) {
            this.foreground = foreground;
        }

        public Color getBackgroundHover() {
            return backgroundHover;
        }

        public void setBackgroundHover(Color backgroundHover) {
            this.backgroundHover = backgroundHover;
        }

        public Color getBackgroundPress() {
            return backgroundPress;
        }

        public void setBackgroundPress(Color backgroundPress) {
            this.backgroundPress = backgroundPress;
        }
        
        private void changeStyle(ButtonStyle b){
            this.background = b.background;
            this.foreground = b.foreground;
            this.backgroundHover = b.background;
            this.backgroundPress = b.backgroundPress;
        }
        
    }
    
    private class AnimationStyle{
        private final Animator animator;
        private final ButtonColor style;
        private final String property;
        private TimingTarget target;

        public AnimationStyle(int duration, ButtonColor style, String property) {
            this.style = style;
            this.property = property;
            this.animator = new Animator(duration);
        }
        
        public void start(Color...colors){
            stop();
            animator.removeTarget(target);
            target = new PropertySetter(style, property, colors);
            animator.addTarget(target);
            animator.start();
        }
        
        public void addTarget(TimingTarget target){
            animator.addTarget(target);
        }
        
        public void stop(){
            if(animator.isRunning()){
                animator.stop();
            }
        }
    }
    
}

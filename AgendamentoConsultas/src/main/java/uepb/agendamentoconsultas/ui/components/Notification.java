package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import uepb.agendamentoconsultas.ui.models.shadowRenderer.ShadowRenderer;

public class Notification extends javax.swing.JComponent {
    private BufferedImage imageShadow;
    private int shadowSize;
    private JDialog dialog;
    private Animator animator;
    private final Frame fram;
    private boolean showing;
    private Thread thread;
    private int animate;
    private Type type;
    private Location location;
    private boolean translucentIsSupported;
    
    public Notification(Frame fram, Type type, Location location, String message) {
        this.shadowSize = 6;
        this.animate = 10;
        this.fram = fram;
        this.type = type;
        this.location = location;
        initComponents();
        init(message);
        initAnimator();
    }
    
    private void init(String message){
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        translucentIsSupported = gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
        
        setBackground(Color.WHITE);
        dialog = new JDialog(fram);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        
        if(translucentIsSupported){
            dialog.setBackground(new Color(0,0,0,0));
        }else{
            dialog.setBackground(Color.WHITE);
        }
        
        dialog.add(this);
        dialog.setSize(getPreferredSize());
        if(null != type)switch (type) {
            case SUCESS:
                lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sucess.png")));
                lblMessage.setText("Concluido!");
                break;
            case INFO:
                lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png")));
                lblMessage.setText("Informação");
                break;
            case WARNING:
                lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/warning.png")));
                lblMessage.setText("Atenção!");
                break;
            default:
                break;
        }
        lblMessageText.setText(message);
    }
    
    private void initAnimator(){
        TimingTarget target = new TimingTarget(){
            
            private int x, top;
            private boolean top_to_bot;
            
            @Override
            public void timingEvent(float f) {
                float alpha;
                int y;
                
                if(showing){
                    alpha = 1f - f;
                    y = (int) ((1f - f) * animate);
                }else{
                    alpha = f;
                    y = (int) (f * animate);
                }
                
                if(top_to_bot){
                    dialog.setLocation(x, top + y);
                }else{
                    dialog.setLocation(x, top - y);
                }
                if(translucentIsSupported) dialog.setOpacity(alpha);
                repaint();
            }
            
            @Override
            public void begin() {
                if(!showing){
                    if(translucentIsSupported) dialog.setOpacity(0f);
                    int margin = 10;
                    int y = 0;
                    if (null == location) {
                        x = fram.getX() + ((fram.getWidth() - dialog.getWidth()) / 2);
                        y = fram.getY() + ((fram.getHeight() - dialog.getHeight()) / 2);
                        top_to_bot = true;
                    } else switch (location) {
                        case TOP_CENTER:
                            x = fram.getX() + ((fram.getWidth() - dialog.getWidth()) / 2);
                            y = fram.getY();
                            top_to_bot = true;
                            break;
                        case TOP_RIGHT:
                            x = fram.getX() + fram.getWidth() - dialog.getWidth() - margin;
                            y = fram.getY();
                            top_to_bot = true;
                            break;
                        case TOP_LEFT:
                            x = fram.getX() + margin;
                            y = fram.getY();
                            top_to_bot = true;
                            break;
                        case BOTTOM_CENTER:
                            x = fram.getX() + ((fram.getWidth() - dialog.getWidth()) / 2);
                            y = fram.getY() + fram.getHeight() - dialog.getHeight();
                            top_to_bot = false;
                            break;
                        case BOTTOM_RIGHT:
                            x = fram.getX() + fram.getWidth() - dialog.getWidth() - margin;
                            y = fram.getY() + fram.getHeight() - dialog.getHeight();
                            top_to_bot = false;
                            break;
                        case BOTTOM_LEFT:
                            x = fram.getX() + margin;
                            y = fram.getY() + fram.getHeight() - dialog.getHeight();
                            top_to_bot = false;
                            break;
                        default:
                            x = fram.getX() + ((fram.getWidth() - dialog.getWidth()) / 2);
                            y = fram.getY() + ((fram.getHeight() - dialog.getHeight()) / 2);
                            top_to_bot = true;
                            break;
                    }
                    top = y;
                    dialog.setLocation(x, y);
                    dialog.setVisible(true);
                }
            }
            
            @Override
            public void end() {
                showing = !showing;
                if(showing){
                    thread = new Thread(new Runnable(){
                    
                        @Override
                        public void run() {
                            sleep();
                            closeNotification();
                        }
                        
                    });
                    thread.start();
                }else{
                    dialog.dispose();
                }
            }
            
            @Override
            public void repeat() {
                
            }
            
        };
        animator = new Animator(500, target);
        animator.setResolution(5);
    }
    
    public void showNotification(){
        animator.start();
    }
    
    private void closeNotification(){
        if(thread != null && thread.isAlive()){
            thread.interrupt();
        }
        if(animator.isRunning()){
            if(!showing){
                animator.stop();
                showing = true;
                animator.start();
            }
        }else{
            showing = true;
            animator.start();
        }
    }
    
    private void sleep(){
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            
        }
    }
    
    @Override
    public void paint(Graphics g) {
        int x, y, width, height;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawImage(imageShadow, 0, 0, null);
        
        x = shadowSize;
        y = shadowSize;
        width = getWidth() - shadowSize *2;
        height = getHeight() - shadowSize * 2;
        
        g2.fillRect(x, y, width, height);
        if (null == type) {
            g2.setColor(new Color(241, 196, 15));
        } else switch (type) {
            case WARNING:
                g2.setColor(new Color(241, 196, 15));
                break;
            case INFO:
                g2.setColor(new Color(28, 139, 206));
                break;
            default:
                g2.setColor(new Color(18, 163, 24));
                break;
        }
        g2.fillRect(6, 5, 5, getHeight() - shadowSize*2 - 1);
        
        g2.dispose();
        super.paint(g);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        createImageShadow();
    }
    
    private void createImageShadow() {
        imageShadow = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
    }

    private BufferedImage createShadow() {
        BufferedImage img = new BufferedImage(getWidth() - shadowSize * 2, getHeight() - shadowSize * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();
        return new ShadowRenderer(shadowSize, 0.3f, new Color(100, 100, 100)).createShadow(img);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        lblMessageText = new javax.swing.JLabel();
        cmdClose = new javax.swing.JButton();

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sucess.png"))); // NOI18N

        panel.setOpaque(false);

        lblMessage.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(38, 38, 38));
        lblMessage.setText("Mensagem");

        lblMessageText.setForeground(new java.awt.Color(127, 127, 127));
        lblMessageText.setText("Texto da mensagem");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessageText)
                    .addComponent(lblMessage))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMessageText)
                .addContainerGap())
        );

        cmdClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        cmdClose.setBorder(null);
        cmdClose.setContentAreaFilled(false);
        cmdClose.setFocusable(false);
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdClose)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        closeNotification();
    }//GEN-LAST:event_cmdCloseActionPerformed

    public static enum Type{
        SUCESS, INFO, WARNING;
    }
    public static enum Location{
        TOP_CENTER, TOP_RIGHT, TOP_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdClose;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblMessageText;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}

package uepb.agendamentoconsultas.ui.centralmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import uepb.agendamentoconsultas.ui.models.ModelMenu;

public class MenuItem extends javax.swing.JPanel {
    
    private boolean selected;
    private boolean over;
    
    public MenuItem(ModelMenu data) {
        initComponents();
        this.setOpaque(false);
        if(null != data.getType()){
            if (data.getType() == ModelMenu.MenuType.MENU) {
                lblIcon.setIcon(data.toIcon());
                lblName.setText(data.getName());
            } else if (data.getType() == ModelMenu.MenuType.TITLE) {
                lblIcon.setText(data.getName());
                lblIcon.setFont(new Font("sansserif", 1, 12));
                lblName.setVisible(false);
            } else {
                lblName.setText(" ");
            }
        }else{
            lblName.setText(" ");
        }
    }
    
    public void setSelected(boolean s){
        this.selected = s;
        repaint();
    }
    public void setOver(boolean over){
        this.over = over;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(200, 40));

        lblIcon.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblIcon.setForeground(new java.awt.Color(255, 255, 255));

        lblName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Menu Item");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblIcon)
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    protected void paintComponent(Graphics g) {
        if(selected || over){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(selected){
                g2.setColor(new Color(255,255,255,80));
            }else{
                g2.setColor(new Color(255,255,255,20));
            }
            g2.fillRoundRect(10, 0, this.getWidth() - 20, this.getHeight(), 5, 5);
        }
        super.paintComponent(g);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}

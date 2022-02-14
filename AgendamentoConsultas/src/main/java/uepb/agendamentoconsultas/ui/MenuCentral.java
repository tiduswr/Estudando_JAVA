package uepb.agendamentoconsultas.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import uepb.agendamentoconsultas.ui.event.EventMenuSelected;
import uepb.agendamentoconsultas.ui.forms.CadastrarPessoa;

import javax.swing.JFrame;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.ui.forms.AdministrarConsulta;
import uepb.agendamentoconsultas.ui.forms.AgendarConsulta;

public final class MenuCentral extends JFrame {
    private static JFrame form;
    private static DataBase db;
    private static boolean fullScreen = false;
    
    public MenuCentral() {
        initComponents();
        
        //Checa se o sistema permite telas translucidas
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if(gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)){
            setBackground(new Color(0,0,0,0)); 
        }else{
            setBackground(Color.WHITE); 
        }
        
        this.setPreferredSize(this.getSize());
        this.setTitle("Agendamento de Consultas");
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/agenda.png")).getImage());
        
        menuLateral.initMoving(MenuCentral.this);
        mainPanel.setLayout(new BorderLayout());
        menuLateral.addEventMenuSelected(new EventMenuSelected(){
            @Override
            public void selected(int index){
                switch (index) {
                    case 1:
                        AgendarConsulta form1 = new AgendarConsulta();
                        setForm(form1);
                        form1.clearFields();
                        form1.repaint();
                        break;
                    case 2:
                        CadastrarPessoa form2 = new CadastrarPessoa();
                        setForm(form2);
                        form2.clearFields();
                        break;
                    case 3:
                        setForm(new AdministrarConsulta());
                        break;
                    case 4:
                        db.saveData();
                        dispose();
                        break;
                    default:
                        break;
                }
            }
        });
        this.addWindowListener(new WindowAdapter(){
        
            @Override
            public void windowClosing(WindowEvent e) {
                db.saveData();
            }
        
        });
    }
    
    private void setForm(JComponent c){
        mainPanel.removeAll();
        mainPanel.add(c);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static DataBase getDataBase() {
        return db;
    }
    
    public static boolean isFullScreen(){
        return fullScreen;
    }
    
    public static void setFullScreen(boolean v){
        fullScreen = v;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder = new uepb.agendamentoconsultas.ui.centralmenu.PanelBorder();
        menuLateral = new uepb.agendamentoconsultas.ui.centralmenu.MenuLateral();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        panelBorder.setBackground(new java.awt.Color(255, 255, 255));

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelBorderLayout = new javax.swing.GroupLayout(panelBorder);
        panelBorder.setLayout(panelBorderLayout);
        panelBorderLayout.setHorizontalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addComponent(menuLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorderLayout.setVerticalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuLateral, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuCentral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuCentral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuCentral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuCentral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            db = new DataBase();
            form = new MenuCentral();
            form.setVisible(true);
        });
    }
    
    public static JFrame getFrame(){
        return form;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private uepb.agendamentoconsultas.ui.centralmenu.MenuLateral menuLateral;
    private uepb.agendamentoconsultas.ui.centralmenu.PanelBorder panelBorder;
    // End of variables declaration//GEN-END:variables
}

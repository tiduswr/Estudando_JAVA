package uepb.agendamentoconsultas.ui.centralmenu;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.ui.event.EventMenuSelected;
import uepb.agendamentoconsultas.ui.models.ModelMenu;

public final class MenuLateral extends javax.swing.JPanel {
    
    private EventMenuSelected event;
    private static Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    
    public MenuLateral() {
        initComponents();
        setOpaque(false);
        listMenu.setOpaque(false);
        init();
    }

    private void init(){
        this.listMenu.addItem(new ModelMenu("", "Ações do Usuário", ModelMenu.MenuType.TITLE));
        this.listMenu.addItem(new ModelMenu("agendar.png", "Agendar Consulta", ModelMenu.MenuType.MENU));
        this.listMenu.addItem(new ModelMenu("cadastrar.png", "Cadast./Buscar Pessoa", ModelMenu.MenuType.MENU));
        this.listMenu.addItem(new ModelMenu("buscar.png", "Administrar Consulta", ModelMenu.MenuType.MENU));
        this.listMenu.addItem(new ModelMenu("excluir.png", "Sair", ModelMenu.MenuType.MENU));
    }
    
    public void addEventMenuSelected(EventMenuSelected e){
        this.event = e;
        this.listMenu.addEventMenuSelected(e);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        listMenu = new uepb.agendamentoconsultas.ui.models.ListMenu<>();

        setPreferredSize(new java.awt.Dimension(215, 454));

        panelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        //
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agenda.png"))); // NOI18N
        jLabel1.setText("Agendamentos");

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    //Código para deixar o formulário com bordas arredondadas
    @Override
    protected void paintChildren(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Código para adicionar um gradiente no formuário
        GradientPaint gp = new GradientPaint(0,0, Color.decode("#b92b27"), 0, this.getHeight(), Color.decode("#1565C0"));
        g2.setPaint(gp);
        //Deixa a borda esquerda arredondada e a direita reta
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
        g2.fillRect(this.getWidth() - 20, 0, this.getWidth(), this.getHeight());
        super.paintChildren(gr);
        
    }
    
    //Còdigo para fazer o Formulário se movimentar ao clicar e arrastar o menu lateral
    //Còdigo para maximizar e minimizar a tela
    private int x, y;
    public void initMoving(JFrame fr){
        panelMoving.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                
                if(e.getClickCount() == 2 && !e.isConsumed()){
                    
                    if(!MenuCentral.isFullScreen()){
                        MenuCentral.getFrame().setSize(r.width, r.height);
                        MenuCentral.setFullScreen(true);
                    }else{
                        MenuCentral.getFrame().setSize(MenuCentral.getFrame().getPreferredSize());
                        MenuCentral.getFrame().setLocationRelativeTo(null);
                        MenuCentral.setFullScreen(false);
                    }
                    e.consume();
                }else{
                    x = e.getX();
                    y = e.getY();
                }
                
            }
        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                fr.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        });
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private uepb.agendamentoconsultas.ui.models.ListMenu<String> listMenu;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}

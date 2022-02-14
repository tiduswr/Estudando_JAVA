package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.models.PessoaTableModel;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.ui.models.shadowRenderer.ShadowRenderer;
import uepb.agendamentoconsultas.users.Pessoa;

public final class FindPessoa extends javax.swing.JComponent {
    private BufferedImage imageShadow;
    private int shadowSize;
    private JDialog dialog;
    private final Frame fram;
    private boolean translucentIsSupported;
    private PessoaTableModel dataModel;
    private TableRowSorter trs;
    private Pessoa pessoaSelected;
    private int x, y; // Moving frame control
    
    public FindPessoa(Frame fram, String title, String[] filtro) {
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        translucentIsSupported = gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
        
        initComponents();
        initMoving();
        
        this.shadowSize = 6;
        this.fram = fram;
        this.lblMessage.setText(title);
        setBackground(Color.WHITE);
        CustomTable.setBasicScrollConfigurations(jScrollPane1);
        this.dataModel = new PessoaTableModel(MenuCentral.getDataBase(), filtro);
        this.table.setModel(dataModel);
        this.table.setAutoCreateRowSorter(true);
        this.pessoaSelected = null;
    }
    
    private void init(){
        
        dialog = new JDialog(this.fram);
        dialog.add(this);
        dialog.setSize(getPreferredSize());
        dialog.setUndecorated(true);
        dialog.setModal(true);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(this.fram);
        
        if(translucentIsSupported){
            dialog.setBackground(new Color(0,0,0,0));
        }else{
            dialog.setBackground(Color.WHITE);
        }
        
    }
    
    public void showNotification(){
        init();
        dialog.setVisible(true);
    }
    
    public void closeNotification(){
        dialog.dispose();
    }
    
    public Pessoa getPessoaSelected(){
        return this.pessoaSelected;
    }
    
    private Pessoa findPessoa(String CPF, String tipo){
        Pessoa p;
        DataBase db = MenuCentral.getDataBase();
        
        switch(tipo){
            case "Paciente":
                p = db.getPacientes().getRecord(CPF);
                break;
            case "Médico":
                p = db.getMedicos().getRecord(CPF);
                break;
            default:
                p = db.getRecepcionistas().getRecord(CPF);
                break;
        }
        
        return p;
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
        width = getWidth() - shadowSize * 2;
        height = getHeight() - shadowSize * 2;
        
        g2.fillRoundRect(x, y, width, height, 15, 15);
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
    
    private void initMoving(){
        lblMessage.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
        lblMessage.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dialog.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new uepb.agendamentoconsultas.ui.components.CustomTable();
        cmdClose = new javax.swing.JButton();
        txtbBusca = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        lblMessage = new javax.swing.JLabel();

        panel.setOpaque(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
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

        txtbBusca.setLabelText("Buscar Nome");
        txtbBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbBuscaKeyTyped(evt);
            }
        });

        lblMessage.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(38, 38, 38));
        lblMessage.setText("Mensagem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtbBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        dialog.dispose();
    }//GEN-LAST:event_cmdCloseActionPerformed

    private void txtbBuscaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbBuscaKeyTyped
        txtbBusca.addKeyListener(new KeyAdapter(){
        
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter(txtbBusca.getText(), 1));
            }
            
        });
        
        trs = new TableRowSorter(dataModel);
        table.setRowSorter(trs);
    }//GEN-LAST:event_txtbBuscaKeyTyped

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if(table.getSelectedRow() != -1){
            String selectedCPF = table.getValueAt(table.getSelectedRow(), 0).toString();
            pessoaSelected = findPessoa(selectedCPF, table.getValueAt(table.getSelectedRow(), 3).toString());
            dialog.dispose();
        }
    }//GEN-LAST:event_tableMouseClicked

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdClose;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JPanel panel;
    private uepb.agendamentoconsultas.ui.components.CustomTable table;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbBusca;
    // End of variables declaration//GEN-END:variables
}

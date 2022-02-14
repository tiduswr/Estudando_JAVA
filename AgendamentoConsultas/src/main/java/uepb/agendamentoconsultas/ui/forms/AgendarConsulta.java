package uepb.agendamentoconsultas.ui.forms;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uepb.agendamentoconsultas.calendario.Dia;
import uepb.agendamentoconsultas.calendario.Hora;
import uepb.agendamentoconsultas.calendario.HoraDia;
import uepb.agendamentoconsultas.calendario.Periodo;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.models.AgendamentoTableModel;
import uepb.agendamentoconsultas.services.Agendamento;
import uepb.agendamentoconsultas.services.products.Consulta;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.ui.components.CustomTable;
import uepb.agendamentoconsultas.ui.components.FindPessoa;
import uepb.agendamentoconsultas.ui.components.Notification;
import uepb.agendamentoconsultas.users.Pessoa;

public final class AgendarConsulta extends javax.swing.JPanel {
    
    private FindPessoa find;
    private Agendamento agendar;
    private AgendamentoTableModel model;
    
    public AgendarConsulta() {
        initComponents();
        this.setBackground(Color.WHITE);
        this.txtbHoraInicial.setEditable(false);
        this.txtbHoraFinal.setEditable(false);
        agendar = new Agendamento(MenuCentral.getDataBase().getAgendamentos());
        CustomTable.setBasicScrollConfigurations(jScrollPane1);
        model = new AgendamentoTableModel(null, (DataBase) null);
        table.setModel(model);
        
        this.dtConsulta.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) {
                createTableModel();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                createTableModel();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {}
        });
                
    }
    
    private void createTableModel(){
        Dia d = agendar.getDia(dtConsulta.getText());
        if(d != null){
            model = new AgendamentoTableModel(d.getHorariosMarcados(), MenuCentral.getDataBase());
            table.setModel(model);
            table.repaint();
        }
    }
    
    public Pessoa findPessoa(String message, String filtro){
        find = new FindPessoa(MenuCentral.getFrame(),message, new String[] {filtro});
        find.showNotification();
        return find.getPessoaSelected();
    }
    
    private HoraDia to24HourFormat(String s){
        if(s != null){
            String[] splited = s.split(" ");

            if(splited.length > 1){

                String [] timeSplit = splited[0].split(":");

                if(timeSplit.length > 1){

                    int h = Integer.parseInt(timeSplit[0]);
                    int m = Integer.parseInt(timeSplit[1]);

                    if(splited[1].equalsIgnoreCase("AM")){
                        return new HoraDia(h, m);
                    }else{
                        h += 12;
                        if(h != 24){
                            return new HoraDia(h, m);
                        }else{
                            return new HoraDia(0, m);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private String validateFields(){
        
        if(!date1IsAfterDate2(this.dtConsulta.getText(), this.dtSolicitacao.getText())){
            return "Data da Consulta e de Solicitação";
        }
        if(this.txtbMedico.getText().equals("")){
            return "Médico Responsavel";
        }
        if(this.txtbPaciente.getText().equals("")){
            return "Paciente Encaminhado";
        }
        
        HoraDia i, f;
        int comp;
        
        i = to24HourFormat(this.txtbHoraInicial.getText());
        f = to24HourFormat(this.txtbHoraFinal.getText());
        
        if(i == null || f == null){
            return "Hora Inicial e Hora Final";
        }else{
            comp = i.compareTo(f);

            if(comp == 1){
                return "Hora Inicial e Hora Final";
            }else if(comp == 0){
                return "Hora Inicial e Hora Final";
            }
        }
        
        
        return null;
    }
    
    public boolean date1IsAfterDate2(String d1,String d2)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            return date1.after(date2) || date1.equals(date2);
        }
        catch(ParseException ex){
            System.out.println("Erro ao tentar converter datas! ErrorMessage: " + ex.getMessage());
        }
        return false;
    }
    
    private void saveData(){
        String errorField = validateFields();
        DataBase db = MenuCentral.getDataBase();
        
        if(errorField == null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date c = sdf.parse(this.dtConsulta.getText());
                Date s = sdf.parse(this.dtSolicitacao.getText());
                
                Consulta consulta = new Consulta(db.getIdControl().createNewID(), c, s, 
                        this.txtbPaciente.getText(), this.txtbMedico.getText());
                
                HoraDia ini = to24HourFormat(this.txtbHoraInicial.getText());
                HoraDia fin = to24HourFormat(this.txtbHoraFinal.getText());
                
                if(!agendar.agendarConsulta(consulta, new Periodo(ini,fin), c)){
                    Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Ja existe uma consulta nesse Horario!");
                    n.showNotification();
                }else{
                    db.getConsultas().addRecord(consulta, String.valueOf(consulta.getIdConsulta()));
                    Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                Notification.Location.TOP_RIGHT, "A consulta foi agendada!");
                    n.showNotification();
                    createTableModel();
                }
                
                
                
            } catch (ParseException ex) {}
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Corrija o campo de " + errorField);
            n.showNotification();
        }
        
    }
    
    public void clearFields(){
        this.txtbMedico.setText("");
        this.txtbPaciente.setText("");
        this.txtbHoraInicial.setText("");
        this.txtbHoraFinal.setText("");
        this.dtConsulta.setText("");
        this.dtSolicitacao.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dtChooserConsulta = new uepb.agendamentoconsultas.ui.datechooser.DateChooser();
        timeInicio = new com.raven.swing.TimePicker();
        dtChooserSolicitacao = new uepb.agendamentoconsultas.ui.datechooser.DateChooser();
        timeFinal = new com.raven.swing.TimePicker();
        dtSolicitacao = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        dtConsulta = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbMedico = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbPaciente = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbHoraInicial = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbHoraFinal = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        subTituloHorario = new javax.swing.JLabel();
        subTituloDadosGerais = new javax.swing.JLabel();
        cmdLimpa = new uepb.agendamentoconsultas.ui.components.CustomButton();
        cmdSalva = new uepb.agendamentoconsultas.ui.components.CustomButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new uepb.agendamentoconsultas.ui.components.CustomTable();
        subTituloHorario1 = new javax.swing.JLabel();

        dtChooserConsulta.setForeground(new java.awt.Color(0, 0, 204));
        dtChooserConsulta.setTextRefernce(dtConsulta);

        timeInicio.setDisplayText(txtbHoraInicial);

        dtChooserSolicitacao.setForeground(new java.awt.Color(0, 0, 204));
        dtChooserSolicitacao.setTextRefernce(dtSolicitacao);

        timeFinal.setDisplayText(txtbHoraFinal);

        dtSolicitacao.setLabelText("Data de Solicitação");
        dtSolicitacao.setLineColor(new java.awt.Color(51, 0, 204));

        dtConsulta.setLabelText("Data da Consulta");
        dtConsulta.setLineColor(new java.awt.Color(51, 0, 204));

        txtbMedico.setLabelText("Médico Responsavel");
        txtbMedico.setLineColor(new java.awt.Color(51, 0, 204));
        txtbMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbMedicoMouseClicked(evt);
            }
        });

        txtbPaciente.setLabelText("Paciente Encaminhado");
        txtbPaciente.setLineColor(new java.awt.Color(51, 0, 204));
        txtbPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbPacienteMouseClicked(evt);
            }
        });

        txtbHoraInicial.setLabelText("Inicio");
        txtbHoraInicial.setLineColor(new java.awt.Color(51, 0, 204));
        txtbHoraInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbHoraInicialMouseClicked(evt);
            }
        });

        txtbHoraFinal.setLabelText("Fim");
        txtbHoraFinal.setLineColor(new java.awt.Color(51, 0, 204));
        txtbHoraFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbHoraFinalMouseClicked(evt);
            }
        });

        subTituloHorario.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        subTituloHorario.setForeground(new java.awt.Color(76, 76, 76));
        subTituloHorario.setText("Horário da Consulta");

        subTituloDadosGerais.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        subTituloDadosGerais.setForeground(new java.awt.Color(76, 76, 76));
        subTituloDadosGerais.setText("Dados Gerais da Consulta");

        cmdLimpa.setText("Limpar");
        cmdLimpa.setStyle(uepb.agendamentoconsultas.ui.components.CustomButton.ButtonStyle.SECONDARY);
        cmdLimpa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLimpaActionPerformed(evt);
            }
        });

        cmdSalva.setText("Salvar");
        cmdSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSalvaActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(table);

        subTituloHorario1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        subTituloHorario1.setForeground(new java.awt.Color(76, 76, 76));
        subTituloHorario1.setText("Consultas na data Selecionada");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(subTituloHorario1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(subTituloHorario)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtbHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtbHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtbPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dtSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dtConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(subTituloDadosGerais, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(subTituloDadosGerais)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dtSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(subTituloHorario)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(subTituloHorario1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtbMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbMedicoMouseClicked
        Pessoa p = findPessoa("Selecionar o Médico", "Médico");
        if(p != null){
            txtbMedico.setText(p.getNumCPF());
        }else{
            txtbMedico.setText("");
        }
    }//GEN-LAST:event_txtbMedicoMouseClicked

    private void txtbPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbPacienteMouseClicked
        Pessoa p = findPessoa("Selecionar o Paciente", "Paciente");
        if(p != null){
            txtbPaciente.setText(p.getNumCPF());
        }else{
            txtbPaciente.setText("");
        }
    }//GEN-LAST:event_txtbPacienteMouseClicked

    private void txtbHoraInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbHoraInicialMouseClicked
        this.timeInicio.showPopup(txtbHoraInicial, 0, txtbHoraInicial.getHeight());
    }//GEN-LAST:event_txtbHoraInicialMouseClicked

    private void txtbHoraFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbHoraFinalMouseClicked
        this.timeFinal.showPopup(txtbHoraFinal, 0, txtbHoraFinal.getHeight());
    }//GEN-LAST:event_txtbHoraFinalMouseClicked

    private void cmdSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalvaActionPerformed
        saveData();
    }//GEN-LAST:event_cmdSalvaActionPerformed

    private void cmdLimpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLimpaActionPerformed
        clearFields();
    }//GEN-LAST:event_cmdLimpaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdLimpa;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdSalva;
    private uepb.agendamentoconsultas.ui.datechooser.DateChooser dtChooserConsulta;
    private uepb.agendamentoconsultas.ui.datechooser.DateChooser dtChooserSolicitacao;
    private uepb.agendamentoconsultas.ui.components.CustomTextField dtConsulta;
    private uepb.agendamentoconsultas.ui.components.CustomTextField dtSolicitacao;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel subTituloDadosGerais;
    private javax.swing.JLabel subTituloHorario;
    private javax.swing.JLabel subTituloHorario1;
    private uepb.agendamentoconsultas.ui.components.CustomTable table;
    private com.raven.swing.TimePicker timeFinal;
    private com.raven.swing.TimePicker timeInicio;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbHoraFinal;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbHoraInicial;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbMedico;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbPaciente;
    // End of variables declaration//GEN-END:variables
}

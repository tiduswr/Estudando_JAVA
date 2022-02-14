package uepb.agendamentoconsultas.ui.forms;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.models.PessoaTableModel;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.ui.components.CustomTable;
import uepb.agendamentoconsultas.ui.components.Notification;
import uepb.agendamentoconsultas.ui.models.RegexValidation;
import uepb.agendamentoconsultas.users.Medico;
import uepb.agendamentoconsultas.users.Paciente;
import uepb.agendamentoconsultas.users.Pessoa;
import uepb.agendamentoconsultas.users.Recepcionista;
import uepb.agendamentoconsultas.users.data.Endereco;

public class CadastrarPessoa extends javax.swing.JPanel {
    
    private Pessoa pessoa;
    private String tipoPessoa;
    private PessoaTableModel dataModel;
    private String selectedCPF;
    private TableRowSorter trs;
    
    public CadastrarPessoa() {
        initComponents();
        this.dataModel = new PessoaTableModel(MenuCentral.getDataBase(), new String[] {"Paciente", "Médico", "Recepcionista"});
        CustomTable.setBasicScrollConfigurations(jScrollPane1);
        this.table.setModel(dataModel);
        this.table.setAutoCreateRowSorter(true);
        this.table.addMouseListener(new MouseAdapter(){
        
            @Override
            public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow() != -1){
                    selectedCPF = table.getValueAt(table.getSelectedRow(), 0).toString();
                    txtbCPF.setEditable(false);
                    cbTipoPessoa.setEnabled(false);
                    fillForm(selectedCPF, table.getValueAt(table.getSelectedRow(), 3).toString());
                }
                super.mouseClicked(e);
            }
        
        });
    }
    
    private Pessoa toPessoa(){
        Pessoa obj;
        String errorField = validarDados();
        
        if(errorField != null){
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Corrija o campo de " + errorField);
            n.showNotification();
            return null;
        }else{
            if(this.cbTipoPessoa.getSelectedItem().toString().equalsIgnoreCase("Paciente")){
                obj = new Paciente();
            }else if(this.cbTipoPessoa.getSelectedItem().toString().equalsIgnoreCase("Médico")){
                obj = new Medico();
            }else{
                obj = new Recepcionista();
            }
            
            obj.setNome(this.txtbNome.getText());
            obj.setSobrenome(this.txtbSobreNome.getText());
            obj.setNumCPF(this.txtbCPF.getText());
            
            SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
            try {
                obj.setDtNascimento(fDate.parse(this.txtbDtNascimento.getText()));
            } catch (ParseException ex) {
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Corrija o campo de Data do Nascimento");
                n.showNotification();
                return null;
            }
            
            tipoPessoa = cbTipoPessoa.getSelectedItem().toString();
            
            obj.setEndereco(new Endereco(this.txtbRua.getText(), this.txtbBairro.getText(), this.txtbCidade.getText(), 
                                            this.txtbEstado.getText(), Integer.parseInt(this.txtbNumCasa.getText())));
            
            clearFields();
        }
        
        return obj;
        
    }
    
    public void clearFields(){
        this.txtbNome.setText("");
        this.txtbSobreNome.setText("");
        this.txtbCPF.setText("");
        this.txtbRua.setText("");
        this.txtbNumCasa.setText("");
        this.txtbCidade.setText("");
        this.txtbEstado.setText("");
        this.txtbBairro.setText("");
        this.cbTipoPessoa.setSelectedIndex(-1);
        this.txtbDtNascimento.setText("");
    }
    
    private String validarDados(){
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbNome.getText())){
            return "Nome";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.CPF, this.txtbCPF.getText())){
            return "CPF";
        }
        if(this.txtbDtNascimento.getText().equals("")){
            return "Data de Nascimento";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbSobreNome.getText())){
            return "Sobrenome";
        }
        if(this.cbTipoPessoa.getSelectedItem() == null){
            return "Tipo de Usuário";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbRua.getText())){
            return "Rua";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.ONLY_NUMBERS, this.txtbNumCasa.getText())){
            return "Numero da Casa";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbBairro.getText())){
            return "Bairro";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbCidade.getText())){
            return "Cidade";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, this.txtbEstado.getText())){
            return "Estado";
        }
        
        return null;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    
    public boolean updatePessoa(Pessoa u, Pessoa n){
        boolean ret = false;
        if (!u.getNome().equals(n.getNome())) {
            u.setNome(n.getNome());
            ret = true;
        }
        if (!u.getSobrenome().equals(n.getSobrenome())) {
            u.setSobrenome(n.getSobrenome());
            ret = true;
        }
        if (!u.getDtNascimento().equals(n.getDtNascimento())) {
            u.setDtNascimento(n.getDtNascimento());
            ret = true;
        }
        if(!u.getEndereco().equals(n.getEndereco())){
            u.setEndereco(n.getEndereco());
            ret = true;
        }
        return ret;
    }
    
    public Pessoa findPessoa(String CPF, String tipo){
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
    
    public void fillForm(String cpf, String tipo){
        Pessoa p = findPessoa(cpf, tipo);
        
        clearFields();
        this.txtbNome.setText(p.getNome());
        this.txtbSobreNome.setText(p.getSobrenome());
        this.txtbCPF.setText(p.getNumCPF());
        this.txtbRua.setText(p.getEndereco().getRua());
        this.txtbNumCasa.setText(String.valueOf(p.getEndereco().getNumCasa()));
        this.txtbCidade.setText(p.getEndereco().getCidade());
        this.txtbEstado.setText(p.getEndereco().getEstado());
        this.txtbBairro.setText(p.getEndereco().getBairro());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.txtbDtNascimento.setText(sdf.format(p.getDtNascimento()));
        
        if(p instanceof Paciente){
            this.cbTipoPessoa.setSelectedIndex(0);
        }else if(p instanceof Medico){
            this.cbTipoPessoa.setSelectedIndex(2);
        }else{
            this.cbTipoPessoa.setSelectedIndex(1);
        }
        
    }
    
    public boolean isVinculado(String cpf){
        DataBase db = MenuCentral.getDataBase();
        return db.getConsultas().getHashMapDataBase().entrySet().stream().anyMatch(c -> 
                (c.getValue().getCpfMedico().equals(cpf) || c.getValue().getCpfPaciente().equals(cpf)));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        date = new uepb.agendamentoconsultas.ui.datechooser.DateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new uepb.agendamentoconsultas.ui.components.CustomTable();
        txtbNome = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbSobreNome = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbCPF = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbDtNascimento = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        cbTipoPessoa = new uepb.agendamentoconsultas.ui.components.CustomComboBox();
        txtbRua = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbBairro = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbEstado = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbNumCasa = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbCidade = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        cmdSalva = new uepb.agendamentoconsultas.ui.components.CustomButton();
        cmdLimpa = new uepb.agendamentoconsultas.ui.components.CustomButton();
        cmdExclui = new uepb.agendamentoconsultas.ui.components.CustomButton();
        jLabel1 = new javax.swing.JLabel();
        txtbFiltro = new uepb.agendamentoconsultas.ui.components.CustomTextField();

        date.setTextRefernce(txtbDtNascimento);

        setOpaque(false);

        jScrollPane1.setBorder(null);

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        txtbNome.setLabelText("Nome");
        txtbNome.setLineColor(new java.awt.Color(51, 0, 204));

        txtbSobreNome.setLabelText("Sobrenome");
        txtbSobreNome.setLineColor(new java.awt.Color(51, 0, 204));

        txtbCPF.setLabelText("CPF");
        txtbCPF.setLineColor(new java.awt.Color(51, 0, 204));

        txtbDtNascimento.setLabelText("Data de Nascimento");
        txtbDtNascimento.setLineColor(new java.awt.Color(51, 0, 204));

        cbTipoPessoa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paciente", "Recepcionista", "Médico" }));
        cbTipoPessoa.setSelectedIndex(-1);
        cbTipoPessoa.setLblText("Tipo de Usuário");
        cbTipoPessoa.setLineColor(new java.awt.Color(51, 0, 204));

        txtbRua.setLabelText("Rua");
        txtbRua.setLineColor(new java.awt.Color(51, 0, 204));

        txtbBairro.setLabelText("Bairro");
        txtbBairro.setLineColor(new java.awt.Color(51, 0, 204));

        txtbEstado.setLabelText("Estado");
        txtbEstado.setLineColor(new java.awt.Color(51, 0, 204));

        txtbNumCasa.setLabelText("Numero da Casa");
        txtbNumCasa.setLineColor(new java.awt.Color(51, 0, 204));

        txtbCidade.setLabelText("Cidade");
        txtbCidade.setLineColor(new java.awt.Color(51, 0, 204));

        cmdSalva.setText("Salvar");
        cmdSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSalvaActionPerformed(evt);
            }
        });

        cmdLimpa.setText("Limpar");
        cmdLimpa.setStyle(uepb.agendamentoconsultas.ui.components.CustomButton.ButtonStyle.SECONDARY);
        cmdLimpa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLimpaActionPerformed(evt);
            }
        });

        cmdExclui.setText("Excluir");
        cmdExclui.setStyle(uepb.agendamentoconsultas.ui.components.CustomButton.ButtonStyle.DESTRUCTIVE);
        cmdExclui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcluiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(76, 76, 76));
        jLabel1.setText("Cadastrados");

        txtbFiltro.setLabelText("Filtrar por Nome");
        txtbFiltro.setLineColor(new java.awt.Color(51, 0, 204));
        txtbFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbFiltroKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtbCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addComponent(txtbRua, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addComponent(txtbNome, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addComponent(txtbSobreNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbTipoPessoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(txtbNumCasa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtbEstado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtbCPF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtbBairro, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addComponent(txtbDtNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmdExclui, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                            .addComponent(txtbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtbNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtbSobreNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtbCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbTipoPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtbNumCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtbDtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(txtbBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(txtbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmdExclui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalvaActionPerformed
        DataBase db = MenuCentral.getDataBase();
        this.pessoa = toPessoa();
        
        if(this.pessoa != null){
            switch(tipoPessoa){
                case "Paciente":
                    if(db.getPacientes().addRecord((Paciente) this.pessoa, this.pessoa.getNumCPF())){
                        Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O Paciente foi salvo!");
                        n.showNotification();
                        this.dataModel.addRow(this.pessoa);
                    }else{
                        if(this.pessoa.getNumCPF().equals(this.selectedCPF)){
                            Pessoa u = db.getPacientes().getRecord(this.pessoa.getNumCPF());
                            if(updatePessoa(u, this.pessoa)){
                                this.dataModel.updateData(u, table.getSelectedRow());
                                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O paciente foi atualizado!");
                                n.showNotification();
                                table.repaint();
                            }

                        }else{
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                    Notification.Location.TOP_RIGHT, "Um Paciente com esse mesmo CPF ja existe!");
                            n.showNotification();
                        }
                    }
                    break;
                case "Médico":
                    if(db.getMedicos().addRecord((Medico) this.pessoa, this.pessoa.getNumCPF())){
                        Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O Médico foi salvo!");
                        n.showNotification();
                        this.dataModel.addRow(this.pessoa);
                    }else{
                        if(this.pessoa.getNumCPF().equals(this.selectedCPF)){
                            Pessoa u = db.getMedicos().getRecord(this.pessoa.getNumCPF());
                            if(updatePessoa(u, this.pessoa)){
                                this.dataModel.updateData(u, table.getSelectedRow());
                                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O Médico foi atualizado!");
                                n.showNotification();
                                table.repaint();
                            }
                        }else{
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                        Notification.Location.TOP_RIGHT, "Um Médico com esse mesmo CPF ja existe!");
                            n.showNotification();
                        }
                    }
                    break;
                default:
                    if(db.getRecepcionistas().addRecord((Recepcionista) this.pessoa, this.pessoa.getNumCPF())){
                        Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O Recepcionista foi salvo!");
                        n.showNotification();
                        this.dataModel.addRow(this.pessoa);
                    }else{
                        if(this.pessoa.getNumCPF().equals(this.selectedCPF)){
                            Pessoa u = db.getRecepcionistas().getRecord(this.pessoa.getNumCPF());
                            if(updatePessoa(u, this.pessoa)){
                                this.dataModel.updateData(u, table.getSelectedRow());
                                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "O Recepcionista foi atualizado!");
                                n.showNotification();
                                table.repaint();
                            }
                        }else{
                            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                        Notification.Location.TOP_RIGHT, "Um Recepcionista com esse mesmo CPF ja existe!");
                            n.showNotification();
                        }
                    }
                    break;
            }
            
            selectedCPF = "";
            txtbCPF.setEditable(true);
            cbTipoPessoa.setEnabled(true);
            table.clearSelection();
            
        }
    }//GEN-LAST:event_cmdSalvaActionPerformed

    private void cmdLimpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLimpaActionPerformed
        clearFields();
        selectedCPF = "";
        txtbCPF.setEditable(true);
        cbTipoPessoa.setEnabled(true);
        table.clearSelection();
        Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                                Notification.Location.TOP_RIGHT, "O formulário foi limpo!");
        n.showNotification();
    }//GEN-LAST:event_cmdLimpaActionPerformed

    private void cmdExcluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcluiActionPerformed
        DataBase db = MenuCentral.getDataBase();
        int selectedRow = this.table.getSelectedRow();
        
        if(selectedRow != -1){
            String cpf = this.table.getValueAt(selectedRow, 0).toString();

            if(!isVinculado(cpf)){
                switch(this.table.getValueAt(selectedRow, 3).toString()){
                    case "Paciente":
                        db.getPacientes().removeRecord(cpf);
                        break;
                    case "Médico":
                        db.getMedicos().removeRecord(cpf);
                        break;
                    default:
                        db.getRecepcionistas().removeRecord(cpf);
                        break;
                }
               dataModel.removeRow(selectedRow);
               Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                   Notification.Location.TOP_RIGHT, "A pessoa foi excluida!");
               n.showNotification();
            }else{
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Impossivel excluir, essa pessoa esta vinculada a uma consulta!");
                n.showNotification();
            }
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Selecione uma pessoa para excluir!");
            n.showNotification();
        }
        
    }//GEN-LAST:event_cmdExcluiActionPerformed

    private void txtbFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbFiltroKeyTyped

        txtbFiltro.addKeyListener(new KeyAdapter(){
        
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter(txtbFiltro.getText(), 1));
            }
            
        });
        
        trs = new TableRowSorter(dataModel);
        table.setRowSorter(trs);
        
    }//GEN-LAST:event_txtbFiltroKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uepb.agendamentoconsultas.ui.components.CustomComboBox cbTipoPessoa;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdExclui;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdLimpa;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdSalva;
    private uepb.agendamentoconsultas.ui.datechooser.DateChooser date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private uepb.agendamentoconsultas.ui.components.CustomTable table;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbBairro;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbCPF;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbCidade;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbDtNascimento;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbEstado;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbFiltro;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbNome;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbNumCasa;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbRua;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbSobreNome;
    // End of variables declaration//GEN-END:variables
}

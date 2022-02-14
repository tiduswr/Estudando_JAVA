package uepb.agendamentoconsultas.ui.forms;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.models.AgendamentoDataTable;
import uepb.agendamentoconsultas.models.AgendamentoTableModel;
import uepb.agendamentoconsultas.models.ExameTableModel;
import uepb.agendamentoconsultas.models.MedicamentoTableModel;
import uepb.agendamentoconsultas.services.Agendamento;
import uepb.agendamentoconsultas.services.products.Consulta;
import uepb.agendamentoconsultas.services.products.Exame;
import uepb.agendamentoconsultas.services.products.Medicamento;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.ui.components.CustomTable;
import uepb.agendamentoconsultas.ui.components.FindPessoa;
import uepb.agendamentoconsultas.ui.components.Notification;
import uepb.agendamentoconsultas.ui.models.RegexValidation;
import uepb.agendamentoconsultas.users.Pessoa;

public class AdministrarConsulta extends javax.swing.JPanel {
    
    private FindPessoa find;
    private Pessoa pessoaSelecionada;
    private Agendamento agendar;
    private AgendamentoTableModel modelBusca;
    private ExameTableModel modelExame;
    private MedicamentoTableModel modelMedicamentos;
    private AgendamentoDataTable selectedAgendamento;
    
    public AdministrarConsulta() {
        initComponents();
        CustomTable.setBasicScrollConfigurations(scrollPrincipal);
        CustomTable.setBasicScrollConfigurations(this.scrollExames);
        CustomTable.setBasicScrollConfigurations(this.scrollMedicamentos);
        CustomTable.setBasicScrollConfigurations(this.scrollPrincipal);
        CustomTable.setBasicScrollConfigurations(this.scrollSearch);
        CustomTable.setBasicScrollConfigurations(this.scrollTextArea);
        this.pessoaSelecionada = null;
        this.selectedAgendamento = null;
        
        agendar = new Agendamento(MenuCentral.getDataBase().getAgendamentos());
        modelBusca = new AgendamentoTableModel(null, (String) null);
        modelExame = new ExameTableModel(null);
        modelMedicamentos = new MedicamentoTableModel(null);
        tableBusca.setModel(modelBusca);
        tblExames.setModel(modelExame);
        tableMedicamentos.setModel(modelMedicamentos);
        
        this.txtbBuscar.getDocument().addDocumentListener(new DocumentListener(){
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
        tblExames.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(tblExames.getSelectedRow() != -1  && pessoaSelecionada != null && selectedAgendamento != null){
                    if(e.getKeyChar() == KeyEvent.VK_DELETE){
                        modelExame.removeValue(tblExames.getSelectedRow());
                    }
                }
            }
        });
        tableMedicamentos.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(tableMedicamentos.getSelectedRow() != -1  && pessoaSelecionada != null && selectedAgendamento != null){
                    if(e.getKeyChar() == KeyEvent.VK_DELETE){
                        modelMedicamentos.removeValue(tableMedicamentos.getSelectedRow());
                    }
                }
            }
        });
    }
    
    public void clearFields(){
        this.txtbBuscar.setText("");
        this.tableBusca.clearSelection();
        this.dtConsulta.setText("");
        this.dtSolicitacao.setText("");
        this.txtbMedico.setText("");
        this.txtbPaciente.setText("");
        this.txtbHoraInicial.setText("");
        this.txtbHoraFinal.setText("");
        this.txtbExame.setText("");
        this.txtbNomeMedicamento.setText("");
        this.txtbMls.setText("");
        this.txtbDiasUsando.setText("");
        this.txtAreaParecer.setText("");
        modelBusca = new AgendamentoTableModel(null, (String) null);
        modelExame = new ExameTableModel(null);
        modelMedicamentos = new MedicamentoTableModel(null);
        tableBusca.setModel(modelBusca);
        tblExames.setModel(modelExame);
        tableMedicamentos.setModel(modelMedicamentos);
        pessoaSelecionada = null;
        selectedAgendamento = null;
    }
    
    private String validarDadosMedicamentos(){
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.NOT_EMPTY_ALPHABETIC, 
                this.txtbNomeMedicamento.getText())){
            return "Nome do Medicamento";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.DECIMAL_VALUE, 
                this.txtbMls.getText())){
            return "Dose em mls";
        }
        if(!RegexValidation.TestString(RegexValidation.StandardRegexString.ONLY_NUMBERS, 
                this.txtbDiasUsando.getText())){
            return "Dias Usando";
        }
        return null;
    }
    
    private Medicamento createMedicamento(){
        if(validarDadosMedicamentos() == null){
            String nomeMedicamento = this.txtbNomeMedicamento.getText();
            double dose = Double.parseDouble(this.txtbMls.getText().replace(",", "."));
            int dias = Integer.parseInt(this.txtbDiasUsando.getText());
            return new Medicamento(nomeMedicamento, dose, dias);
        }
        return null;
    }
    
    private void fillForm(AgendamentoDataTable d){
        DataBase db = MenuCentral.getDataBase();
        
        if(d != null && db != null && db.getConsultas() != null && db.getConsultas().getRecordCount() > 0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Consulta c = MenuCentral.getDataBase().getConsultas().getRecord(String.valueOf(d.getIdConsulta()));

            this.dtConsulta.setText(sdf.format(c.getDtConsulta()));
            this.dtSolicitacao.setText(sdf.format(c.getDtSolicitacao()));
            this.txtbMedico.setText(d.getMedico().getNumCPF());
            this.txtbPaciente.setText(d.getPaciente().getNumCPF());
            this.txtbHoraInicial.setText(d.getHoraMarcada().getDe().toString());
            this.txtbHoraFinal.setText(d.getHoraMarcada().getAte().toString());
            modelExame = new ExameTableModel(c.getExames());
            modelMedicamentos = new MedicamentoTableModel(c.getMedicamentos());
            tblExames.setModel(modelExame);
            tableMedicamentos.setModel(modelMedicamentos);
            this.txtAreaParecer.setText(c.getParecerMedico());
            
        }else{
            System.out.println("Erro ao carregar dados: AdministrarConsulta.fillForm()");
        }
    }
    
    private void createTableModel(){
        if(!this.txtbBuscar.getText().equals("")){
            modelBusca = new AgendamentoTableModel(MenuCentral.getDataBase(), this.txtbBuscar.getText());
            tableBusca.setModel(modelBusca);
            tableBusca.repaint();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPrincipal = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        scrollSearch = new javax.swing.JScrollPane();
        tableBusca = new uepb.agendamentoconsultas.ui.components.CustomTable();
        tableTitle = new javax.swing.JLabel();
        dtSolicitacao = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        dtConsulta = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbMedico = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbPaciente = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbHoraFinal = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbHoraInicial = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        subTituloHorario = new javax.swing.JLabel();
        subTituloDadosGerais = new javax.swing.JLabel();
        lblParecer = new javax.swing.JLabel();
        scrollTextArea = new javax.swing.JScrollPane();
        txtAreaParecer = new javax.swing.JTextArea();
        cmdExclui = new uepb.agendamentoconsultas.ui.components.CustomButton();
        cmdLimpa = new uepb.agendamentoconsultas.ui.components.CustomButton();
        cmdSalva = new uepb.agendamentoconsultas.ui.components.CustomButton();
        txtbBuscar = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        jPanel1 = new javax.swing.JPanel();
        frExames = new javax.swing.JPanel();
        scrollExames = new javax.swing.JScrollPane();
        tblExames = new uepb.agendamentoconsultas.ui.components.CustomTable();
        lblTitleExame = new javax.swing.JLabel();
        txtbExame = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        btSalvaExame = new uepb.agendamentoconsultas.ui.components.CustomButton();
        frMedicamentos = new javax.swing.JPanel();
        scrollMedicamentos = new javax.swing.JScrollPane();
        tableMedicamentos = new uepb.agendamentoconsultas.ui.components.CustomTable();
        txtbMls = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbDiasUsando = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        txtbNomeMedicamento = new uepb.agendamentoconsultas.ui.components.CustomTextField();
        btSalvarMedicamento = new uepb.agendamentoconsultas.ui.components.CustomButton();
        lblMedicamento = new javax.swing.JLabel();

        panel.setBackground(new java.awt.Color(255, 255, 255));

        scrollSearch.setBorder(null);

        tableBusca.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBuscaMouseClicked(evt);
            }
        });
        scrollSearch.setViewportView(tableBusca);

        tableTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        tableTitle.setForeground(new java.awt.Color(76, 76, 76));
        tableTitle.setText("Selecionar Consulta");

        dtSolicitacao.setEditable(false);
        dtSolicitacao.setLabelText("Data de Solicitação");
        dtSolicitacao.setLineColor(new java.awt.Color(51, 0, 204));

        dtConsulta.setEditable(false);
        dtConsulta.setLabelText("Data da Consulta");
        dtConsulta.setLineColor(new java.awt.Color(51, 0, 204));

        txtbMedico.setEditable(false);
        txtbMedico.setLabelText("Médico Responsavel");
        txtbMedico.setLineColor(new java.awt.Color(51, 0, 204));

        txtbPaciente.setEditable(false);
        txtbPaciente.setLabelText("Paciente Encaminhado");
        txtbPaciente.setLineColor(new java.awt.Color(51, 0, 204));

        txtbHoraFinal.setEditable(false);
        txtbHoraFinal.setLabelText("Fim");
        txtbHoraFinal.setLineColor(new java.awt.Color(51, 0, 204));

        txtbHoraInicial.setEditable(false);
        txtbHoraInicial.setLabelText("Inicio");
        txtbHoraInicial.setLineColor(new java.awt.Color(51, 0, 204));

        subTituloHorario.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        subTituloHorario.setForeground(new java.awt.Color(76, 76, 76));
        subTituloHorario.setText("Horário da Consulta");

        subTituloDadosGerais.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        subTituloDadosGerais.setForeground(new java.awt.Color(76, 76, 76));
        subTituloDadosGerais.setText("Dados Gerais da Consulta");

        lblParecer.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblParecer.setForeground(new java.awt.Color(76, 76, 76));
        lblParecer.setText("Parecer");

        txtAreaParecer.setBackground(new java.awt.Color(255, 255, 255));
        txtAreaParecer.setColumns(20);
        txtAreaParecer.setRows(5);
        txtAreaParecer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        scrollTextArea.setViewportView(txtAreaParecer);

        cmdExclui.setText("Excluir");
        cmdExclui.setStyle(uepb.agendamentoconsultas.ui.components.CustomButton.ButtonStyle.DESTRUCTIVE);
        cmdExclui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcluiActionPerformed(evt);
            }
        });

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

        txtbBuscar.setEditable(false);
        txtbBuscar.setLabelText("Paciente Encaminhado");
        txtbBuscar.setLineColor(new java.awt.Color(51, 0, 204));
        txtbBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbBuscarMouseClicked(evt);
            }
        });

        jPanel1.setOpaque(false);

        frExames.setBackground(new java.awt.Color(255, 255, 255));
        frExames.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        frExames.setOpaque(false);

        scrollExames.setBorder(null);

        tblExames.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollExames.setViewportView(tblExames);

        lblTitleExame.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblTitleExame.setForeground(new java.awt.Color(76, 76, 76));
        lblTitleExame.setText("Exames");

        txtbExame.setLabelText("Nome Exame");

        btSalvaExame.setText("+");
        btSalvaExame.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btSalvaExame.setRound(50);
        btSalvaExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvaExameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frExamesLayout = new javax.swing.GroupLayout(frExames);
        frExames.setLayout(frExamesLayout);
        frExamesLayout.setHorizontalGroup(
            frExamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frExamesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frExamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollExames, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(frExamesLayout.createSequentialGroup()
                        .addComponent(lblTitleExame, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 155, Short.MAX_VALUE))
                    .addGroup(frExamesLayout.createSequentialGroup()
                        .addComponent(txtbExame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSalvaExame, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        frExamesLayout.setVerticalGroup(
            frExamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frExamesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleExame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frExamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbExame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvaExame, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollExames, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        frMedicamentos.setBackground(new java.awt.Color(255, 255, 255));
        frMedicamentos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        frMedicamentos.setOpaque(false);

        scrollMedicamentos.setBorder(null);

        tableMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollMedicamentos.setViewportView(tableMedicamentos);

        txtbMls.setLabelText("Dose em mls");

        txtbDiasUsando.setLabelText("Dias Usando");

        txtbNomeMedicamento.setLabelText("Nome Medicamento");

        btSalvarMedicamento.setText("+");
        btSalvarMedicamento.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btSalvarMedicamento.setRound(50);
        btSalvarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarMedicamentoActionPerformed(evt);
            }
        });

        lblMedicamento.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblMedicamento.setForeground(new java.awt.Color(76, 76, 76));
        lblMedicamento.setText("Medicamentos");

        javax.swing.GroupLayout frMedicamentosLayout = new javax.swing.GroupLayout(frMedicamentos);
        frMedicamentos.setLayout(frMedicamentosLayout);
        frMedicamentosLayout.setHorizontalGroup(
            frMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frMedicamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollMedicamentos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frMedicamentosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtbMls, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbDiasUsando, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frMedicamentosLayout.createSequentialGroup()
                        .addComponent(txtbNomeMedicamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSalvarMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frMedicamentosLayout.createSequentialGroup()
                        .addComponent(lblMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frMedicamentosLayout.setVerticalGroup(
            frMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frMedicamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMedicamento)
                .addGap(8, 8, 8)
                .addGroup(frMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbNomeMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvarMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbDiasUsando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbMls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollMedicamentos, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(frExames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(frMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(frMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frExames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdExclui, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollTextArea)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(subTituloDadosGerais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subTituloHorario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(dtSolicitacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtbMedico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtbHoraInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtbPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dtConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtbHoraFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(13, 13, 13))
                    .addComponent(lblParecer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(tableTitle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableTitle)
                .addGap(7, 7, 7)
                .addComponent(scrollSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(subTituloDadosGerais)
                .addGap(22, 22, 22)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtConsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(subTituloHorario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblParecer)
                .addGap(18, 18, 18)
                .addComponent(scrollTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmdExclui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        scrollPrincipal.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1161, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtbBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbBuscarMouseClicked
        pessoaSelecionada = findPessoa("Selecionar o Paciente", "Paciente");
        if(pessoaSelecionada != null){
            this.txtbBuscar.setText(pessoaSelecionada.getNumCPF());
        }else{
            this.txtbBuscar.setText("");
        }
    }//GEN-LAST:event_txtbBuscarMouseClicked

    private void tableBuscaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBuscaMouseClicked
        if(this.tableBusca.getSelectedRow() != -1){
            selectedAgendamento = this.modelBusca.getAgendamentoAt(this.tableBusca.getSelectedRow());
            fillForm(selectedAgendamento);
        }
    }//GEN-LAST:event_tableBuscaMouseClicked

    private void cmdLimpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLimpaActionPerformed
        clearFields();
        Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.INFO, 
                                                Notification.Location.TOP_RIGHT, "Formulário Limpo!");
        n.showNotification();
    }//GEN-LAST:event_cmdLimpaActionPerformed

    private void btSalvaExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvaExameActionPerformed
        if(pessoaSelecionada != null && selectedAgendamento != null){
            if(!this.txtbExame.getText().equals("")){
                this.modelExame.setValue(new Exame(this.txtbExame.getText()));
                this.txtbExame.setText("");
            }else{
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Preencha os campos do Exame corretamente!");
                n.showNotification();
            }
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Selecione um registro!");
            n.showNotification();
        }
    }//GEN-LAST:event_btSalvaExameActionPerformed

    private void btSalvarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarMedicamentoActionPerformed

        if(pessoaSelecionada != null && selectedAgendamento != null){
            String errorField = this.validarDadosMedicamentos();

            if(errorField == null){
                
                this.modelMedicamentos.setValue(this.createMedicamento());
                this.txtbNomeMedicamento.setText("");
                this.txtbMls.setText("");
                this.txtbDiasUsando.setText("");
                
            }else{
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                    Notification.Location.TOP_RIGHT, "Corrija o campo de " + errorField + "!");
                n.showNotification();
            }
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Selecione um registro!");
            n.showNotification();
        }
    }//GEN-LAST:event_btSalvarMedicamentoActionPerformed

    private void cmdSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalvaActionPerformed
        if(pessoaSelecionada != null && selectedAgendamento != null){
            DataBase db = MenuCentral.getDataBase();
            
            Consulta c = db.getConsultas().getRecord(String.valueOf(selectedAgendamento.getIdConsulta()));
            
            if(c != null){
                c.setParecerMedico(this.txtAreaParecer.getText());
                this.clearFields();
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "Parecer Registrado/Atualizado");
                n.showNotification();
                
            }else{
               Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Erro ao salvar, consulta não encontrada!");
                n.showNotification(); 
            }         
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Selecione um registro!");
            n.showNotification();
        }
    }//GEN-LAST:event_cmdSalvaActionPerformed

    private void cmdExcluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcluiActionPerformed
        if(pessoaSelecionada != null && selectedAgendamento != null){
            DataBase db = MenuCentral.getDataBase();
            
            Consulta c = db.getConsultas().getRecord(String.valueOf(selectedAgendamento.getIdConsulta()));
            
            if(c != null){
                
                agendar.desmarcarConsulta(String.valueOf(selectedAgendamento.getIdConsulta()));
                db.getConsultas().removeRecord(String.valueOf(selectedAgendamento.getIdConsulta()));
                this.clearFields();
                
                Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.SUCESS, 
                                                    Notification.Location.TOP_RIGHT, "Consulta Excluida!");
                n.showNotification();
                
            }else{
               Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Erro ao excluir, consulta não encontrada!");
                n.showNotification(); 
            }         
            
        }else{
            Notification n = new Notification(MenuCentral.getFrame(), Notification.Type.WARNING, 
                                                Notification.Location.TOP_RIGHT, "Selecione um registro!");
            n.showNotification();
        }
    }//GEN-LAST:event_cmdExcluiActionPerformed
    
    public Pessoa findPessoa(String message, String filtro){
        find = new FindPessoa(MenuCentral.getFrame(),message, new String[] {filtro});
        find.showNotification();
        return find.getPessoaSelected();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uepb.agendamentoconsultas.ui.components.CustomButton btSalvaExame;
    private uepb.agendamentoconsultas.ui.components.CustomButton btSalvarMedicamento;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdExclui;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdLimpa;
    private uepb.agendamentoconsultas.ui.components.CustomButton cmdSalva;
    private uepb.agendamentoconsultas.ui.components.CustomTextField dtConsulta;
    private uepb.agendamentoconsultas.ui.components.CustomTextField dtSolicitacao;
    private javax.swing.JPanel frExames;
    private javax.swing.JPanel frMedicamentos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMedicamento;
    private javax.swing.JLabel lblParecer;
    private javax.swing.JLabel lblTitleExame;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollExames;
    private javax.swing.JScrollPane scrollMedicamentos;
    private javax.swing.JScrollPane scrollPrincipal;
    private javax.swing.JScrollPane scrollSearch;
    private javax.swing.JScrollPane scrollTextArea;
    private javax.swing.JLabel subTituloDadosGerais;
    private javax.swing.JLabel subTituloHorario;
    private uepb.agendamentoconsultas.ui.components.CustomTable tableBusca;
    private uepb.agendamentoconsultas.ui.components.CustomTable tableMedicamentos;
    private javax.swing.JLabel tableTitle;
    private uepb.agendamentoconsultas.ui.components.CustomTable tblExames;
    private javax.swing.JTextArea txtAreaParecer;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbBuscar;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbDiasUsando;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbExame;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbHoraFinal;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbHoraInicial;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbMedico;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbMls;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbNomeMedicamento;
    private uepb.agendamentoconsultas.ui.components.CustomTextField txtbPaciente;
    // End of variables declaration//GEN-END:variables
}

package uepb.agendamentoconsultas.models;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;
import uepb.agendamentoconsultas.calendario.Periodo;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.services.products.Consulta;
import uepb.agendamentoconsultas.ui.MenuCentral;
import uepb.agendamentoconsultas.users.Medico;
import uepb.agendamentoconsultas.users.Paciente;

public class AgendamentoTableModel extends AbstractTableModel{
    
    private ArrayList<AgendamentoDataTable> data;
    private final String[] header = {"PACIENTE", "HORA INICIAL", "HORA FINAL", "MÉDICO", "STATUS"};
    
    public AgendamentoTableModel(HashMap<String, Periodo> agendamentos, DataBase db){
        data = new ArrayList<>();
        
        if(agendamentos != null && db != null){
            agendamentos.entrySet().forEach(a -> {
                
                Consulta c = db.getConsultas().getRecord(a.getKey());
                if(c != null){
                    Paciente p = db.getPacientes().getRecord(c.getCpfPaciente());
                    Medico m = db.getMedicos().getRecord(c.getCpfMedico());
                    
                    if(p != null && m != null){
                        data.add(new AgendamentoDataTable(Integer.parseInt(a.getKey()), p, m, a.getValue()));
                    }
                    
                }
                
            });
        }
    }
    
    public AgendamentoTableModel(DataBase db, String cpfFilter){
        data = new ArrayList<>();
        if(db != null){
            db.getAgendamentos().getHashMapDataBase().entrySet().forEach(ano -> {
                ano.getValue().entrySet().forEach(mes -> {
                    mes.getValue().getDias().entrySet().forEach(semana -> {
                        semana.getValue().entrySet().stream().map(dia -> dia.getValue().getHorariosMarcados()).
                                filter(agendamentos -> (agendamentos != null)).forEachOrdered(agendamentos -> {
                            agendamentos.entrySet().forEach(a -> {
                                Consulta c = db.getConsultas().getRecord(a.getKey());
                                if(c != null && c.getCpfPaciente().equalsIgnoreCase(cpfFilter)){
                                    Paciente p = db.getPacientes().getRecord(c.getCpfPaciente());
                                    Medico m = db.getMedicos().getRecord(c.getCpfMedico());
                                    if(p != null && m != null){
                                        data.add(new AgendamentoDataTable(Integer.parseInt(a.getKey()), p, m, a.getValue()));
                                    }

                                }

                            });
                        });
                    });
                });
            });
        }
    }
    
    @Override
    public String getColumnName(int c) {
        return header[c];
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AgendamentoDataTable d = data.get(rowIndex);
        
        if(d != null){
            switch(columnIndex){
                case 0:
                    return d.getPaciente().getNome();
                case 1:
                    return d.getHoraMarcada().getDe().toString();
                case 2:
                    return d.getHoraMarcada().getAte().toString();
                case 3:
                    return d.getMedico().getNome();
                case 4:
                    Consulta c = MenuCentral.getDataBase().getConsultas().getRecord(String.valueOf(d.getIdConsulta()));
                    if(c != null){
                        if(c.isFinalizada()){
                            return "COM PARECER";
                        }else{
                            return "SEM PARECER";
                        }
                    }
                    return "";
            }
        }
        return null;
    }
    
    public AgendamentoDataTable getAgendamentoAt(int rowIndex){
        return data.get(rowIndex);
    }
    
    public void updateData(AgendamentoDataTable n, int row){
        if(row != -1){
            AgendamentoDataTable u = this.data.get(row);
            if(!u.equals(n)){
                this.fireTableRowsUpdated(row, row);
            }
        }
    }
    
    public void addRow(AgendamentoDataTable p){
        if(p != null){
            this.data.add(p);
            this.fireTableDataChanged();
        }
    }
    
    public void removeRow(int r){
        if(r != -1){
            this.data.remove(r);
            this.fireTableRowsDeleted(r, r);
        }
    }
    
}

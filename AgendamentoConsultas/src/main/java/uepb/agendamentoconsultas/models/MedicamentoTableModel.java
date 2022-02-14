package uepb.agendamentoconsultas.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import uepb.agendamentoconsultas.services.products.Medicamento;

public class MedicamentoTableModel extends AbstractTableModel{
    private ArrayList<Medicamento> medicamentos;
    
    private final String[] header = {"Nome Medicamento", "Mls por Dia", "Dias usando"};
    
    public MedicamentoTableModel(ArrayList<Medicamento> medicamentos){
        if(medicamentos == null){
            this.medicamentos = new ArrayList<>();
        }else{
            this.medicamentos = medicamentos;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    
    @Override
    public int getRowCount() {
        return medicamentos.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        Medicamento i = medicamentos.get(r);
        if(i != null){
            switch(c){
                case 0:
                    return i.getNomeMedicamento();
                case 1:
                    return String.valueOf(i.getQtdMlsPorDia()).replace(".", ",");
                case 2:
                    return i.getDiasUsando();
                default:
                    return null;
            }
        }else{
            return null;
        }
    }
    
    public void setValue(Medicamento e){
        medicamentos.add(e);
        this.fireTableDataChanged();
    }
    
    public void removeValue(int i){
        if(i != -1){
            medicamentos.remove(i);
            this.fireTableRowsDeleted(i, i);
        }
    }
    
    public ArrayList<Medicamento> getData(){
        return medicamentos;
    }
    
}

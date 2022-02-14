package uepb.agendamentoconsultas.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import uepb.agendamentoconsultas.calendario.Mes;
import uepb.agendamentoconsultas.models.IdControl;
import uepb.agendamentoconsultas.services.products.Consulta;
import uepb.agendamentoconsultas.users.Medico;
import uepb.agendamentoconsultas.users.Paciente;
import uepb.agendamentoconsultas.users.Recepcionista;

public final class DataBase{
    
    private DataBaseModel<String, Paciente> pacientes;
    private DataBaseModel<String, Medico> medicos; 
    private DataBaseModel<String, Recepcionista> recepcionistas;
    private DataBaseModel<String, Consulta> consultas;
    private DataBaseModel<String, HashMap<String, Mes>> agendamentos;
    private IdControl idControl;
    
    public DataBase(){
        if(!this.loadData()){
            this.createNewDataBase();
        }
        this.idControl = new IdControl();
    }
    
    private void createNewDataBase(){
        this.pacientes = new DataBaseModel<>();
        this.medicos = new DataBaseModel<>();
        this.recepcionistas = new DataBaseModel<>();
        this.consultas = new DataBaseModel<>();
        this.agendamentos = new DataBaseModel<>();
        this.saveData();
    }
    private boolean loadData(){
        ObjectInputStream ois;
        boolean ret = true;
        String[] arquivos = {"pacientes.ser", "medicos.ser", "recepcionistas.ser", "consultas.ser", "agendamentos.ser"};
        
        for(String s : arquivos){
            mkDir(System.getProperty("user.dir") + "/db/");
            ois = retrieveData(System.getProperty("user.dir") + "/db/" + s);
            if(ois != null){
                try {
                    switch(s){
                        case "pacientes.ser":
                            this.pacientes = (DataBaseModel<String, Paciente>) ois.readObject();
                            break;
                        case "medicos.ser":
                            this.medicos = (DataBaseModel<String, Medico>) ois.readObject();
                            break;    
                        case "recepcionistas.ser":
                            this.recepcionistas = (DataBaseModel<String, Recepcionista>) ois.readObject();
                            break;    
                        case "consultas.ser":
                            this.consultas = (DataBaseModel<String, Consulta>) ois.readObject();
                            break;    
                        case "agendamentos.ser":
                            this.agendamentos = (DataBaseModel<String, HashMap<String, Mes>>) ois.readObject();
                            break;    
                        default:
                            break;
                    }
                    ois.close();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println(ex.getClass() + ": " + ex.getMessage());
                    ret = false;
                }
            }else{
                ret = false;
            }
        }
        
        return ret;
    }
    public boolean saveData(){
        boolean ret = true;
        String[] arquivos = {"pacientes.ser", "medicos.ser", "recepcionistas.ser", "consultas.ser", "agendamentos.ser"};
        
        for(String s : arquivos){
            mkDir(System.getProperty("user.dir") + "/db/");
            ObjectOutputStream oos = createDataBase(System.getProperty("user.dir") + "/db/" + s);
            if(oos != null){
                try {
                    switch(s){
                        case "pacientes.ser":
                            oos.writeObject(this.pacientes);
                            break;
                        case "medicos.ser":
                            oos.writeObject(this.medicos);
                            break;    
                        case "recepcionistas.ser":
                            oos.writeObject(this.recepcionistas);
                            break;    
                        case "consultas.ser":
                            oos.writeObject(this.consultas);
                            break;    
                        case "agendamentos.ser":
                            oos.writeObject(this.agendamentos);
                            break;    
                        default:
                            break;
                    }
                    oos.close();
                } catch (IOException ex) {
                    System.out.println(ex.getClass() + ": " + ex.getMessage());
                    ret = false;
                }
            }else{
                ret = false;
            }
        }
        return ret;
    }
    
    private void mkDir(String directoryName){
        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdirs();
            System.out.println("oi");
        }
    }
    
    private ObjectInputStream retrieveData(String arquivo){
        FileInputStream fin;
        ObjectInputStream ois;
        try {
            fin = new FileInputStream(arquivo);
            ois = new ObjectInputStream(fin);
            return ois;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
    private ObjectOutputStream createDataBase(String arquivo){
        FileOutputStream fout;
        ObjectOutputStream oos;
        try {
            fout = new FileOutputStream(arquivo);
            oos = new ObjectOutputStream(fout);
            return oos;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
    
    public DataBaseModel<String, Paciente> getPacientes() {
        return pacientes;
    }
    public DataBaseModel<String, Medico> getMedicos() {
        return medicos;
    }
    public DataBaseModel<String, Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }
    public DataBaseModel<String, Consulta> getConsultas() {
        return consultas;
    }
    public DataBaseModel<String, HashMap<String, Mes>> getAgendamentos() {
        return this.agendamentos;
    }
    public IdControl getIdControl(){
        return this.idControl;
    }
}

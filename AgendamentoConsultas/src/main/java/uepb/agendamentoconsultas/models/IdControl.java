package uepb.agendamentoconsultas.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IdControl{
    private int lastId;
    private final String ARQUIVO = System.getProperty("user.dir") + "/db/" + "idConsultasControl.ser";
    
    public IdControl(){
        if(!retrieveData()){
            this.mkDir(System.getProperty("user.dir") + "/db/");
            this.createNewDataBase();
        }
    }
    
    private void createNewDataBase(){
        this.lastId = 0;
        saveDataBase();
    }
    
    public boolean saveDataBase(){
        FileOutputStream fout;
        ObjectOutputStream oos;
        try {
            fout = new FileOutputStream(ARQUIVO);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(lastId);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return false;
    }
    
    private boolean retrieveData(){
        FileInputStream fin;
        ObjectInputStream ois;
        try {
            fin = new FileInputStream(ARQUIVO);
            ois = new ObjectInputStream(fin);
            this.lastId = (int) ois.readObject();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return false;
    }
    
    private void mkDir(String directoryName){
        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdirs();
            System.out.println("oi");
        }
    }
    
    public int getLastId(){
        return this.lastId;
    }
    
    public int createNewID(){
        this.lastId++;
        saveDataBase();
        return this.lastId;
    }
    
    public int undoLasIdCreated(){
        this.lastId--;
        return this.lastId;
    }
    
}

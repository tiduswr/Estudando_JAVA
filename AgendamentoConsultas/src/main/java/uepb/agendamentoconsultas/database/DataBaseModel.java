package uepb.agendamentoconsultas.database;

import java.io.Serializable;
import java.util.HashMap;

public class DataBaseModel <K,T> implements Serializable{
    private HashMap<K,T> records;
    
    public DataBaseModel(){
        this.records = new HashMap<>();
    }
    public DataBaseModel(HashMap<K,T> db){
        this.records = db;
    }
    
    public boolean addRecord(T record, K key){
        if(getRecord(key) == null){
            this.records.put(key, record);
            return true;
        }else{
            return false;
        }
    }
    public boolean removeRecord(K key){
        return this.records.remove(key) != null;
    }
    public T getRecord(K key){
        return this.records.get(key);
    }
    public int getRecordCount(){
        return this.records.size();
    }
    public HashMap<K,T> getHashMapDataBase(){
        return this.records;
    }
}

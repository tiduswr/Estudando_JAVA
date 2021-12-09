package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HSQLDBConnection {
    
    private final String url = "jdbc:hsqldb:file:DataBase/";
    private final String nome = "testeDB";
    private final String user = "SA";
    private final String password = "";
    
    
    private Connection conexao = null;
    
    public Connection connect() throws SQLException{
        if(conexao == null){
            conexao = DriverManager.getConnection(url + nome, user, password);
        }
        return conexao;
    }
    
    public boolean disconnect() throws SQLException{
        conexao.close();
        conexao = null;
        return conexao == null;
    }
    
    public void executeQuery(String query) throws SQLException{
        this.connect();
        
        PreparedStatement pstm = conexao.prepareStatement(query);
        pstm.execute();
        this.conexao.commit();
        this.conexao.close();
        this.conexao = null;
    }
    
    public Connection getConnection(){
        return conexao;
    }
}

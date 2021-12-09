
import Persistencia.DBBuildTables;
import Persistencia.HSQLDBConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestaConexao {
    
    public static void main(String args[]) {
        
        //testeConexao();
        
        testeCriacaoTabela();
        
        
    }
    
    public static void testeConexao(){
        HSQLDBConnection db = new HSQLDBConnection();
        try {
            db.connect();
            System.out.println("O BD Foi conectado!");
            db.disconnect();
            System.out.println("Agora foi desconectado!");
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao tentar conectar!");
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testeCriacaoTabela(){
        DBBuildTables dbBuild = new DBBuildTables();
        
        try {
            dbBuild.buildTables();
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao tentar criar as Tabelas!");
            Logger.getLogger(TestaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

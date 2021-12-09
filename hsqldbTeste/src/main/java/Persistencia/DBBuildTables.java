package Persistencia;

import java.sql.SQLException;


public class DBBuildTables extends HSQLDBConnection {

    private final String tabelaPefil = "CREATE TABLE IF NOT EXISTS PERFIL("
            + "CODIGO INTEGER NOT NULL,"
            + "NOME VARCHAR(50) NOT NULL,"
            + "DESCRICAO VARCHAR(100) NOT NULL,"
            + "PRIMARY KEY(CODIGO));";
    
    private final String tabelaUsuarios = "CREATE TABLE IF NOT EXISTS USUARIOS("
            + "CPF VARCHAR(13) NOT NULL,"
            + "NOME VARCHAR(50) NOT NULL,"
            + "USERNAME VARCHAR(30) NOT NULL,"
            + "SENHA VARCHAR(100) NOT NULL,"
            + "CODIGO_PERFIL INTEGER NOT NULL,"
            + "TELEFONE VARCHAR(15) NOT NULL,"
            + "MATRICULA VARCHAR(5) NOT NULL,"
            + "DATA_NASCIMENTO DATE NOT NULL,"
            + "PRIMARY KEY(CPF),"
            + "FOREIGN KEY(CODIGO_PERFIL) REFERENCES PERFIL(CODIGO));";
    
    
    public void buildTables() throws SQLException{
        super.executeQuery(tabelaPefil);
        System.out.println("DB->log: Tabela Perfil Criada!");
        super.executeQuery(tabelaUsuarios);
        System.out.println("DB->log: Tabela Usuario Criada!");
    }

}

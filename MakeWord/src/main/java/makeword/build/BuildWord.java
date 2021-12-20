package makeword.build;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Numberings.NumberingBuilder;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TableRenderData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import makeword.tables.TableProperties;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

/** Classe para criação da instrução de substituição no Arquivo Word feito pela classe MakeWord
 * @author tiduswr
 * @version 1.0
 */
public class BuildWord {
    
    protected HashMap<String, Object> dtm;
    protected ArrayList<BuildTableWord> buildTbl;
    protected XWPFTemplate docFile;
    
    /**Método de instancia do objeto BuildWor
     * @param docReferenced XWPFTemplate - Template instanciado para trabalhar
     */
    public BuildWord(XWPFTemplate docReferenced){
        this.dtm = new HashMap<>();
        this.buildTbl = new ArrayList<>();
        this.docFile = docReferenced;
    }
        
    /**Método que procura uma chave no documento word e substitui por um valor do tipo String,
     * a tag que deve constar no arquivo word é a {fieldname}
     * @param key String - Valor a ser procurado no documento word
     * @param value String - Valor a ser substituido no documento word
     * @return Retorna o próprio objeto para facilitar nas substituições posteriores
     */
    public BuildWord replaceWithText(String key, String value){
        dtm.put(key, value);
        return this;
    }
    
    /** * Método que procura uma chave no documento word e substitui por uma imagem,
     * a tag que deve constar no arquivo word é a {{@literal @}fieldname}.
     * Ao passar o valor zero no height ou width da imagem, ela não será redimensionada.
     * @param key String - Valor a ser procurado no documento word
     * @param path String - Local e nome da imagem a ser inserida no arquivo
     * @param height int - Define a altura da imagem.
     * @param width int - Define a largura da imagem.
     * @param isUrl boolean - Especifica se o local da imagem é uma url
     * @return Retorna o próprio objeto para facilitar nas substituições posteriores
     */
    public BuildWord replaceWithPicture(String key, String path, int height, int width, boolean isUrl){
        Pictures.PictureBuilder pic;
        if(isUrl){
            pic = Pictures.ofUrl(path);
        }else{
            pic = Pictures.ofLocal(path);
        }
        if(height != 0 && width != 0) pic.size(height, width);
        dtm.put(key, pic.create());
        return this;
    }
       
    /**Método para inserir uma lista no template
     * @param key String - Parametro com a chave de pesquisa no template 
     * @param values String[] - Parametro com os valores para colocar na lista
     * @param FORMAT NumberingFormat - Formato da Lista para inserir no template
     * @return Retorna o próprio objeto para facilitar nas substituições posteriores
     */
    public BuildWord replaceWithList(String key, String[] values, NumberingFormat FORMAT){
        NumberingBuilder builder = Numberings.of(FORMAT);
        
        for(String s : values){
            builder.addItem(s);
        }
        dtm.put(key, builder.create());
        
        return this;
    }
    
     /**Função que cria uma nova tabela e substitui no arquivo template atraves do atributo key
     * @param table_data String[][] - Matriz contendo os dados para a tabela
     * @param key String - Valor a ser substituido no Template pela tabela
     * @param mergeRepeatedRows boolean - Mescla as células repetidas da tabela
     * @param hasHeader boolean - Informa se a tabela possui cabeçalho
     * @return TableProperties - Retorna a configuração da tabela criada para substituir no template
     */
    public TableProperties replaceWithNewTable(String key, String[][] table_data, boolean mergeRepeatedRows,
                                                boolean hasHeader){
        BuildTableWord bw = new BuildTableWord(key, table_data, mergeRepeatedRows, hasHeader);
        this.buildTbl.add(bw);
        return bw;
    }
    
    /** Monta as tabelas no modelo de substituição
     */
    protected void buildTablesOnModel(){
        this.buildTbl.forEach(currentTable -> {
            TableRenderData table = currentTable.createRows();
            if(currentTable.getMergeRule() != null){
                table.setMergeRule(currentTable.getMergeRule());
            }
            this.dtm.put(currentTable.getSearchTag(), table);
        });
        this.buildTbl.clear();
        this.buildTbl = null;
    }
    /**Insere os dados de uma matriz em uma tabela ja criada no template através do seu id
     * @param tableId int - Id da tabela presente no arquivo template
     * @param table_data String[][] - Matriz contendo os dados para a tabela
     * @return boolean - Retorna se a tabela foi preenchida no template ou não
     */
    public boolean replaceWithDocumentTable(int tableId, String[][] table_data){
        if(docFile != null){
            XWPFTable tblInDoc = docFile.getXWPFDocument().getAllTables().get(tableId);
            if(tblInDoc != null){
                int modelRow = 1;
                if(modelRow <= tblInDoc.getRows().size()){
                    try {
                        for (String[] dadosTeste1 : table_data) {
                            XWPFTableRow newRow = new XWPFTableRow(
                                CTRow.Factory.parse(tblInDoc.getRow(modelRow).getCtRow().newInputStream()), 
                                tblInDoc);

                            for (int j = 0; j < newRow.getTableCells().size(); j++) {
                                XWPFTableCell cell = newRow.getTableCells().get(j);
                                cell.setText(dadosTeste1[j]);
                            }
                            tblInDoc.addRow(newRow);
                        }
                        tblInDoc.removeRow(modelRow);
                    } catch (XmlException ex) {
                        System.out.println(ex.getError());
                        return false;
                    } catch (IOException ex) {
                        System.out.println(ex.getCause());
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

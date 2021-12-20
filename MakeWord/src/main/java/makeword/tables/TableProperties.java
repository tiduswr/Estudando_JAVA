package makeword.tables;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**Classe de configuração do construtor de uma tabela para uso da classe MakeWord
 * @author tiduswr
 * @version 1.0
 */
public class TableProperties {
    
    private TableRowProperties headerModel = null, rowsModel = null;
    protected String[][] tblData;
    protected boolean hasHeader = false;
    protected String searchTag;
    protected boolean autoWidth;
    
    /**Construtor para inserir os dados para criação da tabela
     * @param searchTag String - Tag de referencia a ser substituida no template word
     * @param table_data String[][] - Matriz contendo os dados para a tabela
     * @param hasHeader boolean - Informa se a tabela possui cabeçalho
     */
    public TableProperties(String searchTag, String[][] table_data, boolean hasHeader){
        this.searchTag = searchTag;
        this.autoWidth = false;
        this.hasHeader = hasHeader;
        this.tblData = table_data;
        this.rowsModel = new TableRowProperties();
        if(hasHeader) this.headerModel = new TableRowProperties();
    }
    
    /**Alterna a configuração de redimensionar automaticamente as colunas da tabela
     * @param config boolean - Estado desejado para esse parametro
     * @return TableProperties - Retorna o proprio objeto para continuar fazendo configurações*/
    public TableProperties setAutoWidth(boolean config){
        this.autoWidth = config;
        return this;
    }
    
    /**Seta os valores padrões para o Cabeçalho da tabela
     */
    protected void setStandardHeaderValues(){
        this.headerModel = new TableRowProperties();
        this.headerModel.setBackgroundColor("000066");
        this.headerModel.setTextColor("ffffff");
        this.headerModel.setTextBold(true);
        this.headerModel.setHorizontalAlign(ParagraphAlignment.CENTER);
    }
    
    /**Função para customizar o header da tabela
     * @return TableRowProperties - Retorna o objeto de customização do Header
     */
    public TableRowProperties buildHeader(){
        return this.headerModel;
    }
    
    /**Função para customizar as linhas da tabela
     * @return TableRowProperties - Retorna o objeto de customização das linhas
     */
    public TableRowProperties buildRows(){
        return this.rowsModel;
    }
    
}

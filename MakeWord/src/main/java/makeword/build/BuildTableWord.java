package makeword.build;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MergeCellRule;
import com.deepoove.poi.data.MergeCellRule.MergeCellRuleBuilder;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Rows.RowBuilder;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Tables.TableBuilder;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import java.util.ArrayList;
import makeword.tables.TableProperties;
import makeword.tables.TableRowProperties;

/**Classe para organizar as instruções de criação de uma tabela pelo BuildWord
 * @author tiduswr
 * @version 1.0
 */
public class BuildTableWord extends TableProperties{
    private RowBuilder header = null;
    private ArrayList<RowBuilder> rows;
    private MergeCellRule rule = null;
    
    /**Construtor que inicializa a tabela para customização antes de substituir no template do MakeWord
     * @param searchTag String - Chave para busca e substituição pela tabela no arquivo word
     * @param table_data String[][] - Matriz contendo os dados para inserir na tabela
     * @param mergeRepeated boolean - Informa se vai aplicar a regra de mesclagem nas celulas repetidas
     * @param hasHeader boolean - Informa se a tabela possui cabeçalho
     */
    public BuildTableWord(String searchTag, String [][] table_data, boolean mergeRepeated, boolean hasHeader){
        super(searchTag, table_data, hasHeader);
        this.rows = new ArrayList<>();
        if(mergeRepeated){
            this.rule = this.mergeRuleRepeatedBuild(this.tblData);
        }
        
        for (String[] data : tblData) {
            rows.add(Rows.of(data));
        }
        if(hasHeader) {
            header = rows.remove(0);
            super.setStandardHeaderValues();
        }
    }
    
    /**Aplica os modelos definidos pelas funções buildRows() e buildHeader()
     * @return Table Render Data - Retorna a lista das linhas criadas para uso na classe MakeWord*/
    public TableRenderData createRows(){
        TableBuilder builder = null;
        
        if(this.hasHeader && this.buildHeader() != null && this.hasHeader) {
            builder = Tables.of(this.applyAllStyles(this.header, this.buildHeader()));
        }
        
        for(RowBuilder row : this.rows){
            if(builder == null) {
                builder = Tables.of(this.applyAllStyles(row, this.buildRows()));
            }else{
                builder.addRow(this.applyAllStyles(row, this.buildRows()));
            }
        }

        if(builder != null){
            if(this.autoWidth) builder.autoWidth();
            return builder.create();
        }else{
            return null;
        }
    }
    
    /**Método para pegar a chave de pesquisa da tabela
     * @return String - Chave de pesquisa da tabela
     */
    public String getSearchTag(){
        return this.searchTag;
    }
    
    /**Cria uma MergeCellRule para celulas de uma mesma coluna que estejam repetidas
     * @param rowsData String[][] - Matriz com os dados da tabela
     * @return MergeCellRule - Retorna a MergeCellRule criada na função
     */
    private MergeCellRule mergeRuleRepeatedBuild(String[][] rowsData){
        MergeCellRuleBuilder repeatedRule = MergeCellRule.builder();
        int initialLine;
        if(this.hasHeader){
            initialLine = 1;
        }else{
            initialLine = 0;
        }
        
        for(int j = 0; j < rowsData[0].length; j++){
            int matchValues = 0;
            String previousValue = "";
            
            for(int i = initialLine; i < rowsData.length; i++){
                if(i != initialLine && previousValue.equalsIgnoreCase(rowsData[i][j])){
                    matchValues++;
                }else{
                    if(matchValues != 0){
                        repeatedRule.map(MergeCellRule.Grid.of((i-1) - matchValues, j), MergeCellRule.Grid.of(i-1, j));
                    }
                    matchValues = 0;
                }
                previousValue = rowsData[i][j];
            }
        }
        
        return repeatedRule.build();
    }
    
    /**Retorna a propriedade que define a regra de mesclagem
     * @return MergeCellRule - Retorna a MergeCellRule deste objeto
     */
    public MergeCellRule getMergeRule(){
        return this.rule;
    }
    
    /**Aplica os Estilos de Celula e Texto na tabela
     * @param ref RowBuilder - Objeto RowBuilder para aplicar as alterações do modelo
     * @param model TableRowProperties - Modelo com as especificações das alterações da tabela
     * @return RowRenderData - Retorna a linha da tabela construida
     */
    private RowRenderData applyAllStyles(RowBuilder ref, TableRowProperties model){
        RowRenderData rowCreated = ref.create();        
        rowCreated.getCells().forEach(cell -> {
            CellRenderData currentCell = this.applyCellStyle(cell, model);
            currentCell.getParagraphs().forEach(prg -> {
                this.applyParagraphStyle(prg, model);
                this.applyTextStyle(prg, model);
            });
        });
        return rowCreated;
    }
    
    /**Aplica os Estilos de Celula na tabela
     * @param cell CellRenderData - Objeto CellRenderData para aplicar as alterações do modelo
     * @param model TableRowProperties - Modelo com as especificações das alterações da tabela
     * @return CellRenderData - Retorna a celula da tabela construida
     */
    private CellRenderData applyCellStyle(CellRenderData cell, TableRowProperties model){
        try{
            CellStyle cellStyle = new CellStyle();
            cellStyle.setVertAlign(model.getVerticalAlign());
            cellStyle.setBackgroundColor(model.getBackgroundColor());
            cell.setCellStyle(cellStyle);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return cell;
        }
        return cell;
    }
    
    /**Aplica os Estilos de Paragrafo na tabela
     * @param prd ParagraphRenderData - Objeto ParagraphRenderData para aplicar as alterações do modelo
     * @param model TableRowProperties - Modelo com as especificações das alterações da tabela
     * @return ParagraphRenderData - Retorna o paragrafo da tabela construido
     */
    private ParagraphRenderData applyParagraphStyle(ParagraphRenderData prd, TableRowProperties model){
        try{
            ParagraphStyle prStyle = new ParagraphStyle();
            prStyle.setAlign(model.getHorizontalAlign());
            prd.setParagraphStyle(prStyle);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return prd;
        }
        return prd;
    }
    
    /**Aplica os Estilos de texto a um paragrafo na tabela
     * @param prd ParagraphRenderData - Objeto ParagraphRenderData para aplicar as alterações do modelo
     * @param model TableRowProperties - Modelo com as especificações das alterações da tabela
     * @return ParagraphRenderData - Retorna o paragrafo da tabela construido
     */
    private ParagraphRenderData applyTextStyle(ParagraphRenderData prd, TableRowProperties model){
        try{
            Style txtStyle = new Style();
            txtStyle.setColor(model.getTextColor());
            txtStyle.setFontFamily(model.getTextFontFamily());
            txtStyle.setFontSize(model.getTextFontSize());
            txtStyle.setItalic(model.isTextItalic());
            txtStyle.setBold(model.isTextBold());
            prd.getParagraphStyle().setDefaultTextStyle(txtStyle);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return prd;
        }
        return prd;
    }
}

package makeword.tables;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;


/**Classe para especificar os detalhes de customização visual de uma tabela 
 * para se usar na classe BuildTableWord
 * @author tiduswr
 */
public class TableRowProperties {
    private boolean textBold, textItalic;
    private int textFontSize;
    private String textFontFamily, backgroundColor, textColor;
    private ParagraphAlignment horizontalAlign;
    private XWPFVertAlign verticalAlign;
    
    /**Construtor com configuração inicial para a celula*/
    public TableRowProperties() {
        this.textBold = false;
        this.textItalic = false;
        this.textFontSize = 12;
        this.textFontFamily = "Arial";
        this.backgroundColor = null;
        this.textColor = "000000";
        this.verticalAlign = XWPFVertAlign.CENTER;
        this.horizontalAlign = ParagraphAlignment.LEFT;
    }
    
    /**Retorna se o atributo Negrito para o texto
     * @return boolean - Retorna se o texto é negrito ou não
     */    
    public boolean isTextBold() {
        return textBold;
    }

    /**Atribui Negrito para o texto ou não
     * @param textBold boolean - Insira um valor boleano para definir se o texto é negrito ou não
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setTextBold(boolean textBold) {
        this.textBold = textBold;
        return this;
    }
    
    /**Retorna se o atributo é Iatlico para o texto
     * @return boolean - Retorna se o texto é italico ou não
     */
    public boolean isTextItalic() {
        return textItalic;
    }
    
    /**Atribui Italico para o texto ou não
     * @param textItalic boolean - Insira um valor boleano para definir se o texto é italico ou não
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setTextItalic(boolean textItalic) {
        this.textItalic = textItalic;
        return this;
    }
    
    /**Retorna o tamanho da fonte do texto
     * @return int - Tamanho da fonta do texto
     */
    public int getTextFontSize() {
        return textFontSize;
    }
    
    /**Atribui o tamanho da fonte do texto
     * @param textFontSize int - Valor para atribuir o tamanho da fonte
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
        return this;
    }
    
    /**Retorna qual a fonte usada no texto
     * @return String - Fonte do texto
     */
    public String getTextFontFamily() {
        return textFontFamily;
    }
    
    /**Atribui um tipo de fonte ao texto
     * @param textFontFamily String - Atribui uma fonte ao texto
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setTextFontFamily(String textFontFamily) {
        this.textFontFamily = textFontFamily;
        return this;
    }

    /**Retorna a cor de fundo das celulas dessa linha
     * @return String - Retorna a cor usada no background das celulas dessa linha tabela em formato Hexadecimal
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }
    
    /**Retorna a cor de fundo da celula
     * @param backgroundColor String - Passe como parametro a cor usada no 
     * background das celulas dessa linha da tabela em formato Hexadecimal 
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    /**Retorna a cor do texto
     * @return String - Cor do texto em formato hexadecimal
     */
    public String getTextColor() {
        return textColor;
    }
    
    /**Define a cor do texto
     * @param textColor String - Cor do texto em formato hexadecimal
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }
    
    /**Retorna o tipo de alinhamento Vertical da Celula
     * @return XWPFVertAlign - Valor que define o alinhamento vertical da celula
     */
    public XWPFVertAlign getVerticalAlign() {
        return verticalAlign;
    }

    /**Atribui o tipo de alinhamento Vertical da Celula
     * @param VERTICAL_ALIGN XWPFVertAlign - Valor que define o alinhamento vertical da celula
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setVerticalAlign(XWPFVertAlign VERTICAL_ALIGN) {
        this.verticalAlign = VERTICAL_ALIGN;
        return this;
    }
    
    /**Retorna o tipo de alinhamento horizontal da celula
     * @return ParagraphAlignment - Tipo de alinhamento horizontal 
     */
    public ParagraphAlignment getHorizontalAlign() {
        return horizontalAlign;
    }

    /**Atribui o tipo de alinhamento horizontal da celula
     * @param HORIZONTAL_ALIGN ParagraphAlignment - Tipo de alinhamento horizontal  
     * @return TableRowProperties - Retorna o proprio objeto para alterações posteriores
     */
    public TableRowProperties setHorizontalAlign(ParagraphAlignment HORIZONTAL_ALIGN) {
        this.horizontalAlign = HORIZONTAL_ALIGN;
        return this;
    }
    
}

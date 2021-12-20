package makeword;

import makeword.build.BuildWord;
import com.deepoove.poi.XWPFTemplate;
import java.io.FileOutputStream;
import java.io.IOException;

/**Classe para criação de arquivos word a partir de um Template
 * @author tiduswr
 * @version 1.0
 */
public class MakeWord extends BuildWord{
        
    /**Método de instancia do objeto MakeWord
     * @param templatePath - Local com nome do arquivo para carregar o template word
     */
    public MakeWord(String templatePath){
        super(XWPFTemplate.compile(templatePath));
    }
    
    /**Aplica o modelo construido na classe BuildWord
     * @param outputFilePath - Local com nome do arquivo para salvar o arquivo com as alterações feitas pelo modelo
     * @throws java.io.IOException - Caso não consiga salvar o arquivo, vai dar um erro
     */
    public void applyModel(String outputFilePath) throws IOException{
        this.buildTablesOnModel();
        docFile.render(this.dtm).writeAndClose(new FileOutputStream(outputFilePath));
    }
}

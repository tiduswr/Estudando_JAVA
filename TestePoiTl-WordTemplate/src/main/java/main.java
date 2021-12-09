
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MergeCellRule;
import com.deepoove.poi.data.MergeCellRule.Grid;
import com.deepoove.poi.data.MergeCellRule.MergeCellRuleBuilder;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Pictures.PictureBuilder;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

public class main {
    private static final String TEMPLATE = "template.docx";
    private static final String OUTPUT_FILE = "out_template.docx";
    
    public static void main(String args[]) {
        
        //Autor: Harllem Nascimento
        //Data: 06 de dezembro de 2021
        
        HashMap<String, Object> dataModel = new HashMap<>(); //Carrega um modelo em forma de HashMap
        XWPFTemplate docFile = XWPFTemplate.compile(TEMPLATE); //Carrega o documento base na memoria
        
        //Substitui um texto por outro texto no arquivo Template
        //Texto nao precisa de tag especifica, o formato é -> {{merge_field}}
        dataModel.put("numOficio", "32");
        dataModel.put("nomePresidente", "JOSÉ JEFFERSSON");
        dataModel.put("ano", "2021");
        dataModel.put("municipioMA", "PATOS");
        dataModel.put("municipioMin", "Patos");
        
        //Inserindo uma data formatada no documento
        //GregorianCalendar(ANO, MES, DIA, HORA, MINUTO, SEGUNDO)
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Calendar data = new GregorianCalendar(2021, 11, 30, 23, 29, 50);
        String dataFormatada = data.get(Calendar.DAY_OF_MONTH) + " de " + sdf.format(data.getTime())
                                + " de " + data.get(Calendar.YEAR);
        dataModel.put("dataExtenso", dataFormatada);
        
        
        //Substitui um texto por uma imagem no arquivo Template
        //A tag de imagem segue o seguinte padrão -> {{@merge_field}}
        PictureBuilder pic = Pictures.ofLocal("brasao.jpg");
        pic.size(90, 90);
        dataModel.put("imagem", pic.create());
        
        //Substitui um texto por uma lista
        //A tag de lista segue o seguinte padrão -> {{*merge_field}}
        String list[] = {"Aprovação da Prestação de Contas do FEAS", "Eleição de Presidente", 
                            "Analise do Relatório Anual da Secretariao de Assistência Social"};
        dataModel.put("pautas", 
            Numberings.create(list)
        );
        
        //Substitui um texto por uma tabela
        //A tag de tabela segue o seguinte padrão -> {{#merge_field}}
        String[][] rowsData = {
            new String[] {"Nome", "Representação", "Tipo"},
            new String[] {"Harllem", "Assistencia Social", "Titular"},
            new String[] {"Neto", "Assistencia Social", "Suplente"},
            new String[] {"Socorro", "Assistencia Social", "Titular"},
            new String[] {"Fernanda", "Saude", "Suplente"},
            new String[] {"José Jefferson", "Saude", "Presidente"}
        };
        
        //Instancia um modelo para a tabela
        TableRenderData tbl = new TableRenderData();
        
        //Adiciona um modelo para o cabeçalho
        tbl.addRow(Rows.of(rowsData[0])
                    .center()
                    .verticalCenter()
                    .textBold()
                    .bgColor("4472c4")
                    .textFontSize(14)
                    .textFontFamily("Arial")
                    .create()
        );
        
        //Adiciona um modelo para as Linhas
        for(int i = 1; i < rowsData.length; i++){
            tbl.addRow(Rows.of(rowsData[i])
                    .textFontSize(12)
                    .textFontFamily("Arial")
                    .verticalCenter()
                    .create());
        }
        
        //Faz uma regra de mesclagem para as linhas
        MergeCellRuleBuilder rule = MergeCellRule.builder();
        
        for(int j = 0; j < rowsData[0].length; j++){
            int matchValues = 0;
            String previousValue = "";
            
            for(int i = 1; i < rowsData.length; i++){
                if(previousValue.equalsIgnoreCase(rowsData[i][j])){
                    matchValues++;
                }else{
                    if(matchValues != 0){
                        rule.map(Grid.of(i - matchValues, j), Grid.of(i, j));
                    }
                    matchValues = 0;
                }
                previousValue = rowsData[i][j];
            }
        }
        tbl.setMergeRule(rule.build());
        
        String dadosTeste[][] = {
            new String[]{"Harllem", "23", "Masculino"},
            new String[]{"Jullyana", "12", "Feminino"},
            new String[]{"José Carlos", "50", "Masculino"}
        };
        
        //Adicionar valores a uma tabela ja existente
        XWPFTable tblInDoc = docFile.getXWPFDocument().getAllTables().get(0);
        int modelRow = 1;
        
        if(modelRow <= tblInDoc.getRows().size()){
            try {
                for (String[] dadosTeste1 : dadosTeste) {
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
            } catch (IOException ex) {
                System.out.println(ex.getCause());
            }
        }

        //Adiciona a alteração ao modelo do arquivo (HashMap)
        dataModel.put("tabela", tbl);
        
        //Aplica as alterações do modelo
        try {
            docFile.render(dataModel).writeToFile(OUTPUT_FILE);
            docFile.close();
        } catch (IOException ex) {
            ex.getMessage();
        }

    }
}

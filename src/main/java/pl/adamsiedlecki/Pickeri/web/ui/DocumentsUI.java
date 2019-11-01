package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.ExcelGenerationTab;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.PdfDocumentsGeneratorTab;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.EarningsRaportGenerationTab;

@SpringUI(path = "/documents-generator")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${documents.title}")
public class DocumentsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab;
    private MenuTab menuTab;
    private Environment env;
    private EarningsRaportGenerationTab pdfGenerationTab;
    private ExcelGenerationTab excelGenerationTab;

    @Autowired
    public DocumentsUI(PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, MenuTab menuTab, Environment environment,
                       EarningsRaportGenerationTab pdfGenerationTab, ExcelGenerationTab excelGenerationTab) {
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
        this.pdfGenerationTab = pdfGenerationTab;
        this.menuTab = menuTab;
        this.env = environment;
        this.excelGenerationTab = excelGenerationTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        addTabs();
        AlignmentSetter.apply(root, pickeriLogo, tabs);
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(pdfGenerationTab, env.getProperty("generate.pdf.tab"));
        tabs.addTab(pdfDocumentsGeneratorTab, env.getProperty("generate.qr.list.raport.tab"));
        tabs.addTab(excelGenerationTab, env.getProperty("generate.excel.tab"));
        tabs.addTab(menuTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}

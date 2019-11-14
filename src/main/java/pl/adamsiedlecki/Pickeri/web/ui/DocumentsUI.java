package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.ExcelGenerationTab;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.PdfDocumentsGeneratorTab;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.documentsTabs.EarningsRaportGenerationTab;

@SpringUI(path = "/documents-generator")
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
    private SettingsService settingsService;

    @Autowired
    public DocumentsUI(PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, MenuTab menuTab, Environment environment,
                       EarningsRaportGenerationTab pdfGenerationTab, ExcelGenerationTab excelGenerationTab,
                       SettingsService settingsService) {
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
        this.pdfGenerationTab = pdfGenerationTab;
        this.menuTab = menuTab;
        this.env = environment;
        this.excelGenerationTab = excelGenerationTab;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        HeaderAdder.add(root, settingsService);
        addTabs();
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
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

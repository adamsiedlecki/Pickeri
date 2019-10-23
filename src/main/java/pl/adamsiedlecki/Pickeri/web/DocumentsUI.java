package pl.adamsiedlecki.Pickeri.web;

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
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.*;

@SpringUI(path = "/documents-generator")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${documents.title}")
public class DocumentsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab;
    private MenuTab othersTab;
    private Environment env;

    @Autowired
    public DocumentsUI(PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, MenuTab othersTab, Environment environment) {
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
        this.othersTab = othersTab;
        this.env = environment;
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
        tabs.addTab(pdfDocumentsGeneratorTab, env.getProperty("generate.pdf.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}

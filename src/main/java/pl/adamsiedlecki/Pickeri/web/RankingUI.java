package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.RankingTab;

@SpringUI(path = "/ranking")
public class RankingUI extends UI {

    private VerticalLayout root;
    private RankingTab rankingTab;
    private OthersTab othersTab;

    @Autowired
    public RankingUI(RankingTab rankingTab, OthersTab othersTab) {
        this.rankingTab = rankingTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        addTabs();
        this.setContent(root);
    }

    private void addTabs() {
        TabSheet tabs = new TabSheet();
        tabs.addTab(rankingTab, "Ranking");
        tabs.addTab(othersTab, "Reszta");
        root.addComponent(tabs);
    }

}

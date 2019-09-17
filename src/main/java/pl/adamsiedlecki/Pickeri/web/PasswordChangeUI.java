package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;
import pl.adamsiedlecki.Pickeri.service.PickeriUserDetailsService;

@SpringUI(path="/password-change")
public class PasswordChangeUI extends UI {

    private VerticalLayout root;
    private PickeriUserDetailsService userDetailsService;

    public PasswordChangeUI(PickeriUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        Notification.show("Uwaga! Ta strona przeznaczona jest do zmiany hasła!");
        initComponents();
    }

    private void initComponents(){
        root = new VerticalLayout();

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Label label = new Label("Nazwa aktualnie zalogowanego użytkownika: "+username);
        TextField newPassword = new TextField();
        newPassword.setCaption(" NOWE HASŁO ");
        Button applyButton = new Button("ZAPISZ NOWE HASŁO");
        applyButton.addClickListener(x->{
            userDetailsService.changePassword(username,newPassword.getValue());
            newPassword.clear();
            Notification.show("HASŁO ZOSTAŁO ZMIENIONE ");
                });

        root.addComponents(label,newPassword,applyButton);
        this.setContent(root);
    }

}
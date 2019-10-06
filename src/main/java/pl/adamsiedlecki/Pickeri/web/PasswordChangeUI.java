package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.adamsiedlecki.Pickeri.service.PickeriUserDetailsService;

@SpringUI(path = "/password-change")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${password.change.title}")
public class PasswordChangeUI extends UI {

    private VerticalLayout root;
    private PickeriUserDetailsService userDetailsService;
    private Environment env;

    @Autowired
    public PasswordChangeUI(PickeriUserDetailsService userDetailsService, Environment environment) {
        this.userDetailsService = userDetailsService;
        this.env = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        Notification.show(env.getProperty("warning.password.change.page"));
        initComponents();
    }

    private void initComponents() {
        root = new VerticalLayout();

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Label label = new Label(env.getProperty("actual.logged.username") + username);
        TextField newPassword = new TextField();
        newPassword.setCaption(env.getProperty("new.password.label"));
        Button applyButton = new Button(env.getProperty("save.new.password.button"));
        applyButton.addClickListener(x -> {
            userDetailsService.changePassword(username, newPassword.getValue());
            newPassword.clear();
            Notification.show(env.getProperty("password.has.been.changed.notification"));
        });

        root.addComponents(label, newPassword, applyButton);
        this.setContent(root);
    }

}

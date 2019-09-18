package pl.adamsiedlecki.Pickeri.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Scope("prototype")
@Controller
public class URLController {

    @RequestMapping("/error1")
    public RedirectView handleError() {
        //do something like logging
        return new RedirectView("errorPage");
    }

}

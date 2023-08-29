package com.ftn.TeretanaVebProjekat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping(value="/change-locale")
public class LocaleController {

    @GetMapping("/change-locale")
    public String changeLocale(
            @RequestParam("locale") String localeCode,
            HttpServletRequest request
    ) {
        // Postavite novi Locale na osnovu dobijenog localeCode
        Locale newLocale = new Locale(localeCode);

        // Postavite novi Locale u sesiju
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, newLocale);

        return "redirect:/"; // Redirekcija na početnu stranicu ili neku drugu
    }
}

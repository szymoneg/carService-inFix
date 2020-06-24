package com.company.infix.controler;

import com.company.infix.service.SendMails;
import com.company.infix.service.impl.SendMailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class SendMailsAPIController {
    private SendMails mailService;

    @Autowired
    public SendMailsAPIController(SendMails mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/sendMail")
    public String sendMail(String mail,String login) throws MessagingException {
        mailService.sendMail( mail,
                "witamy na platformie inFix!",
                "<b>Witaj, "+ login+"!</b><br>:P", true);
        return "wysłano";
    }
}

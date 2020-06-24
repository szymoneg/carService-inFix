package com.company.infix.service;

import javax.mail.MessagingException;

public interface SendMails {
    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;
}

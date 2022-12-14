package com.synechron.insurance.service;

import com.synechron.insurance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final ProposalService proposalService;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("car.insurance.register@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @JmsListener(destination="report-queue", containerFactory = "jmsFactory")
    public void sendReportMail(Map<String, Object> map) throws JRException, IOException, NotFoundException, MessagingException {
        Long proposalId = (Long)map.get("proposal");
        String subscriberEmail = (String)map.get("subscriberEmail");
        String path = proposalService.generateReport(proposalId);
        File file = ResourceUtils.getFile(path);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(subscriberEmail);
        helper.setFrom("car.insurance.register@gmail.com");
        helper.setSubject("Proposal report");
        helper.setText(String.format("Report for proposal %d is done!", proposalId));
        helper.addAttachment(String.format("proposal_report_%d", proposalId), file);
        mailSender.send(message);
    }
}

package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.email.templates.EmailHtmlTemplates;
import br.com.felipedev.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAutomationService {

    private final EmailService emailService;
    private final UserService userService;

    @Scheduled(cron = "* * 11 * * *", zone = "America/Sao_Paulo")
    public void notifyUsersAboutPasswordExpiration() {
        List<User> userList = userService.getUsersWithExpiredPasswords();
        for (User user : userList) {
            String to = user.getEmail();
            String subject = "Alteração de Senha";
            String content = EmailHtmlTemplates.buildPasswordChangeNotificationEmailHtml(user.getPerson().getName());
            emailService.sendEmail(to, subject, content);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException ex) {
                throw new RuntimeException("sleep falhou");
            }
        }
    }
}

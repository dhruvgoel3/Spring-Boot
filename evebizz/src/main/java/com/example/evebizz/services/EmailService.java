package com.example.evebizz.services;

import com.example.evebizz.entities.CollaborationInterest;
import com.example.evebizz.entities.ExpertInvitation;
import com.example.evebizz.entities.SponsorshipApplication;
import com.example.evebizz.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name}")
    private String fromName;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Async
    public void sendWelcomeEmail(User user) {
        String subject = "Welcome to EventHub!";
        String body = buildHtmlEmail(
                "Welcome to EventHub, " + user.getFullName() + "!",
                "You've successfully registered as a <strong>" + user.getRole().name() + "</strong>.",
                "Start exploring opportunities and building connections in the event ecosystem.",
                frontendUrl + "/dashboard",
                "Go to Dashboard"
        );
        sendEmail(user.getEmail(), subject, body);
    }

    @Async
    public void sendNewApplicationNotification(User companyUser, SponsorshipApplication application) {
        String subject = "New Sponsorship Application - " + application.getEventName();
        String body = buildHtmlEmail(
                "New Application Received",
                "You have a new sponsorship application from <strong>" + application.getOrganizer().getFullName() + "</strong>",
                "Event: " + application.getEventName(),
                frontendUrl + "/dashboard/applications/" + application.getId(),
                "Review Application"
        );
        sendEmail(companyUser.getEmail(), subject, body);
    }

    @Async
    public void sendApplicationStatusUpdate(SponsorshipApplication application) {
        String status = application.getStatus().name();
        String subject = "Your Sponsorship Application - " + status;
        String body = buildHtmlEmail(
                "Application Status Update",
                "Your application for <strong>" + application.getListing().getTitle() + "</strong> has been <strong>" + status.toLowerCase() + "</strong>.",
                application.getRejectionReason() != null ? "Reason: " + application.getRejectionReason() : "",
                frontendUrl + "/dashboard/my-applications",
                "View My Applications"
        );
        sendEmail(application.getOrganizer().getEmail(), subject, body);
    }

    @Async
    public void sendCollaborationInterestNotification(User clubOwner, CollaborationInterest interest) {
        String subject = "New Collaboration Interest!";
        String body = buildHtmlEmail(
                "Someone's Interested in Collaborating!",
                "<strong>" + interest.getInterestedClub().getClubName() + "</strong> is interested in your collaboration request.",
                "Request: " + interest.getRequest().getTitle(),
                frontendUrl + "/dashboard/collaborations/" + interest.getRequest().getId(),
                "View Request"
        );
        sendEmail(clubOwner.getEmail(), subject, body);
    }

    @Async
    public void sendExpertInvitation(ExpertInvitation invitation) {
        String subject = "You've been invited to " + invitation.getEventName() + "!";
        String body = buildHtmlEmail(
                "Event Invitation",
                "You have been invited as a <strong>" + invitation.getRoleRequested().name() + "</strong> for <strong>" + invitation.getEventName() + "</strong>.",
                "Message from organizer: " + invitation.getMessage(),
                frontendUrl + "/dashboard/invitations/" + invitation.getId(),
                "View Invitation"
        );
        sendEmail(invitation.getExpert().getUser().getEmail(), subject, body);
    }

    @Async
    public void sendInvitationResponseNotification(ExpertInvitation invitation) {
        String status = invitation.getStatus().name().toLowerCase();
        String subject = "Invitation " + status + " - " + invitation.getEventName();
        String body = buildHtmlEmail(
                "Invitation Response",
                invitation.getExpert().getUser().getFullName() + " has <strong>" + status + "</strong> your invitation for " + invitation.getEventName(),
                invitation.getResponseMessage() != null ? "Response: " + invitation.getResponseMessage() : "",
                frontendUrl + "/dashboard/sent-invitations",
                "View Invitations"
        );
        sendEmail(invitation.getOrganizer().getEmail(), subject, body);
    }

    private void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            log.info("Email sent to: {}", to);
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    private String buildHtmlEmail(String heading, String body, String subtext, String ctaUrl, String ctaText) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
                        .container { max-width: 600px; margin: 40px auto; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
                        .header { background: #6366f1; padding: 32px; text-align: center; }
                        .header h1 { color: white; margin: 0; font-size: 24px; }
                        .content { padding: 32px; }
                        .content h2 { color: #1f2937; }
                        .content p { color: #4b5563; line-height: 1.6; }
                        .cta { display: block; width: fit-content; margin: 24px auto; padding: 14px 28px; background: #6366f1; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }
                        .footer { padding: 16px 32px; background: #f9fafb; text-align: center; color: #9ca3af; font-size: 12px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header"><h1>EventHub</h1></div>
                        <div class="content">
                            <h2>%s</h2>
                            <p>%s</p>
                            <p>%s</p>
                            <a href="%s" class="cta">%s</a>
                        </div>
                        <div class="footer">
                            <p>EventHub — Digital Infrastructure for Events</p>
                            <p>If you didn't request this email, you can safely ignore it.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(heading, body, subtext, ctaUrl, ctaText);
    }
}
package com.BusBooking.Bus.service;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTicket(String toEmail, byte[] pdfBytes, String fileName, Booking booking, Bus bus) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("🚌 Your Bus Ticket Confirmation");

        String emailBody = """
                Dear Passenger,

                Thank you for booking your journey with us through the Online Bus Booking platform.

                📅 Travel Date: %s
                🚌 Route: %s → %s
                🏢 Operator: %s
                💺 Passenger Count: %d
                💳 Amount Paid: ₹%.2f

                Your e-ticket (attached as PDF) contains:
                - Passenger information
                - Seat numbers and types
                - Bus operator & timings
                - QR code for quick verification

                👉 Please carry a digital or printed copy of this ticket while boarding.
                ✅ The QR code on the ticket can be scanned at the boarding point.

                For any assistance, feel free to reach us at: support@onlinebusbooking.com

                Wishing you a safe and comfortable journey!
                — Online Bus Booking Team
                """.formatted(
                booking.getTravelDate(),
                bus.getFrom(),
                bus.getTo(),
                bus.getOperatorName(),
                booking.getPassengers().size(),
                booking.getTotalAmount()
        );

        helper.setText(emailBody);

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        helper.addAttachment(fileName, resource);

        mailSender.send(message);
    }
}

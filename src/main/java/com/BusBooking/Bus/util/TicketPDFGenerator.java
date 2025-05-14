package com.BusBooking.Bus.util;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.model.Passenger;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;

public class TicketPDFGenerator {

    public static byte[] generateTicketPDF(Booking booking, Bus bus) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // Title
        doc.add(new Paragraph("🚌 Bus Ticket")
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("------------------------------------------------------------"));

        // Trip and Booking Details
        doc.add(new Paragraph("Operator: " + bus.getOperatorName()));
        doc.add(new Paragraph("Route: " + bus.getFrom() + " → " + bus.getTo()));
        doc.add(new Paragraph("Departure Time: " + bus.getDepartureTime()));
        doc.add(new Paragraph("Arrival Time: " + bus.getArrivalTime()));
        doc.add(new Paragraph("Travel Date: " + booking.getTravelDate()));
        doc.add(new Paragraph("Booking Date: " + booking.getBookingDate()));
        doc.add(new Paragraph("Total Paid: ₹" + booking.getTotalAmount()));
        doc.add(new Paragraph(" "));

        // Passenger Details
        doc.add(new Paragraph("👥 Passengers:").setBold());
        for (Passenger p : booking.getPassengers()) {
            doc.add(new Paragraph(" - " + p.getName()
                    + ", Age: " + p.getAge()
                    + ", Gender: " + p.getGender()
                    + ", Seat: " + p.getSeatNumber()
                    + ", Type: " + p.getSeatType()));
        }

        doc.add(new Paragraph(" "));

        // ✅ Razorpay Payment Details (Assuming available in Booking object)
        if (booking.getPaymentId() != null) {
            doc.add(new Paragraph("💳 Payment Details").setBold());
            doc.add(new Paragraph("Payment ID: " + booking.getPaymentId()));
            doc.add(new Paragraph("Order ID: " + booking.getOrderId()));
            doc.add(new Paragraph("Receipt: " + booking.getReceipt()));
            doc.add(new Paragraph("Status: " + booking.getPaymentStatus()));
            doc.add(new Paragraph(" "));
        }

        // QR Code content
        String qrContent = "Bus: " + bus.getOperatorName()
                + " | From: " + bus.getFrom()
                + " | To: " + bus.getTo()
                + " | Travel Date: " + booking.getTravelDate()
                + " | Paid: ₹" + booking.getTotalAmount();

        ByteArrayOutputStream qrOutput = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        var matrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 150, 150);
        MatrixToImageWriter.writeToStream(matrix, "PNG", qrOutput);

        Image qrImage = new Image(ImageDataFactory.create(qrOutput.toByteArray()));
        qrImage.setTextAlignment(TextAlignment.CENTER);
        doc.add(new Paragraph("\nScan QR for quick ticket info").setTextAlignment(TextAlignment.CENTER));
        doc.add(qrImage);

        doc.close();
        return outputStream.toByteArray();
    }
}

package com.BusBooking.Bus.util;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.model.Passenger;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TicketPDFGenerator {

    public static byte[] generateTicketPDF(Booking booking, Bus bus) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // 1. Add Logo (Assuming logo.png is inside /resources/static)
        InputStream logoStream = TicketPDFGenerator.class.getResourceAsStream("/static/logo.png");
        if (logoStream != null) {
            Image logo = new Image(ImageDataFactory.create(logoStream.readAllBytes()));
            logo.scaleToFit(60, 60);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(logo);
        }

        // 2. Title
        Paragraph title = new Paragraph("🚌 Bus Ticket")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.DARK_GRAY);
        doc.add(title);

        // 3. Trip Info Table
        Table tripTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .useAllAvailableWidth()
                .setMarginTop(5);
        tripTable.addCell(getCell("Operator:", true));
        tripTable.addCell(getCell(bus.getOperatorName(), false));
        tripTable.addCell(getCell("Route:", true));
        tripTable.addCell(getCell(bus.getFrom() + " → " + bus.getTo(), false));
        tripTable.addCell(getCell("Departure:", true));
        tripTable.addCell(getCell(bus.getDepartureTime(), false));
        tripTable.addCell(getCell("Arrival:", true));
        tripTable.addCell(getCell(bus.getArrivalTime(), false));
        tripTable.addCell(getCell("Travel Date:", true));
        tripTable.addCell(getCell(booking.getTravelDate().toString(), false));
        tripTable.addCell(getCell("Booking Date:", true));
        tripTable.addCell(getCell(booking.getBookingDate().toString(), false));
        tripTable.addCell(getCell("Paid:", true));
        tripTable.addCell(getCell("₹" + booking.getTotalAmount(), false));

        doc.add(tripTable);

        // 4. Passenger Details
        doc.add(new Paragraph("\n👥 Passenger Details").setBold().setFontSize(12).setMarginBottom(5));
        Table passengerTable = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}))
                .useAllAvailableWidth();
        passengerTable.addHeaderCell(getHeaderCell("Name"));
        passengerTable.addHeaderCell(getHeaderCell("Age"));
        passengerTable.addHeaderCell(getHeaderCell("Gender"));
        passengerTable.addHeaderCell(getHeaderCell("Seat (Type)"));

        for (Passenger p : booking.getPassengers()) {
            passengerTable.addCell(getCell(p.getName(), false));
            passengerTable.addCell(getCell(String.valueOf(p.getAge()), false));
            passengerTable.addCell(getCell(p.getGender(), false));
            passengerTable.addCell(getCell(p.getSeatNumber() + " (" + p.getSeatType() + ")", false));
        }

        doc.add(passengerTable);

        // 5. Payment Info
        if (booking.getPaymentId() != null) {
            doc.add(new Paragraph("\n💳 Payment Info").setBold().setFontSize(12).setMarginBottom(5));
            Table paymentTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .useAllAvailableWidth();
            paymentTable.addCell(getCell("Payment ID:", true));
            paymentTable.addCell(getCell(booking.getPaymentId(), false));
            paymentTable.addCell(getCell("Order ID:", true));
            paymentTable.addCell(getCell(booking.getOrderId(), false));
            paymentTable.addCell(getCell("Receipt:", true));
            paymentTable.addCell(getCell(booking.getReceipt(), false));
            paymentTable.addCell(getCell("Status:", true));
            paymentTable.addCell(getCell(booking.getPaymentStatus(), false));
            doc.add(paymentTable);
        }

        // 6. QR Code (compact)
        String qrContent = "Bus: " + bus.getOperatorName()
                + " | From: " + bus.getFrom()
                + " | To: " + bus.getTo()
                + " | Travel Date: " + booking.getTravelDate()
                + " | Paid: ₹" + booking.getTotalAmount();

        ByteArrayOutputStream qrOutput = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        var matrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 100, 100);
        MatrixToImageWriter.writeToStream(matrix, "PNG", qrOutput);

        Image qrImage = new Image(ImageDataFactory.create(qrOutput.toByteArray()));
        qrImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrImage.setMaxHeight(100);
        qrImage.setMaxWidth(100);
        qrImage.setAutoScale(true);

        doc.add(new Paragraph("\nScan QR for quick ticket info")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setMarginBottom(5));
        doc.add(qrImage);

        doc.close();
        return outputStream.toByteArray();
    }

    private static Cell getCell(String text, boolean isBold) {
        Paragraph para = new Paragraph(text).setFontSize(10);
        if (isBold) para.setBold();
        return new Cell().add(para).setPadding(3).setTextAlignment(TextAlignment.LEFT);
    }

    private static Cell getHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold().setFontSize(10))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setPadding(3)
                .setTextAlignment(TextAlignment.CENTER);
    }
}

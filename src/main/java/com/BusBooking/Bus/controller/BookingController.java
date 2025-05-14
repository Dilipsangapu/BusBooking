package com.BusBooking.Bus.controller;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.model.Passenger;
import com.BusBooking.Bus.model.User;
import com.BusBooking.Bus.repository.BookingRepository;
import com.BusBooking.Bus.repository.BusRepository;
import com.BusBooking.Bus.repository.UserRepository;
import com.BusBooking.Bus.util.TicketPDFGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "Endpoints for managing bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    @PostMapping("/book/{busId}")
    @ResponseBody
    @Operation(summary = "Book a bus (returns JSON for Swagger)")
    public ResponseEntity<?> bookBus(@PathVariable String busId,
                                     @RequestParam List<String> passengerName,
                                     @RequestParam List<Integer> passengerAge,
                                     @RequestParam List<String> passengerGender,
                                     @RequestParam String seatNumber,
                                     @RequestParam List<String> seatType,
                                     @RequestParam(required = false) String travelDate,
                                     Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User must be logged in"));
        }

        String email = principal.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found"));
        }

        if (travelDate == null || travelDate.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Travel date must not be null or blank"));
        }

        Date travelSqlDate;
        try {
            travelSqlDate = Date.valueOf(travelDate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid travel date format. Use yyyy-MM-dd"));
        }

        User user = userOpt.get();
        Bus bus = busRepository.findById(busId).orElse(null);
        if (bus == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Bus not found"));
        }

        List<Integer> seatNumbers = Arrays.stream(seatNumber.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < passengerName.size(); i++) {
            passengers.add(new Passenger(
                    passengerName.get(i),
                    passengerAge.get(i),
                    passengerGender.get(i),
                    seatNumbers.get(i),
                    seatType.get(i)));
        }

        double total = passengers.stream().mapToDouble(p ->
                p.getSeatType().equalsIgnoreCase("Seater") ? bus.getSeaterPrice() : bus.getSleeperPrice()).sum();

        Booking booking = Booking.builder()
                .busId(busId)
                .userId(user.getId())
                .bookingDate(new Date(System.currentTimeMillis()))
                .travelDate(travelSqlDate)
                .passengers(passengers)
                .totalAmount(total)
                .build();

        if (bus.getBookedSeats() == null) bus.setBookedSeats(new ArrayList<>());
        bus.getBookedSeats().addAll(seatNumbers);

        busRepository.save(bus);
        bookingRepository.save(booking);

        return ResponseEntity.ok(Map.of(
                "message", "Booking successful",
                "bookingId", booking.getId(),
                "totalAmount", booking.getTotalAmount()
        ));
    }

    @GetMapping("/history")
    @Operation(summary = "View user booking history (browser only)")
    public String bookingHistory(Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        String email = principal.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return "redirect:/login";

        User user = userOpt.get();
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());
        Map<String, Bus> busMap = new HashMap<>();
        for (Booking b : bookings) {
            busRepository.findById(b.getBusId()).ifPresent(bus -> busMap.put(b.getId(), bus));
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("busMap", busMap);
        model.addAttribute("user", user);

        return "history";
    }

    @GetMapping("/download-ticket/{id}")
    @Operation(summary = "Download booking ticket PDF")
    public void downloadTicket(@PathVariable String id, HttpServletResponse response) throws IOException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        Bus bus = booking != null ? busRepository.findById(booking.getBusId()).orElse(null) : null;

        if (booking == null || bus == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            byte[] pdfBytes = TicketPDFGenerator.generateTicketPDF(booking, bus);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=ticket_" + id + ".pdf");
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/booked-seats/{busId}")
    @ResponseBody
    @Operation(summary = "Get booked seat numbers by seat type for a bus on a given date")
    public Map<String, Object> getAllBookedSeats(@PathVariable String busId,
                                                 @RequestParam(required = false) String travelDate) {
        if (travelDate == null || travelDate.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Travel date is required");
        }

        Date travelSqlDate;
        try {
            travelSqlDate = Date.valueOf(travelDate);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Expected yyyy-MM-dd");
        }

        List<Booking> bookings = bookingRepository.findByBusIdAndTravelDate(busId, travelSqlDate);
        Set<Integer> seater = new HashSet<>();
        Set<Integer> sleeper = new HashSet<>();

        for (Booking booking : bookings) {
            for (Passenger p : booking.getPassengers()) {
                if (p.getSeatType().equalsIgnoreCase("Seater")) {
                    seater.add(p.getSeatNumber());
                } else if (p.getSeatType().equalsIgnoreCase("Sleeper")) {
                    sleeper.add(p.getSeatNumber());
                }
            }
        }

        return Map.of("Seater", seater, "Sleeper", sleeper);
    }
}

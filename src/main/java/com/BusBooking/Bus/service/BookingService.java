package com.BusBooking.Bus.service;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.model.Passenger;
import com.BusBooking.Bus.repository.BookingRepository;
import com.BusBooking.Bus.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;

    public void createBooking(String userId, Bus bus, List<Passenger> passengers, List<Integer> seatNumbers) {
        double totalAmount = 0;
        for (Passenger p : passengers) {
            if (p.getSeatType().equalsIgnoreCase("Sleeper")) {
                totalAmount += bus.getSleeperPrice();
            } else {
                totalAmount += bus.getSeaterPrice();
            }
        }

        Booking booking = Booking.builder()
                .userId(userId)
                .busId(bus.getId())
                .bookingDate(new Date())
                .passengers(passengers)
                .totalAmount(totalAmount)
                .build();

        if (bus.getBookedSeats() == null) {
            bus.setBookedSeats(seatNumbers);
        } else {
            bus.getBookedSeats().addAll(seatNumbers);
        }

        busRepository.save(bus);
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    // ✅ NEW
    public List<Booking> getLatestTwoBookings(String userId) {
        return bookingRepository.findTop2ByUserIdOrderByBookingDateDesc(userId);
    }

    public long countBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId).size();
    }

    public String getLastDestination(String userId) {
        List<Booking> bookings = bookingRepository.findTop1ByUserIdOrderByTravelDateDesc(userId);
        return bookings.isEmpty() ? "N/A" : bookings.get(0).getDestination();
    }
}

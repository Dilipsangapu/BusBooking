package com.BusBooking.Bus.controller;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.model.User;
import com.BusBooking.Bus.repository.BookingRepository;
import com.BusBooking.Bus.repository.BusRepository;
import com.BusBooking.Bus.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin Dashboard & Bus Management")
public class AdminController {

    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    @Operation(summary = "Admin dashboard showing all buses")
    public String adminDashboard(Model model) {
        List<Bus> buses = busRepository.findAll();
        model.addAttribute("buses", buses);
        return "admin_dashboard";
    }

    @GetMapping("/add")
    @Operation(summary = "Show form to add a new bus")
    public String showAddBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "add_bus";
    }

    @PostMapping("/add")
    @Operation(summary = "Add new bus")
    public String addBus(@ModelAttribute Bus bus) {
        int total = (bus.getSeaterCount() != null ? bus.getSeaterCount() : 0)
                + (bus.getSleeperCount() != null ? bus.getSleeperCount() : 0);
        bus.setTotalSeats(total);
        busRepository.save(bus);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Edit existing bus")
    public String editBusForm(@PathVariable String id, Model model) {
        Bus bus = busRepository.findById(id).orElse(null);
        model.addAttribute("bus", bus);
        return "edit_bus";
    }

    @PostMapping("/update")
    @Operation(summary = "Update existing bus")
    public String updateBus(@ModelAttribute Bus bus) {
        busRepository.save(bus);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Delete bus by ID")
    public String deleteBus(@PathVariable String id) {
        busRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/bookings")
    @Operation(summary = "View all bookings in admin panel")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingRepository.findAll();
        Map<String, Bus> busMap = new HashMap<>();
        Map<String, User> userMap = new HashMap<>();

        for (Booking booking : bookings) {
            Bus bus = busRepository.findById(booking.getBusId()).orElse(null);
            User user = userRepository.findById(booking.getUserId()).orElse(null);
            if (bus != null) busMap.put(booking.getBusId(), bus);   // ✅ Fixed
            if (user != null) userMap.put(booking.getUserId(), user); // ✅ Fixed
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("busMap", busMap);
        model.addAttribute("userMap", userMap);
        return "booking_admin";
    }

}

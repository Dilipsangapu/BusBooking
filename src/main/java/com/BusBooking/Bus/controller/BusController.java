package com.BusBooking.Bus.controller;

import com.BusBooking.Bus.model.Bus;
import com.BusBooking.Bus.repository.BusRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Bus", description = "Bus search and details endpoints")
public class BusController {

    private final BusRepository busRepository;

    @GetMapping("/search")
    @Operation(summary = "Search buses based on from and to")
    public String searchBuses(@RequestParam(required = false) String from,
                              @RequestParam(required = false) String to,
                              Model model) {
        if (from != null && to != null) {
            List<Bus> buses = busRepository.findByFromAndTo(from, to);
            model.addAttribute("buses", buses);
        }
        return "search";
    }

    @GetMapping("/bus/{id}")
    @Operation(summary = "Show bus details by ID")
    public String showBusDetails(@PathVariable String id, Model model) {
        Bus bus = busRepository.findById(id).orElse(null);
        if (bus == null) {
            return "redirect:/search";
        }

        int booked = (bus.getBookedSeats() != null) ? bus.getBookedSeats().size() : 0;
        int availableSeats = bus.getTotalSeats() - booked;

        model.addAttribute("bus", bus);
        model.addAttribute("availableSeats", availableSeats);
        return "bus_details";
    }
}

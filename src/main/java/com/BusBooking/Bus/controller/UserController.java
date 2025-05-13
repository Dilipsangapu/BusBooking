package com.BusBooking.Bus.controller;

import com.BusBooking.Bus.model.Booking;
import com.BusBooking.Bus.model.User;
import com.BusBooking.Bus.repository.BookingRepository;
import com.BusBooking.Bus.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Tag(name = "User", description = "User registration, login, dashboard, and profile")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;

    @GetMapping("/register")
    @Operation(summary = "Show user registration form")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public String register(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    @Operation(summary = "Show login page")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password.");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    @Operation(summary = "User dashboard")
    public String dashboard(Principal principal, Model model) {
        String email = principal.getName();
        userRepository.findByEmail(email).ifPresent(user -> model.addAttribute("user", user));
        return "dashboard";
    }

    @GetMapping("/profile")
    @Operation(summary = "User profile")
    public String userProfile(Principal principal, Model model) {
        String email = principal.getName();
        userRepository.findByEmail(email).ifPresent(user -> model.addAttribute("user", user));
        return "profile";
    }

    @GetMapping("/user/history")
    @Operation(summary = "View user's booking history")
    public String bookingHistory(Principal principal, Model model) {
        String email = principal.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            String userId = userOpt.get().getId();
            List<Booking> bookings = bookingRepository.findByUserId(userId);
            model.addAttribute("bookings", bookings);
            return "booking_history";
        }
        return "redirect:/login";
    }
}

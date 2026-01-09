package com.example.HospitalManagement.service;

import com.example.HospitalManagement.entity.RefreshToken;
import com.example.HospitalManagement.enums.Role;
import com.example.HospitalManagement.entity.User;
import com.example.HospitalManagement.repository.RefreshTokenRepository;
import com.example.HospitalManagement.repository.UserRepository;
import com.example.HospitalManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ---------------- REGISTER ----------------
    public User register(String username, String password, Role role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    // ---------------- LOGIN ----------------
    public AuthResponse login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    // ---------------- REFRESH TOKEN ----------------
    public AuthResponse refresh(String refreshTokenStr) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user);

        return new AuthResponse(newAccessToken, refreshTokenStr);
    }

    // ---------------- HELPERS ----------------
    private RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUserId(user.getId());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days

        return refreshTokenRepository.save(refreshToken);
    }
}

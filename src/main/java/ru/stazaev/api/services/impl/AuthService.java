package ru.stazaev.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.request.UserLoginDto;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.api.dto.response.JwtTokensDto;
import ru.stazaev.api.security.JwtTokenProvider;
import ru.stazaev.api.services.SelectionService;
import ru.stazaev.store.entitys.Role;
import ru.stazaev.store.entitys.Status;
import ru.stazaev.store.entitys.User;
import ru.stazaev.store.repositories.UserRepository;


import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SelectionService selectionService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
//    private final UserRegDtoToActiveUserMapper userMapper;
    private final ModelMapper mapper;


    public JwtTokensDto  registerUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
//        User user = userMapper.dtoToEntity(userRegistrationDto);
        User user = mapper.map(userRegistrationDto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        user = userRepository.save(user);

        var selection = selectionService.createFavoriteSelection(user.getId());
        user.setFavoriteSelection(selection);

        user = userRepository.save(user);
        return createTokensForUser(user);
    }

    public JwtTokensDto loginUser(UserLoginDto user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));

        return createTokensForUser(dbUser);
    }

    public JwtTokensDto refreshToken(String refreshToken) {
        String nickname = tokenProvider.getUsernameFromJwt(refreshToken);
        User dbUser = userRepository.findByUsername(nickname)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не существует"));
        return createTokensForUser(dbUser);
    }

    private JwtTokensDto createTokensForUser(User user) {
        if (user.getStatus().equals(Status.DELETED)) {
            throw new AccessDeniedException("Пользователь заблокирован");
        }

        return new JwtTokensDto(tokenProvider.generateAccessToken(user), tokenProvider.generateRefreshToken(user), user.getId());
    }
}

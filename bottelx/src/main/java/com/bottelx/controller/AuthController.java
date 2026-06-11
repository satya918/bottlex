package com.bottelx.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bottelx.dto.AuthResponse;
import com.bottelx.dto.CompanyDto;
import com.bottelx.dto.LoginRequest;
import com.bottelx.dto.PermissionDto;
import com.bottelx.dto.RegisterRequest;
import com.bottelx.dto.RoleDto;
import com.bottelx.dto.UserDto;
import com.bottelx.entity.User;
import com.bottelx.repository.UserRepository;
import com.bottelx.security.JwtUtil;
import com.bottelx.security.RefreshTokenService;
import com.bottelx.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	// private final JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RefreshTokenService refreshTokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpReq,
			HttpServletResponse httpRes) {
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

			User user = userRepository
					.findByUserNameIgnoreCaseOrEmailIgnoreCase(request.getUserName(), request.getUserName())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			String accessToken = jwtUtil.generateAccessToken(user);
			String refreshToken = refreshTokenService.create(user, httpReq);

			ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true).secure(false)
					.sameSite("Lax").path("/api/auth").maxAge(Duration.ofDays(30)).build();

			httpRes.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			CompanyDto companyDto = null;

			if (user.getCompany() != null) {
				companyDto = new CompanyDto(
						user.getCompany().getId(),
						user.getCompany().getCompanyName(),
						user.getCompany().getCompanyCode());
			}

			List<RoleDto> roleDtos = user.getRoles().stream().map(role -> {

				RoleDto roleDto = new RoleDto();

				roleDto.setId(role.getId());
				roleDto.setRoleName(role.getRoleName());
				roleDto.setDescription(role.getDescription());

				// permissions
				List<PermissionDto> permissionDtos = role.getPermissions().stream().map(permission -> {

					PermissionDto permissionDto = new PermissionDto();

					permissionDto.setId(permission.getId());
					permissionDto.setPermissionName(permission.getPermissionName());
					permissionDto.setDescription(permission.getDescription());

					return permissionDto;

				}).toList();

				roleDto.setPermissions(permissionDtos);

				return roleDto;

			}).toList();
			UserDto dto = new UserDto(user.getId(), user.getEmail(), user.getUserName(), user.getFirstName(),
					user.getLastName(), user.getPhone(), companyDto, roleDtos, user.isActive());

			return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, dto));

		} catch (BadCredentialsException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("message", "Invalid username or password"));

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Login failed"));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(
			@RequestBody RegisterRequest request) {

		String response = authService.register(request);

		return ResponseEntity.ok(
				response);
	}
}

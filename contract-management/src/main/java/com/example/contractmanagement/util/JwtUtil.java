package com.example.contractmanagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
	private String secretkey = "${jwt.secret}";

	/**
	 * Extracts user name by using the given token. If the token is a Bearer token
	 * it returns User name. Else returns null.
	 *
	 * @param token
	 * @return User Name in String format
	 */
	public String extractUsername(String token) {
		try {

			if (!token.substring(0, 7).equals("Bearer ")) {
				return null;
			}

			String token1 = token.substring(7);
			return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token1).getBody().getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public String extractId(String token) {
		try {
			if (!token.substring(0, 7).equals("Bearer ")) {
				return null;
			}
			String token1 = token.substring(7);
			return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token1).getBody().getSubject();
		} catch(Exception e) {
			return null;
		}
	}

	public String generateToken(UserDetails userDetails, String role) {
		String compact = Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.setId(role)
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
		return compact;
	}

	public Boolean validateToken(String token) {
		try {

			if (!token.substring(0, 7).equals("Bearer ")) {
				return false;
			}

			String token1 = token.substring(7);
			Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token1).getBody().getExpiration();
			return true;

		} catch (Exception e) {
			return false;
		}
	}
}
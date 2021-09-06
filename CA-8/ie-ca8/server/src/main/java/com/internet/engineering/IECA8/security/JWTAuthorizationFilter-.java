//package com.internet.engineering.IECA7.security;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.internet.engineering.IECA7.repository.Course.CourseMapper;
//import com.internet.engineering.IECA7.utils.CustomPair;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import io.jsonwebtoken.*;
//import com.internet.engineering.IECA7.controllers.Exceptions.ExceptionBadCharacters;
//import com.internet.engineering.IECA7.domain.CourseEnrolment.Student.Student;
//import com.internet.engineering.IECA7.repository.MzRepository;
//import com.internet.engineering.IECA7.utils.StringUtils;
//import org.springframework.expression.spel.ast.OpNE;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//public class JWTAuthorizationFilter extends OncePerRequestFilter {
//
//    private static final String HEADER = "Authorization";
//    private static final String PREFIX = "Bearer ";
//    private static final String PREFIX_ONE_TIME_USE = "ONE_TIME_USE ";
//    private static final String SECRET = "bolbolestan";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        try {
//            if (checkJWTToken(request, response)) {
//                Claims claims = validateToken(request);
//                if(StringUtils.hasIllegalChars(claims.getSubject())){
//                    throw new ExceptionBadCharacters();
//                }
//                if (claims.get("authorities") != null) {
//                    Student student = MzRepository.getInstance().getStudent(claims.getSubject());
//                    setUpSpringAuthentication(claims);
//                } else {
//                    SecurityContextHolder.clearContext();
//                }
//            } else {
//                SecurityContextHolder.clearContext();
//            }
//            chain.doFilter(request, response);
//        } catch (UnsupportedJwtException | MalformedJwtException | ExceptionBadCharacters e) {
//            System.out.println(e.getMessage());
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//        } catch (SQLException e) {
//            SecurityContextHolder.clearContext();
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//        }
//    }
//
//    public static String getJWTToken(String username) {
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList("ROLE_USER");
//
//        String token = Jwts
//                .builder()
//                .setId("softtekJWT")
//                .setSubject(username)
//                .claim("authorities",
//                        grantedAuthorities.stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.toList()))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 3000))
//                .signWith(SignatureAlgorithm.HS512,
//                        SECRET.getBytes()).compact();
//
//        return "Bearer " + token;
//    }
//
//    private Claims validateToken(HttpServletRequest request) {
//        CustomPair customPair = getToken(request);
//        switch (customPair.getArgs().get(0)) {
//            case (PREFIX):
//                return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(customPair.getArgs().get(1)).getBody();
//            case (PREFIX_ONE_TIME_USE):
//                return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(customPair.getArgs().get(1)).getBody();
//            default:
//                return null;
//        }
//    }
//
//    private static CustomPair getToken(HttpServletRequest request) {
//        String jwtToken = "";
//        List<String> args = new ArrayList<>();
//
//        if(request.getHeader(HEADER).startsWith(PREFIX)) {
//            jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
//            args.add(PREFIX);
//        }
//        else if(request.getHeader(HEADER).startsWith(PREFIX_ONE_TIME_USE)) {
//            jwtToken = request.getHeader(HEADER).replace(PREFIX_ONE_TIME_USE, "");
//            args.add(PREFIX_ONE_TIME_USE);
//        }
//
//        args.add(jwtToken);
//        return new CustomPair(args);
//    }
//
//    /**
//     * Authentication method in Spring flow
//     *
//     * @param claims
//     */
//    private void setUpSpringAuthentication(Claims claims) {
//        @SuppressWarnings("unchecked")
////                todo check user email for authorization
//        List<String> authorities = (List<String>) claims.get("authorities");
//
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//    }
//
//    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
//        String authenticationHeader = request.getHeader(HEADER);
//        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX) || !authenticationHeader.startsWith(PREFIX_ONE_TIME_USE))
//            return false;
//        return true;
//    }
//}

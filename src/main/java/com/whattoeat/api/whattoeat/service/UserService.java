package com.whattoeat.api.whattoeat.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.whattoeat.api.whattoeat.dto.UserDTO;
import com.whattoeat.api.whattoeat.exception.AuthenticationException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String FB_ACCESS_TOKEN_RESOLVER_URL = "https://graph.facebook.com/me?access_token=";
    private static final Pattern FB_USER_ID_FIELD = Pattern.compile("id\":\"(\\d+)\"");

    public String getUserID() {
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes();
        final HttpServletRequest request = attr.getRequest();
        String tokenId = request.getHeader("Authorization");
        if (tokenId == null) {
            throw new AuthenticationException();
        }
        final String userId;
        try {
            userId = parseToken(tokenId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }

    private String parseToken(String token) throws IOException {
        if (token.indexOf('.') < 0) {
            return parseFacebookLoginToken(token);
        } else {
            return parseGoogleLoginToken(token);
        }
    }

    private String parseFacebookLoginToken(String accessToken) {
        try {
            final HttpClient client = HttpClientBuilder.create().build();
            final HttpGet request = new HttpGet(FB_ACCESS_TOKEN_RESOLVER_URL + accessToken);
            final HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != HttpStatusCodes.STATUS_CODE_OK) {
                System.out.println(
                    String.format(
                        "Received responseCode=%s for token=%s from FB",
                        responseCode,
                        accessToken));
                throw new AuthenticationException();
            }

            final InputStream inputStream = response.getEntity().getContent();
            final String responseBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            final Matcher matcher = FB_USER_ID_FIELD.matcher(responseBody);
            if (matcher.find()) {
                return matcher.group(1);
            }

            System.out.println(
                String.format(
                    "Unable to find id field from responseBody=[%s] for accessToken=[%s]",
                    responseBody,
                    accessToken));
            throw new AuthenticationException();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseGoogleLoginToken(String jwtToken) throws IOException {
        final JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdToken idToken = GoogleIdToken.parse(jacksonFactory, jwtToken);
        if (idToken == null) {
            throw new AuthenticationException();
        }

        return idToken.getPayload().getSubject();
    }

    public UserDTO getUser() {
        // TODO refactor for Google vs Facebook login
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        final HttpServletRequest request = attr.getRequest();
        String tokenId = request.getHeader("Authorization");
        if (tokenId == null) {
            throw new AuthenticationException();
        }
        final UserDTO userDTO;
        try {
            userDTO = parseTokenUserDetails(tokenId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userDTO;
    }

    private UserDTO parseTokenUserDetails(String idTokenString) throws IOException {
        // TODO refactor for Google vs Facebook login
        final JacksonFactory jacksonFactory = new JacksonFactory();

        GoogleIdToken idToken = GoogleIdToken.parse(jacksonFactory, idTokenString);
        if (idToken == null) {
            throw new AuthenticationException();
        }

        String userId = idToken.getPayload().getSubject();
        //String email = idToken.getPayload().getEmail();
        //boolean emailVerified = Boolean.valueOf(idToken.getPayload().getEmailVerified());
        //String name = (String) idToken.getPayload().get("name");
        String pictureUrl = (String) idToken.getPayload().get("picture");
        //String locale = (String) idToken.getPayload().get("locale");
        //String familyName = (String) idToken.getPayload().get("family_name");
        //String givenName = (String) idToken.getPayload().get("given_name");
        UserDTO userDTO = UserDTO.builder()
                .photoUrl(pictureUrl)
                .id(userId)
                .build();
        return userDTO;
    }
}

package com.whattoeat.api.whattoeat.service;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.whattoeat.api.whattoeat.exception.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private static final String CLIENT_ID_IOS = "8792279534-3tv6nsf4apfh8ufuj1s43k7a2dqa6rnb.apps.googleusercontent.com";
    private static final String CLIENT_ID_ANDROID = "8792279534-j6dcrb38tjco1231a39dtt4ea21ga2oo.apps.googleusercontent.com";
    private static final List<String> CLIENT_ID_LIST = Arrays.asList(CLIENT_ID_IOS, CLIENT_ID_ANDROID);

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

    private String parseToken(String idTokenString) throws IOException {
        final JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
            .setAudience(CLIENT_ID_LIST)
            .build();

        GoogleIdToken idToken = GoogleIdToken.parse(jacksonFactory, idTokenString);
        if (idToken == null) {
            throw new AuthenticationException();
        }

        return idToken.getPayload().getSubject();
//        String userId = payload.getSubject();
//        String email = payload.getEmail();
//        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//        String name = (String) payload.get("name");
//        String pictureUrl = (String) payload.get("picture");
//        String locale = (String) payload.get("locale");
//        String familyName = (String) payload.get("family_name");
//        String givenName = (String) payload.get("given_name");
    }
}

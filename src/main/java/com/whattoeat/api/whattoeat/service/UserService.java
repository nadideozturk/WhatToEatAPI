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

@Service
public class UserService {
    private final String CLIENT_ID = "8792279534-3tv6nsf4apfh8ufuj1s43k7a2dqa6rnb.apps.googleusercontent.com";

    public String getUserID(){
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        final HttpServletRequest request = attr.getRequest();
        String tokenId = request.getHeader("Authorization");
        if(tokenId == null){
            throw new AuthenticationException();
        }
        String userId = null;
        try {
            userId = parseToken(tokenId);
        }
        catch(GeneralSecurityException e) {
            throw new AuthenticationException(e);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
        return userId;
    }
    private String parseToken(String idTokenString) throws GeneralSecurityException, IOException {

        final JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String userId = payload.getSubject();
//            String email = payload.getEmail();
//            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");

            return userId;
        } else {
            // System.out.println("Invalid ID token.");
            return null;
        }
    }
}

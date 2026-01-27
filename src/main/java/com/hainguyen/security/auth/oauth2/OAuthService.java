package com.hainguyen.security.auth.oauth2;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class OAuthService {

    @Value("${spring.security.oauth2.client.registration.facebook.auth-uri}")
    private String facebookUri;

    @Value("${spring.security.oauth2.client.registration.facebook.token-uri}")
    private String facebookTokenUri;

    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
    private String facebookClientSecret;

    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    private String facebookRedirectUri;

    @Value("${spring.security.oauth2.client.registration.facebook.user-info-uri}")
    private String facebookUserInfoUri;



    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.user-info-uri}")
    private String googleUserInfoUri;
    
    public String generateAuthUrl(String loginType) {
        if (loginType.equals("google")) {
            String url = "";
            String clientId = "?client_id=";
            String redirectUri = "&redirectUrl=";
            String responseType = "&response_type=code";
            String scope = "&scope=email%20profile%20openid";
            return url + clientId + redirectUri + responseType + scope;
        }
        if (loginType.equals("facebook")) {
            String uri = UriComponentsBuilder
                            .fromUriString(facebookUri)
                            .queryParam("client_id", facebookClientId)
                            .queryParam("client_secret", facebookClientSecret)
                            .queryParam("redirect_uri", facebookRedirectUri)
                            .queryParam("response_type", "code")
                            .queryParam("scope", "email,public_profile")
                            .toUriString();
            
            return uri;
        }
        return null;
    }

    public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) throws RestClientException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String uriAccessToken;
        ObjectMapper mapper = new ObjectMapper();

        switch (loginType.toLowerCase()) {
            case "facebook":
                uriAccessToken = UriComponentsBuilder
                                .fromUriString(facebookTokenUri)
                                .queryParam("client_id", facebookClientId)
                                .queryParam("redirect_uri", facebookRedirectUri)
                                .queryParam("client_secret", facebookClientSecret)
                                .queryParam("code", code)
                                .toUriString();
                ResponseEntity<String> response = restTemplate.getForEntity(uriAccessToken, String.class);
                JsonNode node = mapper.readTree(response.getBody());
                String accessToken = node.get("access_token").asText();
                String userInfoUri = facebookUserInfoUri + "&access_token=" + accessToken;

                return mapper.readValue(
                    restTemplate.getForEntity(userInfoUri, String.class).getBody(),
                    new TypeReference<>() {}
                );
            case "google":
                uriAccessToken = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), 
                    new GsonFactory(), 
                    googleClientId,
                    googleClientSecret,
                    code,
                    googleRedirectUri
                ).execute().getAccessToken();

                restTemplate.getInterceptors().add((req, body, executionContext) -> {
                    req.getHeaders().set("Authorization", "Bearer " + uriAccessToken);
                    return executionContext.execute(req, body);
                });

                return mapper.readValue(
                    restTemplate.getForEntity(googleUserInfoUri, String.class).getBody(),
                    new TypeReference<>() {}
                );
            default:
                return null;
        }

        
    }
}

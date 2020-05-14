package com.andy.service;

import com.andy.data.api.zuora.response.OathToken;
import com.andy.exception.ZuoraServiceException;
import com.google.gson.Gson;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Date;

@Service
public class ZuoraService {
    // Defined in application.properties
    @Value("${zuora.api.base.url}")
    private String baseUrl;
    @Value("${zuora.api.client.id}")
    private String clientId;
    @Value("${zuora.api.client.secret}")
    private String clientSecret;

    private OathToken cachedOathToken = null;
    private Date oathTokenCreateDate;

    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private void generateOathToken() {
        Form oathRequest = new Form();
        oathRequest.param("client_id", clientId);
        oathRequest.param("client_secret", clientSecret);
        oathRequest.param("grant_type", "client_credentials");
        this.oathTokenCreateDate = new Date();
        String response = post("/oauth/token", APPLICATION_FORM, oathRequest, false);
        this.cachedOathToken = new Gson().fromJson(response, OathToken.class);
        log.info("New token generated");
    }

    public String get(String path) {
        Invocation.Builder builder = getSecureBuilder(path, true);
        Response response = builder.get(Response.class);
        return handleResponse(response);
    }

    public String put(String path, Object object) {
        Invocation.Builder builder = getSecureBuilder(path, true);
        Response response = builder.put(Entity.entity(object, APPLICATION_JSON), Response.class);
        return handleResponse(response);
    }

    public String postJson(String path, Object object) {
        return post(path, APPLICATION_JSON, object, true);
    }

    private String post(String path, String contentType, Object object, boolean authorized) {
        Invocation.Builder builder = getSecureBuilder(path, authorized);
        Response response = builder.post(Entity.entity(object, contentType), Response.class);
        return handleResponse(response);
    }

    private Invocation.Builder getSecureBuilder(String path, boolean authorized) {
        Client client = ClientBuilder.newBuilder()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();
        WebTarget target = client
                .target(baseUrl)
                .path(path);
        Invocation.Builder builder =  target
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        if (authorized) {
            if (cachedOathToken == null || cachedOathToken.isExpired(oathTokenCreateDate)) {
                generateOathToken();
            }
            builder.header("Authorization", "Bearer " + cachedOathToken.getAccessToken());
        }

        return builder;
    }

    private String handleResponse(Response response) {
        String responseAsString = response.readEntity(String.class);
        if (response.getStatus() == 200) {
            return responseAsString;
        }
        throw new ZuoraServiceException(responseAsString);
    }
}

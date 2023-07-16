package com.kap.kap_server.service.impl;

import com.kap.kap_server.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    private final static String URL = "https://smsc.ru/sys/send.php";
    @Value("${message.smsc.login}")
    private String login;

    @Value("${message.smsc.password}")
    private String password;

    @Override
    public void sendAuthorizationCode(String toPhoneNumber) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            URIBuilder uriBuilder = new URIBuilder(URL);
            uriBuilder.addParameter("login", login);
            uriBuilder.addParameter("psw", password);
            uriBuilder.addParameter("phones", toPhoneNumber);
            uriBuilder.addParameter("mes", "code");

            URI uri = uriBuilder.build();
            HttpGet request = new HttpGet(uri);

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            // Обработка ответа
            System.out.println("Response: " + result);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}

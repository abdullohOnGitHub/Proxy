package com.example.proxy;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

@Service
public class OlchaService {
    public void requestForProductURL() {

        /**
         *  2 xil hostname bor :
         *  1) 143.244.189.158
         *  2) 202.88.38.137
         *
         *  4 ta port mavjud : 5432, 5433, 3389, 2534
         *
         *  Username : cp3az
         *  Password : 8npxx2zg
         */

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("143.244.189.158", 5432));
        requestFactory.setProxy(proxy);


        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cp3az", "8npxx2zg".toCharArray());
            }
        });
        RestTemplate template = new RestTemplate(requestFactory);

        String object = template.getForObject("https://cbu.uz/uz/arkhiv-kursov-valyut/json/", String.class);
        System.out.println(object);

    }
}

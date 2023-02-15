package com.example.proxy;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;


@Service
public class OlchaService {
    public void requestForProductURL() {

        /**
         *  2 xil hostname bor :
         *  1) 143.244.189.158
         *  2) 202.88.38.137
         *
         *  4 ta port mavjud : , 5433, 3389, 2534
         *
         *  Username : cp3az
         *  Password : 8npxx2zg
         */

        try {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("143.244.189.158", 5432));
            requestFactory.setProxy(proxy);

            RestTemplate template = new RestTemplate(requestFactory);

            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope("143.244.189.158",5432),
                    new UsernamePasswordCredentials("cp3az", "8npxx2zg")
            );
            clientBuilder.useSystemProperties();
            clientBuilder.setProxy(new HttpHost("143.244.189.158", 5432));
            clientBuilder.setDefaultCredentialsProvider(credsProvider);
            clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
            clientBuilder.setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()));

            template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(clientBuilder.build()));

            String object = template.getForObject("https://mobile.olcha.uz/api/v2/products", String.class);
            System.out.println(object);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

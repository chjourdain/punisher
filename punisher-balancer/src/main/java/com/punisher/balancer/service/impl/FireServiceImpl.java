package com.punisher.balancer.service.impl;

import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.service.FireService;
import com.punisher.balancer.service.ProgrammerService;
import com.punisher.balancer.service.exception.IllegalLauncherException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by guillaumenostrenoff on 30/03/2016.
 */
@Service("fireService")
public class FireServiceImpl implements FireService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FireServiceImpl.class);
    private static HttpClient client = HttpClientBuilder.create().build();

    @Autowired
    private ProgrammerService programmerService;

    @Override
    public void fire(Programmer programmer) {
        LOGGER.debug("Sending fire command to shooter api ... (target: " + programmer.getName() + " at (" + programmer.getCoords() + ")");

        int x = programmer.getCoords().getX();
        int y = programmer.getCoords().getY();
        String url = programmer.getLauncher().getAddress();
        System.out.println(url + "/api/fire/" + x + "/" + y);
        if (url == null || url.isEmpty()) {
            throw new IllegalLauncherException();
        }
        HttpGet request = new HttpGet(url + "/api/fire/" + x + "/" + y);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }

        // to add basic athentication ->
        /*String encoding = Base64Encoder.encode ("test1:test1");
        HttpPost httppost = new HttpPost("http://host:post/test/login");
        httppost.setHeader("Authorization", "Basic " + encoding);

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();*/

        // register fail
        programmer.setStats(programmer.getStats() + 1);
        programmerService.update(programmer);
        LOGGER.debug("One failure was added to " + programmer.getName());
        LOGGER.info(response.getStatusLine().toString());
    }
}

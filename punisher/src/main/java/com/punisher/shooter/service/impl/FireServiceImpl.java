package com.punisher.shooter.service.impl;

import com.punisher.shooter.driver.ThunderDriver;
import com.punisher.shooter.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by guillaumenostrenoff on 30/03/2016.
 */
@Service("fireService")
public class FireServiceImpl implements FireService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FireServiceImpl.class);

    private ThunderDriver driver = ThunderDriver.getInstance();

    @Override
    public synchronized void fire(int x, int y) {

        LOGGER.info("Shoot triggered! Target at (" + x + "," + y + ")");

        try {
            driver.fire(x, y);
            LOGGER.info("Target down.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.punisher.balancer.service.impl;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.punisher.balancer.model.MissileLauncher;
import com.punisher.balancer.service.MissileLauncherService;
import com.punisher.balancer.service.exception.IllegalLauncherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service("launcherService")
public class MissileLauncherServiceImpl implements MissileLauncherService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MissileLauncherServiceImpl.class);
    //read path to the yml files
    static String file;

    static {
        Properties properties = new Properties();
        try {
            InputStream input = ProgrammerServiceImpl.class.getClassLoader().getResourceAsStream("path.properties");
            properties.load(input);
            file = properties.getProperty("launcheryml");
        } catch (IOException e) {
            LOGGER.error("Error loading YML file");
            System.exit(0);
        }
    }

    @Override
    public MissileLauncher get(int id) {
        LOGGER.debug("Getting launcher from file");
        YamlReader reader;
        MissileLauncher launcher = null;
        try {
            reader = new YamlReader(new FileReader(file));
            while (true) {
                launcher = reader.read(MissileLauncher.class);
                if (launcher == null)
                    break;
                if (launcher.getId() == id) {
                    return launcher;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MissileLauncher> get() {
        LOGGER.debug("Getting launcher list from file");
        List<MissileLauncher> list = new ArrayList<>();
        MissileLauncher launcher;
        YamlReader reader;
        try {
            reader = new YamlReader(new FileReader(file));
            while (true) {
                launcher = reader.read(MissileLauncher.class);
                if (launcher == null)
                    break;
                list.add(launcher);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    @Override
    public void create(MissileLauncher launcher) {

        if (launcher.getAddress() == null || launcher.getAddress().isEmpty()) {
            throw new IllegalLauncherException();
        }
        LOGGER.debug("Adding launcher in file");

        YamlWriter writer;
        try {
            writer = new YamlWriter(new FileWriter(file, true));
            writer.write(launcher);
            writer.close();
            FileWriter fW = new FileWriter(file, true);
            fW.write("---\n");
            fW.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void delete(MissileLauncher launcher) {

        LOGGER.debug("Deleting launcher from file");

        List<MissileLauncher> list = get();
        for (MissileLauncher p : list) {
            if (p.getId() == launcher.getId()) {
                list.remove(p);
                break;
            }
        }
        try {
            FileWriter fW = new FileWriter(file);
            fW.write("");
            fW.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        for (MissileLauncher p : list) {
            create(p);
        }
    }

    @Override
    public void update(MissileLauncher launcher) {
        if (launcher.getAddress() == null || launcher.getAddress().isEmpty()) {
            throw new IllegalLauncherException();
        }
        LOGGER.debug("Updating launcher from file");
        delete(launcher);
        create(launcher);
    }
}

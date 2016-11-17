package com.punisher.balancer.service.impl;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.punisher.balancer.model.MissileLauncher;
import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.service.ProgrammerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by guillaumenostrenoff on 23/03/2016.
 */
@Service("programmerService")
@EnableScheduling
@EnableAsync
public class ProgrammerServiceImpl implements ProgrammerService {

    @Value("classpath:/programmer.yml")
    private Resource requestFile;
    @Autowired
    MissileLauncherServiceImpl launcherService;

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgrammerServiceImpl.class);
    //read path to the yml files
    static String file;
    static {
        Properties properties = new Properties();
        try {
            InputStream input = ProgrammerServiceImpl.class.getClassLoader().getResourceAsStream("path.properties");
            properties.load(input);
            file = properties.getProperty("programmeryml");
        } catch (IOException e) {
            LOGGER.error("Error loading YML file");
            System.exit(0);
        }
    }

    @Override
    public Programmer get(String name) {
        LOGGER.debug("Getting programmer from file");
        YamlReader reader;
        Programmer prog;
        try {
            reader = new YamlReader(new FileReader(file));
            reader.getConfig().setClassTag("programmer", com.punisher.balancer.model.Programmer.class);
            while (true) {
                prog = reader.read(Programmer.class);
                if (prog == null)
                    break;
                if (prog.getName().equals(name)) {
                    MissileLauncher launcher = launcherService.get(prog.getLauncher().getId());
                    prog.setLauncher(launcher);
                    return prog;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Programmer> get() {
        LOGGER.debug("Getting programmer list from file");
        List<Programmer> list = new ArrayList<>();
        Programmer prog;
        YamlReader reader;
        try {
            reader = new YamlReader(new FileReader(file));
            reader.getConfig().setClassTag("programmer", com.punisher.balancer.model.Programmer.class);
            while (true) {
                prog = reader.read(Programmer.class);
                if (prog == null)
                    break;
                if (prog.getLauncher() != null) {
                    MissileLauncher launcher = launcherService.get(prog.getLauncher().getId());
                    prog.setLauncher(launcher);
                }
                list.add(prog);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    @Override
    public void create(Programmer programmer) {
        LOGGER.debug("Adding programmer in file");
        YamlWriter writer;
        try {
            writer = new YamlWriter(new FileWriter(file, true));
            writer.getConfig().setBeanProperties(false);
            writer.getConfig().setClassTag("programmer", com.punisher.balancer.model.Programmer.class);
            writer.write(programmer);
            writer.close();
            FileWriter fW = new FileWriter(file, true);
            fW.write("---\n");
            fW.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void delete(Programmer programmer) {

        LOGGER.debug("Deleting programmer from file");

        List<Programmer> list = get();
        for (Programmer p : list) {
            if (p.getName().equals(programmer.getName())) {
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
        for (Programmer p : list) {
            create(p);
        }
    }

    @Override
    public void update(Programmer programmer) {
        LOGGER.debug("Updating programmer from file");
        delete(programmer);
        create(programmer);
    }
}
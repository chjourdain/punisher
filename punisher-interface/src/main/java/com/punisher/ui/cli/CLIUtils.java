package com.punisher.ui.cli;

import com.punisher.ui.mapper.ProgrammerMapper;
import com.punisher.ui.model.Coords;
import com.punisher.ui.model.Programmer;
import com.punisher.ui.model.ProgrammerReadOnly;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class CLIUtils {

    private Scanner sc;
    private static Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    private static String url;
    private static WebTarget webTarget;
    private static WebTarget fireWebTarget;

    static {
        Properties properties = new Properties();
        try {
            InputStream input = CLIUtils.class.getClassLoader().getResourceAsStream("conf.properties");
            properties.load(input);
            url = properties.getProperty("url");
        } catch (IOException e) {
            System.out.println("Error loading properties of url");
            url = "http://localhost:8080";
        }
        webTarget = client.target(url + "/api/programmer");
        fireWebTarget = client.target(url + "/api/fire");
    }

    public CLIUtils(Scanner sc) {
        this.sc = sc;
    }


    protected void displayWelcomeMsg() {


        System.out.println("              \\ \\______                ######");
        System.out.println("              ###[==_____>           _/_ #####");
        System.out.println("              /_/                  [.[.]-=###");
        System.out.println("                \\ \\_____             /_     )#");
        System.out.println("                ###[==___>            |__/   #");
        System.out.println("                /_/                    \\___/\n");
        System.out.println("         ___ _   _ _  _ ___ ___ _  _     _  _ ___ ___");
        System.out.println("        | _ \\ | | | \\| |_ _/ __| || |   | || | __| _ \\");
        System.out.println("        |  _/ |_| | .` || |\\__ \\ __ | = | __ | _||   /");
        System.out.println("        |_|  \\___/|_|\\_|___|___/_||_|   |_||_|___|_|_\\");

        System.out.println("        |############################################|");
        System.out.println("        |             Punish her Command :           |");
        System.out.println("        |############################################|");
        System.out.println("        |                                            |");
        System.out.println("        |  1 fire programmer.                        |");
        System.out.println("        |  2 add programmer.                         |");
        System.out.println("        |  3 update programmer.                      |");
        System.out.println("        |  4 delete programmer.                      |");
        System.out.println("        |  5 list programmers.                       |");
        System.out.println("        |  6 exit.                                   |");
        System.out.println("        |                                            |");
        System.out.println("        |############################################|");
    }

    protected void fire() {
        String name = askString("->Fire \n Enter his/her name");

        if (get(name) == null) {
            System.out.println("Programmer not found");
            return;
        }

        Response response = fireWebTarget.path("/" + name).request().accept(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 200) {
            System.out.println("An error occured while firing");
            System.out.println(response.toString());
        } else {
            System.out.println("shot!");
        }
    }

    protected void add() {
        String name = askString("-> New programmer. \n Enter his/her name");
        int x = askInt("Enter x");
        int y = askInt("Enter y");
        if (x <0 || x > 345 || y <0 || y > 45) {
            System.out.println("Error, Coords must be valid");
            return;
        }
        int idLauncher = askInt("Enter id of room");
        Coords coords = new Coords(x, y);
        Programmer p = new Programmer(name, new Coords(x, y), idLauncher);
        createProgrammer(p);
    }

    protected void update() {
        String name = askString("->Update \n Enter name.");

        Programmer p = get(name);

        if (p == null) {
            System.out.println("Programmer not found");
            return;
        }

        System.out.println(p.toString());
        int x = askInt("Enter x");
        int y = askInt("Enter y");
        if (x <0 || x > 345 || y <0 || y > 45) {
            System.out.println("Error, Coords must be valid");
            return;
        }
        p.setCoords(new Coords(x, y));

        updateProgrammer(name, p);

    }

    protected void delete() {
        String name = askString("->Delete \n Enter name.");

        Programmer p = get(name);

        if (p == null) {
            System.out.println("Programmer not found");
            return;
        }

        deleteProgrammer(p.getName());
    }

    protected void list() {
        // get and display
        List<ProgrammerReadOnly> programmers = getAll();
        programmers.forEach(p -> System.out.println(p.toString()));
    }

    protected String askString(String msg) {
        System.out.println(msg);
        String buffer = null;
        while ("".equals(buffer) || buffer == null) {
            buffer = sc.nextLine();
        }
        return buffer;
    }

    protected int askInt(String msg) {
        System.out.println(msg);
        int buffer = sc.nextInt();
        return buffer;
    }

    private Programmer get(String name) {
        ProgrammerReadOnly programmer = webTarget.path("/" + name).request().accept(MediaType.APPLICATION_JSON)
                .get(ProgrammerReadOnly.class);
        return programmer == null ? null : ProgrammerMapper.toProgrammer(programmer);
    }

    private List<ProgrammerReadOnly> getAll() {
        List<ProgrammerReadOnly> programmers = webTarget.request().accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ProgrammerReadOnly>>() {
                });
        return programmers == null ? null : programmers;
    }

    private void createProgrammer(Programmer programmer) {
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(programmer, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            System.out.println("An error occured while requesting web service :");
            System.out.println(response.toString());
        } else {
            System.out.println("Programmer were successfully added!");
        }
    }

    private void updateProgrammer(String name, Programmer programmer) {

        Response response = webTarget.path("/" + name).request(MediaType.APPLICATION_JSON).put(Entity.entity(programmer, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            System.out.println("An error occured while requesting web service");
            System.out.println(response.toString());
        } else {
            System.out.println("Programmer " + programmer.getName() + " was successfully updated !");
        }

    }

    private void deleteProgrammer(String name) {
        Response response = webTarget.path("/" + name).request().delete();

        if (response.getStatus() != 200) {
            System.out.println("An error occured while requesting web service, are you sure this programmer exists ?");
            System.out.println(response.toString());
        } else {
            System.out.println("Programmer " + name + " were successfully deleted !");
        }
    }
}

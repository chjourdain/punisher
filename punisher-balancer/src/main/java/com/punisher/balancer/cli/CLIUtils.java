package com.punisher.balancer.cli;

import java.util.List;
import java.util.Scanner;

import com.punisher.balancer.model.Coords;
import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.service.FireService;
import com.punisher.balancer.service.ProgrammerService;

public class CLIUtils {

	private Scanner sc;

	public CLIUtils(Scanner sc) {
		this.sc = sc;
	}


	protected void displayWelcomeMsg() {
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

	protected void fire(FireService fService, ProgrammerService pService) {
		String name = askString("->Fire \n Enter his/her name");
		Programmer programmer = pService.get(name);
		fService.fire(programmer);
        //TODO : http client jax rs
	}

	protected void add(ProgrammerService pService) {
		String name = askString("-> New programmer. \n Enter his/her name");
		Programmer p = new Programmer();
		p.setName(name);
		int x = askInt("Enter x");
		int y = askInt("Enter y");
		Coords coords = new Coords(x,y);
		p.setCoords(coords);
		pService.create(p);
	}
	
	protected void update(ProgrammerService pService) {
		String name = askString("->Update \n Enter name.");
		Programmer p = pService.get(name);
		if (p != null){
		System.out.println(p.toString());
		int x = askInt("Enter x");
		int y = askInt("Enter y");
		p.setCoords(new Coords(x,y));
		pService.update(p);
		} else {
			System.out.println("No programmer with this name");
		}
	}
	
	protected void delete(ProgrammerService pService) {
		String name = askString("->Delete \n Enter name.");
		Programmer p = pService.get(name);
		if (p != null){
		pService.delete(p);
		System.out.println(p.getName()+" Deleted");
		} else {
			System.out.println("No programmer with this name");
		}
	}

	protected void list(ProgrammerService pService) {
		System.out.println("->List");
		List<Programmer> list = pService.get(); 
		for (Programmer p : list) {
			System.out.println(p.toString());
		}
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
}

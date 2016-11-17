package com.punisher.balancer.cli;

import java.io.IOException;
import java.util.Scanner;

import com.punisher.balancer.service.FireService;
import com.punisher.balancer.service.ProgrammerService;

public class CmdLineInterface {

	private FireService fService;
	private ProgrammerService pService;
	private CLIUtils utils;
	private static final Scanner SC;

	static {
		SC = new Scanner(System.in);
	}

	public CmdLineInterface(FireService fService, ProgrammerService pService) {
		this.fService = fService;
		this.pService = pService;
		utils = new CLIUtils(SC);
	}

	public void startCmdLineInterface() {

		boolean doContinue = true;

		try {
			while (doContinue) {
				utils.displayWelcomeMsg();

				String cmd = SC.next();

				switch (cmd) {
				case "fire":
				case "1":
					utils.fire(fService, pService);
					;
					break;

				case "add":
				case "2":
					utils.add(pService);
					;
					break;

				case "update":
				case "3":
					utils.update(pService);;
					break;

				case "delete":
				case "4":
					utils.delete(pService);;
					break;

				case "list":
				case "5":
					utils.list(pService);;
					break;

				case "exit":
				case "6":
					doContinue = false;
					System.out.println("Operation terminated.");
					break;

				default:
					break;
				}

				System.out.println("\nPress 'enter' to continue.");
				System.in.read();
			}
		} catch (IOException ioe) {
		} finally {
			SC.close();
		}
	}
}
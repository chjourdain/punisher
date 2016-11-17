package com.punisher.ui.cli;

import java.io.IOException;
import java.util.Scanner;

public class CmdLineInterface {

	private CLIUtils utils;
	private static final Scanner SC;

	static {
		SC = new Scanner(System.in);
	}

	public CmdLineInterface() {
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
					utils.fire();
					break;

				case "add":
				case "2":
					utils.add();
					;
					break;

				case "update":
				case "3":
					utils.update();
					break;

				case "delete":
				case "4":
					utils.delete();
					break;

				case "list":
				case "5":
					utils.list();
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
package com.punisher.balancer.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.punisher.balancer.service.FireService;
import com.punisher.balancer.service.ProgrammerService;
import com.punisher.balancer.service.impl.FireServiceImpl;
import com.punisher.balancer.service.impl.ProgrammerServiceImpl;

public class Launcher {
	
	public static void main(String[] args) {

		CmdLineInterface cli = null;

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:api-context.xml")) {
			  FireService fService = context.getBean(FireServiceImpl.class);
	            ProgrammerService pService = context.getBean(ProgrammerServiceImpl.class);    
			cli = new CmdLineInterface(fService, pService);
		}

		cli.startCmdLineInterface();
	}
}

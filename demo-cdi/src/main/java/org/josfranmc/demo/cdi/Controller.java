package org.josfranmc.demo.cdi;

import jakarta.inject.Inject;

public class Controller {

    @Inject
    private Service service;

	public void execute() {
		service.doTask();
	}
}
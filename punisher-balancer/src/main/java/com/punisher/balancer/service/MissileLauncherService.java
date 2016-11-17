package com.punisher.balancer.service;

import java.util.List;

import com.punisher.balancer.model.MissileLauncher;

public interface MissileLauncherService {

	MissileLauncher get(int id);

	List<MissileLauncher> get();

	void create(MissileLauncher launcher);

	void delete(MissileLauncher launcher);

	void update(MissileLauncher launcher);

}

package com.punisher.balancer.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class MissileLauncher {

	@Min(value = 0)
	public int id;
	@Pattern(regexp = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" , message = "Invalid address")
	private String address;
	
	public MissileLauncher() {}
	
	public MissileLauncher(int id, String address) {
		super();
		this.id = id;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissileLauncher other = (MissileLauncher) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MissileLauncher [id=" + id + ", address=" + address + "]";
	}
}

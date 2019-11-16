package model.epanet.element.systemoperation;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
	String id;
	List<Double> multipliers;
	
	public Pattern() {
		this.multipliers = new ArrayList<Double>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the multipliers
	 */
	public List<Double> getMultipliers() {
		return multipliers;
	}

	/**
	 * @param multipliers the multipliers to set
	 */
	public void setMultipliers(List<Double> multipliers) {
		this.multipliers = multipliers;
	}
	
	/**
	 * Add a multiplier to multipliers list
	 * @param multiplier the multiplier to add
	 */
	public void addMultipliers(double multiplier) {
		this.multipliers.add(multiplier);
	}
	
	
}

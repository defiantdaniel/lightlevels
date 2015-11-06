package com.dd.lightlevels.init;

public class LightRange {
	
	// Temp Vars for all the elements of the DefaultValues array.
	private int[] TempLr;
	private float TempLrDmg;
	private String TempLrCmd;
	private int TempLrTimer;
	private int TempLrIndex;	
	
	// Stores all the variables in an object.
	public LightRange(int[] TempLr, float TempLrDmg, String TempLrCmd, int TempLrTimer, int TempLrIndex) {
		this.TempLr = TempLr;
		this.TempLrDmg = TempLrDmg;
		this.TempLrCmd = TempLrCmd;
		this.TempLrTimer = TempLrTimer;
		this.TempLrIndex = TempLrIndex;		
	}
	
	public int[] getLr () {
		return this.TempLr;
	}

	public float getLrDmg () {
		return this.TempLrDmg;
	}
	
	public String getLrCmd () {
		return this.TempLrCmd;
	}
	
	public int getLrTimer () {
		return this.TempLrTimer;
	}
	
	public int getLrIndex () {
		return this.TempLrIndex;
	}		
	
	public void setLr (int[] TempLr) {
		this.TempLr = TempLr;
	}
	
	public void setLrDmg (float TempLrDmg) {
		this.TempLrDmg = TempLrDmg;
	}
	
	public void setLrCmd (String TempLrCmd) {
		this.TempLrCmd = TempLrCmd;
	}
	
	public void setLrTimer (int TempLrTimer) {
		this.TempLrTimer = TempLrTimer;
	}
	
	public void setLrIndex (int TempLrIndex) {
		this.TempLrIndex = TempLrIndex;
	}	
}

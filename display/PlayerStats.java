package edu.virginia.engine.display;

public class PlayerStats {
	private int strength = 0;
	private int stamina = 0;
	private int knowledge = 0;
	private boolean hasDagger = false;
	private boolean hasAxe = false;
	private boolean hasMagic = false;
	private boolean hasStick = false;
	private boolean hasLazer = false;
	private boolean hasCalibur = false;
	
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public int getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}
	public boolean isHasMagic() {
		return hasMagic;
	}
	public void setHasMagic(boolean hasMagic) {
		this.hasMagic = hasMagic;
	}
	public boolean isHasAxe() {
		return hasAxe;
	}
	public void setHasAxe(boolean hasAxe) {
		this.hasAxe = hasAxe;
	}
	public boolean isHasDagger() {
		return hasDagger;
	}
	public void setHasDagger(boolean hasDagger) {
		this.hasDagger = hasDagger;
	}
	public boolean isHasStick() {
		return hasStick;
	}
	public void setHasStick(boolean hasStick) {
		this.hasStick = hasStick;
	}
	public boolean isHasLazer() {
		return hasLazer;
	}
	public void setHasLazer(boolean hasLazer) {
		this.hasLazer = hasLazer;
	}
	public boolean isHasCalibur() {
		return hasCalibur;
	}
	public void setHasCalibur(boolean hasCalibur) {
		this.hasCalibur = hasCalibur;
	}



}

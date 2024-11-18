package units;

public abstract class Unit {
	protected int level;
	protected String name;
	protected int att;
	protected int maxHp;
	protected int hp;

	public Unit() {}

	public int getLevel() {
		return level;
	}
	
	public String getName() {
		return name;
	}

	public int getAtt() {
		return att;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public abstract void attack(Unit target);
}
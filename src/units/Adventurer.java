package units;

import item.Item;
import manager.UnitManager;

public class Adventurer extends Unit {
	private int identifierCode;

	private Item weapon;
	private Item armor;
	private Item ring;

	private boolean party;

	public Adventurer(String name, int att, int hp) {
		this.identifierCode = UnitManager.generateAdventurerCode();
		this.level = 1;
		this.name = name;
		this.att = att;
		this.maxHp = hp;
		this.hp = hp;
	}

	public Adventurer(int code, int level, String name, int att, int hp, boolean party, Item weapon, Item armor,
			Item ring) {
		this.identifierCode = code;
		this.level = level;
		this.name = name;
		this.att = att;
		this.maxHp = hp;
		this.hp = hp;
		this.party = party;
		this.weapon = weapon;
		this.armor = armor;
		this.ring = ring;
	}

	@Override
	public void attack(Unit target) {
		if (target instanceof Monster)
			;
	}

	public void levelUp() {
		level++;
		maxHp *= 1.1;
		hp = maxHp;
		att *= 1.1;
	}

	public boolean isParty() {
		return party;
	}

	public void toggleParty() {
		party = !party;
	}

	public int getCode() {
		return identifierCode;
	}

	public Item getArmor() {
		return armor;
	}

	public Item getWeapon() {
		return weapon;
	}

	public Item getRing() {
		return ring;
	}

	public void setItem(Item item) {
		switch (item.getKind()) {
		case 1 -> this.weapon = equipItem(this.weapon, item);
		case 2 -> this.armor = equipItem(this.armor, item);
		case 3 -> this.ring = equipItem(this.ring, item);
		}
	}

	private Item equipItem(Item curItem, Item item) {
		if (curItem != null && curItem.getEquippedBy()!=0) {
			removeItemEffects(curItem);
			curItem.setEquippedBy(0);
		}

		if (item == null)
			return null;

		curItem = item;
		curItem.setEquippedBy(identifierCode);

		applyItemEffects(item);
		return item;
	}

	// 장착시 아이템 효과 적용
	private void applyItemEffects(Item item) {
		this.att += item.getAtt();
		this.hp += item.getHp();
		this.maxHp += item.getHp();
	}

	// 해제시 아이템 효과 적용
	private void removeItemEffects(Item item) {
		this.att -= item.getAtt();
		this.hp -= item.getHp();
		this.maxHp -= item.getHp();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Adventurer) {
			Adventurer target = (Adventurer) obj;
			return target.getCode() == identifierCode;
		}
		return false;
	}
}
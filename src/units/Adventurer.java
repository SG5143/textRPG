package units;

import item.Item;
import manager.IOManager;
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
		if (!(target instanceof Monster))
			return;

		int damage = UnitManager.r.nextInt(att / 2) + (int) (att * 0.8);
		target.setHp(target.getHp() - damage);
		String msg = "[%s의 공격] 몬스터 [%s]에게 공격하여 %d의 피해 입혔습니다.\n";
		IOManager.printString(String.format(msg, name, target.getName(), damage));
	}

	public void criticalAttack(Unit target) {
		if (!(target instanceof Monster))
			return;

		if (UnitManager.r.nextBoolean()) {
			int damage = UnitManager.r.nextInt(att / 2) + att * 2;
			target.setHp(target.getHp() - damage);
			String msg = "[%s의 공격] 몬스터 [%s]에게 공격하여 %d의 치명타를 적중했습니다.\n";
			IOManager.printString(String.format(msg, name, target.getName(), damage));
		} else {
			String msg = "[%s의 공격] 몬스터 [%s]의 급소를 노렸지만 실패했습니다..\n";
			IOManager.printString(String.format(msg, name, target.getName()));
		}
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

	public void toggleParty(boolean b) {
		party = b;
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

	public void unequipItem(int kind) {
		if (kind == 1 && weapon != null) {
			removeItemEffects(weapon);
			weapon.setEquippedBy(0);
			weapon = null;
		} else if (kind == 2 && armor != null) {
			removeItemEffects(armor);
			armor.setEquippedBy(0);
			armor = null;
		} else if (kind == 3 && ring != null) {
			removeItemEffects(ring);
			ring.setEquippedBy(0);
			ring = null;
		}
	}

	public void setItem(Item item) {
		switch (item.getKind()) {
		case 1 -> this.weapon = equipItem(this.weapon, item);
		case 2 -> this.armor = equipItem(this.armor, item);
		case 3 -> this.ring = equipItem(this.ring, item);
		}
	}

	private Item equipItem(Item curItem, Item item) {
		if (curItem != null && curItem.getEquippedBy() != 0) {
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
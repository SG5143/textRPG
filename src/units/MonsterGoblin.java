package units;

import manager.IOManager;
import manager.UnitManager;
import manager.UserDataManager;

public class MonsterGoblin extends Unit implements Monster {

	public MonsterGoblin() {
		super.level = UserDataManager.getNextDungeonLevel();
		super.name = "고블린";
		super.hp = UnitManager.r.nextInt(level * 200) + (level * 50);
		super.att = UnitManager.r.nextInt(level * 10) + (level * 5);
	}

	@Override
	public void attack(Unit target) {
		if (!(target instanceof Adventurer))
			return;

		int action = UnitManager.r.nextInt(3);

		switch (action) {
		case 0 -> attackMiss(target);
		case 1, 2 -> normalAttack(target);
		}
	}

	private void attackMiss(Unit target) {
		String msg = "[%s의 공격 실패] 플레이어 [%s]에게 공격했지만 빗나갔습니다.\n";
		IOManager.printString(String.format(msg, name, target.getName()));
	}

	private void normalAttack(Unit target) {
		int damage = UnitManager.r.nextInt(att / 2) + (att / 2);

		target.setHp(target.getHp() - damage);
		String msg = "[%s의 공격 성공] 플레이어 [%s]에게 공격하여 %d의 피해 입혔습니다.\n";
		IOManager.printString(String.format(msg, name, target.getName(), damage));
	}
}
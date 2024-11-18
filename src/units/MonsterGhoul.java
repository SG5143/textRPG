package units;

import manager.UnitManager;
import manager.UserDataManager;
import manager.IOManager;

public class MonsterGhoul extends Unit implements Monster {

	public MonsterGhoul() {
		level = UserDataManager.getNextDungeonLevel();
		name = "구울";
		super.hp = UnitManager.r.nextInt(level * 300) + (level * 1);
		super.att = UnitManager.r.nextInt(level * 20) + (level * 1);
	}

	@Override
	public void attack(Unit target) {
		if (!(target instanceof Adventurer))
			return;

		int action = UnitManager.r.nextInt(3);

		switch (action) {
		case 0 -> attackMiss(target);
		case 1 -> normalAttack(target);
		case 2 -> strongAttack(target);
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

	private void strongAttack(Unit target) {
		int damage = UnitManager.r.nextInt(att / 2) + att;

		target.setHp(target.getHp() - damage);
		String msg = "[%s의 강력 공격] 플레이어 [%s]에게 공격하여 %d의 피해 입혔습니다.\n";
		IOManager.printString(String.format(msg, name, target.getName(), damage));
	}
}
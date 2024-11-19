package stage;

import java.util.ArrayList;

import manager.IOManager;
import manager.UnitManager;
import manager.UserDataManager;
import units.Adventurer;
import units.Monster;
import units.Unit;

// █, ▓, ▒, ░

public class StageBettle implements Stage {
	private final int MONSTERS_DEFEATED = 1;
	private final int PARTY_DEFEATED = 2;

	private ArrayList<Adventurer> party;
	private ArrayList<Monster> monsters;

	private int gameState = 0;
	private boolean isEscape = false;

	@Override
	public void activateStage() {
		initData();
		printDungeonLevel();
		bettle();
	}

	private void bettle() {
		while (!isEscape) {
			printBettleInfo();

			for (int i = 0; i < party.size(); i++) {
				if (party.get(i).getHp() <= 0)
					continue;

				String msg = "\"%s\"의 공격 차례 (%d/%d)\n";
				IOManager.printString(String.format(msg, party.get(i).getName(), i + 1, party.size()));
				String command = IOManager.inputString("(1)일반공격 (2)급소노리기 (3)도망가기 >> ");

				int cmdNum = parseCommand(command);

				if (cmdNum == 1)
					normalAttack(i);
				else if (cmdNum == 2)
					criticalHit(i);
				else if (cmdNum == 3)
					attemptEscape();
				else {
					i--;
					continue;
				}
			}
			monstersAttack();
			if (updateGameState())
				break;
		}

		if (gameState == MONSTERS_DEFEATED) {
			int gold = UnitManager.r.nextInt(UserDataManager.getNextDungeonLevel() * 1000 / 2)
					+ (UserDataManager.getNextDungeonLevel() * 1000 / 2);
			printDungeonClearMessage(gold);
			UserDataManager.setGold(UserDataManager.getGold() + gold);
			UserDataManager.setNextDungeonLevel(UserDataManager.getNextDungeonLevel() + 1);

			for (Adventurer adven : party)
				adven.levelUp();
		}

		for (Adventurer adven : party)
			adven.setHp(adven.getMaxHp());
	}

	private boolean updateGameState() {
		for (int i = monsters.size() - 1; i >= 0; i--) {
			Unit mon = (Unit) monsters.get(i);
			if (mon.getHp() <= 0)
				monsters.remove((Monster) mon);
		}

		if (monsters.size() == 0) {
			gameState = MONSTERS_DEFEATED;
			return true;
		}

		int defeatedPartyMembers = 0;
		for (int i = 0; i < party.size(); i++) {
			if (party.get(i).getHp() <= 0)
				defeatedPartyMembers++;
		}

		if (defeatedPartyMembers == party.size()) {
			gameState = PARTY_DEFEATED;
			return true;
		}
		return false;
	}

	private void monstersAttack() {
		for (int i = 0; i < monsters.size(); i++) {
			Unit mon = (Unit) monsters.get(i);

			int advenIdx = UnitManager.r.nextInt(party.size());
			mon.attack(party.get(advenIdx));
		}
	}

	private void normalAttack(int idx) {
		int monIdx = UnitManager.r.nextInt(monsters.size());
		Unit monster = (Unit) monsters.get(monIdx);
		party.get(idx).attack(monster);
	}

	private void criticalHit(int idx) {
		int monIdx = UnitManager.r.nextInt(monsters.size());
		Unit monster = (Unit) monsters.get(monIdx);
		party.get(idx).criticalAttack(monster);
	}

	private void attemptEscape() {
		int attempt = UnitManager.r.nextInt(10);
		if (attempt < 3)
			isEscape = true;
	}

	private void initData() {
		party = UserDataManager.getPartyList();
		monsters = UnitManager.getMonster();
	}

	private void printDungeonClearMessage(int gold) {
		String msg = """
				
				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				▓             던전을 클리어 하셨습니다.               ▓
				▓                  획득 골드 %6d                   ▓
				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				""";
		IOManager.printString(String.format(msg, gold));
	}
	
	private void printDungeonLevel() {
		sleep(300);
		String msg = """

				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				▓             < 현재 던전 단계 %3d >                  ▓
				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				""";
		IOManager.printString(String.format(msg, UserDataManager.getNextDungeonLevel()));
	}

	private void printBettleInfo() {
		String msg = """

				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				▓                      현재 파티                      ▓
				▓                                                     ▓
				""";
		IOManager.printString(msg);

		// 파티리스트 출력
		for (Adventurer adven : party) {
			int level = adven.getLevel();
			String name = adven.getName();
			int maxHP = adven.getMaxHp();
			int hp = adven.getHp();
			int att = adven.getAtt();

			msg = "▓Lv%-3d %-6s\tHp[%6d/%6d] 공격력 : %-3d▓\n";
			IOManager.printString(String.format(msg, level, name, hp, maxHP, att));
		}

		msg = """
				▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
				▓                       Monster                       ▓
				▓                                                     ▓
				""";
		IOManager.printString(msg);

		// 몬스터 리스트 출력
		for (Monster mon : monsters) {
			if (mon instanceof Unit) {
				Unit m = (Unit) mon;
				int level = m.getLevel();
				String name = m.getName();
				int maxHP = m.getMaxHp();
				int hp = m.getHp();
				int att = m.getAtt();
				msg = "▓Lv%-3d %-6s  \tHp[%6d/%6d] 공격력 : %-3d▓\n";
				IOManager.printString(String.format(msg, level, name, hp, maxHP, att));
			}
		}
		IOManager.printString("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
	}

	private void sleep(int a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
		}
	}

	private int parseCommand(String command) {
		int cmdNum = -1;
		try {
			cmdNum = Integer.parseInt(command);
		} catch (Exception e) {
			return -1;
		}
		return cmdNum;
	}

}
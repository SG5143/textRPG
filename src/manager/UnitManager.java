package manager;

import java.util.ArrayList;
import java.util.Random;

import managers.UserDataManager;
import units.Adventurer;
import units.Monster;

public class UnitManager {
	public static Random r;
	private static ArrayList<Monster> monsterList;

	private final static String PATE = "manager.";
	private final static String MONS[] = { "MonsterGhoul", "MonsterGoblin", "MonsterOrc" };

	private UnitManager() {
		UnitManager.r = new Random();
		monsterList = new ArrayList<Monster>();
	}

	private static UnitManager instance = new UnitManager();

	public static UnitManager getInstance() {
		return instance;
	}

	public static int generateAdventurerCode() {
		ArrayList<Adventurer> list = UserDataManager.getAdventurerList();

		while (true) {
			int code = r.nextInt(999) + 1;

			boolean isFound = false;
			for (Adventurer a : list) {
				if (a.getCode() == code)
					isFound = true;
			}

			if (!isFound)
				return code;
		}
	}

	public static ArrayList<Monster> getMonster() {
		int num = r.nextInt(UserDataManager.getNextDungeonLevel() / 10 + 2) + 1;

		generateRandomMoster(num);
		return monsterList;
	}

	private static void generateRandomMoster(int size) {
		for (int i = 0; i < size; i++) {
			int num = r.nextInt(MONS.length);
			String className = PATE + MONS[num];
			try {
				Class<?> cls = Class.forName(className);
				Monster monster = (Monster) cls.getConstructor().newInstance();
				monsterList.add(monster);
			} catch (Exception e) {
				return;
			}
		}
	}
}
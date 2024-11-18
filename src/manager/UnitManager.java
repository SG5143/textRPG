package manager;

import java.util.ArrayList;
import java.util.Random;

import units.Adventurer;

public class UnitManager {
	public static Random r;

	private UnitManager() {
		UnitManager.r = new Random();
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

}

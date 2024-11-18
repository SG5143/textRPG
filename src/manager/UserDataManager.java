package manager;

import java.util.ArrayList;

import item.Item;
import units.Adventurer;

public class UserDataManager {
	private static int nextDungeonLevel;
	private static int gold;

	private static ArrayList<Adventurer> adventurerList; // 보유한 모든 모험가 리스트 = 길드원
	private static ArrayList<Adventurer> partyList; // 현재 파티에 관여하는 길드원 (최대 3)
	private static ArrayList<Item> itemList; // 가지거나 사용하고 있는 모든 아이템리스트

	private UserDataManager() {
		adventurerList = new ArrayList<>();
		partyList = new ArrayList<>();
		itemList = new ArrayList<>();
		gold = 5000;
		nextDungeonLevel = 1;
	}

	private static UserDataManager instance = new UserDataManager();

	public static UserDataManager getInstance() {
		return instance;
	}

	// 로드용
	public static void loadUserData(int level, int gold, ArrayList<Adventurer> adventurerList,
			ArrayList<Item> itemList) {
		UserDataManager.nextDungeonLevel = level;
		UserDataManager.gold = gold;
		UserDataManager.adventurerList = adventurerList;
		UserDataManager.itemList = itemList;
		refreshPartyList();
	}

	public static void refreshPartyList() {
		ArrayList<Adventurer> partyList = new ArrayList<>();
		for (Adventurer adv : adventurerList) {
			if (adv.isParty())
				partyList.add(adv);
		}
		UserDataManager.partyList = partyList;
	}

	public static ArrayList<Adventurer> getAdventurerList() {
		return adventurerList;
	}

	public static ArrayList<Adventurer> getPartyList() {
		return partyList;
	}

	public static ArrayList<Item> getItemList() {
		return itemList;
	}

	public static void addItem(Item addItem) {
		itemList.add(addItem);
	}

	public static void removeItem(Item delItem) {
		itemList.remove(delItem);
	}

	public static void addAdventurer(Adventurer target) {
		adventurerList.add(target);
	}

	public static void removeAdventurer(Adventurer target) {
		adventurerList.remove(target);
	}

	public static int getNextDungeonLevel() {
		return nextDungeonLevel;
	}

	public static void setNextDungeonLevel(int nextDungeonLevel) {
		UserDataManager.nextDungeonLevel = nextDungeonLevel;
	}

	public static int getGold() {
		return gold;
	}

	public static void setGold(int gold) {
		UserDataManager.gold = gold;
	}
}
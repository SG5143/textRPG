package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import item.Item;
import units.Adventurer;

public class FileManager {
	private final static String FILE_NAME = "Text_RPG_DATA";

	private static File file;

	private FileManager() {
		file = new File(FILE_NAME);
	}

	private static FileManager instance = new FileManager();

	public static FileManager getInstance() {
		return instance;
	}

	public static void saveUserData() {
		ArrayList<Adventurer> adventurerList = UserDataManager.getAdventurerList();
		ArrayList<Item> itemList = UserDataManager.getItemList();

		IOManager.sb.setLength(0);

		// 던전단계/골드/전체길드원크기/보유아이템개수
		IOManager.sb.append(UserDataManager.getNextDungeonLevel() + "/");
		IOManager.sb.append(UserDataManager.getGold() + "/");
		IOManager.sb.append(adventurerList.size() + "/");
		IOManager.sb.append(itemList.size() + "\n");

		/*
		 * idenC/level/name/att/maxHp/party/weaponCode/armorCode/ringCode
		 * kind/code/name/hp/att/price/isEquipped;
		 */

		for (Item item : itemList) {
			IOManager.sb.append(String.format("%d/%d/%s/%d/%d/%d/%d\n",
					item.getKind(),
					item.getCode(),
					item.getName(),
					item.getHp(),
					item.getAtt(),
					item.getPrice(),
					item.getEquippedBy()));
		}

		for (Adventurer adventurer : adventurerList) {
			IOManager.sb.append(String.format("%d/%d/%s/%d/%d/%b/%d/%d/%d\n", 
					adventurer.getCode(),
					adventurer.getLevel(),
					adventurer.getName(),
					adventurer.getAtt(),
					adventurer.getMaxHp(),
					adventurer.isParty(),
					getItemCode(adventurer.getWeapon()),
					getItemCode(adventurer.getArmor()),
					getItemCode(adventurer.getRing())));
		}

		try (FileWriter fw = new FileWriter(FILE_NAME)) {
			fw.write(IOManager.sb.toString());
		} catch (IOException e) {
			return;
		}
	}

	public static boolean loadUserData() {
		if (!file.exists())
			return false;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String[] basicInfo = br.readLine().split("/");

			UserDataManager.setNextDungeonLevel(Integer.parseInt(basicInfo[0]));
			UserDataManager.setGold(Integer.parseInt(basicInfo[1]));
			int adventurerCount = Integer.parseInt(basicInfo[2]);
			int itemCount = Integer.parseInt(basicInfo[3]);

			for (int i = 0; i < itemCount; i++) {
				String[] itemData = br.readLine().split("/");
				UserDataManager.addItem(parseItem(itemData));
			}

			for (int i = 0; i < adventurerCount; i++) {
				String[] adventurerData = br.readLine().split("/");
				UserDataManager.addAdventurer(parseAdventurer(adventurerData, UserDataManager.getItemList()));
			}

		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private static Item parseItem(String[] data) {
		int kind = Integer.parseInt(data[0]);
		int code = Integer.parseInt(data[1]);
		String name = data[2];
		int hp = Integer.parseInt(data[3]);
		int att = Integer.parseInt(data[4]);
		int price = Integer.parseInt(data[5]);
		int equippedByCode = Integer.parseInt(data[6]);

		return new Item(kind, code, name, hp, att, price, equippedByCode);
	}

	private static Adventurer parseAdventurer(String[] data, ArrayList<Item> itemList) {
		int idenCode = Integer.parseInt(data[0]);
		int level = Integer.parseInt(data[1]);
		String name = data[2];
		int att = Integer.parseInt(data[3]);
		int hp = Integer.parseInt(data[4]);
		boolean party = Boolean.parseBoolean(data[5]);

		Item weapon = findEquipItem(itemList, idenCode, Integer.parseInt(data[6]));
		Item armor = findEquipItem(itemList, idenCode, Integer.parseInt(data[7]));
		Item ring = findEquipItem(itemList, idenCode, Integer.parseInt(data[8]));

		return new Adventurer(idenCode, level, name, att, hp, party, weapon, armor, ring);
	}

	private static int getItemCode(Item item) {
		return item == null ? 0 : item.getCode();
	}

	private static Item findEquipItem(ArrayList<Item> itemList, int idenCode, int code) {
		if (code == 0)
			return null;
		for (Item i : itemList) {
			if (i.getEquippedBy() == idenCode && code == i.getCode())
				return i;
		}
		return null;
	}
}
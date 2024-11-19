package stage;

import java.util.ArrayList;

import item.Item;
import manager.IOManager;
import manager.UserDataManager;
import units.Adventurer;

public class StagePartyEquipment {

	public void activateStage(String command) {
		int advenIdx = parseAdventurerIndex(command);
		if (advenIdx == -1)
			return;

		Adventurer adven = UserDataManager.getPartyList().get(advenIdx);
		printEquipmentStage(adven);
		while (true) {
			String command2 = IOManager.inputString("여기에 입력해 >> ");

			if (command2.equals("나가기"))
				break;
			if (command2.startsWith("해제-")) {
				unequipItem(command2, adven);
				printEquipmentStage(adven);
				continue;
			}

			int itemIdx = parseItemIndex(command2);
			if (itemIdx == -1)
				continue;

			if (itemIdx < 0 || itemIdx >= getAvailableItemList().size()) {
				printInvalidIndex();
				continue;
			}

			Item selectedItem = getAvailableItemList().get(itemIdx);
			adven.setItem(selectedItem);
			printEquipConfirmation();
			break;
		}
	}

	private void unequipItem(String command, Adventurer adven) {
		try {
			int kind = Integer.parseInt(command.substring(3));
			adven.unequipItem(kind);
		} catch (NumberFormatException e) {
			return;
		}
	}

	private void printEquipmentStage(Adventurer adven) {
		printAdventurerEquip(adven);
		printAvailableItems();
	}

	private void printAdventurerEquip(Adventurer adven) {
		String msg = """
				===========================================
				=           < %s의 착용 정보 >        =
				=                                         =
				=  장비이름              체력     공격력  =
				===========================================
				""";
		IOManager.printString(String.format(msg, adven.getName()));

		Item[] equippedItems = { adven.getWeapon(), adven.getArmor(), adven.getRing() };

		for (Item item : equippedItems) {
			String itemName = (item == null) ? "없음" : item.getName();
			int itemAtt = (item == null) ? 0 : item.getAtt();
			int itemHp = (item == null) ? 0 : item.getHp();

			msg = "=   %-12s\t%5d\t   %4d   =\n";
			IOManager.printString(String.format(msg, itemName, itemHp, itemAtt));
		}
		IOManager.printString("===========================================\n");
	}

	private void printAvailableItems() {
		ArrayList<Item> availableItems = getAvailableItemList();
		if (availableItems.size() == 0) {
			printExitGuide();
			return;
		}

		String msg = """

				===========================================
				=         착용 가능한 장비 목록           =
				=                                         =
				=  번호  장비이름         체력    공격력  =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < availableItems.size(); i++) {
			Item item = availableItems.get(i);
			msg = "=  %2d   %-8s  %5d       %4d    =\n";
			IOManager.printString(String.format(msg, i + 1, item.getName(), item.getHp(), item.getAtt()));
		}

		msg = """
				===========================================
				=             번호를 입력하면             =
				=  같은 부위 아이템을 착용 및 교체합니다. =
				=                                         =
				""";

		IOManager.printString(msg);
		printExitGuide();
	}

	private void printExitGuide() {
		String msg = """
				=        착용된 장비를 해체하려면         =
				=         해제-번호를 입력하세요.         =
				=       무기(1), 방어구(2), 반지(3)       =
				=   '나가기'를 입력하면 뒤로 이동합니다.  =
				===========================================
				""";
		IOManager.printString(msg);
	}

	private void printInvalidIndex() {
		String msg = """

				==========================================
				=          번호를 다시 확인해줘          =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printInvalidInput() {
		String msg = """

				==========================================
				=           입력을 다시 확인해           =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printEquipConfirmation() {
		String msg = """
				===========================================
				=           아이템을 장착했어             =
				===========================================
				""";
		IOManager.printString(msg);
	}

	private int parseAdventurerIndex(String command) {
		int advenIdx = -1;
		try {
			advenIdx = Integer.parseInt(command.substring(3)) - 1;
		} catch (NumberFormatException e) {
			printInvalidInput();
			return -1;
		}

		if (advenIdx < 0 || advenIdx >= UserDataManager.getPartyList().size()) {
			printInvalidIndex();
			return -1;
		}

		return advenIdx;
	}

	private int parseItemIndex(String command) {
		int itemIdx = -1;
		try {
			itemIdx = Integer.parseInt(command) - 1;
		} catch (Exception e) {
			printInvalidInput();
			return -1;
		}

		return itemIdx;
	}

	private ArrayList<Item> getAvailableItemList() {
		ArrayList<Item> availableItems = new ArrayList<>();
		for (int i = 0; i < UserDataManager.getItemList().size(); i++) {
			Item item = UserDataManager.getItemList().get(i);
			if (item.getEquippedBy() == 0)
				availableItems.add(item);
		}
		return availableItems;
	}
}
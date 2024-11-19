package stage;

import java.util.ArrayList;

import item.Item;
import manager.IOManager;
import manager.UserDataManager;

public class StageInventory implements Stage {
	private ArrayList<Item> itemList;

	public StageInventory() {
		itemList = UserDataManager.getItemList();
	}

	@Override
	public void activateStage() {
		printInventoryStage();
		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (command.equals("나가기"))
				break;
			else if (command.startsWith("판매-"))
				sellItem(command);
		}
	}

	private void sellItem(String command) {
		int select = -1;
		try {
			select = Integer.parseInt(command.substring(3)) - 1;
		} catch (NumberFormatException e) {
			return;
		}

		if (select < 0 && select >= itemList.size())
			return;

		Item item = itemList.get(select);

		int userGold = UserDataManager.getGold();

		if (item.getPrice() + userGold > 999999) {
			printSellFailure();
			printInventoryStage();
			return;
		}

		UserDataManager.setGold(userGold + item.getPrice() / 2);
		UserDataManager.removeItem(item);

		printSellSuccess();
		printInventoryStage();
	}

	private void printSellFailure() {
		String msg = """

				=========================================
				=          판매에 실패했습니다!         =
				=     골드는 999999원 까지 보유 가능    =
				=========================================
				""";
		IOManager.printString(msg);
	}

	private void printSellSuccess() {
		String msg = """

				=========================================
				=          판매에 성공했습니다!         =
				=========================================
				""";
		IOManager.printString(msg);
	}

	private void printInventoryStage() {
		String msg = """

				=========================================
				=              < 인벤토리 >             =
				=                                       =
				=     이름      체력   공격        가격 =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			
			if (item.getEquippedBy() != 0)
				continue;
			
			IOManager.printString(String.format("= %2d)%-6s\t%5d\t%3d\t%7d =\n", i + 1, item.getName(), item.getHp(),
					item.getAtt(), item.getPrice()));
		}

		msg = """
				=========================================
				=     판매-번호를 입력하면 판매 가능    =
				=          단 판매 시 가격 절반         =
				=  나가기를 입력하면 로비로 이동합니다  =
				=       현재 Gold : %10d          =
				=========================================
				""";
		IOManager.printString(String.format(msg, UserDataManager.getGold()));
	}
}
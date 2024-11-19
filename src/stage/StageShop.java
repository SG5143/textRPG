package stage;

import java.util.ArrayList;

import item.Item;
import manager.IOManager;
import manager.UserDataManager;

public class StageShop implements Stage {
	private ArrayList<Item> items;
	
	public StageShop() {
		items = new ArrayList<Item>();
		initializeShop();
	}
	
	@Override
	public void activateStage() {
		printShopStage();
		while(true) {
			String command = IOManager.inputString("여기에 입력해 >> ");
			

			if (command.equals("나가기"))
				break;
			else if (isValidInputNum(command)) {
				buyItem(command);
				printShopStage();
			}
		}
	}
	
	private boolean isValidInputNum(String menu) {
		int num = -1;

		try {
			num = Integer.parseInt(menu) - 1;

			if (num >= 0 && num < items.size())
				return true;

		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}

	private void buyItem(String menu) {
		int select = Integer.parseInt(menu) - 1;

		Item item = items.get(select);

		int price = item.getPrice();
		int userGold = UserDataManager.getGold();

		if (item.getPrice() > userGold) {
			printBuyFailure();
			return;
		}

		UserDataManager.setGold(userGold - item.getPrice());

		int kind = item.getKind();
		int code = item.getCode();
		String name = item.getName();
		int hp = item.getHp();
		int att = item.getAtt();

		UserDataManager.addItem(new Item(kind, code, name, hp, att, price));
		printBuySuccess();
	}

	private void initializeShop() {
		items.add(new Item(1, 101, "나무막대", 0, 30, 1000));
		items.add(new Item(1, 102, "주걱", 0, 40, 1500));
		items.add(new Item(1, 103, "몽둥이", 0, 60, 3000));
		items.add(new Item(1, 104, "나무 벹", 0, 100, 7000));
		items.add(new Item(1, 105, "알미늄 벹", 0, 200, 30000));
		items.add(new Item(1, 106, "쇠파이프", 0, 300, 60000));
		items.add(new Item(1, 107, "칼", 0, 500, 300000));
		// -----------------------------------------------------
		items.add(new Item(2, 201, "나뭇잎", 50, 0, 1000));
		items.add(new Item(2, 202, "거적대기", 100, 0, 3000));
		items.add(new Item(2, 203, "가죽갑옷", 500, 0, 10000));
		items.add(new Item(2, 204, "튼튼갑옷", 1000, 0, 40000));
		items.add(new Item(2, 205, "강철갑옷", 2000, 0, 100000));
		// -----------------------------------------------------
		items.add(new Item(3, 301, "모래반지", 0, 10, 1000));
		items.add(new Item(3, 302, "은반지", 0, 30, 20000));
		items.add(new Item(3, 303, "금반지", 0, 50, 50000));
		items.add(new Item(3, 303, "ㅋㅋ반지", 0, 100, 100000));
	}

	private void printBuyFailure() {
		String msg = """

				=========================================
				=          구매에 실패했습니다!         =
				=       보유한 골드를 확인해보세요.     =
				=========================================
				""";
		IOManager.printString(msg);
	}

	private void printBuySuccess() {
		String msg = """

				=========================================
				=          구매에 성공했습니다!         =
				=         인벤토리를 확인해보세요.      =
				=========================================
				""";
		IOManager.printString(msg);
	}

	private void printShopStage() {
		String msg = """

				=========================================
				=               < 상 점 >               =
				=                                       =
				=     이름      체력   공격        가격 =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			IOManager.printString(String.format("= %2d)%-6s\t%5d\t%3d\t%7d =\n", i + 1, item.getName(), item.getHp(),
					item.getAtt(), item.getPrice()));
		}

		msg = """
				=========================================
				=       번호를 입력하면 구매 가능       =
				=  나가기를 입력하면 로비로 이동합니다  =
				=       현재 Gold : %10d          =
				=========================================
				""";
		IOManager.printString(String.format(msg, UserDataManager.getGold()));
	}
}

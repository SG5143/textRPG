package stage;

import java.util.HashMap;
import java.util.Map;

import manager.IOManager;

public class MainStage {
	Map<String, Stage> stageList;

	public MainStage() {
		stageList = new HashMap<String, Stage>();
		stageList.put("전투", new StageBettle());
		stageList.put("파티", new StageParty());
		stageList.put("길드", new StageGuild());
		stageList.put("상점", new StageShop());
		stageList.put("인벤토리", new StageInventory());
	}

	public boolean activate() {
		printMainStage();
		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (stageList.containsKey(command)) {
				stageList.get(command).activateStage();
				printMainStage();
			} else if (command.equals("종료"))
				break;
		}
		return false;
	}

	private void printMainStage() {
		String msg = """

				=============================
				=     < 메인 스테이지 >     =
				=   하단에 원하시는 메뉴를  =
				=    입력하면 이동합니다    =
				=                           =
				=     전투 | 파티 | 길드    =
				=       상점 | 인벤토리     =
				=                           =
				=       프로그램 종료는     =
				=    "종료"를 입력하세요.   =
				=============================
				""";
		IOManager.printString(msg);
	}
}
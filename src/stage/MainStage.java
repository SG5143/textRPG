package stage;

import java.util.HashMap;
import java.util.Map;

import manager.FileManager;
import manager.IOManager;
import manager.UserDataManager;
import units.Adventurer;

public class MainStage {
	private Map<String, Stage> stageList;

	public MainStage() {
		stageList = new HashMap<String, Stage>();
		stageList.put("전투", new StageBettle());
		stageList.put("파티", new StageParty());
		stageList.put("길드", new StageGuild());
		stageList.put("상점", new StageShop());
		stageList.put("인벤토리", new StageInventory());
		initLoadData();
	}

	public boolean activate() {
		printMainStage();
		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (stageList.containsKey(command)) {
				stageList.get(command).activateStage();
				printMainStage();
			} else if (command.equals("종료")) {
				FileManager.saveUserData();
				break;
			}
		}
		return false;
	}

	// 데이터 로드-리프레시, 없을 경우 디폴트 모험가
	private void initLoadData() {
		if (!FileManager.loadUserData()) {
			Adventurer adven1 = new Adventurer("모험가", 40, 1000);
			Adventurer adven2 = new Adventurer("초보자", 80, 700);

			adven1.toggleParty(true);
			adven2.toggleParty(true);
			UserDataManager.addAdventurer(adven1);
			UserDataManager.addAdventurer(adven2);
		}
		UserDataManager.refreshPartyList();
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
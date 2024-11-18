package textrpg;

import manager.IOManager;
import stage.MainStage;

public class TextRPG {
	private MainStage mainStage;
	private boolean isRun;

	private TextRPG() {
		this.mainStage = new MainStage();
		this.isRun = true;
	}

	private static TextRPG instance = new TextRPG();

	public static TextRPG getInstance() {
		return instance;
	}

	public void run() {
		printMainMenuGuide();

		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (command.equals("시작"))
				isRun = mainStage.activate();
			else if (command.equals("종료"))
				isRun = false;

			if (!isRun) {
				printExitMessage();
				break;
			}
		}
	}

	private void printMainMenuGuide() {
		String msg = """
				=============================
				=         TEXT RPG          =
				=    시작하려면 "시작"을    =
				=    종료하려면 "종료"를    =
				=       입력해주세요.       =
				=============================
				""";
		IOManager.printString(msg);
	}

	private void printExitMessage() {
		String msg = """

				=============================
				=   Text RPG를 종료합니다   =
				=============================
				""";
		IOManager.printString(msg);
	}
}
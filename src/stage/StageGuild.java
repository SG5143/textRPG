package stage;

import manager.IOManager;
import manager.UnitManager;
import manager.UserDataManager;
import units.Adventurer;

public class StageGuild implements Stage {
	private static final int MAX_GUILD_MEMBERS = 10;
	private static final int COST = 5000;
	private static final String[] NAME = { "모", "험", "가", "랜", "덤", "이", "름" };

	@Override
	public void activateStage() {
		printGuildStage();
		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (command.equals("나가기")) {
				break;
			} else if (command.equals("뉴맴버")) {
				gennerateNewMember();
				printGuildStage();
			} else if (command.startsWith("삭제-")) {
				deleteMember(command);
				printGuildStage();
			}
		}
	}

	public void gennerateNewMember() {
		if (UserDataManager.getGold() < COST) {
			printInsufficientGoldMessage();
			return;
		}

		if (UserDataManager.getAdventurerList().size() == MAX_GUILD_MEMBERS) {
			printGuildLimitMessage();
			return;
		}

		String name = generateRandomName();

		int rNum = UnitManager.r.nextInt(8) + 2;
		int hp = rNum * 120;
		int att = rNum * 12;
		Adventurer adven = new Adventurer(name, hp, att);
		UserDataManager.getAdventurerList().add(adven);
		UserDataManager.setGold(UserDataManager.getGold() - COST);

		printNewMember();
	}

	private String generateRandomName() {
		IOManager.sb.setLength(0);
		IOManager.sb.append(NAME[UnitManager.r.nextInt(NAME.length)]);
		IOManager.sb.append(NAME[UnitManager.r.nextInt(NAME.length)]);
		IOManager.sb.append(NAME[UnitManager.r.nextInt(NAME.length)]);
		return IOManager.sb.toString();
	}

	public void deleteMember(String msg) {
		if (UserDataManager.getAdventurerList().size() == 1) {
			printMinimumMemberWarning();
			return;
		}

		try {
			int index = Integer.parseInt(msg.substring(3)) - 1;
			if (index < 0 || index >= UserDataManager.getAdventurerList().size()) {
				printInvalidIndexMessage();
				return;
			}

			printMemberDeletedMessage(index);
			UserDataManager.getAdventurerList().remove(index);
		} catch (NumberFormatException e) {
			printInvalidInputMessage();
		}
	}

	private void printInsufficientGoldMessage() {
		String msg = """

				==========================================
				=  길드원을 추가하기 위한 골드가 부족해  =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printGuildLimitMessage() {
		String msg = """

				==========================================
				=      길드원은 10명을 넘을 수 없어      =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printNewMember() {
		String msg = """

				==========================================
				=       길드원 "%s"가 추가되었어\t =
				==========================================
				""";
		String playerName = UserDataManager.getAdventurerList().get(UserDataManager.getAdventurerList().size() - 1)
				.getName();
		IOManager.printString(String.format(msg, playerName));
	}

	private void printInvalidInputMessage() {
		String msg = """

				==========================================
				=           입력을 다시 확인해           =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printMinimumMemberWarning() {
		String msg = """

				==========================================
				=       최소 한명의 유저가 필요해        =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printInvalidIndexMessage() {
		String msg = """

				==========================================
				=          번호를 다시 확인해줘          =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printMemberDeletedMessage(int index) {
		String msg = """

				==========================================
				=       길드원 "%s"가 삭제되었어\t =
				==========================================
				""";
		String playerName = UserDataManager.getAdventurerList().get(index).getName();
		IOManager.printString(String.format(msg, playerName));
	}

	private void printGuildStage() {
		String msg = """

				==========================================
				=               < 내 길드 >              =
				=                                        =
				=    이름      체력  공격\t파티여부 =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < UserDataManager.getAdventurerList().size(); i++) {
			Adventurer adven = UserDataManager.getAdventurerList().get(i);
			String party = adven.isParty() ? "O" : "X";
			IOManager.printString(String.format("= %2d)%-6s%5d  %3d\t    %s    =\n", i + 1, adven.getName(),
					adven.getHp(), adven.getAtt(), party));
		}

		msg = """
				==========================================
				=     뉴맴버를 입력하면 5000 골드를      =
				=      지불하여 길드원을 추가합니다.     =
				=                                        =
				=          삭제-번호를 입력하면          =
				=        해당 길드원이 삭제됩니다.       =
				=                                        =
				=  나가기를 입력하면 로비로 이동합니다   =
				==========================================
				""";
		IOManager.printString(msg);
	}
}

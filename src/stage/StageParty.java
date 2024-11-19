package stage;

import java.util.ArrayList;

import manager.IOManager;
import manager.UserDataManager;
import units.Adventurer;

public class StageParty implements Stage {
	private StagePartyEquipment stagePartyEquipment;

	public StageParty() {
		this.stagePartyEquipment = new StagePartyEquipment();
	}

	@Override
	public void activateStage() {
		printPartyStage();
		while (true) {
			String command = IOManager.inputString("여기에 입력해 >> ");

			if (command.equals("나가기")) {
				break;
			} else if (command.startsWith("정보-")) {
				stagePartyEquipment.activateStage(command);
				printPartyStage();
			} else if (command.startsWith("추가-")) {
				addMember();
				printPartyStage();
			} else if (command.startsWith("추방-")) {
				kickMember(command);
				printPartyStage();
			}
		}
	}

	private void addMember() {
		if (UserDataManager.getPartyList().size() == 3) {
			printPartyFullMessage();
			return;
		}

		ArrayList<Adventurer> guildMembers = new ArrayList<>();
		for (int i = 0; i < UserDataManager.getAdventurerList().size(); i++) {
			Adventurer adven = UserDataManager.getAdventurerList().get(i);
			if (!adven.isParty()) // 파티에 없는 멤버
				guildMembers.add(adven);
		}

		if (guildMembers.size() == 0) {
			printGuildAdventurerEmptyMessage();
			return;
		}

		printGuildMembersForAddition(guildMembers);

		try {
			int index = Integer.parseInt(IOManager.inputString("추가할 길드원 번호 입력 >> ")) - 1;
			if (index < 0 || index >= guildMembers.size()) {
				printInvalidIndex();
				return;
			}

			Adventurer selectedAdven = guildMembers.get(index);
			UserDataManager.getPartyList().add(selectedAdven);
			selectedAdven.toggleParty(true);
			UserDataManager.refreshPartyList();
			printMemberAdded(selectedAdven.getName());
		} catch (NumberFormatException e) {
			printInvalidInput();
		}
	}

	private void kickMember(String menu) {
		if (UserDataManager.getPartyList().size() <= 1) {
			printMinimumPartyWarning();
			return;
		}

		try {
			int index = Integer.parseInt(menu.substring(3)) - 1;
			if (index < 0 || index >= UserDataManager.getPartyList().size()) {
				printInvalidIndex();
				return;
			}

			Adventurer removedAdventurer = UserDataManager.getPartyList().remove(index);
			removedAdventurer.toggleParty(false);
			UserDataManager.refreshPartyList();
			printMemberKicked(removedAdventurer.getName());
		} catch (NumberFormatException e) {
			printInvalidInput();
		}
	}

	private void printGuildMembersForAddition(ArrayList<Adventurer> guildMembers) {
		String msg = """

				==========================================
				=       < 추가 가능한 길드원 목록 >      =
				=                                        =
				=     Lv  이름     체력 공격             =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < guildMembers.size(); i++) {
			Adventurer adven = guildMembers.get(i);
			IOManager.printString(String.format("= %2d)%3d %-6s%5d  %3d\t\t =\n", i + 1, adven.getLevel(),
					adven.getName(), adven.getHp(), adven.getAtt()));
		}

		msg = """
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printMemberAdded(String AdvenName) {
		String msg = """

				==========================================
				=  길드원 "%s"가 파티에 추가되었어   =
				==========================================
				""";
		IOManager.printString(String.format(msg, AdvenName));
	}

	private void printGuildAdventurerEmptyMessage() {
		String msg = """

				==========================================
				=     추가할 수 있는 길드원이 없어       =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printPartyFullMessage() {
		String msg = """

				==========================================
				=      파티는 최대 3명까지만 가능해      =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printMinimumPartyWarning() {
		String msg = """

				==========================================
				=       파티에는 최소 한 명이 필요해     =
				==========================================
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

	private void printMemberKicked(String AdvenName) {
		String msg = """

				==========================================
				=       파티원 "%s"가 추방되었어      =
				==========================================
				""";
		IOManager.printString(String.format(msg, AdvenName));
	}

	private void printInvalidInput() {
		String msg = """

				==========================================
				=           입력을 다시 확인해           =
				==========================================
				""";
		IOManager.printString(msg);
	}

	private void printPartyStage() {
		String msg = """

				==========================================
				=             < 파티 정보 >              =
				=                                        =
				=     Lv  이름     체력 공격             =
				""";
		IOManager.printString(msg);

		for (int i = 0; i < UserDataManager.getPartyList().size(); i++) {
			Adventurer adven = UserDataManager.getPartyList().get(i);
			IOManager.printString(String.format("= %2d)%3d %-6s%5d  %3d\t\t =\n", i + 1, adven.getLevel(),
					adven.getName(), adven.getHp(), adven.getAtt()));
		}

		msg = """
				==========================================
				=          파티원을 추방하려면           =
				=       '추방-번호'를 입력하세요.        =
				=                                        =
				=          추가하려면 '추가'를           =
				=     입력하면 길드 목록을 불러옵니다.   =
				=                                        =
				=      장비 정보 및 장착을 원하시면      =
				=       '정보-번호'를 입력하세요.        =
				=                                        =
				=   나가기를 입력하면 로비로 이동합니다  =
				==========================================
				""";
		IOManager.printString(msg);
	}
}

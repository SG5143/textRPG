# Text RPG

### 구현기능
- [x] 유저관리
- [x] 스테이지 (+맵추가)
  - [x] 길드
  - [x] 파티
  - [x] 전투기능 (+몬스터 추가)
  - [x] 상점
  - [x] 인벤토리
- [x] 데이터 저장 및 로드

### 임시 - Tree
```
📦src
 ┣ 📂item
 ┃ ┗ 📜Item.java
 ┣ 📂manager
 ┃ ┣ 📜FileManager.java
 ┃ ┣ 📜IOManager.java
 ┃ ┣ 📜UnitManager.java
 ┃ ┗ 📜UserDataManager.java
 ┣ 📂stage
 ┃ ┣ 📜MainStage.java
 ┃ ┣ 📜Stage.java
 ┃ ┣ 📜StageBettle.java
 ┃ ┣ 📜StageGuild.java
 ┃ ┣ 📜StageInventory.java
 ┃ ┣ 📜StageParty.java
 ┃ ┣ 📜StagePartyEquipment.java
 ┃ ┗ 📜StageShop.java
 ┣ 📂textrpg
 ┃ ┣ 📜Main.java
 ┃ ┗ 📜TextRPG.java
 ┗ 📂units
   ┣ 📜Adventurer.java
   ┣ 📜Monster.java
   ┣ 📜MonsterGhoul.java
   ┣ 📜MonsterGoblin.java
   ┣ 📜MonsterOrc.java
   ┗ 📜Unit.java
```

### 임시 - Class Diagram
![클래스다이어그램](https://github.com/SG5143/textRPG/blob/develop/resource/textRPG.png)

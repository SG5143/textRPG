package item;

public class Item {
	private int kind;  // 1.weapon 2.armor 3.ring 
	private int code; 
	private String name; 
	private int hp; 
	private int att; 
	private int price; 

	private boolean isEquipped; 
	
	public Item(int kind, int code, String name, int hp, int att, int price) {
		this.kind = kind;
		this.code = code;
		this.name = name;
		this.hp = hp;
		this.att = att;
		this.price = price;
	}
	
	// 로드용 생성자
	public Item(int kind, int code, String name, int hp, int att, int price, boolean isEquipped) {
		this.kind = kind;
		this.code = code;
		this.name = name;
		this.hp = hp;
		this.att = att;
		this.price = price;
		this.isEquipped = isEquipped;
	}
	
	public int getKind() {
		return kind;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getAtt() {
		return att;
	}

	public int getPrice() {
		return price;
	}

	public boolean isEquipped() {
		return isEquipped;
	}

	public void setEquipped(boolean set) {
		this.isEquipped = set;
	}
}
public class Monster implements Comparable<Monster> {
	
	private String name = null;
	private String copyFrom = null;
	private int hp = -1;
	private int ac = -1;
	private int str = -1;
	private int dex = -1;
	private int con = -1;
	private int intel = -1;
	private int wis = -1;
	private int cha = -1;
	
	public Monster(String monsterName, int healthPoints, int armorClass,
			int strength, int dexterity, int constitution, int intelligence, int wisdom,
			int charisma, String copy) {
		name = monsterName;
		hp = healthPoints;
		ac = armorClass;
		str = strength;
		dex = dexterity;
		con = constitution;
		intel = intelligence;
		wis = wisdom;
		cha = charisma;
		copyFrom = copy;
		
	}
	
	public Monster(Monster specificMonster) {
		name = specificMonster.getName();
		hp = specificMonster.getHp();
		ac = specificMonster.getAc();
		str = specificMonster.getStr();
		dex = specificMonster.getDex();
		con = specificMonster.getCon();
		intel = specificMonster.getIntel();
		wis = specificMonster.getWis();
		cha = specificMonster.getCha();
		copyFrom = specificMonster.getCopyFrom();
	}

	public void setHp(int healthPoints) {
		hp = healthPoints;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setName(String monsterName) {
		name = monsterName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAc(int armorClass) {
		ac = armorClass;
	}
	
	public int getAc() {
		return ac;
	}

	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getDex() {
		return dex;
	}

	public void setDex(int dex) {
		this.dex = dex;
	}

	public int getCon() {
		return con;
	}

	public void setCon(int con) {
		this.con = con;
	}

	public int getIntel() {
		return intel;
	}

	public void setIntel(int intel) {
		this.intel = intel;
	}

	public int getWis() {
		return wis;
	}

	public void setWis(int wis) {
		this.wis = wis;
	}

	public int getCha() {
		return cha;
	}

	public void setCha(int cha) {
		this.cha = cha;
	}

	public String getCopyFrom() {
		return copyFrom;
	}

	public void setCopyFrom(String copyFrom) {
		this.copyFrom = copyFrom;
	}

	@Override
	public int compareTo(Monster o) {
		return name.compareTo(o.getName());
	}
	
	
	// ONLY HP AND AC
	public String getStats() {
		String stats = "";
		try {
		stats = "Hit Points: " + hp;
		stats += "\nArmor Class: " + ac;
		stats += "\nStrength: " + str + "\nDexterity: " + dex + "\nConstitution: " + con + "\nIntelligence: " + intel + "\nWisdom: " + wis + "\nCharisma: " + cha;
		} catch (Exception e) {
			System.out.println("Failed to retrieve stats of monster: " + name);
		}
		return stats;
	}
}

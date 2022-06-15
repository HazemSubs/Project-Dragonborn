import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MonsterList {
	
	private Monster[] monsters;
	private int numOfMonsters = 0;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public MonsterList() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Monster> list = new ArrayList<Monster>();
		addBestiary(list);
		monsters = list.toArray(new Monster[list.size()]);
		Arrays.sort(monsters);
		
		for (int i = 0; i < 4; i++)
		{
		this.fixCopies();
		}		
		
		this.finish();
	}
	
	// ONLY FIXES AC AND HP
	private void finish() {
		for (int i = 0; i < monsters.length; i++)
		{
			if (monsters[i].getAc() == -1) monsters[i].setAc(17);
			if (monsters[i].getHp() == -1) monsters[i].setHp(50);
		}
	}

	private void fixCopies() {
		for (int i = 0; i < monsters.length; i++) {
			
			if (monsters[i].getCopyFrom() != null) // IF THERE IS A COPY (i HOLDS INDEX OF MONSTER WHICH REQUIRES VALUES)
			{
				String copyFrom = monsters[i].getCopyFrom();
				
				if (copyFrom.compareTo((monsters[i].getName())) > 0) // IF COPY IS AFTER MONSTER AT INDEX i (j HOLDS INDEX OF MONSTER WHICH HAS THE VALUES THAT NEED TO BE COPIED)
				{
					for (int j = i; j < monsters.length; j++)
					{
						if (monsters[j].getName().compareTo(copyFrom) == 0)
						{
							if (monsters[i].getAc() == -1) monsters[i].setAc(monsters[j].getAc());
							if (monsters[i].getHp() == -1) monsters[i].setHp(monsters[j].getHp());
							if (monsters[i].getStr() == -1) monsters[i].setStr(monsters[j].getStr());
							if (monsters[i].getDex() == -1) monsters[i].setDex(monsters[j].getDex());
							if (monsters[i].getCon() == -1) monsters[i].setCon(monsters[j].getCon());
							if (monsters[i].getIntel() == -1) monsters[i].setIntel(monsters[j].getIntel());
							if (monsters[i].getWis() == -1) monsters[i].setWis(monsters[j].getWis());
							if (monsters[i].getCha() == -1) monsters[i].setCha(monsters[j].getCha());
							break;
						}
					}
				}
				if (copyFrom.compareTo((monsters[i].getName())) < 0) // IF COPY IS BEFORE MONSTER AT INDEX i
				{
					for (int j = 0; j < i; j++)
					{
						if (monsters[j].getName().compareTo(copyFrom) == 0)
						{
							if (monsters[i].getAc() == -1) monsters[i].setAc(monsters[j].getAc());
							if (monsters[i].getHp() == -1) monsters[i].setHp(monsters[j].getHp());
							if (monsters[i].getStr() == -1) monsters[i].setStr(monsters[j].getStr());
							if (monsters[i].getDex() == -1) monsters[i].setDex(monsters[j].getDex());
							if (monsters[i].getCon() == -1) monsters[i].setCon(monsters[j].getCon());
							if (monsters[i].getIntel() == -1) monsters[i].setIntel(monsters[j].getIntel());
							if (monsters[i].getWis() == -1) monsters[i].setWis(monsters[j].getWis());
							if (monsters[i].getCha() == -1) monsters[i].setCha(monsters[j].getCha());
						}
					}
				}
			}			
		}		
	}

	private void addBestiary(ArrayList<Monster> list) {
		File file = null;
		String pathName = "";
		// BEGIN FOR-FILE LOOP
		for (int i = 0; i < 7; i++) {
			if (i == 0) pathName = "data/bestiary-mm.json";
			if (i == 1) pathName = "data/bestiary-vgm.json";
			if (i == 2) pathName = "data/bestiary-mtf.json";
			if (i == 3) pathName = "data/bestiary-vrgr.json";
			if (i == 4) pathName = "data/bestiary-gos.json";
			if (i == 5) pathName = "data/bestiary-ftd.json";
			if (i == 6) pathName = "data/bestiary-cos.json";
			try {
				file = new File(pathName);
			} catch (Exception e) {
				System.out.println("Error reading file: " + pathName);
			}		
			
			
		JsonNode node = null;
		try {
			node = objectMapper.readTree(file).get("monster"); // GET ALL MONSTERS
		} catch (Exception e) {
			System.out.println("Failed to initialize reading of file: " + pathName);
		}
			
			
		ArrayNode mon = (ArrayNode) node; // ROOT NODE OF ALL MONSTERS
		for (int j = 0; j < mon.size(); j++) { // ITERATE THROUGH TREE
			
			String name = null;
			int hp = -1;
			int ac = -1;
			
			int str = -1;
			int dex = -1;
			int con = -1;
			int intel = -1;
			int wis = -1;
			int cha = -1;
			
			String copy = null;
			
			try { // GET NAME
				
			name = mon.get(j).get("name").toString();
			name = name.replace("\"", "");
			
			} catch (Exception e) {
				System.out.println("Name failure of index: " + j + " in book: " + pathName);
			}
		
			try { // GET HP or -1 instead
				
			hp = mon.get(j).get("hp").get("average").asInt();
			
			} catch (Exception e) {
				hp = -1;
				System.out.println("HP failure of index: " + j + " in book: " + pathName);
			}
				
			try { // GET AC or -1 instead
				
			ac = mon.get(j).get("ac").get(0).asInt();
			if (ac == 0) {
				ac = mon.get(j).get("ac").get(0).get("ac").asInt();
			}
			
			} catch (Exception e) {
				ac = -1;
				System.out.println("AC failure of index: " + j + " in book: " + pathName);
			}
			
			try { str = mon.get(j).get("str").asInt(); } catch (Exception e) {str = -1;}
			try { dex = mon.get(j).get("dex").asInt(); } catch (Exception e) {dex = -1;}
			try { con = mon.get(j).get("con").asInt(); } catch (Exception e) {con = -1;}
			try { intel = mon.get(j).get("int").asInt(); } catch (Exception e) {intel = -1;}
			try { wis = mon.get(j).get("wis").asInt(); } catch (Exception e) {wis = -1;}
			try { cha = mon.get(j).get("cha").asInt(); } catch (Exception e) {cha = -1;}
			
			try { // SEE IF IT IS A MODIFIED COPY OF SOMETHING
				copy = mon.get(j).get("_copy").get("name").toString();
				copy = copy.replace("\"", "");
			} catch (Exception e) {
				copy = null;
			}
			
			
			if (name != null) {
				list.add(new Monster(name, hp, ac, str, dex, con, intel, wis, cha, copy));
			}
		}
		numOfMonsters = numOfMonsters + mon.size();
		}
		// END OF FILE-FOR LOOP
	}

	public int getNumOfMonsters() {
		return numOfMonsters;
	}
	
	public Monster[] getMonsters() {
		return monsters;
	}
	
	public Monster getSpecificMonster(String name) {
		for (int i = 0; i < monsters.length; i++) {
			if ((monsters[i].getName()).equals(name)) return monsters[i];
		}
		return null;
	}
	
	public String[] getMonsterNameList() {
		String[] names =  new String[monsters.length];
		for (int i = 0; i < monsters.length; i++) {
			names[i] = monsters[i].getName();
		}
		return names;
	}
	
	/*public static void main(String[] args) {
		MonsterList list = new MonsterList();
		Monster[] monsters = list.getMonsters();
		for (int i = 0; i < 5; i++) {
			System.out.println(monsters[i].getStats());
		}
		list.getClass();
	}*/

}
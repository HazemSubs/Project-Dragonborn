
public class InitiativeContainer implements Comparable<InitiativeContainer> {
	private String name;
	private String init;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	@Override
	public int compareTo(InitiativeContainer o) {
		int other = Integer.parseInt(o.init);
		int tThis = Integer.parseInt(this.init);
		if (other > tThis) return 1;
		if (other < tThis) return -1;
		if (other == tThis) return 0;
		return 0;
	}
	
}

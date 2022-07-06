import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class InitiativeComposite extends Composite implements Comparable<InitiativeComposite> {
	
	protected Label name;
	// protected Label num;
	protected MonsterComposite monster;
	protected int initiative = 0;

	public InitiativeComposite(Composite parent, int style) {
		super(parent, style);
		
	}

	@Override
	public int compareTo(InitiativeComposite o) {
		if (initiative > o.initiative) {
			return -1;
		} else if (initiative < o.initiative) {
			return 1;
		} else return 0;
	}

}

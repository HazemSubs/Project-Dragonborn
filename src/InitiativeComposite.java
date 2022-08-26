import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class InitiativeComposite extends Composite {
	
	protected Text name;
	protected Text num;
	private int initiative;

	public InitiativeComposite(Composite parent, int style) {
		super(parent, style);
		
	}
	
	public InitiativeComposite(Composite parent, int style, InitiativeComposite another) {
		super(parent, style);
		
		this.name = another.name;
		this.num = another.num;
		this.initiative = another.initiative;
	}

}

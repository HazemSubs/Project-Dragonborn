import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MonsterComposite extends Composite {
	
	protected Label name, hp, ac, num;
	protected Button delete, changeHp, changeAc, addToInitiativeBtn;
	protected Text hpChange, acChange;
	protected Monster monster;

	public MonsterComposite(Composite parent, int style) {
		super(parent, style);
		
	}

}

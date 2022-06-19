import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class InitiativeComposite extends Composite {
	
	protected Label name, num;
	protected DropTarget dropTarget;
	protected MonsterComposite monster;

	public InitiativeComposite(Composite parent, int style) {
		super(parent, style);
		
	}

}

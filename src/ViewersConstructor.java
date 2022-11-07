import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ViewersConstructor {
	
	public void initiativeViewerConstructor(InitiativeComposite[] initiativeListViewers, Composite InitiativeViewer) {
		for (int i = 0; i < 20; i++)
		{
			initiativeListViewers[i] = new InitiativeComposite(InitiativeViewer, SWT.BORDER);
			initiativeListViewers[i].setBounds(10, (71+(36*i)), 180, 30);
			
			initiativeListViewers[i].name = new Text(initiativeListViewers[i], SWT.NONE);
			initiativeListViewers[i].name.setBounds(0, 2, 140, 24);
			initiativeListViewers[i].name.setText("");
			
			initiativeListViewers[i].num = new Text(initiativeListViewers[i], SWT.NONE);
			initiativeListViewers[i].num.setBounds(150, 2, 24, 24);
			initiativeListViewers[i].num.setText("0");
			
		}
	}

}

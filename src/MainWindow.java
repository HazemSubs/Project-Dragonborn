import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

public class MainWindow { //AKA Encounter Mode

	protected Shell shlProjectDragonborn;
	protected static MonsterList list;
	private Text monsterDetails;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			list = new MonsterList();
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlProjectDragonborn.open();
		shlProjectDragonborn.layout();
		while (!shlProjectDragonborn.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlProjectDragonborn = new Shell();
		shlProjectDragonborn.setImage(SWTResourceManager.getImage(MainWindow.class, "/img/Dragonborn.PNG"));
		shlProjectDragonborn.setMinimumSize(800, 600);
		shlProjectDragonborn.setMaximumSize(1920, 1080);
		shlProjectDragonborn.setSize(1200, 1000);
		shlProjectDragonborn.setText("Project Dragonborn");
		shlProjectDragonborn.setLayout(new GridLayout(3, false));
		//shlProjectDragonborn.setFullScreen(true);
		
		Menu menu = new Menu(shlProjectDragonborn, SWT.BAR);
		shlProjectDragonborn.setMenuBar(menu);
		
		MenuItem mntmNewCheckbox = new MenuItem(menu, SWT.CHECK);
		mntmNewCheckbox.setToolTipText("Make Encounters");
		mntmNewCheckbox.setText("Encounter Menu");
		
		Composite choose = new Composite(shlProjectDragonborn, SWT.BORDER);
		choose.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		choose.setLayout(new GridLayout(1, false));
		
		Button btnAddToEncounter = new Button(choose, SWT.FLAT);
		Combo monsterSelectorBox = new Combo(choose, SWT.NONE);
		monsterSelectorBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
				{
					btnAddToEncounter.notifyListeners(e.keyCode, new Event());
				}
			}
		});
		monsterSelectorBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		monsterSelectorBox.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		monsterSelectorBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String monsterName = monsterSelectorBox.getText();
				try {
				monsterDetails.setText(list.getSpecificMonster(monsterName).getStats());
				} catch (Exception setDetailsException) {
					monsterDetails.setText("Please select a monster from the list");
				}
			}
		});
		monsterSelectorBox.setItems(list.getMonsterNameList());
		monsterSelectorBox.setText("Select Monster");
		
		monsterDetails = new Text(choose, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		GridData gd_monsterDetails = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_monsterDetails.widthHint = 191;
		monsterDetails.setLayoutData(gd_monsterDetails);
		monsterDetails.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		monsterDetails.setText("Monster Details");
		
		Composite InitiativeViewer = new Composite(shlProjectDragonborn, SWT.BORDER);
		GridData gd_InitiativeViewer = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
		gd_InitiativeViewer.widthHint = 200;
		InitiativeViewer.setLayoutData(gd_InitiativeViewer);
		
		
		Button groupMonsters = new Button(InitiativeViewer, SWT.CHECK);
		groupMonsters.setBounds(87, 10, 103, 25);
		groupMonsters.setText("Group Monsters");
		groupMonsters.setSelection(true);
		System.out.println(groupMonsters.getSelection());
		
		Label initList = new Label(InitiativeViewer, SWT.NONE);
		initList.setAlignment(SWT.CENTER);
		initList.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		initList.setBounds(0, 0, 81, 25);
		initList.setText("Initiative List");
		
		Button initiativeRollBtn = new Button(InitiativeViewer, SWT.FLAT);
		initiativeRollBtn.setText("Roll Initiative");
		initiativeRollBtn.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		initiativeRollBtn.setBounds(10, 41, 180, 25);
		
		InitiativeComposite[] initiativeListViewers = new InitiativeComposite[21];
		
		for (int i = 0; i < 21; i++) {
			initiativeListViewers[i] = new InitiativeComposite(InitiativeViewer, SWT.BORDER);
			initiativeListViewers[i].setBounds(10, (71+(36*i)), 180, 30);
			
			initiativeListViewers[i].num = new Label(initiativeListViewers[i], SWT.BORDER);
			initiativeListViewers[i].num.setText("");
			initiativeListViewers[i].num.setAlignment(SWT.CENTER);
			initiativeListViewers[i].num.setBounds(150, 2, 25, 25);
			
			initiativeListViewers[i].name = new Label(initiativeListViewers[i], SWT.NONE);
			initiativeListViewers[i].name.setBounds(0, 2, 56, 16);
			initiativeListViewers[i].name.setText("");
		}
		
		/*InitiativeComposite composite_1 = new InitiativeComposite(InitiativeViewer, SWT.BORDER);
		composite_1.setBounds(10, 41, 180, 30);
		
		Label num = new Label(composite_1, SWT.BORDER);
		num.setText("20");
		num.setAlignment(SWT.CENTER);
		num.setBounds(150, 2, 25, 25);
		
		Label name = new Label(composite_1, SWT.NONE);
		name.setBounds(0, 2, 56, 16);
		name.setText("Name");
		
		Composite composite_1_1 = new Composite(InitiativeViewer, SWT.BORDER);
		composite_1_1.setBounds(10, 77, 180, 30);
		
		Label num_1 = new Label(composite_1_1, SWT.BORDER);
		num_1.setText("20");
		num_1.setAlignment(SWT.CENTER);
		num_1.setBounds(150, 2, 25, 25);
		
		Label name_1 = new Label(composite_1_1, SWT.NONE);
		name_1.setText("Name");
		name_1.setBounds(0, 2, 56, 16);*/
		
		Composite monsterViewer = new Composite(shlProjectDragonborn, SWT.BORDER);
		monsterViewer.setLayout(null);
		GridData gd_monsterViewer = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_monsterViewer.heightHint = 572;
		gd_monsterViewer.widthHint = 795;
		monsterViewer.setLayoutData(gd_monsterViewer);
		
		/*// TBR TESTING MONSTOR BLOCK
		Composite test = new Composite(monsterViewer, SWT.BORDER);
		test.setBounds(10+(3*186), 382, 180, 180);
		Label name = new Label(test, SWT.WRAP);
		name.setBounds(0, 0, 150, 40);
		name.setText("TEST");
		
		Label hp = new Label(test, SWT.NONE);
		hp.setBounds(0, 46, 95, 25);
		hp.setText("Hit Points: " + "4");
		
		Label ac = new Label(test, SWT.NONE);
		ac.setBounds(0, 77, 95, 25);
		ac.setText("Armor Class: " + "5");
		
		Label num = new Label(test, SWT.BORDER | SWT.CENTER);
		num.setBounds(157, 157, 21, 21);
		num.setText("1");
		
		Button delete_1 = new Button(test, SWT.NONE);
		delete_1.setBounds(157, 0, 21, 21);
		delete_1.setText("X");
		
		Text hpChange = new Text(test, SWT.BORDER);
		hpChange.setBounds(150, 46, 28, 21);
		hpChange.setText("233");
		
		Button removeHp = new Button(test, SWT.NONE);
		removeHp.setBounds(129, 46, 21, 21);
		removeHp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		removeHp.setText("-");
		
		Button addHp = new Button(test, SWT.NONE);
		addHp.setBounds(108, 46, 21, 21);
		addHp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		addHp.setText("+");
		
		Text acChange = new Text(test, SWT.BORDER);
		acChange.setText("233");
		acChange.setBounds(150, 77, 28, 21);
		
		Button removeAc = new Button(test, SWT.NONE);
		removeAc.setText("-");
		removeAc.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		removeAc.setBounds(129, 77, 21, 21);
		
		Button addAc = new Button(test, SWT.NONE);
		addAc.setText("+");
		addAc.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		addAc.setBounds(108, 77, 21, 21);
		
		Button addToInitiativeBtn = new Button(test, SWT.BORDER);
		addToInitiativeBtn.setBounds(0, 157, 95, 21);
		addToInitiativeBtn.setText("Add to Initiative");
		
		// TESTER MONSTER BOX*/
		
		//String abc = "Abc\nAbc";
		//System.out.println(abc.replaceFirst("Abc\n", ""));
		
		MonsterComposite[] monsterListViewers = new MonsterComposite[12];
		
		btnAddToEncounter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddToEncounter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int emptyViewer = -1;
				for (int i = 0; i < 12; i++) // 12 Because of maximum of 12 monsters on screen. (To be increased?)
				{
					if (monsterListViewers[i] == null || !(monsterListViewers[i].isVisible())) {
						emptyViewer = i;
						break;
					}
					if (i == 11) {
						monsterDetails.setText("No Space Available, please remove a card first");
						return;
					}
				}
				
				try {
					monsterListViewers[emptyViewer] = new MonsterComposite(monsterViewer, SWT.BORDER);
					if (emptyViewer >= 0 && emptyViewer <= 3) monsterListViewers[emptyViewer].setBounds(10+(emptyViewer*186), 10, 180, 180);
					if (emptyViewer >= 4 && emptyViewer <= 7) monsterListViewers[emptyViewer].setBounds(10+((emptyViewer-4)*186), 196, 180, 180);
					if (emptyViewer >= 8 && emptyViewer <= 11) monsterListViewers[emptyViewer].setBounds(10+((emptyViewer-8)*186), 382, 180, 180);
					String sName = list.getSpecificMonster(monsterSelectorBox.getText()).getName(); //GET NAME FROM MONSTER SELECTION BOX
					int hpNum = list.getSpecificMonster(monsterSelectorBox.getText()).getHp(); //GET HP FROM MONSTER SELECTION BOX
					int acNum = list.getSpecificMonster(monsterSelectorBox.getText()).getAc(); //GET AC FROM MONSTER SELECTION BOX
					
					monsterListViewers[emptyViewer].monster = new Monster(list.getSpecificMonster(sName));
					monsterListViewers[emptyViewer].name = new Label(monsterListViewers[emptyViewer], SWT.WRAP);
					monsterListViewers[emptyViewer].name.setBounds(0, 0, 150, 40);
					monsterListViewers[emptyViewer].name.setText(sName);
					
					monsterListViewers[emptyViewer].hp = new Label(monsterListViewers[emptyViewer], SWT.NONE);
					monsterListViewers[emptyViewer].hp.setBounds(0, 46, 95, 25);
					monsterListViewers[emptyViewer].hp.setText("Hit Points: " + String.valueOf(hpNum));
					
					monsterListViewers[emptyViewer].ac = new Label(monsterListViewers[emptyViewer], SWT.NONE);
					monsterListViewers[emptyViewer].ac.setBounds(0, 77, 95, 25);
					monsterListViewers[emptyViewer].ac.setText("Armor Class: " + String.valueOf(acNum));
					
					monsterListViewers[emptyViewer].num = new Label(monsterListViewers[emptyViewer], SWT.BORDER | SWT.CENTER);
					monsterListViewers[emptyViewer].num.setBounds(157, 157, 21, 21);
					monsterListViewers[emptyViewer].num.setText(String.valueOf(emptyViewer+1));
					
					monsterListViewers[emptyViewer].hpChange = new Text(monsterListViewers[emptyViewer], SWT.BORDER);
					monsterListViewers[emptyViewer].hpChange.setBounds(150, 46, 28, 21);
					monsterListViewers[emptyViewer].hpChange.setText("0");
					
					monsterListViewers[emptyViewer].acChange = new Text(monsterListViewers[emptyViewer], SWT.BORDER);
					monsterListViewers[emptyViewer].acChange.setBounds(150, 77, 28, 21);
					monsterListViewers[emptyViewer].acChange.setText("0");
					
					Text tempHpChange = monsterListViewers[emptyViewer].hpChange;
					Text tempAcChange = monsterListViewers[emptyViewer].acChange;
					
					SelectionAdapter hpChange = new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							try {
								MonsterComposite parent = (MonsterComposite) tempHpChange.getParent();
								int healthChange = 0;
								if (tempHpChange.getText().startsWith("+")) {
									healthChange = Integer.parseInt(tempHpChange.getText());
								} else {
									healthChange = -1*Integer.parseInt(tempHpChange.getText());
								}
								this.change(parent, healthChange);
							} catch (Exception hpChangeError) {
								tempHpChange.setText("");
								monsterDetails.setText("Please only enter integer values");
							}
						}

						private void change(Composite parent, int parsedInt) {
							for (int i = 0; i < 12; i++) {
								if (parent.equals(monsterListViewers[i])) {
									int newHp = monsterListViewers[i].monster.getHp() + parsedInt;
									monsterListViewers[i].monster.setHp(newHp); //IF YOU'D LIKE ALL THE NEXT ITERATIONS TO HAVE SAME HP
									monsterListViewers[i].hp.setText("Hit Points: " + String.valueOf(newHp));
									monsterListViewers[i].hpChange.setText("");
									break;
									/*if (newHp <= 0 && !(monsterListViewers[i].monster.getName().endsWith(" (DEAD)"))) {
										monsterListViewers[i].monster.setName(monsterListViewers[i].monster.getName() + " (DEAD)");
										monsterListViewers[i].name.setText(monsterListViewers[i].monster.getName());
									} else {
										int index = monsterListViewers[i].monster.getName().lastIndexOf(" (DEAD)");
										if (index > 0 && newHp >= 0) {
											monsterListViewers[i].monster.setName(monsterListViewers[i].monster.getName().substring(0, index));
											monsterListViewers[i].name.setText(monsterListViewers[i].monster.getName());
										}
									}*/
								}
							}
						}
					};
					
					SelectionAdapter acChange = new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							try {
								MonsterComposite parent = (MonsterComposite) tempAcChange.getParent();
								int acChange = 0;
								if (tempAcChange.getText().startsWith("+")) {
									acChange = Integer.parseInt(tempAcChange.getText());
								} else {
									acChange = -1*Integer.parseInt(tempAcChange.getText());
								}
								this.change(parent, acChange);
							} catch (Exception hpChangeError) {
								tempAcChange.setText("");
								monsterDetails.setText("Please only enter integer values");
							}
						}

						private void change(Composite parent, int parsedInt) {
							for (int i = 0; i < 12; i++) {
								if (parent.equals(monsterListViewers[i])) {
									int newAc = monsterListViewers[i].monster.getAc() + parsedInt;
									monsterListViewers[i].monster.setAc(newAc); //IF YOU'D LIKE ALL THE NEXT ITERATIONS TO HAVE SAME AC
									monsterListViewers[i].ac.setText("Armor Class: " + String.valueOf(newAc));
									monsterListViewers[i].acChange.setText("");
									break;
								}
							}
						}
					};
					
					monsterListViewers[emptyViewer].changeHp = new Button(monsterListViewers[emptyViewer], SWT.NONE);
					monsterListViewers[emptyViewer].changeHp.addSelectionListener(hpChange); // BUTTON TO CHANGE HP VALUE
					monsterListViewers[emptyViewer].changeHp.setBounds(129, 46, 21, 21);
					monsterListViewers[emptyViewer].changeHp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
					monsterListViewers[emptyViewer].changeHp.setText("-");
					
					monsterListViewers[emptyViewer].hpChange.addKeyListener(new KeyAdapter() { // CLICK ENTER TO CHANGE AC VALUE
						@Override
						public void keyPressed(KeyEvent e) {
							if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
							{
								MonsterComposite temp = this.getViewer(tempAcChange.getParent());
								temp.changeHp.notifyListeners(e.keyCode, new Event());
							}
						}

						private MonsterComposite getViewer(Composite parent) {
							for (int i = 0; i < 12; i++) {
								if (parent.equals(monsterListViewers[i])) {
									return monsterListViewers[i];
								}
							}
							return null;
						}
					});
					
					monsterListViewers[emptyViewer].changeAc = new Button(monsterListViewers[emptyViewer], SWT.NONE);
					monsterListViewers[emptyViewer].changeAc.addSelectionListener(acChange); // BUTTON TO CHANGE AC VALUE
					monsterListViewers[emptyViewer].changeAc.setText("-");
					monsterListViewers[emptyViewer].changeAc.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
					monsterListViewers[emptyViewer].changeAc.setBounds(129, 77, 21, 21);
					
					monsterListViewers[emptyViewer].acChange.addKeyListener(new KeyAdapter() { // CLICK ENTER TO CHANGE AC VALUE
						@Override
						public void keyPressed(KeyEvent e) {
							if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
							{
								MonsterComposite temp = this.getViewer(tempAcChange.getParent());
								temp.changeAc.notifyListeners(e.keyCode, new Event());
							}
						}

						private MonsterComposite getViewer(Composite parent) {
							for (int i = 0; i < 12; i++) {
								if (parent.equals(monsterListViewers[i])) {
									return monsterListViewers[i];
								}
							}
							return null;
						}
					});
					
					monsterListViewers[emptyViewer].delete = new Button(monsterListViewers[emptyViewer], SWT.NONE);
					Button tempDelete = monsterListViewers[emptyViewer].delete;
					monsterListViewers[emptyViewer].delete.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							this.delete(tempDelete.getParent());
						}

						private void delete(Composite parent) {
							for (int i = 0; i < 12; i++) {
								if (parent.equals(monsterListViewers[i])) {
									monsterListViewers[i].setVisible(false);
									break;
								}
							}
							
							for (int i = 0; i < 21; i++) {
								if (parent.equals(initiativeListViewers[i].monster)) {
									initiativeListViewers[i].monster = null;
									initiativeListViewers[i].name.setText("");
									initiativeListViewers[i].num.setText("");
								}
							}
						}
					});
					monsterListViewers[emptyViewer].delete.setBounds(157, 0, 21, 21);
					monsterListViewers[emptyViewer].delete.setText("X");
					
					monsterListViewers[emptyViewer].addToInitiativeBtn = new Button(monsterListViewers[emptyViewer], SWT.BORDER);
					Button tempInit = monsterListViewers[emptyViewer].addToInitiativeBtn;
					monsterListViewers[emptyViewer].addToInitiativeBtn.setBounds(0, 157, 95, 21);
					monsterListViewers[emptyViewer].addToInitiativeBtn.setText("Add to Initiative");
					monsterListViewers[emptyViewer].addToInitiativeBtn.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							this.addToInitiative((MonsterComposite) tempInit.getParent());
						}

						private void addToInitiative(MonsterComposite parent) {
							for (int i = 0; i < 21; i++) {
								if (initiativeListViewers[i].name.getText().equals("")) {
									initiativeListViewers[i].monster = parent;
									initiativeListViewers[i].name.setText(initiativeListViewers[i].monster.name.getText());
									initiativeListViewers[i].num.setText(initiativeListViewers[i].monster.num.getText());
									break;
								}
							}	
						}
					});
					
					monsterListViewers[emptyViewer].setVisible(true);
				} catch (Exception addEncounterError) {
						monsterDetails.setText("Please select a monster from the list");
						if (monsterListViewers[emptyViewer] != null) monsterListViewers[emptyViewer].setVisible(false);
				}
			}
		});
		btnAddToEncounter.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAddToEncounter.setText("Add to Encounter");
		
		Composite playerViewer = new Composite(shlProjectDragonborn, SWT.BORDER);
		GridData gd_playerViewer = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_playerViewer.widthHint = 872;
		playerViewer.setLayoutData(gd_playerViewer);

	}
}

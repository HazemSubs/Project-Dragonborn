import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;

import java.util.Arrays;
import java.util.Random;

import org.eclipse.swt.SWT;
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

public class MainWindow { //AKA Encounter Mode

	protected Shell shlProjectDragonborn;
	protected static MonsterList list;
	private Text monsterDetails;
	private Random rand;

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
		while (!shlProjectDragonborn.isDisposed())
		{
			if (!display.readAndDispatch())
			{
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
		
		ViewersConstructor constructor = new ViewersConstructor();
		
		rand = new Random();
		
		Composite choose = new Composite(shlProjectDragonborn, SWT.BORDER);
		choose.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		choose.setLayout(new GridLayout(1, false));
		
		Button btnAddToEncounter = new Button(choose, SWT.FLAT);
		Combo monsterSelectorBox = new Combo(choose, SWT.NONE);
		monsterSelectorBox.addKeyListener(new KeyAdapter()
		{
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
			public void widgetSelected(SelectionEvent e)
			{
				String monsterName = monsterSelectorBox.getText();
				try
				{
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
		
		Label initList = new Label(InitiativeViewer, SWT.NONE);
		initList.setAlignment(SWT.CENTER);
		initList.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		initList.setBounds(0, 0, 190, 25);
		initList.setText("Initiative List");
		
		Button rollInitiativeBtn = new Button(InitiativeViewer, SWT.FLAT);
		rollInitiativeBtn.setText("Roll Initiative");
		rollInitiativeBtn.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		rollInitiativeBtn.setBounds(10, 41, 180, 25);
		
		InitiativeComposite[] initiativeListViewers = new InitiativeComposite[20];
		
		constructor.initiativeViewerConstructor(initiativeListViewers, InitiativeViewer);
				
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
		
		// CONSTRUCTOR STOPPED WORKING HERE
		
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
		
		rollInitiativeBtn.addSelectionListener(new SelectionAdapter() {
			
			//InitiativeComposite[] organizedList = new InitiativeComposite[initiativeListViewers.length];			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
				
					InitiativeContainer[] organizedList = new InitiativeContainer[initiativeListViewers.length];
				
				
					for (int i = 0; i < initiativeListViewers.length; i++) {
						organizedList[i] = new InitiativeContainer();
						organizedList[i].setName(initiativeListViewers[i].name.getText());
						organizedList[i].setInit(initiativeListViewers[i].num.getText());
					}
				
					//System.out.println(initiativeListViewers[0].num.getText() + initiativeListViewers[0].name.getText()); // TBR
					System.out.println(organizedList[0].getName() + organizedList[0].getInit()); // TBR


					if (organizedList[3].getName().equals("Jorah"))
					{
						this.getClass();
					}
					Arrays.sort(organizedList);
				
					//System.out.println(initiativeListViewers[0].num.getText() + initiativeListViewers[0].name.getText()); // TBR
					System.out.println(organizedList[0].getName() + organizedList[0].getInit()); // TBR
				
				
					String[] names = new String[initiativeListViewers.length];
					String[] initCounts = new String[initiativeListViewers.length];
				
					/*for (int i = 0; i < initiativeListViewers.length; i++)
					{
						names[i] = initiativeListViewers[i].name.getText();
						initCounts[i] = initiativeListViewers[i].num.getText();
					}*/
												
					for (int i = 0; i < initiativeListViewers.length; i++) 
					{
						if (initiativeListViewers[i].name.getText().equals(""))
						{
							initiativeListViewers[i].num.setText("");
						}
					}
				
					for (int i = 0; i < initiativeListViewers.length; i++)
					{
						initiativeListViewers[i].name.setText(organizedList[i].getName());
						initiativeListViewers[i].num.setText(organizedList[i].getInit());
						//System.out.println("");
						//System.out.println(organizedList[i].name.getText() + " " + organizedList[i].num.getText()); // TBR
						//System.out.println("");
						//System.out.println(initiativeListViewers[i].name.getText() + " " + initiativeListViewers[i].num.getText()); // TBR
						//organizedList[i].num.setText((initiativeListViewers[i].num.getText()));
						//if (organizedList[i].name.getText().equals("")) {break;}
						//String name = organizedList[i].name.getText();
						//String number = organizedList[i].num.getText();
						//initiativeListViewers[i].name.setText(initiativeListViewers[i].name.getText());
						//initiativeListViewers[i].num.setText(initiativeListViewers[i].num.getText());
					}
				
					this.getClass();
					
				} catch (Exception rollInitiativeErr) {
					monsterDetails.setText("Please enter a valid number into the initiative number slots");
				}
				
			}
		});
		
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
							try
							{
								MonsterComposite parent = (MonsterComposite) tempHpChange.getParent();
								int healthChange = 0;
								if (tempHpChange.getText().startsWith("+"))
								{
									healthChange = Integer.parseInt(tempHpChange.getText());
								}
								else {
									healthChange = -1*Integer.parseInt(tempHpChange.getText());
								}
								this.change(parent, healthChange);
							} catch (Exception hpChangeError) {
								tempHpChange.setText("");
								monsterDetails.setText("Please only enter integer values");
							}
						}

						private void change(Composite parent, int parsedInt)
						{
							for (int i = 0; i < 12; i++)
							{
								if (parent.equals(monsterListViewers[i]))
								{
									int newHp = monsterListViewers[i].monster.getHp() + parsedInt;
									monsterListViewers[i].monster.setHp(newHp); //IF YOU'D LIKE ALL THE NEXT ITERATIONS TO HAVE SAME HP
									monsterListViewers[i].hp.setText("Hit Points: " + String.valueOf(newHp));
									monsterListViewers[i].hpChange.setText("");
									break;
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
							
						}
					});
					monsterListViewers[emptyViewer].delete.setBounds(157, 0, 21, 21);
					monsterListViewers[emptyViewer].delete.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
					monsterListViewers[emptyViewer].delete.setText("x");
					
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
		
		/*MonsterComposite[] playerViewerList = new MonsterComposite[9];
		String[] playerNames = {"Brunk", "Florence", "Gatrie", "Jonathan", "Krusk", "Oceanus", "Rolthos", "Seamus", "Ublish"};//new String[9];
		
		for (int i = 0; i < 9; i++) {
			playerViewerList[i] = new MonsterComposite(playerViewer, SWT.BORDER);
			if (i >= 0 && i <= 2) playerViewerList[i].setBounds(10, 10+(70*i), 180, 30);
			if (i >= 3 && i <= 5) playerViewerList[i].setBounds(280, 10+(70*(i-3)), 180, 30);
			if (i >= 6 && i <= 8) playerViewerList[i].setBounds(550, 10+(70*(i-6)), 180, 30);
			
			playerViewerList[i].name = new Label(playerViewerList[i], SWT.NONE);
			playerViewerList[i].name.setBounds(0, 0, 100, 30);
			playerViewerList[i].name.setText(playerNames[i]);
			
			playerViewerList[i].delete = new Button(playerViewerList[i], SWT.NONE);
			playerViewerList[i].delete.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			playerViewerList[i].delete.setBounds(150, 0, 30, 30);
			playerViewerList[i].delete.setText("X");
		}*/
		
		/*MonsterComposite seamus = new MonsterComposite(playerViewer, SWT.BORDER);
		seamus.setBounds(10, 10, 180, 30);
		
		seamus.name = new Label(seamus, SWT.NONE);
		seamus.name.setBounds(0, 0, 100, 30);
		seamus.name.setText("Seamus McDepp");
		
		seamus.delete = new Button(seamus, SWT.NONE);
		seamus.delete.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		seamus.delete.setBounds(150, 0, 30, 30);
		seamus.delete.setText("X");

		/*
		 * 
		 * initiativeListViewers[i] = new InitiativeComposite(InitiativeViewer, SWT.BORDER);
			initiativeListViewers[i].setBounds(10, (71+(36*i)), 180, 30);
		 * initiativeListViewers[i] = new InitiativeComposite(InitiativeViewer, SWT.BORDER);
			initiativeListViewers[i].setBounds(10, (71+(36*i)), 180, 30);
			
			/*initiativeListViewers[i].num = new Label(initiativeListViewers[i], SWT.BORDER);
			initiativeListViewers[i].num.setText("");
			initiativeListViewers[i].num.setAlignment(SWT.CENTER);
			initiativeListViewers[i].num.setBounds(150, 2, 25, 25);
			
			initiativeListViewers[i].name = new Label(initiativeListViewers[i], SWT.NONE);
			initiativeListViewers[i].name.setBounds(0, 2, 56, 16);
			initiativeListViewers[i].name.setText("");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		GridData gd_playerViewer = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_playerViewer.widthHint = 540;
		playerViewer.setLayoutData(gd_playerViewer);
		

	}
}

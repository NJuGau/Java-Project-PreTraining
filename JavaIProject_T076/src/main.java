import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main {
	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();
	static ArrayList<database> Pl = new ArrayList<>();
	static char[] board = new char[12];
	static int[] PlMove = new int[11];
	static int[] OpMove = new int[11];
	static int idxpl1=-1;
	static int idxpl2=-1;
	static String active_us;
	static String active_pa;
	
	
	static void Sort_Merge(int left, int mid, int right) {
		int LeftSize= mid-left+1;
		int RightSize=right-mid;
		ArrayList<database> ArrLeft= new ArrayList<>();
		ArrayList<database> ArrRight= new ArrayList<>();
		
		for(int i=0; i<LeftSize; i++) {
			ArrLeft.add(Pl.get(left+i));
		}
		
		for(int i=0; i<RightSize; i++) {
			ArrRight.add(Pl.get(mid+i+1));
		}
		
		int currLeft=0;
		int currRight=0;
		int piv=left;
		while(currLeft<LeftSize&&currRight<RightSize) {
			if(ArrLeft.get(currLeft).Score>ArrRight.get(currRight).Score) {
				Pl.set(piv, ArrLeft.get(currLeft));
				currLeft++;
			}else if(ArrLeft.get(currLeft).Score<ArrRight.get(currRight).Score) {
				Pl.set(piv, ArrRight.get(currRight));
				currRight++;
			} else if(ArrLeft.get(currLeft).Score==ArrRight.get(currRight).Score) {
				if(ArrLeft.get(currLeft).User.compareTo(ArrRight.get(currRight).User)<0) {
					Pl.set(piv, ArrLeft.get(currLeft));
					currLeft++;
				} else {
					Pl.set(piv, ArrRight.get(currRight));
					currRight++;
				}
			}
			piv++;
		}
		while(currLeft<LeftSize) {
			Pl.set(piv, ArrLeft.get(currLeft));
			currLeft++; piv++;
		}
		while(currRight<RightSize) {
			Pl.set(piv, ArrRight.get(currRight));
			currRight++; piv++;
		}
		
	}
	
	static void Sort_Split(int left, int right) {
		if(left<right) {
			int mid=left+(right-left)/2;
			Sort_Split(left, mid);
			Sort_Split(mid+1, right);
			Sort_Merge(left, mid, right);
		}
	}
	
	static int win_check(char point) {
		if(board[1]==point&&board[2]==point&&board[3]==point) return 1;
		else if(board[4]==point&&board[5]==point&&board[6]==point) return 1;
		else if(board[7]==point&&board[8]==point&&board[9]==point) return 1;
		else if(board[1]==point&&board[4]==point&&board[7]==point) return 1;
		else if(board[2]==point&&board[5]==point&&board[8]==point) return 1;
		else if(board[3]==point&&board[6]==point&&board[9]==point) return 1;
		else if(board[1]==point&&board[5]==point&&board[9]==point) return 1;
		else if(board[3]==point&&board[5]==point&&board[7]==point) return 1;
		return 0;
	}
	
	static int Check_Defense(int a, int b, int c) {
		if(board[a]=='X'&&board[b]=='X'&&board[c]!='X'&&board[c]!='O') return c;
		else if(board[a]=='X'&&board[c]=='X'&&board[b]!='X'&&board[b]!='O') return b;
		else if(board[c]=='X'&&board[b]=='X'&&board[a]!='X'&&board[a]!='O') return a;
		return -1;
	}
	static int Check_Offense(int a, int b, int c) {
		if(board[a]=='O'&&board[b]=='O'&&board[c]!='X'&&board[c]!='O') return c;
		else if(board[a]=='O'&&board[c]=='O'&&board[b]!='X'&&board[b]!='O') return b;
		else if(board[c]=='O'&&board[b]=='O'&&board[a]!='X'&&board[a]!='O') return a;
		return -1;
	}
	
	static int bot_hard(int move) {
		int idx=0;
		if(move==1) {
			int roll=rand.nextInt(4)+1;
			if(roll==1) return 1;
			else if(roll==2) return 3;
			else if(roll==3) return 7;
			else if(roll==4) return 9;
		}else if(move==2) {
			if(board[5]=='X') {
			int roll=rand.nextInt(4)+1;
			if(roll==1) return 1;
			else if(roll==2) return 3;
			else if(roll==3) return 7;
			else if(roll==4) return 9;
			}else {
				return 5;
			}
		}else {
			idx=Check_Offense(1,2,3);
			if(idx==-1) {
				idx=Check_Offense(4,5,6);
				if(idx==-1) {
					idx=Check_Offense(7,8,9);
					if(idx==-1) {
						idx=Check_Offense(1,4,7);
						if(idx==-1) {
							idx=Check_Offense(2,5,8);
							if(idx==-1) {
								idx=Check_Offense(3,6,9);
								if(idx==-1) {
									idx=Check_Offense(1,5,9);
									if(idx==-1) {
										idx=Check_Offense(3,5,7);
										if(idx==-1) {
											idx=Check_Defense(1,2,3);
											if(idx==-1) {
												idx=Check_Defense(4,5,6);
												if(idx==-1) {
													idx=Check_Defense(7,8,9);
													if(idx==-1) {
														idx=Check_Defense(1,4,7);
														if(idx==-1) {
															idx=Check_Defense(2,5,8);
															if(idx==-1) {
																idx=Check_Defense(3,6,9);
																if(idx==-1) {
																	idx=Check_Defense(1,5,9);
																	if(idx==-1) {
																		idx=Check_Defense(3,5,7);
																		if(idx==-1) {
																			do {
																				idx=rand.nextInt(9)+1;
																			}while(board[idx]=='X'||board[idx]=='O');
																	}
																}
															}
														}
													}
												}
											}
										}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return idx;
	}
	
	static int bot_easy() {
		int idx=0;
		do {
			idx=rand.nextInt(9);
			System.out.println(idx);
		}while(board[idx]=='X'||board[idx]=='O'||idx<1||idx>9);
		return idx;
	}
	
	static int input_turn() {
		int idx=0;
		do {
			System.out.print("Please input number[1-9]: ");
			try {
				idx=scan.nextInt();
			} catch (Exception e) {
				System.out.println("Input invalid");
			} 
			if(idx<1||idx>9) {
				System.out.println("Input must be between [1-9]");
			}else if (board[idx]=='X'||board[idx]=='O') {
				System.out.println("The position has been filled!");
			}
			scan.nextLine();
		}while(idx<1||idx>9||board[idx]=='X'||board[idx]=='O');
		return idx;
	}
	
	static void Board_Display(int method, int move) {
		System.out.println("|   |   |   |");
		
		for(int j=1; j<=3; j++) {
			System.out.print("|");
			for(int i=1+((j-1)*3); i<=3+((j-1)*3); i++) {
				if(board[i]!=' ')
					System.out.printf(" %c ", board[i]);
				else
					System.out.printf(" %d ", i);
				System.out.print("|");
			}
			System.out.println();
			System.out.println("|   |   |   |");
		}
		System.out.println();
		System.out.println("=======================================");
		if(method==1||method==2)
			System.out.println("Your moves:");
		else
			System.out.println(Pl.get(idxpl1).getUser() + " turn:");
		for(int i=1; i<=move; i++) {
			if(PlMove[i]!=0)
				System.out.println("X on " + PlMove[i]);
		}
		System.out.println();
		System.out.println("=======================================");
		if(method==1||method==2)
			System.out.println("Enemy moves:");
		else
			System.out.println(Pl.get(idxpl2).getUser() + " turn:");
		for(int i=1; i<=move; i++) {
			if(OpMove[i]!=0) 
				System.out.println("O on " + OpMove[i]);
			}
		System.out.println();
		System.out.println("=======================================");
		System.out.println();
		System.out.println();
	}
	
	static void Sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Err...");
		}
	}
	
	static void TicTacToe(int turn, int method, int move) {
		int idx=0;
		if(turn==0) {
			//pl1 turn
			if(method==3) {
				System.out.println("Player 1 turn:");
			}else {
				System.out.println("Your turn:");
			}
			Board_Display(method, move);
			idx=input_turn();
			PlMove[move]=idx;
			board[idx]='X';
			turn=1;
			if(win_check('X')==1) {
				if(method==3) {
					System.out.println("Player 1 win!");
					database win = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), Pl.get(idxpl1).getScore()+10);
					Pl.set(idxpl1, win);
					database lose;
					if(Pl.get(idxpl2).getScore()-10<0) {
						lose = new database(Pl.get(idxpl2).getUser(), Pl.get(idxpl2).getPass(), 0);
					}else {
						lose = new database(Pl.get(idxpl2).getUser(), Pl.get(idxpl2).getPass(), Pl.get(idxpl2).getScore()-10);
					}
					Pl.set(idxpl2, lose);
				}else {
					System.out.println("You win!");
					if(method==1) {
						database win = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), Pl.get(idxpl1).getScore()+10);
						Pl.set(idxpl1, win);
					} else if (method==2) {
						database win = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), Pl.get(idxpl1).getScore()+100);
						Pl.set(idxpl1, win);
					}
				}
				System.out.println("Press enter to continue...");
				scan.nextLine();
				return;
			}
		}else if(turn==1){
			//bot/pl2 turn
			if(method==1) {
				System.out.println("Bots turn:");
				Sleep();
				Board_Display(method, move);
				idx=bot_easy();
			} else if (method==2) {
				System.out.println("Bots turn:");
				Sleep();
				Board_Display(method, move);
				idx=bot_hard(move);
			} else if (method==3) {
				System.out.println("Player 2 turn:");
				Board_Display(method, move);
				idx=input_turn();
			}
			OpMove[move]=idx;
			board[idx]='O';
			turn=0;
			if(win_check('O')==1) {
				if(method==1||method==2) {
					System.out.println("You lose...");
					if(method==2) {
						database lose;
						if(Pl.get(idxpl1).getScore()-10<0) {
							lose = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), 0);
						}else {
							lose = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), Pl.get(idxpl1).getScore()-10);
						}
						Pl.set(idxpl1, lose);
					}
				}else if (method==3){
					System.out.println("Player 2 win!");
					database win = new database(Pl.get(idxpl2).getUser(), Pl.get(idxpl2).getPass(), Pl.get(idxpl2).getScore()+10);
					Pl.set(idxpl2, win);
					database lose;
					if(Pl.get(idxpl1).getScore()-10<0) {
						lose = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), 0);
					}else {
						lose = new database(Pl.get(idxpl1).getUser(), Pl.get(idxpl1).getPass(), Pl.get(idxpl1).getScore()-10);
					}
					Pl.set(idxpl1, lose);
				}
				System.out.println("Press enter to continue...");
				scan.nextLine();
				return;
			}
		}
		
		if(move>=9) {
			System.out.println("Tied!");
			System.out.println("Press ENTER to continue...");
			scan.nextLine();
			return;
		}
		TicTacToe(turn, method, move+1);
	}
	
	static int opt_4() {
		int Opt=0;
		do {
			System.out.print("Choose [1-4] >> ");
			try {
				Opt=scan.nextInt();
			} catch (Exception e) {
				System.out.println("Input invalid");
			}
			
			if(Opt<1||Opt>3) System.out.println("Menu invalid");
			scan.nextLine();
		}while(Opt<1||Opt>4);
		return Opt;
	}
	
	static void Dif_Display() {
		System.out.println();
		System.out.println("=========================");
		System.out.println("|     PLAYER DETAIL     |");
		System.out.println("=========================");
		System.out.printf("| Name : %-14s |\n", Pl.get(idxpl1).getUser());
		System.out.printf("| Score: %-14d |\n", Pl.get(idxpl1).getScore());
		System.out.println("=========================");
		System.out.println("|   SELECT DIFFICULTY   |");
		System.out.println("=========================");
		System.out.println("| 1. Easy               |");
        System.out.println("| 2. Hard               |");
        System.out.println("| 3. PvP                |");
        System.out.println("| 4. Back               |");
        System.out.println("=========================");
	}
	
	static void Clean_Board() {
		for(int i=1; i<=9; i++) {
			PlMove[i]=0;
			OpMove[i]=0;
			board[i]=' ';
		}
	}
	
	static int Roll_Dice() {
		int dice=rand.nextInt(10)+1;
		if(dice<=5) {
			return 1;
		} else {
			return 0;
		}
	}
	static int sameAcc() {
		if(idxpl1==idxpl2) {
			System.out.println("[*] Cannot use the same account for 2nd player!");
			System.out.println("Press ENTER to continue...");
			scan.nextLine();
			return 1;
		}
		return 0;
	}
	
	static void Select_Dif() {
		int DOpt=0;
		idxpl2=-1;
		int g= checkCred(active_us,active_pa,1);
		do {
			Clean_Board();
			Dif_Display();
			DOpt=opt_4();
			switch(DOpt) {
			case 1:
				TicTacToe(Roll_Dice(), 1, 1);
				break;
			case 2:
				TicTacToe(Roll_Dice(), 2, 1);
				break;
			case 3:
				int check = Login(2);
				if(check==1&&sameAcc()==0) {
					TicTacToe(Roll_Dice(), 3, 1);
				}
				break;
			}
		}while(DOpt!=4);
	}
	
	static void Scoreboard_Display() {
		System.out.println();
		System.out.println("==============================");
		System.out.println("| Name             | Score   |");
		System.out.println("==============================");
		for(database d : Pl) {
			System.out.print("| ");
			System.out.printf("%-15s", d.getUser());
			System.out.print("  | ");
			System.out.printf("%-7d", d.getScore());
			System.out.println(" |");
		}
		System.out.println("==============================");
		System.out.println("Press ENTER to continue...");
		scan.nextLine();
	}
	
	static void Play_Display() {
		System.out.println();
		System.out.println("=================");
		System.out.println("|  Tic-JaG-Toe  |");
		System.out.println("=================");
		System.out.println("| 1. Play       |");
		System.out.println("| 2. Scoreboard |");
		System.out.println("| 3. Back       |");
		System.out.println("=================");
	}
	
	static void Play() {
		int POpt=0;
		do {
			Sort_Split(0,Pl.size()-1);
			Play_Display();
			POpt=opt_3();
			switch(POpt) {
			case 1:
				Select_Dif();
				break;
			case 2:
				Scoreboard_Display();
				break;
			}
		}while(POpt!=3);
	}
	
	
	static int checkCred(String Us, String Pa, int p) {
		int idx=0;
		for(database d : Pl) {
			if(d.getUser().compareTo(Us)==0&&d.getPass().compareTo(Pa)==0) {
				if(p==1) {
					active_us=Us;
					active_pa=Pa;
					idxpl1=idx;
				}else if(p==2) {
					idxpl2=idx;
				}
				return 1;
			}
			idx++;
		}
		return 0;
	}
	
	static int Login(int p) {
		String User;
		String Pass;
		int wrongPass=0;
		do {
		wrongPass=0;
		System.out.print("Input name [type '0' to go back]: ");
		User=scan.nextLine();
		if(User.compareTo("0")==0) {
			return 0;
		}
		System.out.print("Input password [type '0' to go back]: ");
		Pass=scan.nextLine();
		if(Pass.compareTo("0")==0) {
			return 0;
		}
		if(checkCred(User, Pass, p)==0) {
			System.out.println("[!] Invalid name or password");
			System.out.println("Press ENTER to continue...");
			scan.nextLine();
			wrongPass=1;
		}
		}while(wrongPass==1);
		return 1;
	}
	
	
	static int opt_3() {
		int Opt=0;
		do {
			System.out.print("Choose [1-3] >> ");
			try {
				Opt=scan.nextInt();
			} catch (Exception e) {
				System.out.println("Input invalid");
			}
			
			if(Opt<1||Opt>3) System.out.println("Menu invalid");
			scan.nextLine();
		}while(Opt<1||Opt>3);
		return Opt;
	}
	
	static void Main_display() {
		System.out.println();
		System.out.println("=================");
		System.out.println("|  Tic-JaG-Toe  |");
		System.out.println("=================");
		System.out.println("| 1. Login      |");
		System.out.println("| 2. Register   |");
		System.out.println("| 3. Exit       |");
		System.out.println("=================");
	}
	
	public main() {
		int MOpt=0;
		do {
		idxpl1=-1;
		idxpl2=-1;
		active_us="";
		active_pa="";
		Main_display();
		MOpt=opt_3();
		switch(MOpt) {
		case 1:
			if(!Pl.isEmpty()) {
			int check = Login(1);
			if(check==1) {
				Play();
			}
			} else {
				System.out.println("[!] Please create an account!");
				System.out.println("Press ENTER to continue...");
				scan.nextLine();
			}
			break;
			
		case 2:
			Register();
			break;
		}
		}while(MOpt!=3);
		System.out.println("Thank you for playing!");
	}

	public static void main(String[] args) {
		//testcase bila dibutuhkan
//		database def = new database("juan", "ju1234", 45);
//		Pl.add(def);
//		def = new database("andi", "ju1234", 0);
//		Pl.add(def);
//		def = new database("susi", "ju1234", 21);
//		Pl.add(def);
//		def = new database("juli", "ju1234", 30);
//		Pl.add(def);
//		def = new database("tini", "ju1234", 5);
//		Pl.add(def);
		new main();
	}	
	
	static void Register() {
		String username;
		do {
		System.out.print("Input name to play [more than 3 and less than 15 characters]: ");
		username=scan.nextLine();
		}while(username.length()<=3||username.length()>=15);
		String pass;
		do {
		System.out.print("Input password [alphanumeric & more than 5 characters]: ");
		pass=scan.nextLine();
		}while(CheckAlp(pass)==0||pass.length()<=5);
		System.out.println("[*] Successfully registered an account");
		System.out.println("Press ENTER to continue...");
		scan.nextLine();
		database newPl = new database(username, pass,0);
		Pl.add(newPl);
	}
	
	static int CheckAlp(String pass) {
		int alp=0, num=0;
		for(int i=0; i<pass.length(); i++) {
			if((pass.charAt(i)>=65&&pass.charAt(i)<=90)||(pass.charAt(i)>=97&&pass.charAt(i)<=122)&&alp==0) {
				alp=1;
			}
			if((pass.charAt(i)>=48&&pass.charAt(i)<=57)&&num==0) {
				num=1;
			}
			if(alp==1&&num==1) {
				return 1;
			}
		}
		return 0;
	}
}


public class database {

	String User;
	String Pass;
	int Score;
	public database(String User, String Pass, int Score) {
		this.User=User;
		this.Pass=Pass;
		this.Score=Score;
	}
	
	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getPass() {
		return Pass;
	}
	public void setPass(String pass) {
		Pass = pass;
	}
	

}

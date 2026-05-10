public class HealthBar {
	private int health;

	public HealthBar(){
		health = 100;
	}

	public int getHLevel() {
		return health;
	}

	public int totHealth(){
		return 100;
	}

	public void changeHealth(int x){
		health -= x;
	}
}
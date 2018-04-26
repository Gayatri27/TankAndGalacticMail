package objects;

public interface Destroyable {

  int health = 100;

  void setHealth(int health);

  int getHealth();

  void reduceHealth(int amount);

}

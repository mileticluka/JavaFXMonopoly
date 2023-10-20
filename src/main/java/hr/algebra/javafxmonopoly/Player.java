package hr.algebra.javafxmonopoly;

import java.util.ArrayList;

public class Player {
    private int id;
    private int money;
    private int position;
    ArrayList<PropertyPane> deeds;

    public Player(int id, int money) {
        this.id = id;
        this.money = money;
        this.position = 0;
        this.deeds = new ArrayList<>();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

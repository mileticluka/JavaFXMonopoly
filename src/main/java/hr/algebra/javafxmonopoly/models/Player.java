package hr.algebra.javafxmonopoly.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
    private int id;
    private int money;
    private int position;
    List<PropertyPane> deeds;

    public boolean playing;

    public Player(int id, int money) {
        this.id = id;
        this.money = money;
        this.position = 0;
        this.deeds = new CopyOnWriteArrayList<>();
        this.playing = true;
    }

    public void addTitleDeed(PropertyPane propertyPane) {
        this.deeds.add(propertyPane);
    }

    public void removeTitleDeed(PropertyPane propertyPane) {
        this.deeds.remove(propertyPane);
    }

    public void setBankrupt() {
        this.getTitleDeeds().stream().filter(deed -> deed != null).forEach(deed -> {
            deed.setSold(this);
        });

        this.playing = false;
    }

    public List<PropertyPane> getTitleDeeds() {
        return this.deeds;
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

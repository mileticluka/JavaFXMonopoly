package hr.algebra.javafxmonopoly.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements Serializable{
    private int id;
    private int money;
    private int position;

    transient List<PropertyPane> deeds;
    List<Integer> deedIndices;
    

    public boolean playing;

    public Player(int id, int money) {
        this.id = id;
        this.money = money;
        this.position = 0;
        this.deedIndices = new CopyOnWriteArrayList<>();
        this.playing = true;
    }

    public void addTitleDeed(PropertyPane propertyPane) {
        this.deeds.add(propertyPane);
        this.deedIndices.add(Integer.valueOf(propertyPane.getId()));
    }

    public void removeTitleDeed(PropertyPane propertyPane) {
        this.deeds.remove(propertyPane);
        this.deedIndices.remove( Integer.valueOf(propertyPane.getId()));
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

    public void loadDeeds(List<GamePane> allPanes) {
        for(int id : this.deedIndices)
        {
            this.deeds.add((PropertyPane) allPanes.get(id));
            ((PropertyPane) allPanes.get(id)).setOwnerDirectly(this);
        }
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

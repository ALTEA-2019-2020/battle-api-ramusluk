package com.miage.altea.battle_api.bo;

import lombok.Builder;

@Builder
public class BattlePokemon {

    private int id;
    private int level;
    private int hp;
    private int maxHp;
    private boolean ko;
    private boolean alive;
    private PokemonType type;
    private int attack;
    private int defence;
    private int speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public boolean isKo() {
        return ko;
    }

    public void setKo(boolean ko) {
        this.ko = ko;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * @param damage damage that the pokemon will suffer
     * @return boolean : true = this pokemon is steal alive - false = this pokemon is ko
     */
    public boolean takeDamages(int damage) {
        this.hp = this.hp - damage;
        if (this.hp <= 0) {
            this.alive = false;
            this.ko = true;
            this.hp = 0;
            return false;
        }
        return true;
    }
}

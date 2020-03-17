package com.miage.altea.battle_api.bo;

import static java.lang.Math.round;

public class StatsCalculator {

    public static int calculateHP(int stat, int lvl){
        Double level = Double.valueOf(lvl);
        Double stats = Double.valueOf(stat);
        return (int) (10.00+level+(stats*(level/50.00)));

    }

    public static int calculateStat(int stat, int lvl){
        Double level = Double.valueOf(lvl);
        Double stats = Double.valueOf(stat);
        return (int) (5.00+(stats*(level/50.00)));
    }


    public static int calculateDamage(BattlePokemon attacker, BattlePokemon defend){
        Double attackValue = Double.valueOf(attacker.getAttack());
        Double attackLevel = Double.valueOf(attacker.getLevel());
        Double defendValue = Double.valueOf(defend.getDefence());
        return (int) round((((2.00*attackLevel)/5.00)+(2.00*(attackValue/defendValue)))+2.00);
    }




}

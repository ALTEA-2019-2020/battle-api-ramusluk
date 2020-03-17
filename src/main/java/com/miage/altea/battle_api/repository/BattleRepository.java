package com.miage.altea.battle_api.repository;

import com.miage.altea.battle_api.bo.Battle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BattleRepository {

    private static final BattleRepository INSTANCE = new BattleRepository();


    @Autowired
    private Map<String, Battle> battleMap;


    public BattleRepository(){
        this.battleMap = new HashMap<>();
    }

    public static BattleRepository getInstance() {
        return INSTANCE;
    }


    public Battle getBattle(String uuid){
        return battleMap.get(uuid);
    }

    public void addBattle(Battle battle){
        this.battleMap.put(battle.getUuid(), battle);
    }

    public void removeBattle(String uuid){
        this.battleMap.remove(uuid);
    }

    public List<Battle> getAllBattles(){
        return this.battleMap.values().stream().collect(Collectors.toList());
    }

}

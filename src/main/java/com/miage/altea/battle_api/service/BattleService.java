package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle;

import java.util.List;

public interface BattleService {

    String createBattle(String trainerName1, String trainerName2);

    List<Battle> getAllBattles();

    Battle getBattle(String uuid);

    Battle attack(String uuid, String trainerName);


}

package com.miage.altea.battle_api.controller;

import com.miage.altea.battle_api.bo.Battle;
import com.miage.altea.battle_api.service.BattleServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/battles")
public class BattlesController {

    private BattleServiceImpl battleService;

    @Autowired
    public BattlesController(BattleServiceImpl battleService) {
        this.battleService = battleService;
    }

    @PostMapping()
    public ResponseEntity<String> battles(@RequestBody Map<String, String> body) {
        String trainerName = body.get("trainerName");
        String opponentName = body.get("opponentName");
        return ResponseEntity.ok().body(this.battleService.createBattle(trainerName, opponentName));
    }
    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Battle> getBattle(@PathVariable(value = "uuid")String uuid){
        return ResponseEntity.ok().body(this.battleService.getBattle(uuid));
    }

    @GetMapping()
    public ResponseEntity<List<Battle>> getAllBattles() {
        return ResponseEntity.ok().body(this.battleService.getAllBattles());
    }



    @PostMapping(value = "/{uuid}/{trainerName}/attack")
    public ResponseEntity<Battle> attack(@PathVariable(value = "uuid") String uuid, @PathVariable(value = "trainerName") String trainerName){
        return ResponseEntity.ok().body(this.battleService.attack(uuid, trainerName));
    }
}

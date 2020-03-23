package com.miage.altea.battle_api.controller;


import com.miage.altea.battle_api.service.BattleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BattleControllerTest {



    @Test
    void battlesController_shouldBeAnnotated(){
        var controllerAnnotation =
                BattlesController.class.getAnnotation(RestController.class);
        assertNotNull(controllerAnnotation);

        var requestMappingAnnotation =
                BattlesController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/battles"}, requestMappingAnnotation.value());
    }

    @Test
    void battles_shouldBeAnnotated() throws NoSuchMethodException {
        var createBattle =
                BattlesController.class.getDeclaredMethod("battles", String.class, String.class);
        var postMapping = createBattle.getAnnotation(PostMapping.class);
        assertNotNull(postMapping);

        var crossOrigin = createBattle.getAnnotation(CrossOrigin.class);
        assertNotNull(crossOrigin);
    }


    @Test
    void battles_shouldCallTheService(){
        var service = mock(BattleServiceImpl.class);
        var controller = new BattlesController(service);
        var trainerName = "trainer";
        var opponentName = "opponentName";

        controller.battles(trainerName, opponentName);
        verify(service).createBattle(trainerName, opponentName);
    }

    @Test
    void getBattle_shouldCallTheService(){
        var service = mock(BattleServiceImpl.class);
        var controller = new BattlesController(service);
        var uuid = "uuid";

        controller.getBattle(uuid);
        verify(service).getBattle(uuid);
    }

    @Test
    void getAllBattles_shouldCallTheService(){
        var service = mock(BattleServiceImpl.class);
        var controller = new BattlesController(service);

        controller.getAllBattles();
        verify(service).getAllBattles();
    }

    @Test
    void attack_shouldCallTheService(){
        var service = mock(BattleServiceImpl.class);
        var controller = new BattlesController(service);
        var uuid = "uuid";
        var opponentName = "opponentName";

        controller.attack(uuid,opponentName);
        verify(service).attack(uuid,opponentName);
    }

    @Test
    void getBattle_shouldBeAnnotated() throws NoSuchMethodException {
        var getBattle =
                BattlesController.class.getDeclaredMethod("getBattle", String.class);
        var getMapping = getBattle.getAnnotation(GetMapping.class);
        assertArrayEquals(new String[]{"/{uuid}"}, getMapping.value());

        var crossOrigin = getBattle.getAnnotation(CrossOrigin.class);
        assertNotNull(crossOrigin);
    }

    @Test
    void getAllBattle_shouldBeAnnotated() throws NoSuchMethodException {
        var getBattle =
                BattlesController.class.getDeclaredMethod("getAllBattles");
        var getMapping = getBattle.getAnnotation(GetMapping.class);
        assertNotNull(getMapping);

        var crossOrigin = getBattle.getAnnotation(CrossOrigin.class);
        assertNotNull(crossOrigin);
    }

    @Test
    void attack_shouldBeAnnotated() throws NoSuchMethodException {
        var getBattle =
                BattlesController.class.getDeclaredMethod("attack", String.class, String.class);
        var getPost = getBattle.getAnnotation(PostMapping.class);
        assertNotNull(getPost);
        assertArrayEquals(new String[]{"/{uuid}/{trainerName}/attack"}, getPost.value());

        var crossOrigin = getBattle.getAnnotation(CrossOrigin.class);
        assertNotNull(crossOrigin);
    }
}

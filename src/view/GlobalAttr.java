package view;

public class GlobalAttr {



    public static final int WORLD_WIDTH  = 2400;
    public static final int WORLD_HEIGHT = 1400;


    // Camera
    public static final int MINIMAP_WIDTH  = 300;
    public static final int MINIMAP_HEIGHT = 300;
    public static final int MINIMAP_MARGIN = 10;

    // Player
    public static final double PLAYER_START_X    = 0.1;
    public static final double PLAYER_START_Y    = 0.9;
    public static final double PLAYER_SPEED      = 0.2;
    public static final double PLAYER_MAX_HP     = 100;
    public static final double PLAYER_MAX_MANA   = 100;
    public static final double PLAYER_START_HP   = 20;
    public static final double PLAYER_START_MANA = 20;
    public static final double PLAYER_MANA_REGEN = 2.0;

    // Bot
    public static final double BOT_SPEED    = 0.001;
    public static final double BOT_RANGE    = 0.08;
    public static final double BOT_DAMAGE   = 0.2;
    public static final double BOT_MAX_HP   = 100;
    public static final double BOT_MAX_MANA = 100;

    // Tower
    public static final double TOWER_RANGE             = 0.1;
    public static final double TOWER_DAMAGE            = 0.2;
    public static final double TOWER_COLLISION_RADIUS  = 0.03;
    public static final double TOWER_MAX_HP            = 100;

    //Base
    public static final double BASE_RANGE = 0.1;
    public static final double BASE_MAX_HP = 500;
    public static final double BASE_DAMAGE = 0.1;

    // Fountain
    public static final double FOUNTAIN_RADIUS         = 0.08;
    public static final double FOUNTAIN_HEAL_PER_SEC   = 20.0;
    public static final double FOUNTAIN_DAMAGE         = 2.0;

    // Game loop
    public static final int GAME_SPEED = 10;

    //ARENA
    public static final double CAMERA_ZOOM = 2.0;



    //Minion
    public static final double MINION_DMG = 0.15;
    public static final double MINION_RANGE = 0.06;
    public static final double MINION_MAX_HP = 60;
    public static final double MINION_ATTACK_COOLDOWN = 0.9;



    //Non playable Area
    public static final int MAP_BORDER = 200;
    public static final double PLAYABLE_HEIGHT =  WORLD_HEIGHT  - MAP_BORDER * 2;
    public static final double PLAYABLE_WIDTH =  WORLD_WIDTH  - MAP_BORDER * 2;

}
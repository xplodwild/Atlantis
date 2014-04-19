/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.audio;

/**
 *
 * @author Guigui
 */
public class AudioConstants {
    public static class Path {
        private static final String D = "Audio/";
        private static final String E = ".ogg";

        public static final String MAIN_MUSIC = D + "MainTheme" + E;
        public static final String JUMP_WATER = D + "JumpWater" + E;
        public static final String MOVE_BOAT = D + "MoveBoat" + E;
        public static final String MOVE_SWIM = D + "MoveSwimmer" + E;
        public static final String TILE_FLIP = D + "TileFlip" + E;
        public static final String GO_IN_BOAT = D + "GoInBoat" + E;
    }

    public static class Length {
        public static final float MAIN_MUSIC = 80;
        public static final float JUMP_WATER = 3;
        public static final float MOVE_BOAT = 8;
        public static final float MOVE_SWIM = 7;
        public static final float TILE_FLIP = 1.5f;
        public static final float GO_IN_BOAT = 3;
    }
}

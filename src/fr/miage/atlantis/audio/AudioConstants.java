/*
 * Copyright (C) 2014 Loris Durand, Guillaume Lesniak, Cristian Sanna,
 *                    Lucie Wiemert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.miage.atlantis.audio;

/**
 * Constantes pour le système audio (fichiers)
 */
public class AudioConstants {
    public static class Path {
        private static final String D = "Audio/";
        private static final String E = ".wav";

        public static final String MAIN_MUSIC = D + "MainTheme.ogg";
        public static final String AMBIENCE = D + "Ambience.ogg";
        
        public static final String ERROR = D + "Error" + E;
        public static final String DING = D + "Ding" + E;
        public static final String WHOOSH = D + "Whoosh" + E;
        public static final String JUMP_WATER = D + "JumpWater" + E;
        public static final String MOVE_BOAT = D + "MoveBoat" + E;
        public static final String MOVE_SWIM = D + "MoveSwimmer" + E;
        public static final String TILE_FLIP = D + "TileFlip" + E;
        public static final String TILE_SPLASH = D + "TileSplash" + E;
        public static final String GO_IN_BOAT = D + "GoInBoat" + E;
        public static final String DICE_ROLL = D + "DiceRoll" + E;
    }
}

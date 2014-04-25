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
package fr.miage.atlantis.graphics;

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.graphics.models.BoatModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.graphics.models.WhaleModel;
import java.util.Random;

/**
 * Décide quel animation jouer à quel moment
 */
public class AnimationBrain {

    /**
     * Les différents anomations qui se rapportent aux états (marche, nage etc)
     */
    public static class State {

        /**
         * Constructeur State
         *
         * @param a constante d'un animation
         */
        public State(String a) {
            this(a, 0);
        }

        /**
         * Constructeur State
         *
         * @param y angle de caméra pour cette animation
         * @param a constante d'un animation
         */
        public State(String a, float y) {
            animationName = a;
            yOffset = y;
        }

        /**
         * Constructeur State
         *
         * @param aT constante de l'animation de transition vers cet état
         * @param y angle de caméra pour cette animation
         * @param a constante d'un animation
         */
        public State(String a, float y, boolean aT) {
            animationName = a;
            yOffset = y;
            animateTransition = aT;
        }

        /**
         * Si on répète en boucle l'animation d'état
         *
         * @param loop
         * @return
         */
        public State setLoop(boolean loop) {
            this.loop = loop;
            return this;
        }
        /**
         * Nom de l'animation à jouer
         */
        public String animationName;
        /**
         * Offset d'angle Y
         */
        public float yOffset;
        /**
         * Mettre ou non en boucle
         */
        public boolean loop = true;
        /**
         * Animer ou non la transition
         */
        public boolean animateTransition = true;
    }

    /**
     * Récupère l'animation d'une entité lorsqu'ele est fixe sur une case.
     *
     * @param ent entité concernée
     * @return un etat State
     */
    public static State getIdleAnimation(GameEntity ent) {
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;

            if (pt.getState() == PlayerToken.STATE_SWIMMING) {
                return new State(PlayerModel.ANIMATION_SWIM_IDLE);
            } else {
                int idle = new Random().nextInt(3);
                switch (idle) {
                    case 0:
                        return new State(PlayerModel.ANIMATION_LAND_IDLE_1);

                    case 1:
                        return new State(PlayerModel.ANIMATION_LAND_IDLE_2);

                    case 2:
                        return new State(PlayerModel.ANIMATION_LAND_IDLE_3);
                }
            }
        } else if (ent instanceof SeaSerpent) {
            return new State(SeaSerpentModel.ANIMATION_IDLE, 90.0f);
        } else if (ent instanceof Shark) {
            return new State(SharkModel.ANIMATION_SWIM_CYCLE);
        } else if (ent instanceof Whale) {
            return new State(WhaleModel.ANIMATION_IDLE);
        } else if (ent instanceof Boat) {
            return new State(BoatModel.ANIMATION_BOAT_IDLE);
        }

        return null;
    }

    /**
     * Récupère l'animation de mouvement d'une entité
     *
     * @param ent entité concernée
     * @param dest destination du mouvement
     * @return renvoie un State d'animation
     */
    public static State getMovementAnimation(GameEntity ent, GameTile dest) {
        if (ent instanceof PlayerToken) {
            PlayerToken pt = (PlayerToken) ent;

            if (dest.getHeight() == 0
                    && pt.getState() != PlayerToken.STATE_ON_BOAT) {
                return new State(PlayerModel.ANIMATION_SWIM_CYCLE);
            } else {
                return new State(PlayerModel.ANIMATION_WALK_CYCLE);
            }
        } else if (ent instanceof SeaSerpent) {
            return new State(SeaSerpentModel.ANIMATION_SWIM_CYCLE, 0.0f, false);
        } else if (ent instanceof Shark) {
            return new State(SharkModel.ANIMATION_SWIM_CYCLE, 90.0f);
        } else if (ent instanceof Whale) {
            return new State(WhaleModel.ANIMATION_SWIM);
        } else if (ent instanceof Boat) {
            return new State(BoatModel.ANIMATION_BOAT_ROW, 90.0f);
        }

        return null;
    }

    /**
     * Renvoie l'animation de noyade d'une entité
     *
     * @param ent entité concernée
     * @return un état State d'animation
     */
    public static State getDrownAnimation(GameEntity ent) {
        if (ent instanceof PlayerToken) {
            return new State(PlayerModel.ANIMATION_DROWN);
        } else if (ent instanceof Shark) {
            return new State(SharkModel.ANIMATION_SUCKED_DOWN_WHIRPOOL);
        } else if (ent instanceof Whale) {
            return new State(WhaleModel.ANIMATION_SUCKED_DOWN);
        } else if (ent instanceof SeaSerpent) {
            return new State(SeaSerpentModel.ANIMATION_SUCKED_DOWN);
        } else if (ent instanceof Boat) {
            return new State(BoatModel.ANIMATION_BOAT_SINK);
        }

        return null;
    }

    /**
     * Renvoie l'animation d'apparition sur le terrain d'une entité
     *
     * @param ent entité concernée
     * @return un état State d'animation
     */
    public static State getSpawnAnimation(GameEntity ent) {
        if (ent instanceof Shark) {
            return new State(SharkModel.ANIMATION_RISE).setLoop(false);
        } else if (ent instanceof SeaSerpent) {
            return new State(SeaSerpentModel.ANIMATION_RISE).setLoop(false);
        } else if (ent instanceof Whale) {
            return new State(WhaleModel.ANIMATION_RISE).setLoop(false);
        }

        return null;
    }
}

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
package fr.miage.atlantis.network;

import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.logic.GameTurn;
import fr.miage.atlantis.network.messages.MessageNextTurn;
import fr.miage.atlantis.network.messages.MessageRemoteTile;
import fr.miage.atlantis.network.messages.MessageRollDice;
import fr.miage.atlantis.network.messages.MessageTurnEvent;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Méthodes de jeu communes en réseau
 */
public class GameCommonCommands {

    private Game3DLogic mLogic;

    public GameCommonCommands(Game3DLogic logic) {
        mLogic = logic;
    }

    private void log(final String msg) {
        Logger.getGlobal().info(msg);
    }

    public void handleMessageTurnEvent(final MessageTurnEvent m) {
        mLogic.getRenderer().runOnMainThread(new Callable<Void>() {
            public Void call() throws Exception {
                switch (m.getEvent()) {
                    case GameTurn.STEP_INITIAL_PLAYER_PUT: {
                        String tileName = (String) m.getParameter(0);
                        int points = (Integer) m.getParameter(1);
                        String tokenName = (String) m.getParameter(2);
                        PlayerToken pt = new PlayerToken(tokenName, mLogic.getCurrentTurn().getPlayer(), points);
                        mLogic.getCurrentTurn().getPlayer().getTokens().add(pt);
                        mLogic.getCurrentTurn().putInitialToken(pt, mLogic.getBoard().getTileSet().get(tileName));
                    }
                    break;

                    case GameTurn.STEP_INITIAL_BOAT_PUT: {
                        String tileName = (String) m.getParameter(0);
                        String boatName = (String) m.getParameter(1);
                        mLogic.getCurrentTurn().putInitialBoat(boatName,
                                mLogic.getBoard().getTileSet().get(tileName));
                    }
                    break;

                    case GameTurn.STEP_MOVE_ENTITY: {
                        String entName = (String) m.getParameter(0);
                        boolean isBoat = (Boolean) m.getParameter(1);
                        String tileOrBoatName = (String) m.getParameter(2);
                        log("Moving entity " + entName + " to tile or boat " + tileOrBoatName);

                        if (isBoat) {
                            // On ne peut que bouger un PlayerToken sur un bateau
                            PlayerToken pt = (PlayerToken) mLogic.getBoard().getEntity(entName);
                            Boat b = (Boat) mLogic.getBoard().getEntity(tileOrBoatName);
                            pt.setState(PlayerToken.STATE_ON_BOAT);
                            pt.setBoat(b);
                            b.addPlayer(pt);
                            mLogic.getCurrentTurn().moveEntity(pt, b);
                        } else {
                            mLogic.getCurrentTurn().moveEntity(mLogic.getBoard().getEntity(entName),
                                    mLogic.getBoard().getTileSet().get(tileOrBoatName));
                        }
                    }
                    break;

                    case GameTurn.STEP_MOVE_DICE_ENTITY: {
                        String entName = (String) m.getParameter(0);
                        String tileName = (String) m.getParameter(1);
                        mLogic.getCurrentTurn().moveEntity(mLogic.getBoard().getEntity(entName),
                                mLogic.getBoard().getTileSet().get(tileName));
                    }
                    break;

                    case GameTurn.STEP_SINK_TILE: {
                        String tileName = (String) m.getParameter(0);
                        mLogic.getCurrentTurn().sinkLandTile(mLogic.getBoard().getTileSet().get(tileName));
                    }
                    break;
                }
                return null;
            }
        });
    }

    public void handleMessageNextTurn(final MessageNextTurn m) {
        mLogic.getRenderer().runOnMainThread(new Callable<Void>() {
            public Void call() throws Exception {
                mLogic.nextTurn();
                return null;
            }
        });
    }

    public void handleMessageRollDice(final MessageRollDice m) {
        mLogic.getRenderer().runOnMainThread(new Callable<Void>() {
            public Void call() throws Exception {
                mLogic.onDiceRoll(m.getDiceAction());
                return null;
            }
        });
    }

    public void handleMessageRemoteTile(final MessageRemoteTile m) {
        mLogic.getRenderer().runOnMainThread(new Callable<Void>() {
            public Void call() throws Exception {
                Player playuse = mLogic.getPlayers()[m.getPlayerNumber()];
                mLogic.getCurrentTurn().useRemoteTile(playuse, m.getTileAction());
                return null;
            }
        });
    }
}

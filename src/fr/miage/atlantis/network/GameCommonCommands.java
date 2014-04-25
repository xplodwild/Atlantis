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

import com.jme3.network.serializing.Serializer;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.logic.GameTurn;
import fr.miage.atlantis.network.messages.MessageChat;
import fr.miage.atlantis.network.messages.MessageGameStart;
import fr.miage.atlantis.network.messages.MessageKthxbye;
import fr.miage.atlantis.network.messages.MessageNextTurn;
import fr.miage.atlantis.network.messages.MessageOhai;
import fr.miage.atlantis.network.messages.MessagePlayerJoined;
import fr.miage.atlantis.network.messages.MessageSyncBoard;
import fr.miage.atlantis.network.messages.MessageTurnEvent;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Méthodes de jeu communes en réseau
 */
public class GameCommonCommands {

    private Game3DLogic mLogic;

    static {
        Serializer.registerClass(MessageOhai.class);
        Serializer.registerClass(MessageKthxbye.class);
        Serializer.registerClass(MessageChat.class);
        Serializer.registerClass(MessagePlayerJoined.class);
        Serializer.registerClass(MessageNextTurn.class);
        Serializer.registerClass(MessageGameStart.class);
        Serializer.registerClass(MessageSyncBoard.class);
        Serializer.registerClass(MessageTurnEvent.class);
    }

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
                        PlayerToken pt = new PlayerToken(mLogic.getCurrentTurn().getPlayer(), points);
                        log("FROM NETWORK: INITIAL PLAYER PUT FOR PLAYER " + mLogic.getCurrentTurn().getPlayer().getNumber());
                        mLogic.getCurrentTurn().getPlayer().getTokens().add(pt);
                        mLogic.getCurrentTurn().putInitialToken(pt, mLogic.getBoard().getTileSet().get(tileName));
                    }
                    break;

                    case GameTurn.STEP_INITIAL_BOAT_PUT: {
                        String tileName = (String) m.getParameter(0);
                        mLogic.getCurrentTurn().putInitialBoat(mLogic.getBoard().getTileSet().get(tileName));
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
}

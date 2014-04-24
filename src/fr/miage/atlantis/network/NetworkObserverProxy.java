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

import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.network.messages.MessageGameStart;
import fr.miage.atlantis.network.messages.MessageSyncBoard;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Observateur d'événements pour les parties réseau
 */
public class NetworkObserverProxy {

    private static final NetworkObserverProxy INSTANCE = new NetworkObserverProxy();

    private GameHost mHost;
    private GameClient mClient;

    private NetworkObserverProxy() {

    }

    public static NetworkObserverProxy getDefault() {
        return INSTANCE;
    }

    /**
     * Renvoie si oui ou non la partie en cours est une partie réseau
     * @return true si la partie est en réseau
     */
    public boolean isNetworkGame() {
        return isHost() || isClient();
    }
    
    /**
     * Renvoie true si cet ordinateur est hôte d'une partie réseau
     * @return boolean
     */
    public boolean isHost() {
        return mHost != null;
    }
    
    /**
     * Renvoie true si cet ordinateur est client d'une partie réseau
     * @return 
     */
    public boolean isClient() {
        return mClient != null;
    }

    public void setHost(GameHost host) {
        mHost = host;
    }

    public GameHost getHost() {
        return mHost;
    }

    public void setClient(GameClient client) {
        mClient = client;
    }

    public GameClient getClient() {
        return mClient;
    }

    /**
     * Ferme le client et le serveur, si existe
     */
    public void tearDown() {
        if (mClient != null) {
            mClient.close();
            mClient = null;
        }

        if (mHost != null) {
            mHost.stop();
            mHost = null;
        }
    }

    public void onHostBoardSync(GameBoard board) {
        MessageSyncBoard msg = new MessageSyncBoard();
        try {
            msg.writeBoard(board);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Cannot write board to network message", ex);
        }

        mHost.broadcast(msg);
    }

    public void onHostGameStart() {
        MessageGameStart msg = new MessageGameStart();
        mHost.broadcast(msg);
    }
}

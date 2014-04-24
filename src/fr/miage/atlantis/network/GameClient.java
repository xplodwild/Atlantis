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

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.network.messages.MessageGameStart;
import fr.miage.atlantis.network.messages.MessageOhai;
import java.io.IOException;

/**
 * Client pour le jeu en réseau
 */
public class GameClient implements ClientStateListener, MessageListener {
    
    private GameLogic mLogic;
    private Client mClient;
    
    public GameClient(GameLogic logic) {
        mLogic = logic;
    }
    
    public void connect(final String ipAddress) throws IOException {
        mClient = Network.connectToServer(ipAddress, GameHost.DEFAULT_PORT);
        
        mClient.addClientStateListener(this);
        mClient.addMessageListener(this);
        
        mClient.start();
    }

    public void clientConnected(Client c) {
        
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
        
    }

    public void messageReceived(Object source, Message m) {
        if (m instanceof MessageOhai) {
            // TODO: Connexion acceptée, afficher le lobby
        } else if (m instanceof MessageGameStart) {
            // TODO: Le jeu commence, il faut passer vers le board
        }
    }

    public Client getClient() {
        return this.mClient;
    }
    
    
    
    
}

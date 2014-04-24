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

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import fr.miage.atlantis.gui.controllers.GuiController;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.network.messages.MessageChat;
import fr.miage.atlantis.network.messages.MessageGameStart;
import fr.miage.atlantis.network.messages.MessageKthxbye;
import fr.miage.atlantis.network.messages.MessageNextTurn;
import fr.miage.atlantis.network.messages.MessageOhai;
import fr.miage.atlantis.network.messages.MessageSyncBoard;
import fr.miage.atlantis.network.messages.MessageTurnEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Classe s'occupant d'héberger la partie réseau
 */
public class GameHost implements ConnectionListener, MessageListener<HostedConnection> {

    public static final int DEFAULT_PORT = 8198;

    private Server mServer;
    private List<HostedConnection> mConnections;
    private Map<Integer, String> mConnectionToName;
    private GameLogic mLogic;
    private GuiController mGuiController;

    static {
        Serializer.registerClass(MessageOhai.class);
        Serializer.registerClass(MessageKthxbye.class);
        Serializer.registerClass(MessageChat.class);
        Serializer.registerClass(MessageNextTurn.class);
        Serializer.registerClass(MessageGameStart.class);
        Serializer.registerClass(MessageSyncBoard.class);
        Serializer.registerClass(MessageTurnEvent.class);
    }

    public GameHost(GameLogic logic, GuiController guiController) {
        mConnections = new ArrayList<HostedConnection>();
        mConnectionToName = new HashMap<Integer, String>();
        mLogic = logic;
        mGuiController = guiController;
    }

    public void startListening() throws IOException {
        mServer = Network.createServer(DEFAULT_PORT);
        mServer.addConnectionListener(this);
        mServer.addMessageListener(this);

        mServer.start();

        NetworkObserverProxy.getDefault().setHost(this);
    }

    public void stop() {
        mServer.close();
    }

    public void broadcast(Message msg) {
        mServer.broadcast(msg);
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        if (mConnections.size() == 4) {
            // Maximum 4 joueurs!
            conn.close("This server is full, sorry!");
        }
        mConnections.add(conn);
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        mConnections.remove(conn);
    }

    public void messageReceived(HostedConnection source, Message m) {
        if (m instanceof MessageOhai) {
            // Un joueur s'est connecté
            handleMessageOhai(source.getId(), (MessageOhai) m);
        } else if (m instanceof MessageKthxbye) {
            // Un joueur s'est déconnecté
            handleMessageKthxbye(source.getId(), (MessageKthxbye) m);
        } else if (m instanceof MessageChat) {
            // Un joueur a envoyé un message dans le chat
            handleMessageChat(source.getId(), (MessageChat) m);
        } else if (m instanceof MessageTurnEvent) {
            // Un événement du tour vient d'arriver
            handleMessageTurnEvent(source.getId(), (MessageTurnEvent) m);
        }
    }

    private void handleMessageOhai(int sourceId, MessageOhai m) {
        Logger.getGlobal().info("Network player connected: " + m.getName());
        mConnectionToName.put(sourceId, m.getName());

        // TODO: Notifier le GUI qu'un joueur s'est connecté
        // Insérer le nom du joueur dans la logique
        mGuiController.onPlayerConnected(m.getName());
    }

    private void handleMessageKthxbye(int sourceId, MessageKthxbye m) {
        Logger.getGlobal().info("Network player disconnected: " + mConnectionToName.get(sourceId));
        mConnectionToName.remove(sourceId);
    }

    private void handleMessageChat(int sourceId, MessageChat m) {
        String playerName = mConnectionToName.get(sourceId);
        String message = ((MessageChat) m).getMessage();
        // TODO: Afficher dans le chat!
    }

    private void handleMessageTurnEvent(int sourceId, MessageTurnEvent m) {
        // TODO
    }


}

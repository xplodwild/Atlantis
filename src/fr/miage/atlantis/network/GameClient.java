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
import com.jme3.network.serializing.Serializer;

import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.gui.controllers.GuiController;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.network.messages.MessageChat;
import fr.miage.atlantis.network.messages.MessageGameStart;
import fr.miage.atlantis.network.messages.MessageKthxbye;
import fr.miage.atlantis.network.messages.MessageNextTurn;
import fr.miage.atlantis.network.messages.MessageOhai;
import fr.miage.atlantis.network.messages.MessagePlayerJoined;
import fr.miage.atlantis.network.messages.MessageRemoteTile;
import fr.miage.atlantis.network.messages.MessageRollDice;
import fr.miage.atlantis.network.messages.MessageSyncBoard;
import fr.miage.atlantis.network.messages.MessageTurnEvent;
import java.io.IOException;

import java.util.concurrent.Callable;

import java.util.logging.Logger;

/**
 * Client pour le jeu en réseau
 */
public class GameClient implements ClientStateListener, MessageListener {

    private Game3DLogic mLogic;
    private String mPlayerName;
    private Client mClient;
    private GuiController mGuiController;
    private GameCommonCommands mCommon;

    static {
        Serializer.registerClass(MessageOhai.class);
        Serializer.registerClass(MessageKthxbye.class);
        Serializer.registerClass(MessageChat.class);
        Serializer.registerClass(MessagePlayerJoined.class);
        Serializer.registerClass(MessageRollDice.class);
        Serializer.registerClass(MessageNextTurn.class);
        Serializer.registerClass(MessageGameStart.class);
        Serializer.registerClass(MessageSyncBoard.class);
        Serializer.registerClass(MessageTurnEvent.class);
        Serializer.registerClass(MessageRemoteTile.class);
    }

    /**
     *
     * @param logic
     * @param gui
     */
    public GameClient(GameLogic logic, GuiController gui) {

        mLogic = (Game3DLogic) logic;

        mGuiController = gui;
        mCommon = new GameCommonCommands(mLogic);
    }

    /**
     *
     * @param ipAddress
     * @param name
     * @throws IOException
     */
    public void connect(final String ipAddress, final String name) throws IOException {
        mPlayerName = name;
        mClient = Network.connectToServer(ipAddress, GameHost.DEFAULT_PORT);

        mClient.addClientStateListener(this);
        mClient.addMessageListener(this);

        mClient.start();

        NetworkObserverProxy.getDefault().setClient(this);
    }

    /**
     *
     * @param msg
     */
    public void send(Message msg) {
        mClient.send(msg);
    }

    /**
     *
     */
    public void close() {
        mClient.close();
    }

    public void clientConnected(Client c) {
        // On envoie le nom
        MessageOhai ohai = new MessageOhai(mPlayerName);
        mClient.send(ohai);
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {

    }

    /**
     *
     * @param source
     * @param m
     */
    public void messageReceived(Object source, Message m) {
        if (m instanceof MessageOhai) {
        } else if (m instanceof MessagePlayerJoined) {
            handleMessagePlayerJoined((MessagePlayerJoined) m);
        } else if (m instanceof MessageGameStart) {
            handleMessageGameStart((MessageGameStart) m);
        } else if (m instanceof MessageSyncBoard) {
            handleMessageSyncBoard((MessageSyncBoard) m);

        } else if (m instanceof MessageNextTurn) {
            handleMessageNextTurn((MessageNextTurn) m);
        } else if (m instanceof MessageTurnEvent) {
            handleMessageTurnEvent((MessageTurnEvent) m);
        } else if (m instanceof MessageRollDice) {
            handleMessageRollDice((MessageRollDice) m);
        } else if (m instanceof MessageRemoteTile) {
            handleMessageRemoteTile((MessageRemoteTile) m);
        }
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return this.mClient;
    }

    private void log(final String msg) {
        Logger.getGlobal().info(msg);
    }

    private void handleMessagePlayerJoined(MessagePlayerJoined m) {
        log("Player joined game: " + m.getName());
        mGuiController.onPlayerConnected(m.getName());

        if (m.getName().equals(mPlayerName)) {
            NetworkObserverProxy.getDefault().setPlayerNumber(m.getNumber());
        }

    }

    private void handleMessageGameStart(MessageGameStart m) {
        log("Game is starting!");
        mGuiController.onRemoteGameStart();
    }

    private void handleMessageSyncBoard(final MessageSyncBoard m) {
        log("Host is sending the board!");
        mLogic.getRenderer().runOnMainThread(new Callable<Void>() {
            public Void call() throws Exception {
                // We (re)prepare the game
                mLogic.prepareGame(mGuiController.getPlayers(), false);

                try {
                    m.readBoard(mLogic);
                } catch (IOException ex) {
                    log("Error!");
                }
                return null;
            }
        });
    }

    private void handleMessageNextTurn(MessageNextTurn m) {
        log("Next turn: " + m.getPlayerNumber());
        mCommon.handleMessageNextTurn(m);
    }

    private void handleMessageTurnEvent(final MessageTurnEvent m) {
        log("Turn event: " + m.getEvent());
        mCommon.handleMessageTurnEvent(m);
    }

    private void handleMessageRollDice(final MessageRollDice m) {
        log("Roll dice: " + m.getDiceAction());
        mCommon.handleMessageRollDice(m);
    }

    private void handleMessageRemoteTile(final MessageRemoteTile m) {
        log("Remote tile used by " + m.getPlayerNumber());
        mCommon.handleMessageRemoteTile(m);
    }
}

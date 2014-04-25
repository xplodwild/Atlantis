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
package fr.miage.atlantis.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message lors de la connexion d'un autre joueur
 * (vide)
 */
@Serializable
public class MessagePlayerJoined extends AbstractMessage {
    private String mNickname;
<<<<<<< HEAD
=======
    private int mNumber;
>>>>>>> origin/raie-zo

    public MessagePlayerJoined() {

    }

<<<<<<< HEAD
    public MessagePlayerJoined(final String name) {
        mNickname = name;
=======
    public MessagePlayerJoined(final String name, final int number) {
        mNickname = name;
        mNumber = number;
>>>>>>> origin/raie-zo
    }

    public String getName() {
        return mNickname;
    }
<<<<<<< HEAD
=======

    public int getNumber() {
        return mNumber;
    }
>>>>>>> origin/raie-zo
}

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
import java.util.ArrayList;
import java.util.List;

/**
 * Message indiquant que c'est au joueur suivant
 * (vide)
 */
@Serializable
public class MessageTurnEvent extends AbstractMessage {
    public static final int TURN_EVENT_PUT_INITIAL_TOKEN    = 0;
    public static final int TURN_EVENT_MOVE_ENTITY          = 1;
    public static final int TURN_EVENT_MOVE_DICE_ENTITY     = 2;
    public static final int TURN_EVENT_ROLL_DICE            = 3;
    public static final int TURN_EVENT_TILE_ACTION_TELEPORT = 4;
    public static final int TURN_EVENT_TILE_ACTION_BONUS    = 5;
    public static final int TURN_EVENT_SINK_TILE            = 6;

    private int mEvent;
    private List<Object> mParameters;

    public MessageTurnEvent() {
        
    }
    
    public MessageTurnEvent(int event) {
        mEvent = event;
        mParameters = new ArrayList<Object>();
    }

    public void addParameter(Object param) {
        mParameters.add(param);
    }

    public int getEvent() {
        return mEvent;
    }

    public Object getParameter(int i) {
        return mParameters.get(i);
    }

}

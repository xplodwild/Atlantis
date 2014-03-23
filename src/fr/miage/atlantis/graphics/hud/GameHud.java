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

package fr.miage.atlantis.graphics.hud;

/**
 *
 */
public class GameHud {

    private HudManager mHudManager;
    private AbstractDisplay mRightClickToCancel;

    public GameHud(HudManager man) {
        mHudManager = man;
        setup();
    }

    private void setup() {
        mRightClickToCancel = new AbstractDisplay(52, 75, "RightClick Cancel Hint",
                mHudManager.getAssetManager());
        mRightClickToCancel.showImage("Interface/HintRightClickCancel.png");
        mRightClickToCancel.setAlpha(0.0f);
        mHudManager.displayBottomRight(mRightClickToCancel);
    }

    public void showRightClickHint() {
        mHudManager.getAnimator().animateFade(mRightClickToCancel, 1.0f);
    }

    public void hideRightClickHint() {
        mHudManager.getAnimator().animateFade(mRightClickToCancel, 0.5f);
    }
}

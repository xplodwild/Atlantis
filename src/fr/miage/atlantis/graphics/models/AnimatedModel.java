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

package fr.miage.atlantis.graphics.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;

/**
 *
 */
public class AnimatedModel extends StaticModel {

    private final static float BLEND_TIME = 0.3f;

    private AnimControl mControl;
    private AnimChannel mChannel;

    public AnimatedModel(AssetManager am, String meshName,
            String diffusePath, String normalPath) {
        super(am, meshName, diffusePath, normalPath);

        mControl = getModel().getControl(AnimControl.class);
        mChannel = mControl.createChannel();
    }

    public void playAnimation(String animation) {
        playAnimation(animation, true, null);
    }

    public void playAnimation(String animation, boolean loop, AnimEventListener listener) {
        mChannel.setLoopMode(loop ? LoopMode.Loop : LoopMode.DontLoop);
        mChannel.setAnim(animation, BLEND_TIME);

        if (listener != null) {
            mChannel.getControl().addListener(listener);
        }

        if (loop) {
            // Pour avoir moins l'air copier-coller, les animations loopées
            // sont légèrement décalées. Ainsi, les modèles similaires avec
            // la même animation ne seront pas tous identiques.
            mChannel.setTime((float) Math.random() * mChannel.getAnimMaxTime());
        }
    }

    public void printAnimations() {
        for (String anim : mControl.getAnimationNames()) {
            System.out.println(anim);
        }
    }

}

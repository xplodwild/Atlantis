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
import com.jme3.animation.SkeletonControl;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import fr.miage.atlantis.graphics.AnimationBrain;
import fr.miage.atlantis.graphics.BoneAttachControl;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 *
 */
public class AnimatedModel extends StaticModel {

    public final static String DATA_IS_CUSTOM_COLMODEL = "is_custom_colmodel";

    private final static float BLEND_TIME = 0.2f;

    private AnimControl mControl;
    private AnimChannel mChannel;

    public AnimatedModel(AssetManager am, String meshName,
            String diffusePath, String normalPath) {
        super(am, meshName, diffusePath, normalPath);

        mControl = getModel().getControl(AnimControl.class);
        mChannel = mControl.createChannel();

        // On active l'hardware skinning
        SkeletonControl skeletonControl = getModel().getControl(SkeletonControl.class);
        skeletonControl.setHardwareSkinningPreferred(true);
    }

    public void playAnimation(String animation) {
        playAnimation(animation, true, true, null);
    }

    public void playAnimation(final String animation, boolean loop, boolean animateTransition,
            final AnimEventListener listener) {
        mChannel.setLoopMode(loop ? LoopMode.Loop : LoopMode.DontLoop);
        mChannel.setAnim(animation, animateTransition ? BLEND_TIME : 0.0f);

        if (listener != null) {
            mChannel.getControl().addListener(listener);
            ///////////////////////////
            // WORKAROUND FOR BUGGED JMONKEYENGINE 3.0.7
            // SEE http://hub.jmonkeyengine.org/forum/topic/onanimcycledone-not-call/
            ///////////////////////////
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                     listener.onAnimCycleDone(mChannel.getControl(), mChannel, animation);
                }
            }, (long) mChannel.getControl().getAnimationLength(animation) * 1000);
        }

        if (loop) {
            // Pour avoir moins l'air copier-coller, les animations loopées
            // sont légèrement décalées. Ainsi, les modèles similaires avec
            // la même animation ne seront pas tous identiques.
            mChannel.setTime((float) Math.random() * mChannel.getAnimMaxTime());
        }
    }

    public void playAnimation(AnimationBrain.State state, AnimEventListener listener) {
        if (state != null && state.animationName != null) {
            playAnimation(state.animationName, state.loop, state.animateTransition, listener);
            getModelNode().setLocalRotation(new Quaternion(new float[]{
                0.0f, state.yOffset * 3.1415926f / 180.0f, 0.0f
            }));
        }
    }

    public void playAnimation(AnimationBrain.State state) {
        playAnimation(state, null);
    }

    public void printAnimations() {
        for (String anim : mControl.getAnimationNames()) {
            System.out.println(anim);
        }
    }

    /**
     * Met en place la Geometry de collision placée en paramètre, et la colle automatiquement
     * au bone nommé selon la variable boneName.
     * Cette fonction se charge d'attacher automatiquement 'shape' a la scène, et à lui définir
     * le bon UserData.
     * @param shape La géométrie de collision à utiliser
     * @param boneName Le nom du bone auquel attacher la géométrie
     */
    protected void setupCustomCollisionShape(final Geometry shape, final String boneName) {
        // On a besoin d'une shape custom pour les collisions (picking souris) sur ce model.
        // On attache la geometry à un bone, et on pick celle-ci
        getModel().addControl(new BoneAttachControl(boneName, shape));
        getModelNode().attachChild(shape);
        shape.setUserData(DATA_IS_CUSTOM_COLMODEL, getModel());
    }
}

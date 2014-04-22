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
package fr.miage.atlantis.graphics;

import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.animation.SkeletonControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 * Control permettant, lorsqu'il est attaché à un noeud (exemple un
 * PlayerModel), de mettre à jour un autre spatial en fonction de la position
 * d'un Bone du modèle.
 */
public class BoneAttachControl extends AbstractControl implements Savable, Cloneable {

    private String mBoneName;
    private Geometry mChild;

    public BoneAttachControl() {
    } // empty serialization constructor

    public BoneAttachControl(final String boneName, final Geometry child) {
        mBoneName = boneName;
        mChild = child;
    }

    public void setBoneName(final String boneName) {
        mBoneName = boneName;
    }

    public void setChild(final Geometry child) {
        mChild = child;
    }

    /**
     * Cette méthode est appelée lorsque le Control est ajouté au noeud, et
     * quand il est retiré du noeud (en définissant null).
     */
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    /**
     * Implémentation du Control
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (spatial != null) {
            SkeletonControl skeletonControl = getSpatial().getControl(SkeletonControl.class);
            Skeleton skeleton = skeletonControl.getSkeleton();
            Bone bone = skeleton.getBone(mBoneName);
            if (bone != null) {
                mChild.setLocalTranslation(bone.getModelSpacePosition());
                mChild.setLocalRotation(bone.getModelSpaceRotation());
            } else {
                throw new IllegalArgumentException("Cannot find bone '" + mBoneName + "' for BoneAttachControl");
            }
        }
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final BoneAttachControl control = new BoneAttachControl();
        control.setBoneName(mBoneName);
        control.setChild(mChild);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        /* Optional: rendering manipulation (for advanced users) */
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        throw new UnsupportedOperationException("BoneAttachControl serialization isn't implemented");
        // im.getCapsule(this).read(...);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        throw new UnsupportedOperationException("BoneAttachControl serialization isn't implemented");
        // ex.getCapsule(this).write(...);
    }
}
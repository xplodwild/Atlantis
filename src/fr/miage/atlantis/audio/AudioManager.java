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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package fr.miage.atlantis.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe gérant les sons du jeu
 */
public class AudioManager {

    private static final AudioManager INSTANCE = new AudioManager();
    private AssetManager mAssetManager;
    private Node mRootNode;
    private AudioNode mMainMusicNode;
    private AudioNode mAmbienceNode;
    private Map<String, AudioNode> mKnownNodes;

    /**
     * Constructeur de AudioManager
     */
    private AudioManager() {
        mKnownNodes = new HashMap<String, AudioNode>();
    }

    /**
     * Retourne une instance d'AudioManager en cours
     *
     * @return retourne l'instance d'AudioManager active.
     */
    public static AudioManager getDefault() {
        return INSTANCE;
    }

    /**
     * Initialise un AudioManager
     *
     * @param am asset pour retrouver les fichiers
     * @param rootNode node concerné
     */
    public void initialize(AssetManager am, Node rootNode) {
        mAssetManager = am;
        mRootNode = rootNode;
    }

    /**
     * Met à jour le gestionnaire audio à chaque frame. Appelé automatiquement
     * par Game3DRenderer
     *
     * @param timeDelta Temps en secondes depuis le dernier appel
     */
    public void update(float timeDelta) {
        //TODO : Pas utilisé pour le moment
        //Permet les fondus enchainésentre 2 sons
    }

    /**
     * Lit ou arrête la lecture de la musique principale
     *
     * @param playing true pour lire, false pour arrêter
     */
    public void setMainMusic(boolean playing) {
        if (mMainMusicNode == null) {
            mMainMusicNode = new AudioNode(mAssetManager, AudioConstants.Path.MAIN_MUSIC, false);
            mMainMusicNode.setPositional(false);
            mMainMusicNode.setLooping(true);
            mRootNode.attachChild(mMainMusicNode);
        }

        if (playing) {
            mMainMusicNode.setTimeOffset(0.0f);
            mMainMusicNode.play();
        } else {
            mMainMusicNode.pause();
        }
    }

    /**
     * Lit ou arrête la lecture de l'ambiance audio "eau" de fond
     *
     * @param playing true pour lire, false pour arrêter
     */
    public void setAmbience(boolean playing) {
        if (mAmbienceNode == null) {
            mAmbienceNode = new AudioNode(mAssetManager, AudioConstants.Path.AMBIENCE, false);
            mAmbienceNode.setPositional(false);
            mAmbienceNode.setLooping(true);
            mRootNode.attachChild(mAmbienceNode);
        }

        if (playing) {
            mAmbienceNode.setTimeOffset(0.0f);
            mAmbienceNode.play();
        } else {
            mAmbienceNode.pause();
        }
    }

    /**
     * Lis le song indiqué en chemin et renvoie une node
     *
     * @param path Le chemin du fichier
     */
    public AudioNode playSound(final String path) {
        return playSound(path, false);
    }

    /**
     * Lis le song indiqué en chemin et renvoie une node
     *
     * @param path Le chemin du fichier
     * @param loop true pour lire en boucle
     */
    public AudioNode playSound(final String path, final boolean loop) {
        AudioNode node = mKnownNodes.get(path);
        if (node == null) {
            node = new AudioNode(mAssetManager, path, false);
            node.setPositional(false);
            node.setLooping(loop);
            mRootNode.attachChild(node);
            mKnownNodes.put(path, node);
        }

        node.setTimeOffset(0.0f);
        node.play();

        return node;
    }

    /**
     * Arrête la lecture de la node passée en paramètre.
     *
     * @param node Le noeud audio à arrêter (ce que renvoie playSound)
     */
    public void stopSound(final AudioNode node) {
        node.pause();
    }
}

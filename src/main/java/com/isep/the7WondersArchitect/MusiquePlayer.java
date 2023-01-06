package com.isep.the7WondersArchitect;

import com.isep.MainApplication;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusiquePlayer {

    /* ______ */
    /* Controle de la musique */
    /* ______ */

    private MediaPlayer mediaPlayer;

    private final List<String> songs = Arrays.asList(
            "Christophe-Espern_Cindy-Oasis_SACEM.mp3",
            "Christophe-Espern_Cindy-Sahara_SACEM.mp3",
            "Christophe-Espern_Welcome-to-Persia_SACEM.mp3",
            "MrKey_Chers-Amis_LMK.mp3"
    );


    public void playMedia() {
        String nameMusique = songs.get(new Random().nextInt(songs.size()));
        URL url = MainApplication.class.getResource("musiques/" + nameMusique);
        assert url != null;
        Media media = new Media(url.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void stopMedia() {
        mediaPlayer.stop();
    }

}

package audio;

import core.GameSettings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayer {

    private List<AudioClip> audioClipList;

    public AudioPlayer() {
        audioClipList = new ArrayList<>();
    }

    public void update(GameSettings gameSettings){
        audioClipList.forEach(audioClip -> audioClip.update(gameSettings));

        List.copyOf(audioClipList).forEach(audioClip -> {
            if(audioClip.hasFinishedPlaying()){
                audioClip.cleanUp();
                audioClipList.remove(audioClip);
            }
        });
    }

    public void playMusic(String fileName){
        final Clip clip = getClip(fileName);
        audioClipList.add(new MusicClip(clip));
    }

    public void playSound(String fileName){
        final Clip clip = getClip(fileName);
        audioClipList.add(new SoundClip(clip));
    }

    public void playMeleeAttackSound(boolean isCrit){
        double choice;
        String soundChoice;

        if (isCrit == false){
            //Human attack grunt sound
            choice = (Math.random() * 30);

            if (choice < 1) {soundChoice = "HumanAttack1.wav";}
            else if (choice < 2) {soundChoice = "HumanAttack2.wav";}
            else if (choice < 3) {soundChoice = "HumanAttack3.wav";}
            else if (choice < 4) {soundChoice = "HumanAttack4.wav";}
            else if (choice < 5) {soundChoice = "HumanAttack5.wav";}
            else if (choice < 6) {soundChoice = "HumanAttack6.wav";}
            else if (choice < 7) {soundChoice = "HumanAttack7.wav";}
            else if (choice < 8) {soundChoice = "HumanAttack8.wav";}
            else if (choice < 9) {soundChoice = "HumanAttack9.wav";}
            else if (choice <= 10){soundChoice = "HumanAttack10.wav";}
            else soundChoice = null;

            if(soundChoice != null){
                final Clip clip = getClip(soundChoice);
                audioClipList.add(new SoundClip(clip));
            }

            //1h sword impact sound
            choice = (Math.random() * 3);
            if (choice < 1) {soundChoice = "1hSwordHitFlesh1A.wav";}
            else if (choice < 2) {soundChoice = "1hSwordHitFlesh1B.wav";}
            else if (choice <= 3) {soundChoice = "1hSwordHitFlesh1C.wav";}

            final Clip clip = getClip(soundChoice);
            audioClipList.add(new SoundClip(clip));
        } else {
            //Human attack grunt sound
            choice = (Math.random() * 30);

            if (choice < 1) {soundChoice = "HumanAttack1.wav";}
            else if (choice < 2) {soundChoice = "HumanAttack2.wav";}
            else if (choice < 3) {soundChoice = "HumanAttack3.wav";}
            else if (choice < 4) {soundChoice = "HumanAttack4.wav";}
            else if (choice < 5) {soundChoice = "HumanAttack5.wav";}
            else if (choice < 6) {soundChoice = "HumanAttack6.wav";}
            else if (choice < 7) {soundChoice = "HumanAttack7.wav";}
            else if (choice < 8) {soundChoice = "HumanAttack8.wav";}
            else if (choice < 9) {soundChoice = "HumanAttack9.wav";}
            else if (choice <= 10){soundChoice = "HumanAttack10.wav";}
            else soundChoice = null;

            if(soundChoice != null){
                final Clip clip = getClip(soundChoice);
                audioClipList.add(new SoundClip(clip));
            }

            //1h sword crit impact sound
            soundChoice = "1hSwordHitFleshCrit.wav";

            final Clip clip = getClip(soundChoice);
            audioClipList.add(new SoundClip(clip));
        }
    }

    public Clip getClip(String fileName){
        final URL soundFile = AudioPlayer.class.getResource("/sounds/" + fileName);
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)){
            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        return null;
    }
}

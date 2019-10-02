package Implement;

import Interface.CompactDisc;
import org.springframework.stereotype.Component;

@Component
public class SetPeppers implements CompactDisc {
    private String title = "Sgt Pepper's Lonely Hearts Club BAND";
    private String artist = "The Beatles";
    @Override
    public void play() {
        System.out.println("Playing " + this.title + " By " + this.artist);
    }
}

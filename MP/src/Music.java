import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
	
public class Music {
	AudioInputStream  audioIn;
	Clip music;
	public Music(File s) throws IOException{
		try {
			audioIn = AudioSystem.getAudioInputStream(s);
			music = AudioSystem.getClip();
			music.open(audioIn);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		music.start();
	}
	
	public void stop()
	{
		music.stop();
	}
	
	public void loop(int count) {
		if(count==99)
			music.loop(music.LOOP_CONTINUOUSLY);
		else
			music.loop(count);
	}
	
	/*public static void main(String []args) throws IOException {
		Music m2 = new Music(new File("1.wav"));
		m2.play();
		while(true){
		}
	}*/
	
	/* TO BE PLACED IN 		FRONTEND MAIN()
	try {
		Music sample = new Music(new File("Music.wav"));
		sample.play();
	} catch(IOException error) {}
	*/
}
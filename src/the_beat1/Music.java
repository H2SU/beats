package the_beat1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {//
	
	private Player player; //?λ°? μ€? ?Ό?΄λΈλ¬λ¦? μ€? ??
	private boolean isLoop;//??¬ κ³‘μ΄ λ¬΄νλ°λ³΅?Έμ§? ??Έ? ?? λ³??
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {//??±? ?΄?Ήκ³‘μ΄λ¦κ³Ό λ¬΄νλ°λ³΅?¬λΆ?λ₯? ??Όλ―Έν°λ‘? λ°λ?€
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI()); //music?΄? ?? ?΄?Ή?΄λ¦μ ??Ό? ?€? toURL()λ‘? κ·? ?μΉλ?? κ°?? Έ?€?λ‘? ?¨
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis); //?΄?Ή??Ό? λ²νΌ? ?΄?? ?½?΄?¬ ? ??λ‘ν¨
			player = new Player(bis); //?΄?Ή ??Ό? ?΄? ? ??λ‘? ?¨
		} catch (Exception e) { //?€λ₯μ²λ¦?
			System.out.println(e.getMessage());
		}
	}
	
	public int getTime() { //??¬ ?€? ???΄ ?΄?€ ?μΉμ? ?€??κ³? ??μ§? ?? €μ€? 0.001μ΄λ¨?λ‘? ?? €μ€? ?¨?΄μ§?? ?Έ?Έλ₯? λΆμ?? ?¨?
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	public void close() { //???΄ ?Έ?  ?€???μ§? μ’λ£??λ‘? ?? ?¨? 
		isLoop = false;   //?¬?©?κ°? ???€?μ€? ?€λ‘κ?κΈ°λ?? ?λ₯΄λ?Ό? ?΄?Ή κ³‘μ ?? ? ?Όλ‘? μ’λ£??λ‘? ?¨
		player.close();
		this.interrupt();  //?΄?Ή threadλ₯? μ€μ???λ‘? λ§λ¬
	}
	
	@Override
	public void run() { //threadλ₯? ??λ°μΌλ©? ?¬?©?΄?Ό?? ?¨?
		try {
			do {
				player.play(); //κ³‘μ€?
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop); //λ¬΄νλ°λ³΅κ²??¬
		} catch (Exception e) { //?€λ₯μ²λ¦?
			System.out.println(e.getMessage());
		}
	}

}

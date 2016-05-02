import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.Word;

import org.opencv.core.Rect;


public class TextReader {

	public static List<Word> readText(BufferedImage bi){
		List<Word> words = new ArrayList<Word>();
		Tesseract1 instance = new Tesseract1();  // JNA Interface Mapping
		instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_COLUMN);
		instance.setTessVariable("tessedit_char_blacklist","—");
		int pageIteratorLevel = TessPageIteratorLevel.RIL_TEXTLINE;
		List<Word> result = instance.getWords(bi, pageIteratorLevel);
		for(Word word : result){
			if(word.getText().matches("\\W+"))
				continue;
			if(word.getText().equals(" \n\n"))continue;
			if(word.getText().equals(" \n"))continue;
			words.add(word);
			System.out.println("word : " + word);
		}
		System.out.println("-----------------------------");
		if(words.isEmpty())
			return null;
		return words;
	}
	
//	public static List<Word> readText(BufferedImage bi, Rect roi){
//		List<Word> words = new ArrayList<Word>();
//		Tesseract1 instance = new Tesseract1();  // JNA Interface Mapping
//		instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
//		int pageIteratorLevel = TessPageIteratorLevel.RIL_PARA;
//		List<Word> result = instance.getWords(bi, pageIteratorLevel);
//		for(Word word : result){
//			if(word.getText().equals(" \n\n"))continue;
//			words.add(word);
//		}
//
//		return words;
//	}

}

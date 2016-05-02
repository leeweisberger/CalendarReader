import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.Word;


public class CalendarParser {

	public static List<CalendarEntry> parseCalendarEntries(List<Word> lines){
		List<CalendarEntry> entryList = new ArrayList<CalendarEntry>();

		String firstLine = lines.get(0).getText();
		int day = getDay(firstLine);
		if(day==-1){
			int x =6;
		}
		for(int i=1; i<lines.size(); i++){
			StringBuilder line = new StringBuilder(lines.get(i).getText());
			if(getTime(line.toString())==-1)
				continue;
			String time = line.substring(0, getTime(line.toString())+2);
			time=cleanTime(time);

			StringBuilder rest = new StringBuilder(line.substring(getTime(line.toString())+2, line.length()));
			//while we don't have a location ')' or a time go to the next line
			while(rest.indexOf(")")==-1 && i<lines.size()-1){
				//if the next line has a time, then there is no location!
				if(getTime(lines.get(i+1).getText())!=-1){
					break;
				}
				i++;
				rest.append(lines.get(i).getText());
			}
			String location="";
			//if we found a location ')', store the location and then delete it from the string
			if(rest.indexOf(")")!=-1 && rest.indexOf("(")!=-1){
				location = rest.substring(rest.indexOf("(")+1, rest.indexOf(")"));
				rest = rest.delete(rest.indexOf("("), rest.indexOf(")")+1);
			}

			String event = rest.toString();
			event = event.replaceAll("\\n", " ");
			location = location.replaceAll("\\n", "");

			CalendarEntry entry = new CalendarEntry(day, time.trim(), event.trim(),location.trim());
			entryList.add(entry);
		}
		return entryList;

	}

	private static int getTime(String line){
		line=cleanTime(line);
		return line.indexOf(".m.");


	}

	private static String cleanTime(String time) {
		time=time.replace("*", "");
		if(time.matches("(.)2(.)(.)")){
			time = time.replace('2', ':');
		}
		time = time.replace("am ", "a.m. ");
		time = time.replace("am.", "a.m.");
		time = time.replace("em.", "a.m.");
		time = time.replace("e.m.", "a.m.");
		time = time.replace("e.m ", "a.m. ");
		time = time.replace("a.m ", "a.m. ");
		time = time.replace("pm ", "p.m. ");
		time = time.replace("pm.", "p.m.");
		time = time.replace("p.m ", "p.m. ");
		return time;
	}

	private static int getDay(String firstLine){
		String[] words = firstLine.split(" ");
		for(String word : words){
			word = word.replaceAll("\\n", " ");
			word = word.replaceAll("O", "0");
			word = word.replaceAll("I", "1");
			word=word.trim();
			if(isNumeric(word))
				return Integer.parseInt(word);
		}
		return -1;
	}

	private static boolean isNumeric(String str)  
	{  
		try  
		{  
			int d = Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sourceforge.tess4j.Word;


public class CalendarParser {

	public static List<CalendarEntry> parseCalendarEntries(List<Word> lines){
		List<CalendarEntry> entryList = new ArrayList<CalendarEntry>();
		//the first line should contain the numbered day
		String firstLine = lines.get(0).getText();
		int day = getDay(firstLine);
		for(int i=1; i<lines.size(); i++){
			StringBuilder line = new StringBuilder(lines.get(i).getText());
			//In this calendar, a '-' represents the end of a time
			if(line.indexOf("-")==-1)
				continue;
			String time = line.substring(0, line.indexOf("-"));
			time=cleanTime(time);

			StringBuilder rest = new StringBuilder(line.substring(line.indexOf("-")+1, line.length()));
			//deal with events that span multiple lines
			while(rest.indexOf(")")==-1 && i<lines.size()-1){
				//if the next line has a time, then we are on the next event and there is no location!
				if(lines.get(i+1).getText().indexOf(-1)!=-1){
					break;
				}
				i++;
				rest.append(lines.get(i).getText());
			}
			
			String location="";
			//if we found a location ')', store the location and then delete it from the string
			if(rest.indexOf(")")!=-1){
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

	private static String cleanTime(String time) {
		time=time.replace("*", "");
		if(time.matches("(.)2(.)(.)")){
			time = time.replace('2', ':');
		}
		return time;
	}

	private static int getDay(String firstLine){
		String[] words = firstLine.split(" ");
		for(String word : words){
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

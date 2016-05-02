
public class CalendarEntry {
	private int day;
	private String time;
	private String event;
	private String location;
	

	public CalendarEntry(int day, String time, String event, String location) {
		this.day=day;
		this.time=time;
		this.event=event;
		this.location=location;
	}

	public int getDay() {
		return day;
	}

	public String getTime() {
		return time;
	}

	public String getEvent() {
		return event;
	}

	public String getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		return "day: " + day + " time: " + time + " event: " + event + " location: " + location;
	}
}

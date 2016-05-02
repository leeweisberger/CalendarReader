import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFileReader {
	public static final String WIDTH="min_rect_width";
	public static final String HEIGHT="min_rect_height";
	public static final String WIDTH_DIV_HEIGHT="max_width_div_height";
	public static final String MIN_CONTOUR="min_contour_area";

	
	public static Map<String, Integer> getProperties() {
		Map<String, Integer> propertiesMap = new HashMap<>();
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			propertiesMap.put(WIDTH, Integer.valueOf(prop.getProperty(WIDTH)));
			propertiesMap.put(HEIGHT, Integer.valueOf(prop.getProperty(HEIGHT)));
			propertiesMap.put(WIDTH_DIV_HEIGHT, Integer.valueOf(prop.getProperty(WIDTH_DIV_HEIGHT)));
			propertiesMap.put(MIN_CONTOUR, Integer.valueOf(prop.getProperty(MIN_CONTOUR)));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return propertiesMap;

	}
}

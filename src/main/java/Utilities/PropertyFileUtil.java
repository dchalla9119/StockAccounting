package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

import com.gargoylesoftware.css.dom.Property;

public class PropertyFileUtil {
public static String getValueForKey(String key) throws Throwable
{
	Properties config = new Properties();
	config.load(new FileInputStream("C:\\Users\\Dileep.Challa\\OneDrive\\Desktop\\eclipse december 2021\\ERP_MavenProject\\PropertyFile\\environment.properties"));
	return config.getProperty(key);
}
}

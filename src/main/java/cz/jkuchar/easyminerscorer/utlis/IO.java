package cz.jkuchar.easyminerscorer.utlis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

/**
 * IO utilities
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
public class IO {
	
	public static String streamToString(InputStream input) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line+"\n");
        }
        reader.close();
		return out.toString();		
	}

}

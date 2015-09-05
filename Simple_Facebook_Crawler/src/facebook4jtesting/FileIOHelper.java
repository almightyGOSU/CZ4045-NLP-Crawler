package facebook4jtesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** Borrowed from Algo lab codes.... **/
public class FileIOHelper
{
	
	// This will overwrite any existing file
	public static void writeToFile(String content, String fileName)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(fileName), false);

			fw.write(content);
			fw.close();

			System.out.println("Successfully saved results to \"" + fileName + "\".");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
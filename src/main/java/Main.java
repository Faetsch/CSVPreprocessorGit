import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        Preprocessor p = new Preprocessor("zeitpunkt", "aussentemp", "vorlauf", "rücklauf");
        p.readCSV("C:\\Users\\Fätsch\\IdeaProjects\\CSVPreprocessor\\src\\test\\resources\\test3");

        p.constructNewColumn("Temperaturspreizung", 2, 3, Preprocessor.DIFFERENCE);

        p.processSubstringTable(0, 0, 13);
        p.processIntervalTable(1, 10);
        p.processIntervalTable(2, 10);
        p.processIntervalTable(3, 10);

        p.writeCSV("C:\\Users\\Fätsch\\IdeaProjects\\CSVPreprocessor\\src\\test\\resources\\test3Difference");
    }
}

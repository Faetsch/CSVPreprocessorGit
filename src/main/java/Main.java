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
        p.readCSV("/home/thebrocc/Desktop/test3");
        p.processSubstringTable(0, 0, 10);
        p.processIntervalTable(1, 2);
        p.processIntervalTable(2, 2);
        p.processIntervalTable(3, 2);
        p.writeCSV("/home/thebrocc/Desktop/testMODIFIED");
    }
}

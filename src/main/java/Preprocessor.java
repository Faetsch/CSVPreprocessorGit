import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Preprocessor
{
    public static final int DIFFERENCE = 0;
    public static final int SUM = 1;
    public static final int DIVISION = 2;
    public static final int PRODUCT  = 3;

    private ArrayList<String> header = new ArrayList<String>();
    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();


    public Preprocessor(String... header)
    {
        Arrays.stream(header).forEach((s) -> this.header.add(s));
    }

    public String substringFrom(String s, int a, int b)
    {
        String sub = s.substring(Math.min(a, s.length()), Math.min(s.length(), b));
        return sub;
    }


    public void processSubstringTable(int column, int a, int b)
    {
        for(int i = 1; i < table.size(); i++)
        {
            table.get(i).set(column, substringFrom(table.get(i).get(column), a, b));
        }
    }

    public void processIntervalTable(int column, int intervals)
    {
        double min = Double.valueOf(table.get(1).get(column));
        double max = Double.valueOf(table.get(1).get(column));
        for(int i = 1; i < table.size(); i++)
        {
            double currEntry = Double.valueOf(table.get(i).get(column));
            if(currEntry > max) max=currEntry;
            if(currEntry < min) min=currEntry;
        }
        double intervalsize = (max - min)/intervals;
        for(int i = 1; i < table.size(); i++)
        {
            double currStart = min;
            double currEntry = Double.valueOf(table.get(i).get(column));
            while(currEntry > currStart + intervalsize)
            {
                currStart += intervalsize;
            }
            //System.out.println(currStart);
            // System.out.println(currStart + intervalsize);
            table.get(i).set(column, String.format("%f to %f", currStart, (currStart+intervalsize)));
        }

    }

    //TODO: non math operations base classes
    public void constructNewColumn(String name, int index1, int index2, int code)
    {
        header.add(name);
        table.get(0).add(name);
        int amountRows = table.size();


        double left;
        double right;
        for(int i = 1; i < amountRows; i++)
        {
            left = Double.parseDouble(table.get(i).get(index1));
            right = Double.parseDouble(table.get(i).get(index2));
            switch(code)
            {
                case DIFFERENCE:
                    table.get(i).add(String.valueOf(left - right));
                    break;

                case SUM:
                    table.get(i).add(String.valueOf(left + right));
                    break;

                case PRODUCT:
                    table.get(i).add(String.valueOf(left * right));
                    break;

                case DIVISION:
                    table.get(i).add(String.valueOf(left / right));
                    break;

            }
        }

    }



    public CSVParser readCSV(String path) throws IOException
    {
        File csvData = new File(path);
        System.out.println(csvData.getAbsolutePath());
        CSVParser parser = null;

        try
        {
            int headersize = header.size();
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for(CSVRecord record : parser.getRecords())
            {
                ArrayList<String> row = new ArrayList<String>();
                for(int i = 0; i < headersize; i++)
                {
                    row.add(record.get(i));
                }
                table.add(row);
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return parser;
    }

    public void writeCSV(String path) throws IOException
    {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        )
        {
            for(ArrayList<String> row : table)
            {
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
        }
    }
}

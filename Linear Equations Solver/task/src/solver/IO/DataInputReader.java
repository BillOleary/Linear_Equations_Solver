package solver.IO;

import java.io.*;

public class DataInputReader extends BufferedReader {

    private String fileIn;

    public DataInputReader(String fileIn) throws FileNotFoundException {
        super(new FileReader(fileIn));
        this.fileIn = fileIn;
    }

    @Override
    public String toString() {
        return null;
    }

}

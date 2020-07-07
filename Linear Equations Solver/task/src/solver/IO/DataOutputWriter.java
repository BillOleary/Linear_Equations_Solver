package solver.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class DataOutputWriter extends BufferedWriter {

    protected File fileOut;
    public DataOutputWriter(Writer out) {
        super(out);
    }

    public boolean dataWrite(String data) throws IOException {

        String[] s = data.replaceAll("\\[+|\\]+|,+", "").split(" ");
        for(String element : s) {
            this.write(element + "\n");
        }
        return true;
    }
}

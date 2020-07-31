package solver.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class DataOutputWriter extends BufferedWriter {

    enum Message {No_Solutions, Infinitely_many_solutions};

    protected File fileOut;
    public DataOutputWriter(Writer out) {
        super(out);
    }

    public boolean dataWrite(String data) throws IOException {

        if (data.matches("[No solutions|Infinitely many solutions]+")) {
            this.write(data);
        } else {
            String[] s = data.trim().split(" ");
            for (String element : s) {
                if (!element.matches("")) {
                    this.write(element + "\n");
                }
            }
        }
        return true;
    }
}

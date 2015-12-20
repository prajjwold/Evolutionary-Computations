/**
 *
 */
package com.cs572.assignments.Project2.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author prajjwol
 *
 */
public class GraphWriter {

    private final static String fileName = "input.gv";

    public static void writeContent(String content) {
        try {
            File file = new File(fileName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

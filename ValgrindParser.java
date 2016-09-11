/*
    ValgrindParser
    Copyright 2016 Peter Nirschl <peter.nirschl@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

*/

import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ValgrindParser
{
    private static void printUsage () {
        System.out.println("ValgrindParser <file 1> [<file 2> <file n>]");
    }

    private static void parseSuppressionsFromFile (String path, HashSet<String> suppressions) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            boolean inSuppressionMode = false;
            StringBuilder builder = new StringBuilder ();

            String line = reader.readLine();
            while(line != null) {
                if(line.startsWith("{")) {
                    inSuppressionMode = true;
                    // clear the suppressions buffer
                    builder.setLength(0);
                }

                if(inSuppressionMode) {
                    builder.append(line);
                    builder.append("\n");
                }

                if(line.startsWith("}")) {
                    inSuppressionMode = false;
                    String suppression = builder.toString ();
                    if(!suppressions.contains(suppression)) {
                        suppressions.add (suppression);
                    }
                }

                line = reader.readLine ();
            }

        } catch(IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // args[0] existing suppressions file
    // args[1] file to parse
    // output: diff (non-existing) suppressions
    public static void main (String[] args) {
        if(args.length < 1) {
            printUsage ();
            return;
        }

        HashSet<String> suppressions = new HashSet<String> ();

        // parse the files given by the arguments
        for(int i = 0; i < args.length; i++) {
            parseSuppressionsFromFile(args[i], suppressions);
        }

        // write out the resulting suppressions file
        for(String s : suppressions) {
            System.out.print(s);
        }
    }
}

# valgrind-parser

Parse valgrind logs for suppressions.

## How to Compile

Since it is only one source file simply use `javac`:

    javac ValgrindParser.java

## How to run

The tool takes at least one file as input and parses it for valgrind suppressions.
If more than one file is provided, the suppressions of each file are concatenated.

Every suppression is only written out once. Duplicates are ignored.

### Example

Let's say you have two valgrind logs in your current working directory:

- file1.log
- file2.log

You can call the `valgrind-parser` like this:

    java ValgrindParser file1.log file2.log

## What exactly is parsed?

The parser reads from the file until it finds a valgrind suppression, starting with "{" and ending with "}".
Everything outside the suppressions is ignored.

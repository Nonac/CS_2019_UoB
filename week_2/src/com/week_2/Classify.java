/* Print out the classification of a triangle, given three integer lengths
given on the command line. With no arguments, run the unit tests. */

class Classify {

    public static void main(String[] args) {
        Classify program = new Classify();
        program.run(args);
    }

    // Deal with the command line arguments
    void run(String[] args) {
        boolean testing = false;
        assert(testing = true);
        if (args.length == 0 && testing) test();
        else if (args.length == 3) classify(args);
        else usage();
    }

    // Classify a given triangle.
    void classify(String[] args) {
        int a = convert(args[0]), b = convert(args[1]), c = convert(args[2]);
        Triangle tr = new Triangle(a, b, c);
        System.out.println(tr.classify());
    }

    // Give a usage message and shut down.
    void usage() {
        System.err.println("Use:");
        System.err.println("  java -ea Classify");
        System.err.println("  java Classify a b c");
        System.exit(1);
    }

    // Convert a string into an int, with -1 for illegal input.
    int convert(String s) {
        return -1;
    }

    // ---------- Testing ----------

    // Run the tests
    void test() {
        testConvert();
        testNonDigits();
        testLimits();
    }

    // Check that convert works correctly.
    void testConvert() {
        assert(convert("0") == 0);
        assert(convert("123") == 123);
        assert(convert("-79") == -79);
    }

    // Check that convert returns -1 if there are non-digits, or if there is a
    // leading zero (because of ambiguity with octals).
    void testNonDigits() {
        assert(convert("x") == -1);
        assert(convert("x3") == -1);
        assert(convert("3x") == -1);
        assert(convert("3.0") == -1);
        assert(convert("03") == -1);
    }

    // Check that numbers are accepted only if within the int range
    void testLimits() {
        assert(convert("2147483647") == 2147483647);
        assert(convert("2147483648") == -1);
    }
}

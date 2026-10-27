import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

class PracticeRunner {
    public static void main(String[] args) throws Exception {
        if (Runtime.version().feature() < 21) {
            System.err.println("Use a full JDK 21 or later. JDK 21 matches the course environment.");
            System.exit(2);
        }
        if (args.length > 1 || (args.length == 1 && !args[0].matches("[A-Za-z][A-Za-z0-9]*"))) {
            System.err.println("Run bash run-tests.sh, optionally followed by one test class name.");
            System.exit(2);
        }
        Path root = Path.of("").toAbsolutePath().normalize();
        Path jar = root.resolve("lib/junit-platform-console-standalone-6.0.3.jar");
        if (!Files.isRegularFile(jar)) {
            System.err.println("Missing bundled JUnit library. Extract the complete starter again to a new folder.");
            System.exit(2);
        }
        String digest = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(jar)));
        if (!digest.equals("3ba0d6150af79214a1411f9ea2fbef864eef68b68c89a17f672c0b89bff9d3a2")) {
            System.err.println("The JUnit library does not match the supplied version. Use the original bundled library.");
            System.exit(2);
        }
        boolean win = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");
        Path bin = Path.of(System.getProperty("java.home"), "bin");
        String java = bin.resolve(win ? "java.exe" : "java").toString();
        String javac = bin.resolve(win ? "javac.exe" : "javac").toString();
        if (!Files.isRegularFile(Path.of(javac))) {
            System.err.println("The selected Java installation has no javac. Select a full JDK.");
            System.exit(2);
        }
        Files.createDirectories(root.resolve("build"));
        Path build = Files.createTempDirectory(root.resolve("build"), "run-");
        Files.createDirectories(root.resolve("results"));
        Path record = Files.createTempDirectory(root.resolve("results"), "run-");
        Path report = record.resolve("junit");
        Path console = record.resolve("console.txt");
        int result = 2;
        try (PrintWriter log = new PrintWriter(Files.newBufferedWriter(console, StandardCharsets.UTF_8))) {
            say(log, "EECS 3311 B: Workshop Registration, ungraded JUnit practice");
            say(log, "Run started: " + Instant.now());
            say(log, "Environment: " + System.getProperty("os.name") + ", Java " + Runtime.version());
            result = run(List.of(java, "-version"), log);
            if (result == 0) result = run(List.of(javac, "-version"), log);
            List<String> compile = new ArrayList<>(List.of(javac, "--release", "21", "-cp", jar.toString(), "-d", build.toString()));
            for (String dir : List.of("src", "test")) {
                try (Stream<Path> files = Files.walk(root.resolve(dir))) {
                    files.filter(p -> Files.isRegularFile(p) && p.toString().endsWith(".java"))
                        .sorted().forEach(p -> compile.add(p.toString()));
                }
            }
            if (result == 0) {
                say(log, "Compiling production and test sources into a fresh build folder.");
                result = run(compile, log);
            }
            if (result == 0) {
                List<String> command = new ArrayList<>(List.of(java, "-jar", jar.toString(), "execute", "--class-path", build.toString(),
                    "--include-engine", "junit-jupiter", "--disable-banner", "--disable-ansi-colors", "--details=tree", "--details-theme=ascii", "--fail-if-no-tests",
                    "--reports-dir", report.toString()));
                if (args.length == 1) command.addAll(List.of("--select-class", args[0]));
                else command.add("--scan-class-path");
                say(log, "Running JUnit Jupiter.");
                result = run(command, log);
            } else {
                say(log, "Compilation did not succeed. JUnit was not run for this build.");
            }
            say(log, "Runner exit code: " + result);
            say(log, "Actual output is saved in results.txt and " + root.relativize(console));
            say(log, "This command does not upload work.");
        }
        Files.copy(console, root.resolve("results.txt"), StandardCopyOption.REPLACE_EXISTING);
        Properties summary = new Properties();
        summary.setProperty("exit_code", Integer.toString(result));
        summary.setProperty("report_dir", root.relativize(report).toString());
        summary.setProperty("console", root.relativize(console).toString());
        try (Writer out = Files.newBufferedWriter(root.resolve("results/last-run.properties"))) { summary.store(out, "Actual latest run"); }
        System.exit(result);
    }
    private static int run(List<String> command, PrintWriter log) throws Exception {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        try (BufferedReader reader = process.inputReader(StandardCharsets.UTF_8)) {
            for (String line; (line = reader.readLine()) != null;) say(log, line);
        }
        return process.waitFor();
    }
    private static void say(PrintWriter log, String text) {
        System.out.println(text); log.println(text); log.flush();
    }
}

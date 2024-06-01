package ru.denisovmaksim.procheckreport;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "report", mixinStandardHelpOptions = true,
        description = "Создание сводного отчета по чекам.")
public class App implements Callable {
    @CommandLine.Option(names = {"-f", "--file"}, paramLabel = "file", description = "input file [default: procheck.json]")
    private String jsonFile = "./procheck.json";
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        try {
            String report = Formatter.format(Accumulator.getReport(jsonFile));
            System.out.println(report);;
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
}
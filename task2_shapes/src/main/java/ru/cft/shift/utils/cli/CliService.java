package ru.cft.shift.utils.cli;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.utils.dto.CliDto;


/**
 * @author Dmitrii Taranenko
 */
public class CliService {
    private static final Logger logger = LoggerFactory.getLogger(CliService.class);

    private static final Options OPTIONS = createOptions();

    public static CliDto parseCmdArgs(String[] args) {
        logger.debug("Starting parsing arguments");

        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = cmdParser.parse(OPTIONS, args);
        } catch (ParseException e) {
            return new CliDto(null, null, true);
        }

        return new CliDto(
                cmd.getOptionValue("input"),
                cmd.getOptionValue("output"),
                cmd.hasOption("help")
        );
    }

    public static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("shapes", OPTIONS);
    }

    private static Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder("i")
                .longOpt("input")
                .hasArg()
                .argName("File")
                .desc("Путь входного файла")
                .required()
                .build());

        options.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg()
                .argName("File")
                .desc("Путь выходного файла (Если не указан, то вывод производится в консоль)")
                .build());

        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Вывод сообщения с подсказкой")
                .build());

        return options;
    }
}

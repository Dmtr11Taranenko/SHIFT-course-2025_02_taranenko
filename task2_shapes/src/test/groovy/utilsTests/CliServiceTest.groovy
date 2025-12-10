package utilsTests

import ru.cft.shift.utils.cli.CliService
import spock.lang.Specification

class CliServiceTest extends Specification {

    def parser = new CliService()

    def "Checking parsing valid arguments"() {
        given:
        def args = ["-i", "input.txt", "-o", "output.txt"] as String[]

        when:
        def arguments = parser.parseCmdArgs(args)

        then:
        arguments.inputFile() == "input.txt"
        arguments.outputFile() == "output.txt"
        !arguments.help()
        arguments.hasOutputFile()
    }

    def "Checking parsing help options"() {
        given:
        def args = ["-h"] as String[]

        when:
        def arguments = parser.parseCmdArgs(args)

        then:
        arguments.help()
    }

    def "Checking parsing long options"() {
        given:
        def args = ["--input", "input.txt", "--output", "output.txt", "--help"] as String[]

        when:
        def arguments = parser.parseCmdArgs(args)

        then:
        arguments.inputFile() == "input.txt"
        arguments.outputFile() == "output.txt"
        arguments.help()
    }

    def "Checking without output file"() {
        given:
        def args = ["-i", "input.txt"] as String[]

        when:
        def arguments = parser.parseCmdArgs(args)

        then:
        arguments.inputFile() == "input.txt"
        arguments.outputFile() == null
        !arguments.hasOutputFile()
    }

    def "Checking help message"() {
        given:
        def testOutputStream = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOutputStream))

        when:
        CliService.printHelp()

        then:
        testOutputStream.toString().contains("usage: shapes")
        testOutputStream.toString().contains(" -h,--help            Вывод сообщения с подсказкой")
        testOutputStream.toString().contains(" -i,--input <File>    Путь входного файла")
        testOutputStream.toString().contains(" -o,--output <File>   Путь выходного файла ")
        testOutputStream.toString().contains("(Если не указан, то вывод")
        testOutputStream.toString().contains("производится в консоль)")

        cleanup:
        System.setOut(System.out)
    }
}

package ApplicationTests


import ru.cft.shift.Application
import ru.cft.shift.utils.dto.CliDto
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class ApplicationTests extends Specification {

    @TempDir
    Path tempDir

    Path inputFile
    Path outputFile
    Application runner

    def setup() {
        inputFile = tempDir.resolve("input.txt")
        outputFile = tempDir.resolve("output.txt")
        runner = new Application()
    }


    def "Checking processing circle successfully"() {
        given:
        Files.writeString(inputFile,
                new StringBuilder()
                        .append("CIRCLE")
                        .append("\n")
                        .append("25.0"))

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), false)

        when:
        runner.run(arguments)

        then:
        Files.readString(outputFile).contains("Тип фигуры: Круг")
        Files.readString(outputFile).contains("Площадь: 1963.5 кв. мм")
        Files.readString(outputFile).contains("Периметр: 157.08 мм")
        Files.readString(outputFile).contains("Радиус: 25 мм")
        Files.readString(outputFile).contains("Диаметр: 50 мм")
    }

    def "Checking processing rectangle successfully"() {
        given:
        Files.writeString(inputFile,
                new StringBuilder()
                        .append("RECTANGLE")
                        .append("\n")
                        .append("10.0 20.0"))

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), false)

        when:
        runner.run(arguments)

        then:
        Files.readString(outputFile).contains("Тип фигуры: Прямоугольник")
        Files.readString(outputFile).contains("Площадь: 200 кв. мм")
        Files.readString(outputFile).contains("Периметр: 60 мм")
        Files.readString(outputFile).contains("Длина: 20 мм")
        Files.readString(outputFile).contains("Ширина: 10 мм")
        Files.readString(outputFile).contains("Длина диагонали: 22.36 мм")
    }

    def "Checking processing triangle successfully"() {
        given:
        Files.writeString(inputFile,
                new StringBuilder()
                        .append("TRIANGLE")
                        .append("\n")
                        .append("10.0 20.0 15.0"))

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), false)

        when:
        runner.run(arguments)

        then:
        Files.readString(outputFile).contains("Тип фигуры: Треугольник")
        Files.readString(outputFile).contains("Площадь: 72.62 кв. мм")
        Files.readString(outputFile).contains("Периметр: 45 мм")
        Files.readString(outputFile).contains("Сторона A: 10 мм, противолежащий угол: 28.96°")
        Files.readString(outputFile).contains("Сторона B: 20 мм, противолежащий угол: 104.48°")
        Files.readString(outputFile).contains("Сторона C: 15 мм, противолежащий угол: 46.57°")
    }

    def "Checking throwing file read error"() {
        given:
        inputFile = "NonExistentFile" as Path

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), false)

        when:
        runner.run(arguments)

        then:
        thrown(RuntimeException)
    }

    def "Checking throwing shape creation error"() {
        given:
        Files.writeString(inputFile,
                new StringBuilder()
                        .append("NOT_TRIANGLE")
                        .append("\n")
                        .append("10.0 20.0 200.0"))

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), false)

        when:
        runner.run(arguments)

        then:
        thrown(RuntimeException)
    }

    def "Checking printing help"() {
        given:
        def testOutputStream = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOutputStream))

        Files.writeString(inputFile,
                new StringBuilder()
                        .append("TRIANGLE")
                        .append("\n")
                        .append("10.0 20.0 15.0"))

        def arguments = new CliDto(inputFile.toString(), outputFile.toString(), true)

        when:
        runner.run(arguments)

        then:
        !testOutputStream.toString().contains("Тип фигуры: Треугольник")
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

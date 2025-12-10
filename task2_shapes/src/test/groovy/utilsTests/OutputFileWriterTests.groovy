package utilsTests


import ru.cft.shift.core.models.Circle
import ru.cft.shift.core.models.Rectangle
import ru.cft.shift.utils.io.OutputFileWriter
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class OutputFileWriterTests extends Specification {

    @TempDir
    Path tempDir

    def fileWriter = new OutputFileWriter()
    Path outputFile

    def "Checking writing to file"() {
        given:
        outputFile = tempDir.resolve("output.txt")
        Circle shape = new Circle(15.0)
        String units = "мм"

        when:
        fileWriter.writeResult(shape, outputFile.toString(), units)

        then:
        Files.readString(outputFile).contains("Тип фигуры: Круг")
        Files.readString(outputFile).contains("Площадь: 706.86 кв. мм")
        Files.readString(outputFile).contains("Периметр: 94.25 мм")
        Files.readString(outputFile).contains("Радиус: 15 мм")
        Files.readString(outputFile).contains("Диаметр: 30 мм")
    }

    def "Checking overwriting existing file"() {
        given:
        outputFile = tempDir.resolve("output.txt")
        Files.writeString(outputFile, "Old data")
        String units = "мм"

        when:
        fileWriter.writeResult(new Rectangle(2.0, 1.0), outputFile.toString(), units)

        then:
        !Files.readString(outputFile).contains("Old data")
        Files.readString(outputFile).contains("Тип фигуры: Прямоугольник")
        Files.readString(outputFile).contains("Площадь: 2 кв. мм")
        Files.readString(outputFile).contains("Периметр: 6 мм")
        Files.readString(outputFile).contains("Длина: 2 мм")
        Files.readString(outputFile).contains("Ширина: 1 мм")
        Files.readString(outputFile).contains("Длина диагонали: 2.24 мм")
    }

    def "Checking writing to console when output path is null"() {
        given:
        def testOutputStream = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOutputStream))

        when:
        fileWriter.writeResult(new Circle(15), null, "мм")

        then:
        testOutputStream.toString().contains("Тип фигуры: Круг")
        testOutputStream.toString().contains("Площадь: 706.86 кв. мм")
        testOutputStream.toString().contains("Периметр: 94.25 мм")
        testOutputStream.toString().contains("Радиус: 15 мм")
        testOutputStream.toString().contains("Диаметр: 30 мм")

        cleanup:
        System.setOut(System.out)
    }

    def "Checking throwing exception for invalid file path"() {
        given:
        def invalidPath = tempDir.resolve("invalid/nonexistent/output.txt")

        when:
        fileWriter.writeResult(new Circle(15), invalidPath.toString(), "мм")

        then:
        thrown(IOException)
    }
}

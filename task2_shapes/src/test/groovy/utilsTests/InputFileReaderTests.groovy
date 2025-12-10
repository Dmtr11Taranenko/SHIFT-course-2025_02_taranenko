package utilsTests


import ru.cft.shift.core.dto.ShapeDto
import ru.cft.shift.utils.io.InputFileReader
import spock.lang.Specification
import spock.lang.TempDir
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Path

class InputFileReaderTests extends Specification {

    @TempDir
    Path tempDir

    Path inputFile
    InputFileReader fileReader = new InputFileReader()

    @Unroll
    def "Checking reading #shapeType data from file"() {
        given:
        inputFile = tempDir.resolve("input.txt")
        Files.writeString(inputFile, data)

        when:
        ShapeDto shapeData = fileReader.readShapeData(inputFile.toString())

        then:
        shapeData.type() == shapeType
        shapeData.parameters() == parameters as double[]

        where:
        shapeType   | data                    | parameters
        "CIRCLE"    | "CIRCLE\n5.0"           | [5.0]
        "RECTANGLE" | "RECTANGLE\n3.0 4.0"    | [3.0, 4.0]
        "TRIANGLE"  | "TRIANGLE\n3.0 4.0 5.0" | [3.0, 4.0, 5.0]
    }

    def "Checking multiple spaces"() {
        given:
        inputFile = tempDir.resolve("input.txt")
        Files.writeString(inputFile, "CIRCLE\n  5.0   ")

        when:
        def data = fileReader.readShapeData(inputFile.toString())

        then:
        data.type() == "CIRCLE"
        data.parameters() == [5.0] as double[]
    }

    def "Checking multiple lines"() {
        given:
        inputFile = tempDir.resolve("input.txt")
        Files.writeString(inputFile, "CIRCLE\n  5.0     \n     \n    ")

        when:
        def data = fileReader.readShapeData(inputFile.toString())

        then:
        data.type() == "CIRCLE"
        data.parameters() == [5.0] as double[]
    }

    def "Checking throwing exception when file not found"() {
        when:
        fileReader.readShapeData("nonExistent.txt")

        then:
        thrown(IOException)
    }

    def "Checking throwing exception when file has less than two lines"() {
        given:
        inputFile = tempDir.resolve("input.txt")
        Files.writeString(inputFile, "CIRCLE")

        when:
        fileReader.readShapeData(inputFile.toString())

        then:
        thrown(IllegalArgumentException)
    }

    @Unroll
    def "Checking throwing exception for invalid parameters: #data"() {
        given:
        inputFile = tempDir.resolve("input.txt")
        Files.writeString(inputFile, data)

        when:
        fileReader.readShapeData(inputFile.toString())

        then:
        thrown(IllegalArgumentException)

        where:
        data << [
                "CIRCLE\nabc",
                "CIRCLE\n-5.0"
        ]
    }
}

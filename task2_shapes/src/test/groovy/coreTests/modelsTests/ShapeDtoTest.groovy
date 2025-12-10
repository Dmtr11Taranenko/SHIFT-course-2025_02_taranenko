package coreTests.modelsTests

import ru.cft.shift.core.dto.ShapeDto
import spock.lang.Specification
import spock.lang.Subject

class ShapeDtoTest extends Specification {

    @Subject
    ShapeDto shapeData

    def "Checking creation valid shape data"() {
        when:
        shapeData = new ShapeDto("CIRCLE", [5.0])

        then:
        noExceptionThrown()
        shapeData.type() == "CIRCLE"
        shapeData.parameters() == [5.0]
    }

    def "Checking throwing exception when type is \"#type\""() {
        when:
        new ShapeDto(type, [5.0]).validate()

        then:
        thrown(IllegalArgumentException)

        where:
        type << [null, "", "     "]
    }

    def "Checking throwing exception when parameters is \"#parameters\""() {
        when:
        new ShapeDto("CIRCLE", parameters as List<Double>).validate()

        then:
        thrown(Exception)

        where:
        parameters << [null, []]
    }

    def "Checking immutability"() {
        given:
        def originalParams = [1.0d, 2.0d]
        shapeData = new ShapeDto("RECTANGLE", originalParams)

        when:
        originalParams[0] = 999.0d

        then:
        shapeData.parameters() == [1.0d, 2.0d]
    }
}

package coreTests.modelsTests

import ru.cft.shift.core.models.Rectangle
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RectangleTest extends Specification {

    @Subject
    Rectangle rectangle

    def "Checking creation of rectangles with correct sides"() {
        when:
        rectangle = new Rectangle(3.0, 4.0)

        then:
        noExceptionThrown()
        rectangle.sideA == 3.0d
        rectangle.sideB == 4.0d
    }

    @Unroll
    def "Checking throwing exception when sides are #sideA and #sideB"() {
        when:
        new Rectangle(sideA, sideB)

        then:
        thrown(IllegalArgumentException)

        where:
        sideA | sideB
        0     | 5
        5     | 0
        -1    | 5
        5     | -1
        -1    | -1
    }

    def "Checking identification length and width correctly"() {
        given:
        rectangle = new Rectangle(3.0, 4.0)

        expect:
        rectangle.sideA == 3.0d
        rectangle.sideB == 4.0d
    }

    def "Checking calculation area correctly"() {
        given:
        rectangle = new Rectangle(3.0, 4.0)

        expect:
        rectangle.calculateArea() == 12.0d
    }

    def "Checking calculation perimeter correctly"() {
        given:
        rectangle = new Rectangle(3.0, 4.0)

        expect:
        rectangle.calculatePerimeter() == 14.0d
    }

    def "Checking calculation diagonal correctly"() {
        given:
        rectangle = new Rectangle(3.0, 4.0)

        expect:
        rectangle.calculateDiagonal() == 5.0d
    }

    def "Checking formatting info correctly"() {
        given:
        rectangle = new Rectangle(3.0, 4.0)
        def sb = new StringBuilder()
        String units = "мм"

        when:
        rectangle.getInfo(sb, units)
        def result = sb.toString()

        then:
        result.contains("Тип фигуры: Прямоугольник")
        result.contains("Длина диагонали: 5 мм")
        result.contains("Длина: 4 мм")
        result.contains("Ширина: 3 мм")
    }
}

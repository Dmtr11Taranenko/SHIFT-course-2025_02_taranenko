package coreTests.modelsTests

import ru.cft.shift.core.models.Triangle
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class TriangleTest extends Specification {

    @Subject
    Triangle triangle

    def "Checking creation valid triangle"() {
        when:
        triangle = new Triangle(3.0, 4.0, 5.0)

        then:
        noExceptionThrown()
    }

    @Unroll
    def "Checking throwing exception when sides are invalid: #a, #b, #c"() {
        when:
        new Triangle(a, b, c)

        then:
        thrown(IllegalArgumentException)

        where:
        a  | b | c
        0  | 4 | 5
        3  | 0 | 5
        3  | 4 | 0
        -1 | 4 | 5
    }

    @Unroll
    def "Checking throwing exception when triangle inequality violated: #a, #b, #c"() {
        when:
        new Triangle(a, b, c)

        then:
        thrown(IllegalArgumentException)

        where:
        a | b | c
        1 | 1 | 3
        1 | 2 | 4
        5 | 1 | 1
    }

    def "Checking calculation area correctly"() {
        given:
        triangle = new Triangle(3.0, 4.0, 5.0)

        expect:
        triangle.calculateArea() == 6.0d
    }

    def "Checking calculation perimeter correctly"() {
        given:
        triangle = new Triangle(3.0, 4.0, 5.0)

        expect:
        triangle.calculatePerimeter() == 12.0d
    }

    def "Checking calculation angles correctly for right triangle"() {
        given:
        triangle = new Triangle(3.0, 4.0, 5.0)

        when:
        def angleA = triangle.calculateAngleOppositeToSide(3.0, 4.0)
        def angleB = triangle.calculateAngleOppositeToSide(4.0, 5.0)
        def angleC = triangle.calculateAngleOppositeToSide(5.0, 3.0)

        then:
        angleA == 90.0d
        Math.abs(angleB - 36.87) < 0.1
        Math.abs(angleC - 53.13) < 0.1
    }

    def "Checking formatting info correctly"() {
        given:
        triangle = new Triangle(3.0, 4.0, 5.0)
        def sb = new StringBuilder()
        String units = "мм"

        when:
        triangle.getInfo(sb, units)
        def result = sb.toString()

        then:
        result.contains("Тип фигуры: Треугольник")
        result.contains("Сторона A: 3 мм")
        result.contains("Сторона B: 4 мм")
        result.contains("Сторона C: 5 мм")
        result.contains("противолежащий угол")
    }
}

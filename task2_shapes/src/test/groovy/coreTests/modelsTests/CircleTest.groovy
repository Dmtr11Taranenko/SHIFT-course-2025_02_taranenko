package coreTests.modelsTests

import ru.cft.shift.core.models.Circle
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class CircleTest extends Specification {

    @Subject
    Circle circle

    def "Checking creation of circle with correct radius"() {
        when:
        circle = new Circle(5.0)

        then:
        noExceptionThrown()
        circle.radius == 5.0d
    }

    @Unroll
    def "Checking throwing exception when radius is #radius"() {
        when:
        new Circle(radius as double)

        then:
        thrown(IllegalArgumentException)

        where:
        radius << [0, -1, -0.1]
    }

    def "Checking calculation area correctly"() {
        given:
        circle = new Circle(5.0)

        when:
        def area = circle.calculateArea()

        then:
        area == Math.PI * 5.0 * 5.0
    }

    def "Checking calculation perimeter correctly"() {
        given:
        circle = new Circle(5.0)

        when:
        def perimeter = circle.calculatePerimeter()

        then:
        perimeter == 2 * Math.PI * 5.0
    }

    def "Checking returning correct diameter"() {
        given:
        circle = new Circle(5.0)

        when:
        def diameter = circle.calculateDiameter()

        then:
        diameter == 10.0d
    }

    def "Checking returning correct name"() {
        given:
        circle = new Circle(5.0)

        expect:
        circle.name == "Круг"
    }

    def "Checking formatting info correctly"() {
        given:
        circle = new Circle(5.0)
        def sb = new StringBuilder()
        String units = "мм"

        when:
        circle.getInfo(sb, units)
        def result = sb.toString()

        then:
        result.contains("Тип фигуры: Круг")
        result.contains("Площадь: 78.54 кв. мм")
        result.contains("Периметр: 31.42 мм")
        result.contains("Радиус: 5 мм")
        result.contains("Диаметр: 10 мм")
    }
}
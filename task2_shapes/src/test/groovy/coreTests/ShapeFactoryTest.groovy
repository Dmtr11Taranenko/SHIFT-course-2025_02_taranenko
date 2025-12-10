package coreTests

import ru.cft.shift.core.IShape
import ru.cft.shift.core.exceptions.ShapeCreationException
import ru.cft.shift.core.models.Circle
import ru.cft.shift.core.models.Rectangle
import ru.cft.shift.core.models.Triangle
import ru.cft.shift.core.services.ShapeFactory
import spock.lang.Specification
import spock.lang.Unroll

class ShapeFactoryTest extends Specification {

    def "Checking creation circle"() {
        when:
        IShape shape = ShapeFactory.createShape("CIRCLE", [5.0d])

        then:
        shape instanceof Circle
        shape.name == "Круг"
    }

    def "Checking creation rectangle"() {
        when:
        IShape shape = ShapeFactory.createShape("RECTANGLE", [3.0d, 4.0d])

        then:
        shape instanceof Rectangle
        shape.name == "Прямоугольник"
    }

    def "Checking creation triangle"() {
        when:
        IShape shape = ShapeFactory.createShape("TRIANGLE", [3.0d, 4.0d, 5.0d])

        then:
        shape instanceof Triangle
        shape.name == "Треугольник"
    }

    @Unroll
    def "Checking creation circle with type \"#type\""() {
        when:
        IShape shape = ShapeFactory.createShape(type, [5.0d])

        then:
        shape instanceof Circle

        where:
        type << ["circle", "CIRCLE", "Circle"]
    }

    @Unroll
    def "Checking throwing ShapeCreationException for unknown type \"#type\""() {
        when:
        ShapeFactory.createShape(type, [1.0d])

        then:
        thrown(ShapeCreationException)

        where:
        type << ["UNKNOWN", "SQUARE", ""]
    }

    @Unroll
    def "Checking throwing ShapeCreationException for wrong parameter count: \"#type\" with #params parameters"() {
        when:
        ShapeFactory.createShape(type, params)

        then:
        thrown(ShapeCreationException)

        where:
        type        | params
        "CIRCLE"    | [1.0d, 2.0d]
        "RECTANGLE" | [1.0d]
        "TRIANGLE"  | [1.0d, 2.0d]
    }

    def "Checking throwing exception for invalid triangle"() {
        when:
        ShapeFactory.createShape("TRIANGLE", [1.0d, 1.0d, 3.0d])

        then:
        thrown(IllegalArgumentException)
    }

    def "Checking throwing exception for non-positive parameters"() {
        when:
        ShapeFactory.createShape("CIRCLE", [0.0d])

        then:
        thrown(IllegalArgumentException)
    }
}

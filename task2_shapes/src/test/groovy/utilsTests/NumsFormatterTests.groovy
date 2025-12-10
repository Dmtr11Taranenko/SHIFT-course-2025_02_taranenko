package utilsTests

import ru.cft.shift.utils.formatters.NumsFormatter
import spock.lang.Specification
import spock.lang.Unroll

class NumsFormatterTests extends Specification {

    def formatter = new NumsFormatter()

    @Unroll
    def "Checking formatting #input to #expected"() {
        when:
        def result = formatter.formatNum(input)

        then:
        result == expected

        where:
        input   | expected
        5.0     | "5"
        5.5     | "5.5"
        5.55    | "5.55"
        5.556   | "5.56"
        5.555   | "5.55"
        0.0     | "0"
        0.123   | "0.12"
        123.456 | "123.46"
    }

    def "Checking decimal separator"() {
        when:
        def result = formatter.formatNum(5.5)

        then:
        !result.contains(",")
        result.contains(".")
    }

    def "Checking formatting large numbers"() {
        when:
        def result = formatter.formatNum(1234567.89)

        then:
        result == "1234567.89"
    }

    def "Checking formatting small numbers"() {
        when:
        def result = formatter.formatNum(0.0001)

        then:
        result == "0"
    }
}

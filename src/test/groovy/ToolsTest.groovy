import org.py.plugin.codeinspection.utils.Tools
import spock.lang.Specification

/**
 * @date 2019/8/2
 */
class ToolsTest extends Specification {

    def "test isNumber"() {
        expect:
        Tools.isNumeric(input) == output

        where:
        input      | output
        "1"        | true
        "2"        | true
        "1"        | true
        "323r23r2" | false
        "sfsf"     | false
        "im not "  | false
    }

    def "test Match constant"() {
        expect:
        Tools.isConst(input) == output

        where:
        input          | output
        "AB_BB_CB"     | true
        "AB_BB_1B_VB"  | true
        "_AB_BB_1B_VB" | true
        "AB_BB_CB_"    | false
        "adfg_bsf_csf" | false
        "123sf"        | false
        ""             | false
    }


}

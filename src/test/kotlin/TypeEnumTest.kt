import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TypeEnumTest {

    companion object {
        @JvmStatic
        fun checkDateTimeFormatsWithInvalidFormats(): Stream<Arguments> = Stream.of(
            Arguments.of("MM-dd-yyyy", "01  2-218"),
            Arguments.of("HH:mm:ss.SSS", "13:0.454+0530"),
            Arguments.of("MMMM dd, yy", "February 17 2009"),
            Arguments.of("yy/MM/dd", "2009/0217"),
            Arguments.of("dd/MM/yy", "17/ 022009"),
            Arguments.of("MMM dd, yyyy hh:mm:ss a", "Dec , 2017 2:39:58 AM"),
            Arguments.of("dd/MMM/yyyy:HH:mm:ss ZZZZ", "19/ /2017:  06:36:15 -0700"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a", "9/28/2011 2/ 23:15 PM"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a", "9/28011 2:23:15 PM"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a:SSS", "8/511 3:31:18 AM:234"),
            Arguments.of("MMdd_HH:mm:ss.SSS", "0423_::11:42:35.883"),
            Arguments.of("MMdd_HH:mm:ss", "04231:42:35"),
            Arguments.of("dd MMM yyyy HH:mm:ss*SSS", "23 Apr 2017 10//32:35*311"),
            Arguments.of("dd MMM yyyy HH:mm:ss", "23 Apr 2017 11/42:35"),
            Arguments.of("dd-MMM-yyyy HH:mm:ss", "23-Apr::2017 11:42:35"),
            Arguments.of("dd/MMM/yyyy HH:mm:ss", "23/Apr/2017 11:42//35"),
            Arguments.of("dd/MMM HH:mm:ss,SSS", "23Apr 11:4235,173"),
            Arguments.of("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "2000-07-205:06:07.00"),
            Arguments.of("yyyy-MM-dd HH:mm:ss ZZZZ", "17-08-19 2:17:55 -0  00"),
            Arguments.of("yyyy/MM/dd*HH:mm:ss", "2010412*19:37:50"),
            Arguments.of("yyyy MMM dd HH:mm:ss.SSS*zzz", "2018  13 22:08:13.211*PDT"),
        )

        @JvmStatic
        fun checkDateTimeFormatsWithValidFormats(): Stream<Arguments> = Stream.of(
            Arguments.of("MM-dd-yyyy", "01-02-2018"),
            Arguments.of("HH:mm:ss.SSSZ", "13:03:15.454+0530"),
            Arguments.of("MMMM dd, yy", "February 17, 2009"),
            Arguments.of("yy/MM/dd", "2009/02/17"),
            Arguments.of("dd/MM/yy", "17/02/2009"),
            Arguments.of("MMM dd, yyyy hh:mm:ss a", "Dec 2, 2017 2:39:58 AM"),
            Arguments.of("dd/MMM/yyyy:HH:mm:ss ZZZZ", "19/Apr/2017:06:36:15 -0700"),
            Arguments.of("MMM dd HH:mm:ss ZZZZ yyyy", "Jan 21 18:20:11 +0000 2017"),
            Arguments.of("MMM dd yyyy HH:mm:ss", "Jun 09 2018 15:28:14"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a", "9/28/2011 2:23:15 PM"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a", "9/28/2011 2:23:15 PM"),
            Arguments.of("MM/dd/yyyy hh:mm:ss a:SSS", "8/5/2011 3:31:18 AM:234"),
            Arguments.of("MMdd_HH:mm:ss.SSS", "0423_11:42:35.883"),
            Arguments.of("MMdd_HH:mm:ss", "0423_11:42:35"),
            Arguments.of("dd MMM yyyy HH:mm:ss*SSS", "23 Apr 2017 10:32:35*311"),
            Arguments.of("dd MMM yyyy HH:mm:ss", "23 Apr 2017 11:42:35"),
            Arguments.of("dd-MMM-yyyy HH:mm:ss", "23-Apr-2017 11:42:35"),
            Arguments.of("dd-MMM-yyyy HH:mm:ss", "23-Apr-2017 11:42:35"),
            Arguments.of("dd/MMM/yyyy HH:mm:ss", "23/Apr/2017 11:42:35"),
            Arguments.of("dd/MMM/yyyy HH:mm:ss", "23/Apr/2017 11:42:35"),
            Arguments.of("dd/MMM HH:mm:ss,SSS", "23/Apr 11:42:35,173"),
            Arguments.of("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "2000-07-21T05:06:07.001Z"),
            Arguments.of("yyyy-MM-dd HH:mm:ss ZZZZ", "2017-08-19 12:17:55 -0400"),
            Arguments.of("yyyy-MM-dd HH:mm:ss,SSS", "2017-06-26 02:31:29,573"),
            Arguments.of("yyyy/MM/dd*HH:mm:ss", "2017/04/12*19:37:50"),
            Arguments.of("yyyy MMM dd HH:mm:ss.SSS*zzz", "2018 Apr 13 22:08:13.211*PDT"),
            Arguments.of("yyyy-MM-dd'T'HH:mm:ss", "2000-07-21T05:06:07"),
        )

    }



    @Test
    fun shouldBeAbleToCheckIfValueIsNumeric() {
        val value = "123"

        val actual = TypeEnum.Number.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfValueIsZero() {
        val value = "0"

        val actual = TypeEnum.Number.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphabetic() {
        val value = "shikha"

        val actual = TypeEnum.Alphabets.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphaNumeric() {
        val value = "shikha123"

        val actual =TypeEnum.AlphaNumeric.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumber() {
        val value = "123.45"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumberWithSucceeDingZeroes() {
        val value = "123.000"

        val actual =TypeEnum.FloatingNumber.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumberWithSucceeDingZeroes1() {
        val value = "0"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumbersWihCharacters() {
        val value = "123utk"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumbersWihSymbols() {
        val value = "123**/.12"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsFloatingPointNumbersWihNegativeNumbers() {
        val value = "-123.122"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfTheNumberIsInteger() {
        val value = "123"

        val actual = TypeEnum.FloatingNumber.typeCheck(value)

        assertTrue(actual)
    }

//    @ParameterizedTest
//    @MethodSource("checkSpecialCharactersWithAllPossibleExpressions")
//    fun shouldCheckIfValueHasSpecialCharacters(specialCharactersValue: String) {
//        val typeValidation = TypeValidation()
//
//        val actual =  TypeEnum.Number.typeCheck(specialCharactersValue)
//
//        assertTrue(actual)
//    }

    private fun checkSpecialCharactersWithAllPossibleExpressions(): Stream<Arguments> = Stream.of(
        Arguments.of("Ukarsh12"),
        Arguments.of("SHreya@#"),
        Arguments.of("Suraj#$"),
        Arguments.of("Shikha()<>"),
        Arguments.of("Gaurav+="),
        Arguments.of("test{}"),
        Arguments.of("""This is a test; "ParameterizedTest""""),
        Arguments.of("SAHAJ!@"),
        Arguments.of("SOftware|[]"),
        Arguments.of("Solutions 123 !"),
        Arguments.of("PVT LTD*"),
        Arguments.of("2^(2)"),
        Arguments.of("User & Stories"),
        Arguments.of("Yes || No"),
        Arguments.of("""user@somedomaincom123#$##%$^%&%$^%#$<>{}[]  ;:/|||`'""_+=-*&^%$#@!?/>M<>.,:;"'{}[]()()?.,"""),
        Arguments.of("USA~AUS")
    )

    private fun checkDateFormatsWithValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("MM-dd-yyyy", "01-02-2018"),
        Arguments.of("dd-MM-yyyy", "31-01-2012"),
        Arguments.of("yyyy-MM-dd", "2012-02-15"),
        Arguments.of("dd/MM/yyyy", "17/02/09"),
        Arguments.of("yyyy/MM/dd", "2009/ 2/17"),
        Arguments.of("M/d/yyy", "2/7/12"),
        Arguments.of("d/M/yyyy", "17/2/2009"),
        Arguments.of("yyyy/M/d", "2009/2/17"),
        Arguments.of("MMddYy", "02172009"),
        Arguments.of("ddMMyYYy", "17022009"),
        Arguments.of("dd MMM yyyy", "02 Jan 18"),
    )

    private fun checkDateFormatsWithInValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("MM-dd-yyyy", "01/02/2018"),
        Arguments.of("dd-MM-yyy", "31/ 01-2012"),
        Arguments.of("dd/MM/yyyy", "17-02-09"),
        Arguments.of("yyyy/MM/dd", "2009/017"),
        Arguments.of("M/d/yy", "2 7/12"),
        Arguments.of("d/M/yyyy", "17/2:2009"),
        Arguments.of("yyyy//d", "2009/2/17"),
        Arguments.of("MMddyyyy", "02:17/09"),
        Arguments.of("ddMMyyyy", "170 2009"),
        Arguments.of("dd MMM yyyy", "0 2 Jan 2018"),
    )

    private fun checkTimeFormatsWithValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("hh:mm:ss", "06:07:59"),
        Arguments.of("HH:mm:ss zzz", "18:07:59 IST"),
        Arguments.of("HH:mm:ss.SSSZ", "13:03:15.454+0530"),
    )

    private fun checkTimeFormatsWithInValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("hh:mm/ss", "06:07:59"),
        Arguments.of("HH--mm:ss zzz", "18:07:59 IST"),
        Arguments.of("HH::mm:ss.SSSZ", "13:03:15.454+0530"),
        Arguments.of("HH:mm:ss.SSSZ", "10:35:49.278'Z'"),
    )

    private fun checkEmailWithValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("shikhareddy@gmail.com"),
        Arguments.of("shikha.reddy@gmail.com"),
        Arguments.of("shikhareddy123@my-gmail.com"),
        Arguments.of("shikha_reddy@gmail.com"),
    )

    private fun checkEmailWithInValidFormats(): Stream<Arguments> = Stream.of(
        Arguments.of("@"),
        Arguments.of("shikha.reddy@"),
        Arguments.of("@my-gmail.com"),
    )



    @ParameterizedTest
    @MethodSource("checkDateTimeFormatsWithValidFormats")
    fun shouldBeAbleToCheckIfValueIsInDateTimeFormat(dateTimeFormat: String, dateTimeValue: String) {

        val actual = TypeEnum.Date.typeCheck(dateTimeValue,dateTimeFormat)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("checkDateTimeFormatsWithInvalidFormats")
    fun shouldBeAbleToCheckIfValueIsNotInDateTimeFormat(dateTimeFormat: String, dateTimeValue: String) {

        val actual =TypeEnum.DateTime.typeCheck(dateTimeValue,dateTimeFormat)

        assertFalse(actual)
    }

    @ParameterizedTest
    @MethodSource("checkDateFormatsWithValidFormats")
    fun shouldBeAbleToCheckIfValueIsInDateFormat(dateFormat: String, dateTimeValue: String) {

        val actual = TypeEnum.DateTime.typeCheck(dateTimeValue,dateFormat)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("checkDateFormatsWithInValidFormats")
    fun shouldBeAbleToCheckIfValueIsNotInDateFormat(dateFormat: String , dateTimeValue: String) {

        val actual =TypeEnum.Date.typeCheck(dateTimeValue,dateFormat)

        assertFalse(actual)
    }

    @ParameterizedTest
    @MethodSource("checkTimeFormatsWithValidFormats")
    fun shouldBeAbleToCheckIfValueIsInTimeFormat(dateFormat: String , dateTimeValue: String) {

        val actual =TypeEnum.Time.typeCheck(dateTimeValue,dateFormat)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("checkTimeFormatsWithInValidFormats")
    fun shouldBeAbleToCheckIfValueIsNotInTimeFormat(dateFormat: String , dateTimeValue: String) {

        val actual = TypeEnum.Time.typeCheck(dateTimeValue,dateFormat)

        assertFalse(actual)
    }

    @ParameterizedTest
    @MethodSource("checkEmailWithValidFormats")
    fun shouldBeAbleToCheckIfValueIsInEmailFormat(value:String) {

        val actual = TypeEnum.Email.typeCheck(value)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("checkEmailWithInValidFormats")
    fun shouldBeAbleToCheckIfValueIsNotInEmailFormat(value:String) {
        val actual = TypeEnum.Email.typeCheck(value)

        assertFalse(actual)
    }



}
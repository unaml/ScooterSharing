package dk.itu.moapd.scootersharing

import dk.itu.moapd.scootersharing.models.TextValidator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class TextValidatorTest {

    @Test
    fun textValidator_CorrectNameSimple_ReturnsTrue() {
        assertThat(TextValidator.isValidName("Name"), `is`(true))
    }

    @Test
    fun textValidator_CorrectFullName_ReturnsTrue() {
        assertThat(TextValidator.isValidName("Name Surname"), `is`(true))
    }

    @Test
    fun textValidator_CorrectShortName_ReturnsTrue() {
        assertThat(TextValidator.isValidName("Ram"), `is`(true))
    }

    @Test
    fun textValidator_InvalidNameInitialSpace_ReturnsFalse() {
        assertThat(TextValidator.isValidName(" Name"), `is`(false))
    }

    @Test
    fun textValidator_InvalidNameFinalSpace_ReturnsFalse() {
        assertThat(TextValidator.isValidName("Name "), `is`(false))
    }

    @Test
    fun textValidator_InvalidNameLowerCase_ReturnsFalse() {
        assertThat(TextValidator.isValidName("name"), `is`(false))
    }

    @Test
    fun textValidator_InvalidShortName_ReturnsFalse() {
        assertThat(TextValidator.isValidName("Jo"), `is`(false))
    }

    @Test
    fun textValidator_EmptyNameString_ReturnsFalse() {
        assertThat(TextValidator.isValidName(""), `is`(false))
    }

    @Test
    fun textValidator_InvalidPhoneWithString_ReturnsFalse() {
        assertThat(TextValidator.isValidPhone("A1"), `is`(false))
    }

    @Test
    fun textValidator_EmptyPhoneString_ReturnsFalse() {
        assertThat(TextValidator.isValidPhone(""), `is`(false))
    }

    @Test
    fun textValidator_CorrectFormat_ReturnsTrue() {
        assertThat(TextValidator.isValidPhone("91918"), `is`(true))
    }
    @Test
    fun textValidator_ShortPhoneString_ReturnsFalse() {
        assertThat(TextValidator.isValidPhone("99"), `is`(false))
    }

}
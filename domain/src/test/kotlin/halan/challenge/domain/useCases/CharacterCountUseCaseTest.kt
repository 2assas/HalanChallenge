
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CharacterCountUseCaseTest {
    private val characterCountUseCase = CharacterCountUseCase()

    // Edge Case 1: Normal text (no URLs, emojis, or special characters)
    @Test
    fun `test normal text without special characters`() {
        val input = "Hello World"
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(269, remainingChars) // Remaining characters should be 280 - 11
        assertEquals(11, characterCount) // Character count should be 11
        assertTrue(characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

    // Edge Case 2: Text with emojis
    @Test
    fun `test text with emoji`() {
        val input = "Hello 😄"
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(272, remainingChars) // Emojis may count as more than 1 byte, assuming 2 bytes
        assertEquals(8, characterCount) // The emoji takes more than 1 byte, so we subtract accordingly
        assertTrue(characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }


    // Edge Case 3: Text exactly at the limit
    @Test
    fun `test text at character limit`() {
        val input = "a".repeat(280)
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(0, remainingChars) // Exactly at the limit
        assertEquals(280, characterCount) // Exactly 280 characters used
        assertTrue(characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

    // Edge Case 4: Text exceeding the limit
    @Test
    fun `test text exceeding the character limit`() {
        val input = "a".repeat(281)
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertTrue(remainingChars < 0) // Remaining characters should be negative
        assertEquals(281, characterCount) // Character count used should be 281
        assertFalse(characterCountUseCase.isTweetValid(input)) // Tweet should not be valid
    }

    // Edge Case 5: Text with spaces only
    @Test
    fun `test text with spaces only`() {
        val input = "   "
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(277, remainingChars) // Spaces count as regular characters
        assertEquals(3, characterCount) // 3 spaces = 3 characters
        assertTrue(!characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

    // Edge Case 6: Empty text
    @Test
    fun `test empty text`() {
        val input = ""
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(280, remainingChars) // Empty text, so the remaining character count is 280
        assertEquals(0, characterCount) // No characters used
        assertTrue(!characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

    // Edge Case 7: Special characters in text
    @Test
    fun `test text with special characters`() {
        val input = "!@#$%^&*()_+-=<>?"
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(263, remainingChars) // Special characters count as 1 character each
        assertEquals(17, characterCount) // 17 characters in total
        assertTrue(characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

    // Edge Case 8: Text with newline characters
    @Test
    fun `test text with newline characters`() {
        val input = "Hello\nWorld"
        val remainingChars = characterCountUseCase.calculateRemainingCharacters(input)
        val characterCount = characterCountUseCase.calculateCharacterCount(input)

        assertEquals(269, remainingChars) // \n counts as 1 character
        assertEquals(11, characterCount) // 11 characters used
        assertTrue(characterCountUseCase.isTweetValid(input)) // Tweet should be valid
    }

}
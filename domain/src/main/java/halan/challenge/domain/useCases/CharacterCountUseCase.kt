

import java.text.Normalizer

class CharacterCountUseCase {
    private val twitterLimit = 280

    // Calculate remaining characters based on Twitter's rules
    fun calculateRemainingCharacters(input: String): Int {
        var adjustedCount = 0
        // Normalize the input to NFC form
        val normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFC)

        var index = 0
        while (index < normalizedInput.length) {
            val char = normalizedInput[index]

            // Handle surrogate pairs
            if (Character.isSurrogate(char)) {
                adjustedCount += 2 // Emojis or extended code points count as 2
                index += 2 // Skip the surrogate pair
            } else {
                adjustedCount += 1
                index += 1
            }
        }
        return twitterLimit - adjustedCount
    }

    // Calculate the actual character count used (the total characters used in the tweet)
    fun calculateCharacterCount(input: String): Int {
        val remainingChars = calculateRemainingCharacters(input)
        return twitterLimit - remainingChars
    }

    // Validate tweet (whether it's within the character limit)
    fun isTweetValid(input: String): Boolean {
        if (input.trim().isEmpty()) {
            return false
        }
        return calculateRemainingCharacters(input) >= 0
    }
}
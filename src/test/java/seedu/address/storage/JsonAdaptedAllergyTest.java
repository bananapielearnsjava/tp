package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Allergy;

public class JsonAdaptedAllergyTest {
    private static final String INVALID_ALLERGY = " ";
    private static final String VALID_ALLERGY = "Nuts";

    @Test
    public void toModelType_validAllergy_returnsAllergy() throws Exception {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy(VALID_ALLERGY);
        assertEquals(new Allergy(VALID_ALLERGY), allergy.toModelType());
    }

    @Test
    public void toModelType_invalidAllergy_throwsIllegalValueException() {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy(INVALID_ALLERGY);
        String expectedMessage = Allergy.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, allergy::toModelType);
    }

    @Test
    public void toModelType_nullAllergyName_throwsNullPointerException() {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy((String) null);
        assertThrows(NullPointerException.class, allergy::toModelType);
    }
}

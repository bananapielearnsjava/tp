package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Medicine;

public class JsonAdaptedMedicineTest {
    private static final String INVALID_MEDICINE = " ";
    private static final String VALID_MEDICINE = "Panadol";

    @Test
    public void toModelType_validMedicine_returnsMedicine() throws Exception {
        JsonAdaptedMedicine medicine = new JsonAdaptedMedicine(VALID_MEDICINE);
        assertEquals(new Medicine(VALID_MEDICINE), medicine.toModelType());
    }

    @Test
    public void toModelType_invalidMedicine_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine = new JsonAdaptedMedicine(INVALID_MEDICINE);
        String expectedMessage = Medicine.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullMedicineName_throwsNullPointerException() {
        JsonAdaptedMedicine medicine = new JsonAdaptedMedicine((String) null);
        assertThrows(NullPointerException.class, medicine::toModelType);
    }
}

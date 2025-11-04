package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class JsonAdaptedTagTest {
    private static final String INVALID_TAG = " ";
    private static final String VALID_TAG = "high priority";

    @Test
    public void toModelType_validTag_returnsTag() throws Exception {
        JsonAdaptedTag tag = new JsonAdaptedTag(VALID_TAG);
        assertEquals(new Tag(VALID_TAG), tag.toModelType());
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        JsonAdaptedTag tag = new JsonAdaptedTag(INVALID_TAG);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullTagName_throwsNullPointerException() {
        JsonAdaptedTag tag = new JsonAdaptedTag((String) null);
        assertThrows(NullPointerException.class, tag::toModelType);
    }
}

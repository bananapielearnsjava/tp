package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPT_ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentNotes;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.IdentityNumber;

public class JsonAdaptedAppointmentTest {
    private static final String INVALID_ID = "A 123";
    private static final String INVALID_TIME_FORMAT = "2025-01-01 10:00";
    private static final String INVALID_TIME_VALUE = "31-01-0000 10:00";

    private static final String VALID_ID = APPT_ALICE.getPatientId().toString();
    private static final String VALID_TIME = APPT_ALICE.getDateTime().toString();
    private static final String VALID_NOTES = APPT_ALICE.getNotes().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(APPT_ALICE);
        assertEquals(APPT_ALICE, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidPatientId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(INVALID_ID, VALID_TIME, VALID_NOTES);
        String expectedMessage = IdentityNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPatientId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(null, VALID_TIME, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IdentityNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidTimeFormat_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_ID, INVALID_TIME_FORMAT, VALID_NOTES);
        String expectedMessage = AppointmentTime.MESSAGE_FORMAT_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidTimeValue_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_ID, INVALID_TIME_VALUE, VALID_NOTES);
        String expectedMessage = AppointmentTime.MESSAGE_VALID_DATE_CONSTRAINT + INVALID_TIME_VALUE;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_ID, null, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullNotes_returnsAppointmentWithEmptyNotes() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_ID, VALID_TIME, null);
        Appointment modelAppt = appointment.toModelType();
        assertEquals(new AppointmentNotes(""), modelAppt.getNotes());
    }
}

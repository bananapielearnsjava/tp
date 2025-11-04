package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentNotes;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.IdentityNumber;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    private final String patientId;
    private final String time;
    private final String notes;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("patientId") String patientId, @JsonProperty("time") String time,
                                   @JsonProperty("notes") String notes) {
        this.patientId = patientId;
        this.time = time;
        this.notes = notes;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        patientId = source.getPatientId().identityNumber;
        time = source.getDateTime().toString();
        notes = source.getNotes().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's
     * {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (patientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentityNumber.class.getSimpleName()));
        }
        if (!IdentityNumber.isValidId(patientId)) {
            throw new IllegalValueException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        final IdentityNumber patientId = new IdentityNumber(this.patientId);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentTime.class.getSimpleName()));
        }
        if (!AppointmentTime.isValidDateTimeFormat(time)) {
            throw new IllegalValueException(AppointmentTime.MESSAGE_FORMAT_CONSTRAINTS);
        }
        if (!AppointmentTime.isValidDateTime(time)) {
            throw new IllegalValueException(AppointmentTime.MESSAGE_VALID_DATE_CONSTRAINT + time);
        }
        final AppointmentTime time = new AppointmentTime(this.time);

        final AppointmentNotes notes = new AppointmentNotes(this.notes == null ? "" : this.notes);

        return new Appointment(notes, time, patientId);
    }
}


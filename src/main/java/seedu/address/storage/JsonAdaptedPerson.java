package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.AlcoholicRecord;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Gender;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Medicine;
import seedu.address.model.person.Name;
import seedu.address.model.person.PastMedicalHistory;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SmokingRecord;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String identityNumber;
    private final String phone;
    private final String email;
    private final String address;
    private final String dateOfBirth;
    private final String emergencyContact;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedAllergy> allergies = new ArrayList<>();
    private final List<JsonAdaptedMedicine> medicines = new ArrayList<>();
    private final String smokingRecord;
    private final String bloodType;
    private final String alcoholicRecord;
    private final String gender;
    private final String pastMedicalHistory;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("identityNumber") String identityNumber,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("emergencyContact") String emergencyContact,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("dob") String dateOfBirth, @JsonProperty("bloodType") String bloodType,
                             @JsonProperty("alcoholicRecord") String alcoholicRecord,
                             @JsonProperty("gender") String gender,
                             @JsonProperty("value") String smokingRecord,
                             @JsonProperty("allergies") List<JsonAdaptedAllergy> allergies,
                             @JsonProperty("pastMedicalHistory") String pastMedicalHistory,
                             @JsonProperty("medicines") List<JsonAdaptedMedicine> medicines) {

        this.name = name;
        this.identityNumber = identityNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.alcoholicRecord = alcoholicRecord;
        this.gender = gender;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        if (medicines != null) {
            this.medicines.addAll(medicines);
        }
        this.smokingRecord = smokingRecord;
        this.pastMedicalHistory = pastMedicalHistory;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        identityNumber = source.getIdentityNumber().identityNumber;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        emergencyContact = source.getEmergencyContact().toString();
        dateOfBirth = source.getDateOfBirth().toString();
        bloodType = source.getBloodType().bloodType;
        alcoholicRecord = source.getAlcoholicRecord().alcoholicRecord;
        gender = source.getGender().gender;
        smokingRecord = source.getSmokingRecord().toString();
        pastMedicalHistory = source.getPastMedicalHistory().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        allergies.addAll(source.getAllergies().stream()
                .map(JsonAdaptedAllergy::new)
                .collect(Collectors.toList()));
        medicines.addAll(source.getMedicines().stream()
                .map(JsonAdaptedMedicine::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final Logger logger = LogsCenter.getLogger(JsonAdaptedPerson.class);
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Allergy> personAllergies = new ArrayList<>();
        for (JsonAdaptedAllergy allergy : allergies) {
            personAllergies.add(allergy.toModelType());
        }

        final List<Medicine> personMedicines = new ArrayList<>();
        for (JsonAdaptedMedicine medicine : medicines) {
            personMedicines.add(medicine.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (identityNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentityNumber.class.getSimpleName()));
        }
        if (!IdentityNumber.isValidId(identityNumber)) {
            throw new IllegalValueException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        final IdentityNumber modelIdentityNumber = new IdentityNumber(identityNumber);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (emergencyContact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmergencyContact.class.getSimpleName()));
        }
        if (!EmergencyContact.isValidEmergencyContact(emergencyContact)) {
            throw new IllegalValueException(EmergencyContact.MESSAGE_FORMAT_CONSTRAINTS);
        }
        final EmergencyContact modelEmergencyContact = new EmergencyContact(emergencyContact);

        if (dateOfBirth == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDateFormat(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_FORMAT_CONSTRAINTS);
        }
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_PAST_DATE_CONSTRAINTS);
        }
        final DateOfBirth dob = new DateOfBirth(dateOfBirth);

        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BloodType.class.getSimpleName()));
        }
        if (!BloodType.isValidBloodType(bloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodType);

        if (smokingRecord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SmokingRecord.class.getSimpleName()));
        }
        if (!SmokingRecord.isValidSmokingRecord(smokingRecord)) {
            throw new IllegalValueException(SmokingRecord.MESSAGE_CONSTRAINTS);
        }
        final SmokingRecord modelSmokingRecord = new SmokingRecord(smokingRecord);

        if (alcoholicRecord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AlcoholicRecord.class.getSimpleName()));
        }
        if (!AlcoholicRecord.isValidAlcoholicRecord(alcoholicRecord)) {
            throw new IllegalValueException(AlcoholicRecord.MESSAGE_CONSTRAINTS);
        }
        final AlcoholicRecord modelAlcoholicRecord = new AlcoholicRecord(alcoholicRecord);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (pastMedicalHistory == null) {
            logger.warning(() -> String.format("JsonAdaptedPerson: missing PastMedicalHistory for name='%s'", name));
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PastMedicalHistory.class.getSimpleName()));
        }
        if (!PastMedicalHistory.isValidPastMedicalHistory(pastMedicalHistory)) {
            throw new IllegalValueException(PastMedicalHistory.MESSAGE_CONSTRAINTS);
        }
        final PastMedicalHistory modelPastMedicalHistory = new PastMedicalHistory(pastMedicalHistory);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Set<Medicine> modelMedicines = new HashSet<>(personMedicines);

        final Set<Allergy> modelAllergies = new HashSet<>(personAllergies);

        return new Person(modelName, modelIdentityNumber, modelPhone, modelEmail, modelAddress, modelEmergencyContact,
                modelTags, dob, modelBloodType, modelAlcoholicRecord, modelGender, modelSmokingRecord,
                modelAllergies, modelPastMedicalHistory, modelMedicines);
    }

}

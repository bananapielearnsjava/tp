package seedu.address.ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Panel containing the viewed person's details.
 */
public class PersonViewPanel extends UiPart<Region> {

    private static final String FXML = "PersonViewPanel.fxml";

    // CSS Style classes
    private static final String TAG_STYLE_CLASS = "person-view-tag";
    private static final String NONE_TAG_STYLE_CLASS = "person-view-none-tag";
    private static final String VALUE_LABEL_STYLE_CLASS = "person-view-value-label";

    private final ObservableValue<Person> person;

    @FXML
    private Label defaultLabel;

    @FXML
    private VBox personViewContainer;

    @FXML
    private Label nameLabel;

    // FXML Labels for Personal and Medical Info
    @FXML
    private Label idLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emergencyContactLabel;
    @FXML
    private Label bloodTypeLabel;
    @FXML
    private Label alcoholicRecordLabel;
    @FXML
    private Label smokingRecordLabel;
    @FXML
    private Label pmhLabel;

    @FXML
    private FlowPane allergiesFlowPane;

    @FXML
    private FlowPane medicineFlowPane;

    @FXML
    private VBox upcomingAppointmentsBox;

    @FXML
    private VBox pastAppointmentsBox;

    /**
     * Creates a {@code PersonViewPanel} with the given {@code Person} and their separated appointment lists.
     *
     * @param person The person whose details are to be displayed.
     * @param upcomingAppointments The list of upcoming appointments associated with the person.
     * @param pastAppointments The list of past appointments associated with the person.
     */
    public PersonViewPanel(ObservableValue<Person> person, ObservableList<Appointment> upcomingAppointments,
                           ObservableList<Appointment> pastAppointments) {
        super(FXML);
        this.person = person;
        getRoot().getStylesheets().add(getClass().getResource("/view/PersonViewPanel.css").toExternalForm());
        this.person.addListener((observable, oldValue, newValue) -> {
            defaultLabel.setVisible(newValue == null);
            defaultLabel.setManaged(newValue == null);
            personViewContainer.setManaged(newValue != null);
            personViewContainer.setVisible(newValue != null);
            if (newValue != null) {
                fillPersonDetails(newValue);
            }
        });

        upcomingAppointments.addListener(new ListChangeListener<Appointment>() {
            @Override
            public void onChanged(Change<? extends Appointment> c) {
                populateAppointmentBox(upcomingAppointmentsBox, upcomingAppointments);
            }
        });
        pastAppointments.addListener(new ListChangeListener<Appointment>() {
            @Override
            public void onChanged(Change<? extends Appointment> c) {
                populateAppointmentBox(pastAppointmentsBox, pastAppointments);
            }
        });
    }

    /**
     * Fills in the person's details for viewing.
     */
    private void fillPersonDetails(Person person) {
        nameLabel.setText(person.getName().fullName);

        // Personal Info
        idLabel.setText("Identity Number: " + person.getIdentityNumber().identityNumber);
        dobLabel.setText("Date of Birth: " + person.getDateOfBirth().toString()
                + " (" + person.getDateOfBirth().calculateAge() + " yrs old)");
        genderLabel.setText("Gender: " + person.getGender().toString());
        phoneLabel.setText("Phone: " + person.getPhone().value);
        emailLabel.setText("Email: " + person.getEmail().value);
        addressLabel.setText("Address: " + person.getAddress().value);

        // Medical Info
        emergencyContactLabel.setText("Emergency Contact: " + person.getEmergencyContact().toString());
        bloodTypeLabel.setText("Blood Type: " + person.getBloodType().toString());
        alcoholicRecordLabel.setText("Alcoholic Record: " + person.getAlcoholicRecord().toString());
        smokingRecordLabel.setText("Smoking Record: " + person.getSmokingRecord().toString());
        pmhLabel.setText("Past Medical History: " + person.getPastMedicalHistory().toString());


        // Lists
        Set<String> allergyValues = person.getAllergies().stream()
                .map(a->a.allergyName)
                .collect(Collectors.toSet());
        Set<String> medicineValues = person.getMedicines().stream()
                .map(m->m.medicine)
                .collect(Collectors.toSet());
        populateFlowPane(allergiesFlowPane, allergyValues);
        populateFlowPane(medicineFlowPane, medicineValues);
    }

    /**
     * Fills in the person's appointments using the pre-filtered and sorted lists.
     * Clears previous appointment content before adding new items.
     *
     * @param upcomingAppointments The list of upcoming appointments (sorted chronologically).
     * @param pastAppointments The list of past appointments (sorted reverse chronologically).
     */
    private void fillAppointments(ObservableList<Appointment> upcomingAppointments,
                                  ObservableList<Appointment> pastAppointments) {
        populateAppointmentBox(upcomingAppointmentsBox, upcomingAppointments);
        populateAppointmentBox(pastAppointmentsBox, pastAppointments);
    }

    /**
     * Populates the given VBox with appointment labels from the provided list.
     * If the list is empty or null, adds a "None" label.
     *
     * @param box The VBox to populate.
     * @param appointments The list of appointments to display.
     */
    private void populateAppointmentBox(VBox box, ObservableList<Appointment> appointments) {
        box.getChildren().clear();

        if (appointments == null || appointments.isEmpty()) {
            Label noneLabel = new Label("None");
            noneLabel.getStyleClass().add(NONE_TAG_STYLE_CLASS);
            box.getChildren().add(noneLabel);
        } else {
            for (Appointment appointment : appointments) {
                Label apptLabel = new Label(formatAppointmentForDisplay(appointment));
                apptLabel.getStyleClass().add(VALUE_LABEL_STYLE_CLASS);
                apptLabel.setWrapText(true);
                box.getChildren().add(apptLabel);
            }
        }
    }

    /**
     * Formats an appointment's date, time, and notes for display in the view panel.
     *
     * @param appointment The appointment to format.
     * @return A formatted string representation.
     */
    private String formatAppointmentForDisplay(Appointment appointment) {
        String dateTimeStr = appointment.getDateTime().toString();
        String notesStr = appointment.getNotes().toString();
        return dateTimeStr + (notesStr.isEmpty() ? "" : ": " + notesStr);
    }

    /**
     * Populates a FlowPane with styled labels from a Set of items (like Allergies or Medicines).
     * If the set is empty or null, it adds a "None" label.
     *
     * @param pane The FlowPane to populate.
     * @param items The Set of items to display. Items should have a meaningful `toString()` representation.
     */
    private void populateFlowPane(FlowPane pane, Set<?> items) {
        pane.getChildren().clear();

        if (items == null || items.isEmpty()) {
            Label noneLabel = new Label("None");
            noneLabel.getStyleClass().add(NONE_TAG_STYLE_CLASS);
            pane.getChildren().add(noneLabel);
            return;
        }

        List<String> sortedItemStrings = items.stream()
                .map(Object::toString)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        for (String itemStr : sortedItemStrings) {
            Label itemLabel = new Label(itemStr);
            itemLabel.getStyleClass().add(TAG_STYLE_CLASS);
            pane.getChildren().add(itemLabel);
        }
    }
}

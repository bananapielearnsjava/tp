---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# HealthNote Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is heavily adapted from the **AddressBook-Level3 (AB3)** project, which was created by the
[SE-EDU initiative](https://se-education.org/) as a teaching resource for the CS2103T module at the National University of Singapore.

* We have reused and adapted its core architecture (Model-View-Controller, Logic, Storage), UI framework, and the structure of this Developer Guide.
* All diagrams in this guide are generated using [PlantUML](https://plantuml.com/).

This project also makes use of the following third-party libraries:

* [JavaFX](https://openjfx.io/): For the Graphical User Interface (GUI).
* [Jackson](https://github.com/FasterXML/jackson): For serializing and deserializing data to and from JSON format.
* [JUnit5](https://junit.org/junit5/): For unit testing.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside components being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java).

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g., `CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/resources/view/MainWindow.fxml).

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Appointment` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" />

The `Model` component,

* stores the address book data i.e., all `Person` and `Appointment` objects (which are contained in a `UniquePersonList` object and an `UniqueAppointmentList` object).
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* each `Person` and `Appointment` store a common reference of `IdentityNumber`

The object diagram below illustrates the important parts of how `ModelManager` is structured and how `UI` interacts with it.

<puml src="diagrams/ModelUiObjectDiagram.puml" width="1000" />

The `ModelManager`,

* stores a list of `Person` objects called `FilteredPersonList`. This is the list that is displayed in the `PersonListPanel` in `UI`
* stores 2 lists of `Appointments` objects sorted by time, one which is `SortedAllUpcomingAppointments` and another `SortedAllPastAppointments`. These are the lists displayed by `AppointmentListPanel` in `UI`
* stores a `Person` object called `ViewedPerson` which is the person currently being viewed in the `PersonViewPanel` in `UI`
* stores another 2 lists of `Appointments` objects filtered to current `ViewedPerson` object, one is `SortedViewedPersonUpcomingAppointments` and `SortedViewPersonPastAppointments`
* exposes the above components to outsiders as unmodifiable `ObservableList`s and `ObservableValue` that can be 'observed'. The UI components are bound to them and automatically update when the data changes.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** The above object diagram does not follow the conventional pattern as everything shown above follows a singleton pattern, where only one instance of its class should exist. 

</box>

<box type="info" seamless>

**Note:** The above diagrams are simplified to show the most important components and their main associations. Below is a detailed class diagram of Person and Appointment.

<puml src="diagrams/PersonAndAppointmentClassDiagram.puml" width="1000" />

**Note:** An alternative (arguably, a more OOP) model is given below. Where `ViewedPerson` stores a reference to the currently viewed `Person` and all their `Appointments` in the necessary order.<br>

<puml src="diagrams/ViewedPersonClassDiagram.puml" width="500" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-F11-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`).

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Design considerations**

### Input Validation Rationale

Our input validation is designed to be flexible and user-centred, avoiding overzealous validation that blocks legitimate inputs. 
Since doctors often need to record varied and free-form information, we only reject inputs that would cause functional issues (e.g. empty required fields or unparseable dates) while allowing all other characters and symbols.

For fields marked “must not be blank”, any non-empty input is accepted, allowing symbols such as `*`, `/`, `[ ]`, `{ }`, `#`, and `&`. **This is intentional, and not a feature flaw, nor an oversight in our design consideration.**
This enables users to record data naturally (e.g. Fracture* – left arm, [Mother] 9888-3333 (Office)) without being restricted by unnecessary formatting rules.

In contrast, structured fields like identity number, email, date of birth, and appointment date/time apply format checks only to ensure application operations such as searching, parsing, and sorting remain reliable.

Overall, our approach is to be permissive unless functionality is at risk. We will not restrict values if doing so does not add operational benefit.
This strikes a balance between robustness and usability, preventing over-restriction while keeping data entry smooth and efficient for real-world medical use.

| **Field**                     | **Allowed Example Inputs (with symbols & numbers)**                                    | **Justification and Real-World Basis**                                                                                                                                                           |
|-------------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **n\NAME**                    | `Dr. Alex (Sr.)`, `John Paul the 2nd`, `Damith s/o Sankar-Ashish`, `X Æ A-Xii`         | Real names may include titles, numbers, and punctuation (e.g. *Pope John Paul II*, *Damith s/o Sankar-Ashish*). Allowing all symbols and digits supports inclusivity and accurate record-keeping with flexibility. |
| **id\IDENTITY_NUMBER**        | `S1234567A`, `060402-06-6767`, `A.1234.567`                                            | Identity numbers across systems (passport, hospital, national) may contain letters, digits, hyphens, and dots. Allowing these ensures flexibility while maintaining parseable structure.         |
| **p\PHONE_NUMBER**            | `9888-3333 (Office)`, `[Home] 6222*3333`, `+65 8123 4567`                              | Phone entries often include country codes, separators, or contextual notes. Requiring only two consecutive digits ensures validity without limiting natural formatting.                          |
| **e\EMAIL**                   | `nurse.jane_doe+ward@clinic.com`, `dr.lee77@hospital.sg`                               | `+`, `_`, `.`, and digits are valid in real institutional emails (e.g. `alex+lab@hospital.org`). Restricting them would wrongly reject legitimate addresses.                                       |
| **addr\HOME_ADDRESS**         | `#02-123 Blk 45 Clementi Rd`, `{Unit 3B} 88 Hilltop Ave`                               | Addresses use `#`, `-`, numbers, and brackets (e.g. *#02-45 Blk 88*). These are standard in local and international postal formats.                                                              |
| **ec\EMERGENCY_CONTACT**      | `[Mother] 9888-3333 (Office)`, `[Dr. Tan {Clinic}] 9000-1111`, `[Friend 2] 9333-2222`  | Emergency contacts often include relationship labels or numbering. The flexible format `[relationship] number` mirrors real hospital data entry patterns.                                        |
| **dob\DATE_OF_BIRTH**         | `12/10/1987`, `5.5.2000`, `23-09-1975`                                                 | Restricting to numeric formats with `-`, `/`, or `.` ensures proper parsing while supporting international date conventions.                                                                     |
| **b\BLOOD_TYPE**              | `A+`, `A Rh(D)−`, `Bombay (hh)`, `A/B`, `A*`, `B?`, `Rh(- -)`                          | Blood typing notation often includes symbols like `+`, `-`, `/`, `()`, `*`, and `?` to mark antigen variants, uncertainty, or subgroups (e.g. `A3`, `Rh(- -)`). Allowing such inputs prevents rejection of legitimate lab formats. |
| **g\GENDER**                  | `Male`, `Female`, `Non-binary`, `X`, `M/F`, `M→F`, `Trans♀`, `Other (specify: &fluid)` | Gender identity and administrative codes can include symbols, arrows, or parentheses (e.g. `M→F`, `X`, `Trans*`). Supporting these improves inclusivity and aligns with modern EHR standards (e.g. Epic, Cerner). |
| **ar\ALCOHOLIC_RECORD**       | `None`, `2–3x/week`, `Quit & Recovered`, `Occasional (1 glass)`                        | Lifestyle entries often include numbers and punctuation for frequency or context. Flexibility improves expressiveness in clinical notes.                                                         |
| **sr\SMOKING_RECORD**         | `Quit (2010)`, `Occasional / Social`, `2/day`                                          | Smoking history fields commonly use digits for frequency or cessation year, and symbols like `/` or `()`.                                                                                        |
| **pmh\PAST_MEDICAL_HISTORY**  | `Asthma (mild)`, `Fracture* – left arm`, `Type-2 Diabetes`                             | Medical conditions often contain digits or shorthand symbols (e.g. `Type-2`, `Stage III`). Allowing punctuation supports real medical documentation.                                             |
| **t\TAG**                     | `[Follow-Up]`, `VIP*`, `{HighRisk-3}`, `Urgent #2`                                     | Tags may include brackets, hashes, or numbers for sorting or prioritisation. Flexible tagging supports varied hospital workflows.                                                                |
| **al\ALLERGY**                | `Penicillin 2+`, `Peanut (Severe*)`, `Dust & Pollen`                                   | Allergy records may contain parentheses, severity markers, or numbering. Such notation reflects real-world allergy charting.                                                                     |
| **m\MEDICINE**                | `2 Panadol/day`, `500mg Ibuprofen (AM & PM)`, `*Insulin – sliding scale*`              | Medicine instructions require digits, units, and symbols to describe dosage and timing. These are standard in prescriptions and MARs.                                                            |
| **adt\APPOINTMENT_DATE_TIME** | `13-10-2025 10:00`, `12/10/2025 09:30`, `5.5.2024 14:00`                               | Accepts structured date-time formats using `-`, `/`, or `.` for consistent parsing and scheduling.                                                                                               |
| **note\APPOINTMENT_NOTE**     | `Check BP & HR`, `[Follow-up for MRI]`, `{Review meds}`, `Pain 8/10`                   | Notes naturally contain numbers and symbols for shorthand or emphasis (e.g. `Pain 8/10`, `[Lab Result] pending`). Allowing all supports free-form clinical notation.                             |



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Difficulty Level
The difficulty of this project was high. The most difficult feature to implement was the `Appointments` feature,
as it required transitioning AB3 from a single-entity to a multi-entity application. In AB3, only `Person`
objects were involved whereas in HealthNote, both `Person` and `Appointment` objects are created, displayed, and used
simultaneously. Adding a new section to the UI to display these appointments required new internal lists within 
`Model` and new methods to manage them as well.

Linking each `Person` to their corresponding `Appointment`(s) introduced another layer of complexity, as changes to a 
`Person` object would also affect their associated `Appointment`(s). For example, when a `Person` is deleted from the `Person List`, 
all of their related `Appointment`s must also be deleted from the Appointments list. 


### Challenges Faced
Our team faced several technical and workflow challenges:

**Initial Learning Curve**: As a team new to a large codebase, the initial learning curve was steep. We had to quickly
learn and understand the design patterns, such as the MVC and Observer pattern used in AB3, before they were covered
in the weekly topics. This required a lot of planning and justification of our design choices in order to ensure that 
the existing design of AB3 was not broken. 


**Adhering to Design**: We spent a considerable amount of time planning and justifying our design choices to ensure 
that the existing design of AB3 was not broken. For example, our view command required `UI` to interact with `Logic` 
and `Model` to display a specific `Person` in the new `PersonViewPanel` without violating the `MVC` pattern. We had to 
carefully plan how the selected `Person` to be viewed would be passed from `Model` to `UI` and how the `MainWindow` 
would be updated.


**Cascading Test Failures**: The massive number of tests used in AB3 meant that simple changes to commonly used classes
such as those in `command` and `person` broke over 50 JUnit tests at once, all of which had to be manually traced
and updated. 


**Parallel Branches and Merge Conflicts**: Our team worked on multiple features in parallel. Adding numerous new fields
to `Person` in separate pull requests created a large number of merge conflicts, mostly involving the edited JUnit tests. 
Resolving these conflicts was very tedious and time-consuming as we could not simply accept the incoming or current 
branch without editing the code manually.


### Effort Required
We iterated on the four main AB3 components, which saved us the effort of building a functional address book
from the ground up. However, the effort in adapting it to HealthNote was still substantial. 

**Model**: We modified the `Person` class significantly, adding many new fields that fit our target user such as `BloodType`, 
`PastMedicalHistory`, and `Allergy`. We also designed and implemented new entities, including `Appointment` and 
`AppointmentList`.

**Storage**: We created a new `JsonAdaptedAppointment` class to store appointments locally, and updated the 
`JsonAdaptedPerson` class for our new fields.

**Logic**: We wrote new Command and Parser classes for all new features, such as `AddAppointmentCommand`, 
`DeleteUpcomingAppointmentCommand`, `DeletePastAppointmentCommand`, `ViewCommand`, and `ThemeCommand`.

**UI**: We created several new UI components to display the appointment list and patient information. This includes
the `AppointmentCard`, `AppointmentListPanel`, and `PersonViewPanel` for the `view` command.

**Testing**: A large portion of our effort was dedicated to testing. This involved updating hundreds of existing
AB3 tests to work with our new Person model and writing more testcases to test our new commands, parsers and model classes.


### Achievements
Despite the challenges, our team successfully:

- Transformed AB3 from a generic address book into a specialized, domain-specific application for home healthcare providers.
- Designed and implemented a relational data model, successfully linking Patient entities to their corresponding Appointments.
- Mastered and extended the AB3 architecture, applying its design patterns to new and complex features.
- Delivered an extensive and useful feature set for our target user, such as patient medical records, appointment 
management and a detailed patient view panel. We also added different themes to enhance the overall user experience and aesthetics.


## **Appendix: Planned Enhancements**

Team Size: 5

1. **Save user set theme**:
   In the current implementation, if the user has set a theme, by executing the command: `theme pink` for example, the set
   theme does not persist once the user exits and relaunches the application. We plan to store the user set theme in
   `UserPrefs`. When the application launches, the user's theme will be fetched and set during UI initialisation.
   When the user sets a new theme, this data will be updated.


2. **More fit error message for `schedule` command**:
   The current error message when the `schedule` command is executed with an index but missing or invalid prefixes
   is `You must provide an index and it has to be a non-zero unsigned integer.` This error message occurs because the
   parser parses the invalid prefix together with the index and throws an exception as it sees it as not valid. Therefore,
   we plan to make the error messages for this command more specific and accurate, by mentioning the reason for
   failure. These reasons include, but are not limited to, missing non-optional parameters, and invalid parameters
   provided. An example of such an error message is `Command could not be executed due to missing parameter:
   adt\` or `Command could not be executed due to unrecognised parameter(s): a\, b\ `


3. **Warn Overlapping Appointments:**
   The current implementation allows the user to create two different appointments at the same time and date for
   **different patients.** For example, an appointment may be scheduled for patient `A` at time `25-08-2025 20:00`,
   and another appointment for the same time may also be scheduled for another patient `B`. We did not stop this from
   happening as it could be the intended action of the user. However, there is also a possibility that the user had
   overlooked their schedule and did not intend to add two different appointments with the same time. Therefore, we plan
   to add a message to notify the user if this occurs.


4. **Live updates for appointment lists:**
   In the current implementation, the appointment list does not update automatically when the time of an upcoming
   appointment has passed while the application is still open. The temporary solution to this is to exit and reopen the
   application again. However, this may be annoying to users if they have to do it frequently. Therefore, we plan to
   make the appointment list time-sensitive and update live according to the local date and time.


5. **Add Name on `Appointment` Cards in the appointments list**:
   Currently, the `Appointment` cards inside the appointments list only show the associated patient's ID and appointment
   notes. We figured that it would be much more useful if these appointment cards could also show the patient's names too.
   Therefore, we plan to add patient names to the `Appointment` UI element.


6. **Wrap tag text:**
   Currently, the UI does not wrap the text for tag elements in the `PatientCard` inside the `PatientListPanel` and inside
   the `Patient View Panel` (e.g., `Allergies`, `Medicines`). Furthermore, there is no other way to view tags in a way that would display their full
   content. We did not anticipate tags to have many characters, so we overlooked this in our implementation.
   Therefore, we plan to have a way to view the full tag, whether through text wrapping or inside the `PatientViewPanel`, and
   tweak the UI to be more user-friendly and wrap other overflowing tag elements.


7. **Make certain fields in the `add` command optional:**
   We recognised that our target user may want to quickly add a patient to their record and leave out the additional details
   for a later time. While most fields pertaining to medical information are already optional for this command, such as
   `ar\`, `sr\`, and `pmh\`, we are planning to make other less important fields optional too. Which fields specifically
   to make optional is still up for debate, as there are values and valid arguments from both sides. Our planned decision
   for now is to make all fields other than `Name`, `Identity Number`, `Phone Number`, and `Emergency Contact` optional.


8. **More specific error message for `add` command:**
   In the current implementation, the error message displayed when the `add` command is executed with missing non-optional
   fields or with a prefix but no value is "Invalid Command Format!". We plan to make this error message more descriptive
   and helpful to the user mentioning the cause of the error and why the command is invalid.

9. **More specific error message for `unschedule` and `forget` command**:
   Currently, the error message when executing the `unschedule` and `forget` command when their respective appointments 
   list are empty is `The appointment index provided is invalid. Please refer to the past appointments list.` We believe
   that this error message can be improved to inform the user that the list is empty and there are no appointments to delete.

10. **Better UI for showing appointments in view panel**:
   The current UI does not nicely show a person's appointments. Moreover, if the appointment notes are too long the 
   text may overflow. Therefore, we plan to improve the UI of showing appointments inside the person view panel 
   to show the full appointment card together with time and notes.


---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

***General Characteristics***:

* independent home-care doctor, often making home visits
* has a need to manage a significant number of patients with diverse conditions
* works with limited resources (e.g., no receptionist, no nurse), self-services features are a must

***Technical Characteristics***:
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* needs reliable offline mode

***Workflow Challenges***:
* time pressure during home visits, needs to manage patients quickly
* juggle between patient's contact details, medical history, appointments
* needs to track follow-up appointments, medication schedules

**Value proposition**:

Helps independent doctors manage their patients and schedule more efficiently using a keyboard-focused UI.
It is optimised for more tech-savvy doctors who prefer using a CLI.
Enables quick retrieval of patient’s records, especially useful when they are always on the move.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority   | As a...              | I want to...                                                 | So that I can...                                                     |
|------------|----------------------|--------------------------------------------------------------|----------------------------------------------------------------------|
| `* * *`    | New User             | view user guide                                              | learn how to use the product whenever I need to                      |
| `* * *`    | Doctor               | add a patient's name                                         | identify the patient correctly                                       |
| `* * *`    | Doctor               | add a patient's identity number                              | uniquely distinguish patients with similar names                     |
| `* * *`    | Doctor               | add a patient's phone number                                 | contact the patient when needed                                      |
| `* * *`    | Doctor               | add a patient's email address                                | send medical updates or reports conveniently                         |
| `* * *`    | Doctor               | add a patient's home address                                 | send physical documents or conduct home visits                       |
| `* * *`    | Doctor               | attach emergency contacts with relationship                  | call the right person when there is an emergency                     |
| `* * *`    | Doctor               | view patients' blood type                                    | assure transfusion                                                   |
| `* * *`    | Doctor               | see and update a patient’s drug allergies                    | prevent administering harmful medications                            |
| `* * *`    | Doctor               | view my patients' past health condition                      | gain an understanding of what may cause their current problem        |
| `* * *`    | Doctor               | search for patients by name or ID                            | locate their records efficiently                                     |
| `* * *`    | Doctor               | view my patients' current condition                          | administer the correct treatment                                     |
| `* * *`    | Doctor               | delete outdated patient records                              | keep the patient records clean                                       |
| `* * *`    | Doctor               | have a quick GUI summary on patient records                  | get a refresher on the patient's condition before appointment        |
| `* * *`    | Doctor               | add in the patient record whether they are a smoker          | keep in mind if they have higher risk of certain diseases            |
| `* * *`    | Doctor               | add in the patient record whether they are an alcoholic      | keep in mind if they have higher risk of certain diseases            |
| `* * *`    | Doctor               | retrieve previously inputted records after reopening the app | input records and retrieve them again without them being lost        |
| `* * *`    | Fast typing CLI user | use short command aliases                                    | retrieve data needed easily                                          |
| `* * *`    | Forgetful user       | view available commands                                      | know what command to be used                                         |
| `* *`      | Doctor               | view my past appointments records                            | maintain a complete appointment history for accurate tracking        |
| `* *`      | Doctor               | check my upcoming appointments                               | schedule my day easier and faster                                    |
| `* *`      | Doctor               | view patients tagged with certain conditions                 | filter and prioritize cases more easily                              |
| `* *`      | Doctor               | add my patient's gender to the profile                       | ensure accurate medical records and provide gender-specific care     |
| `* *`      | Meticulous doctor    | add special notes to each appointment                        | add in useful information that I should remember                     |
| `* *`      | Doctor               | detect potential duplicate ID                                | merge records safely                                                 |
| `* *`      | Forgetful doctor     | recover recently deleted records                             | recover the data after I use delete command                          |
| `* *`      | Clumsy doctor        | confirm before I delete records                              | avoid accidentally deleting records                                  |
| `* *`      | Doctor               | add a patient's age and date of birth                        | obtain their age for age-specific medical assessments and treatments |
| `*`        | Doctor               | add a patient's dietary restrictions                         | provide informed medical advice and ensure safe treatment plan       |
| `*`        | Doctor               | pull up records of a disease                                 | check previously successful treatment plans                          |

### Use cases

(For all use cases below, the **System** is the `HealthNote` and the **Actor** is the `user`, unless specified otherwise)

#### **Use case: UC01 - Delete a patient**

Preconditions: <br>
`The actor has access to the patient list and knows the index of the patient they want to delete.`

**MSS**

1. Actor requests to delete a specific patient in the list.
2. System deletes the patient.
3. System shows a confirmation message. 
4. System updates the displayed patient list.

    Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC02 - Add a patient**

**MSS**

1.  Actor requests to add a patient with required parameters.
2.  System adds the patient to the system.
3.  System shows a confirmation message including the details of the added patient.
4.  System updates the displayed patient list.

    Use case ends.

**Extensions**

* 1a. Actor makes an invalid input.

    * 1a1. System shows an error message.

      Use case ends.

* 1b. Duplicate identity number detected.

    * 1b1. System shows an error message.

      Use case ends.

#### **Use case: UC03 - View all commands**

**MSS**

1.  Actor types a command to view all available commands.
2.  System retrieves the list of commands supported.
3.  System displays the list of commands.

    Use case ends.

**Extensions**

* 2a. The list of commands cannot be retrieved due to some error.

    * 2a1. System shows an error message.

      Use case ends.

#### **Use case: UC04 - Edit a patient**

Preconditions: <br>
`The actor has access to the patient list and knows the index of the patient they want to edit.`

**MSS**

1. Actor requests to edit a patient with required parameters.
2. System edits the patient in the system.
3. System shows a confirmation message. 
4. System updates the displayed patient list.

    Use case ends.

**Extensions**

* 1a. Actor makes an invalid input.

    * 1a1. System shows an error message.

      Use case ends.

* 1b. Duplicate identity number detected.

    * 1b1. System shows an error message.

      Use case ends.

#### **Use case: UC05 - List patients**

**MSS**

1. Actor requests to list all patients.
2. System shows a confirmation message.
3. System lists all the patients.

    Use case ends.

#### **Use case: UC06 - Find patients**

**MSS**

1. Actor requests to find patients with required parameters.
2. System shows a confirmation message.
3. System lists the matching patients in the system.

    Use case ends.

**Extensions**

* 1a. Actor makes an invalid input.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC07 - Clear all entries**

**MSS**

1.  Actor requests to clear all entries.
2.  System shows a confirmation message.
3.  System clears all entries.

    Use case ends.

**Extensions**

* 1a. The command entered is invalid.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC08 - Change theme**

**MSS**

1.  Actor requests to change the theme with required argument.
2.  System shows a confirmation message.
3.  System changes the theme.

    Use case ends.

**Extensions**

* 1a. Actor makes an invalid input.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC09 - Schedule an appointment**

**MSS**

1.  Actor requests to schedule an appointment with required parameters.
2.  System adds the appointment to the system.
3.  System shows a confirmation message.
4.  System updates the displayed upcoming appointment list.

    Use case ends.

**Extensions**
* 1a. Actor makes an invalid input.

    * 1a1. System shows an error message.

      Use case ends.

* 1b. Appointment time clashes with an existing appointment for a particular patient.

    * 1b1. System shows an error message.

      Use case ends.

#### **Use case: UC10 - Delete a past appointment**

**MSS**

1.  Actor requests to delete a past appointment with specified index.
2.  System removes the corresponding appointment.
3.  System shows a confirmation message.
4.  System updates the displayed past appointment list.

    Use case ends.

**Extensions**
* 1a. The given index is invalid.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC11 - Delete an upcoming appointment**

**MSS**

1.  Actor requests to cancel an upcoming appointment with specified index.
2.  System removes the corresponding appointment.
3.  System shows a confirmation message.
4.  System updates the displayed upcoming appointment list.

    Use case ends.

**Extensions**
* 1a. The given index is invalid.

    * 1a1. System shows an error message.

      Use case ends.

#### **Use case: UC12 - Exit application**

**MSS**

1.  Actor requests to exit the application.
2.  System saves all data and user preferences automatically.
3.  System closes the application window.

    Use case ends.

**Extensions**

* 2a. Saving data or user preferences fails.
    * 2a1. System shows an error message.

      Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java 17 or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The product should be for a single user.
5.  The product should be packaged into a single `.jar` file for ease of execution.
6.  The product file size should remain below 100 MB to ensure efficient storage and distribution.
7.  The GUI should display correctly and without layout issues on standard screen resolutions (1920×1080 and higher) at 100% and 125% scale.
8.  The GUI should remain usable (i.e., all functions accessible even if layout is suboptimal) at 1280×720 resolution and 150% scale.
9.  A doctor with basic technical knowledge should be able to learn all commands within 30 minutes of using the application.
10. All patient data must be saved automatically and reliably to prevent data loss in case of unexpected application closure.
11. All patient data must be stored locally on the user’s computer and never transmitted over the internet.
12. The application should function offline without needing an internet connection.
13. The system should include JUnit tests covering at least 70% of the codebase to ensure long-term maintainability and prevent regressions.

### Glossary

* **Private contact detail**: A contact detail that is not meant to be shared with others

* **Mainstream OS**: Windows, Linux, Unix, MacOS

* **CLI (Command Line Interface)** — A text-based interface where users interact with the application by typing commands

* **GUI (Graphical User Interface)** — The visual interface of the application that displays patient information and appointment lists

* **Independent Doctor** — A medical professional who works independently, often making home visits and managing patients without support staff like receptionists or nurses

* **Identity Number** — A unique identifier for each patient (e.g., National ID, Passport Number) used to distinguish patients with similar names

* **Emergency Contact** — A designated person to contact in case of medical emergencies, including their relationship to the patient

* **Blood Type** — Classification of blood based on the presence or absence of antibodies and antigens (e.g., A, B, AB, O with +/- Rh factor)

* **Alcoholic Record** — Documentation of a patient's alcohol consumption habits

* **Smoking Record** — Documentation of a patient's smoking habits

* **Medical History (Past Medical History/PMH)** — Previous health conditions, diagnoses, or treatments that a patient has experienced

* **Tag** — A label attached to a patient record for categorization or prioritization (e.g., "priorityHigh", "diabetesFollowUp")

* **Allergy** — A substance or medication that causes an adverse reaction in the patient

* **Medicine** — Current medications that the patient is taking, including dosage information

* **Upcoming Appointment** — An appointment scheduled for a future date and time

* **Past Appointment** — An appointment that has already occurred (date and time in the past)

* **Index** — The position number of an item in a displayed list, starting from 1

* **Parameter** — A piece of information required by a command, specified using a prefix (e.g., `n\` for name, `p\` for phone)

* **Prefix** — A tag that identifies the type of information being provided in a command (e.g., `n\`, `id\`, `p\`)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.

    2. Double-click the jar file. If it doesn't work, use `java -jar healthnote.jar`.

       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

## Manual testing of patient commands

### Adding a person

1. Adding a person with all required fields

    1. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000`<br>
       Expected: New contact is added to the list. Details of the added contact shown in the status message.

    2. Test case: `add n\Jane Smith id\A9876543C`<br>
       Expected: No person is added. All required fields details should be provided.

    3. Test case: `add n\Jammie id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000`<br>
       Expected: No person is added. Error message indicates duplicate identity number.

    4. Other incorrect add commands to try: `add`, `add n\Test`, `add id\A1234567D` (missing required fields)<br>
       Expected: Error details shown in the status message.

2. Adding a person with optional fields

    1. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 t\priorityHigh`<br>
       Expected: New contact with tags is added to the list.

    2. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 al\nuts`<br>
       Expected: New contact with allergies is added to the list.

    3. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 m\100mg Panadol/day`<br>
       Expected: New contact with medicines is added to the list.

    4. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 ar\Social drinker`<br>
       Expected: New contact with alcoholic record is added to the list.

    5. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 sr\Heavy smoker`<br>
       Expected: New contact with smoking record is added to the list.

    6. Test case: `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 pmh\Diabetes`<br>
       Expected: New contact with past medical history is added to the list.

### Editing a person

1. Editing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `edit 1 p\91234567`<br>
       Expected: Phone number of first contact is updated. Details of the edited contact shown in the status message.

    3. Test case: `edit 1 id\A9999999Z`<br>
       Expected: Identity number of first contact is updated if no duplicate exists.

    4. Test case: `edit 1 al\Aspirin al\Ibuprofen`<br>
       Expected: Allergies are replaced with new list.

    5. Test case: `edit 0 p\91234567`<br>
       Expected: No person is edited. Error details shown in the status message.

    6. Other incorrect edit commands to try: `edit`, `edit x p\12345678` (where x is larger than the list size)<br>
       Expected: No person is edited. Error details shown in the status message.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

    3. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

### Viewing a person

1. Viewing detailed information of a person

    1. Prerequisites: Multiple persons in the list.

    2. Test case: `view 1`<br>
       Expected: Detailed view panel appears in the center showing all information about the first person, including:
        - Personal Details (Name, Identity Number, Date of Birth, Gender, Phone, Email, Address)
        - Medical Details (Emergency Contact, Blood Type, Alcoholic Record, Smoking Record, Past Medical History)
        - Allergies (if any)
        - Current Medicines (if any)
        - Upcoming Appointments
        - Past Appointments

    3. Test case: `view 0`<br>
       Expected: No person is viewed. Error message shown.

    4. Test case: `view x` (where x is larger than the list size)<br>
       Expected: Error message shown.

### Listing all persons

1. Listing all persons in the address book

    1. Test case: `list`<br>
       Expected: All persons in the address book are displayed in the person list panel.

    2. Test case after a `find` command: `list`<br>
       Expected: Resets the view to show all persons instead of filtered results.

### Finding a person

1. Finding persons by name keywords

    1. Prerequisites: Multiple persons can have the same or similar names; only identity numbers must be unique.

    2. Test case: `find John`<br>
       Expected: List shows all persons with "John" in their name (case-insensitive). This includes names like "John Doe", "Peter John", "Alice John".

    3. Test case: `find John Doe`<br>
       Expected: List shows all persons with "John" OR "Doe" in their name.

    4. Test case: `find`<br>
       Expected: Error message indicates invalid command format.

    5. Test case: `find xyz` (where no person has this name)<br>
       Expected: Empty list shown with "0 persons listed!" message.

2. Finding persons by identity number

    1. Prerequisites: Multiple persons in the list with unique identity numbers.

    2. Test case: `find A1234567B`<br>
       Expected: List shows the person with identity number "A1234567B". Only one person should be shown as identity numbers are unique.

    3. Test case: `find A12`<br>
       Expected: Error message or no results, as partial identity numbers are not supported. The full identity number must be provided.

    4. Test case: `find a1234567b` (lowercase)<br>
       Expected: List shows the person with identity number "A1234567B" if the search is case-insensitive for identity numbers.

## Manual testing of appointment commands

### Adding an appointment

1. Adding an appointment for a patient

    1. Prerequisites: At least one person in the list.

    2. Test case: `schedule 1 adt\13-10-2025 10:00 note\Needs IV Drip`<br>
       Expected: New appointment is added. Success message shown with appointment details.

    3. Test case: `schedule 1 adt\13-11-2025 10:00`<br>
       Expected: New appointment is added with empty notes.

    4. Test case: `schedule x adt\13-11-2025 10:00 note\Test` (where x is larger than the list size) <br>
       Expected: No appointment is added. Error message indicates invalid index.

    5. Test case: `schedule 1 adt\40-13-2025 note\Test`<br>
       Expected: No appointment is added. Error message indicates invalid date format.

2. Adding appointments using filtered list

    1. Test case: Execute `find John` to filter the list, then `schedule 1 adt\20-11-2025 11:00 note\X-ray`<br>
       Expected: Appointment is added for the first person in the filtered list (not necessarily the first person in the complete list).

3. Adding appointments with special characters in notes

    1. Test case: `schedule 1 adt\13-11-2025 10:00 note\Patient needs medication & IV drip, follow-up required!`<br>
       Expected: Appointment is added with the note containing special characters.

### Deleting an upcoming appointment

1. Deleting an appointment from the upcoming appointments list

    1. Prerequisites: At least one upcoming appointment exists in the system. Upcoming appointments are displayed in the right panel of the application.

    2. Test case: `unschedule 1`<br>
       Expected: First upcoming appointment is deleted from the list. Success message shown with details of the deleted appointment. The appointment is removed from both the upcoming appointments panel and from the person's appointment list in the view panel (if viewing that person).

    3. Test case: `unschedule 0`<br>
       Expected: No appointment is deleted. Error message indicates invalid index (must be a positive integer).

    4. Test case: `unschedule x` (where x is larger than the upcoming appointment list size)<br>
       Expected: No appointment is deleted. Error message indicates index is out of range.

2. Deleting appointments and verifying removal

    1. Prerequisites: At least one person with upcoming appointments.

    2. Test case: Execute `view 1` to view the first person's details, note the upcoming appointments displayed in the person's view panel<br>
       Expected: Person's details are displayed with all upcoming appointments listed.

    3. Test case: Check the upcoming appointments panel on the right side of the application to find the index of the upcoming appointment for that person you want to delete<br>

    4. Test case: Execute `unschedule x` (x is the index you note from the appointment lists that you want to delete)<br>
       Expected: Appointment is deleted. Success message shown: "Deleted Appointment: [appointment details]".

    5. Test case: Execute `view 1` again to refresh the person's view<br>
       Expected: The deleted appointment is no longer shown in the person's upcoming appointments list.

    6. Test case: Verify in the upcoming appointments panel<br>
       Expected: The deleted appointment is also removed from the upcoming appointments list on the right panel.

### Deleting a past appointment

1. Deleting an appointment from the past appointments list

    1. Prerequisites: At least one past appointment exists in the past appointment list.

    2. Test case: `forget 1`<br>
       Expected: First past appointment is deleted from the list. Success message shown with details of the deleted appointment. The appointment is removed from both the past appointments panel and from the person's appointment list in the view panel (if viewing that person).

    3. Test case: `forget 0`<br>
       Expected: No appointment is deleted. Error message indicates invalid index (must be a positive integer).

    4. Test case: `forget x` (where x is larger than the past appointment list size)<br>
       Expected: No appointment is deleted. Error message indicates index is out of range.

2. Deleting past appointments and verifying removal

    1. Prerequisites: At least one person with past appointments.

    2. Test case: Execute `view 1` to view the first person's details, note the past appointments displayed in the person's view panel<br>
       Expected: Person's details are displayed with all past appointments listed.

    3. Test case: Check the past appointments panel on the right side of the application to find the index of the past appointment for that person you want to delete<br>

    4. Test case: Execute `forget x` (x is the index you note from the past appointment list that you want to delete)<br>
       Expected: Appointment is deleted. Success message shown: "Deleted Appointment: [appointment details]".

    5. Test case: Execute `view 1` again to refresh the person's view<br>
       Expected: The deleted appointment is no longer shown in the person's past appointments list.

    6. Test case: Verify in the past appointments panel<br>
       Expected: The deleted appointment is also removed from the past appointments list on the right panel.

## Manual testing of general commands

### Clearing all entries

1. Clearing all data from the address book with confirmation

    1. Prerequisites: At least one person in the list.

    2. Test case: `clear`<br>
       Expected: No data is cleared. Message displayed: "Invalid command format! clear: Clears all data in HealthNote. To confirm, type 'clear CONFIRM'. This action cannot be undone."

    3. Test case: `clear CONFIRM`<br>
       Expected: All persons and appointments are removed. Success message shown: "HealthNote has been cleared!". Person list panel is empty. Upcoming and past appointments panels are empty.

    4. Test case: `clear confirm` (lowercase)<br>
       Expected: No data is cleared. Error message indicating invalid command format. The confirmation keyword must be in uppercase "CONFIRM".

    5. Test case: `clear YES` or `clear CONFIRM extra`<br>
       Expected: No data is cleared. Error message indicating invalid command format. Only "CONFIRM" in uppercase is accepted.

2. Clearing empty address book

    1. Prerequisites: Address book is already empty (no persons or appointments).

    2. Test case: `clear CONFIRM`<br>
       Expected: Success message still shown: "HealthNote has been cleared!". Address book remains empty.

### Changing theme

1. Switching between different themes

    1. Prerequisites: Application is running.

    2. Test case: `theme dark`<br>
       Expected: Application theme changes to dark theme. Success message "Theme changed to dark." displayed.

    3. Test case: `theme light`<br>
       Expected: Application theme changes to light theme. Success message "Theme changed to light." displayed.

    4. Test case: `theme blue`<br>
       Expected: Application theme changes to blue theme. Success message "Theme changed to blue." displayed.

    5. Test case: `theme pink`<br>
       Expected: Application theme changes to pink theme. Success message "Theme changed to pink." displayed.

    6. Test case: `theme DARK` (uppercase)<br>
       Expected: Application theme changes to dark mode (command should be case-insensitive). Success message displayed.

    7. Test case: `theme invalid`<br>
       Expected: Error message "Invalid theme name. Available themes: dark, light, blue, pink" displayed.

    8. Test case: `theme`<br>
       Expected: Error message indicating missing theme parameter.

### Getting help

1. Opening the help window

    1. Test case: `help`<br>
       Expected: Help window opens showing available commands.

    2. Test case: Press F1 key<br>
       Expected: Help window opens.

    3. Test case: Click on "Help" menu and select "Help F1"<br>
       Expected: Help window opens.

    4. Test case: `help 12345`<br>
       Expected: Help window opens.

### Exiting the application

1. Exiting via command

    1. Test case: `exit`<br>
       Expected: Application closes gracefully. All data is automatically saved.

2. Exiting via menu

    1. Test case: Click on "File" menu and select "Exit"<br>
       Expected: Application closes gracefully. All data is automatically saved.

3. Exiting via window close button

    1. Test case: Click the window close button (X)<br>
       Expected: Application closes gracefully. All data is automatically saved.

## Manual testing of data handling

### Saving data issues

1. Dealing with missing data files

   Test case:

    1. Go into the data folder which is in the same folder as the app's jar file. (The location is indicated in the bottom left corner of the application)
    2. Delete the file named `healthnote.json`.
    3. Relaunch the application.

   Expected: A new file with sample patient records is created. Sample patient records are displayed in the application.

2. Dealing with corrupted data files

   Test case:

    1. Go into the data folder which is in the same folder as the app's jar file. (The location is indicated in the bottom left corner of the application)
    2. Open the file named `healthnote.json`.
    3. Modify the file to simulate corruption. For instance, delete the first few lines from the file.
    4. Relaunch the application.
    5. Add a new a patient, using `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000`.

   Expected: At the end of step 4, the panel on the left is empty, and no patient records are displayed in the application. At the end of step 5, check to see that the corrupted json file has been overwritten to reflect the added person.

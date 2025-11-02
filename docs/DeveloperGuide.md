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

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

**`Main`** (consisting of classes [`Main`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` and `Appointment` objects (which are contained in a `UniquePersonList` object and an `UniqueAppointmentList` object).

* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* each `Person` and `Appointment` store a common reference of `IdentityNumber`

<puml src="diagrams/ModelUiObjectDiagram.puml" width="450" />
* stores the currently 'found' `Person` objects (e.g., results of a search query) as a separate _filtered_ list
* stores 2 lists of `Appointments` objects sorted by time, one which is `SortedAllUpcomingAppointments` and another `SortedAllPastAppointments`
* stores another 2 lists of `Appointments` objects filtered to current viewed `Person` object, one is `SortedViewedPersonUpcomingAppointments` and `SortedViewPersonPastAppointments`
* exposes the lists above to outsiders as an unmodifiable `ObservableList` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/nus-cs2103-AY2526S1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team Size: 5

1. **Save user set theme**:
In the current implementation, if the user has set a theme, by executing the command: `theme pink` for example, the set
theme does not persist once the user exits and relaunches the application. We plan to store the user set theme in 
`UserPrefs`. When the application launches, the user's theme will be fetched and set during UI initialisation. 
When the user sets a new theme, this data will be updated.


2. **More specific error message for `schedule` command**:
The current error message when the `schedule` command is executed with missing or invalid parameters is
`Invalid Command Format!` and it is too general. We plan to make the error message mention the reason for failure.
These reasons for failure include missing parameters, or invalid parameters provided.
For example: `Command could not be executed due to missing parameter: adt\` or `Command could not be executed due to
unrecognised parameter(s): a\, b\ `


3. **Warn Overlapping Appointments:**
The current implementation allows the user to create two different appointments at the same time and date for
**different patients.** For example, an appointment may be scheduled for patient `A` at time `25-08-2025 20:00`, 
and another appointment for the same time may also be scheduled for another patient `B`. We did not stop this from 
happening as it could be the intended action of the user. However, there is also a possibility that the user had 
overlooked their schedule and did not intend to add two different appointments with the same time. Therefore, we plan
to add a message to notify the user if this occurs.

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

***General Characteristics***:

* independent home-care doctor often making home visits
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

Helps independent doctors manager their patients and schedule more efficiently using a keyboard-focused UI.
It is optimised for more tech-savvy doctors who prefer using a CLI.
Enables quick retrieval of patient’s records, especially useful when they are always on the move.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority   | As a …​              | I want to …​                                                 | So that I can…​                                                      |
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

**Use case: UC01 - Delete a patient**

**MSS**

1.  User requests to <u>list patients (UC05)</u>
2.  HealthNote shows a list of patients
3.  User requests to delete a specific patient in the list
4.  HealthNote deletes the patient

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. HealthNote shows an error message.

      Use case resumes at step 2.

**Use case: UC02 - Add a patient**

**MSS**

1.  User requests to add a patient using the add command with required parameters.
2.  HealthNote adds the patient to the system.

    Use case ends.

**Extensions**

* 1a. One or more required parameters are missing.

    * 1a1. HealthNote shows an error message.

      Use case resumes at step 1.

* 1b. Input parameters are in invalid formats (e.g. date, email, gender).

    * 1b1. HealthNote shows an error message.

      Use case resumes at step 1.

* 1c. Duplicate identity number detected.

    * 1c1. HealthNote shows an error message.

      Use case resumes at step 1.

**Use case: UC03 - View all commands**

**MSS**

1.  User types a command to view all available commands
2.  HealthNote retrieves the list of commands supported
3.  The list of commands is displayed to the user
4.  User closes the list

    Use case ends.

**Extensions**

* 2a. The list of commands cannot be retrieved due to some error

    * 2a1. HealthNote shows an error message.

      Use case ends

**Use case: UC04 - Edit a patient**

**MSS**

1.  User requests to <u>list patients (UC05)</u>
2.  HealthNote shows a list of patients
3.  User requests to edit a patient using the edit command with required parameters.
4.  HealthNote edits the patient in the system.

    Use case ends.

**Extensions**

* 2a. The list is empty.

    * Use case ends.

* 3a. One or more required parameters are missing.

    * 1a1. HealthNote shows an error message.

      Use case resumes at step 1

* 3b. The given index is invalid.

    * 1bHealthNote shows an error message.

      Use case resumes at step 1.

* 3c. Duplicate identity number detected.

    * 3c1. HealthNote shows an error message.

      Use case resumes at step 1.

**Use case: UC05 - List patients**

**MSS**

1.  User requests to list all patients.
2.  HealthNote lists the patients in the system.

    Use case ends.

**Use case: UC06 - Find patients**

**MSS**

1.  User requests to find patients.
2.  HealthNote lists the matching patients in the system.

    Use case ends.

**Extensions**

* 1a. The list is empty.

    * Use case ends.

**Use case: UC07 - Clear all entries**

**MSS**

1.  User requests to clear all entries in the system.
2.  HealthNote clears all entries in the system.

    Use case ends.

**Extensions**

* 1a. The command inputted is invalid.

    * 1a1. HealthNote shows an error message.

      Use case resumes at step 1.

**Use case: UC08 - Change theme**

**MSS**

1.  User requests to change theme of the app.
2.  HealthNote changes the theme of the app.

    Use case ends.

**Extensions**

* 1a. The argument inputted is invalid.

    * 1a1. HealthNote shows an error message.

      Use case resumes at step 1

**Use case: UC09 - Accessing help**

**MSS**

1.  User requests for help to view available commands.
2.  HealthNote displays the available commands.

    Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The product should be for a single user.
5.  The product should be packaged into a **single `.jar` file** for ease of execution.
6.  The product file size should remain **below 100 MB** to ensure efficient storage and distribution.
7.  The GUI should display correctly and without layout issues on **standard screen resolutions** (1920×1080 and higher) at **100% and 125%** scale.
8.  The GUI should remain usable (i.e., all functions accessible even if layout is suboptimal) at **1280×720** resolution and **150%** scale.
9.  A doctor with basic technical knowledge should be able to learn all commands within 30 minutes of using the application.
10. All patient data must be saved automatically and reliably to prevent data loss in case of unexpected application closure.
11. All patient data must be stored **locally** on the user’s computer and never transmitted over the internet.
12. The application should function **offline** without needing an internet connection.
13. The system should include **JUnit tests** covering at least **70%** of the codebase to ensure long-term maintainability and prevent regressions.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

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

    1. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 t\priorityHigh`<br>
       Expected: New contact with tags is added to the list.

    2. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 al\nuts`<br>
       Expected: New contact with allergies is added to the list.

    3. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 m\100mg Panadol/day`<br>
       Expected: New contact with medicines is added to the list.

    4. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 ar\Social drinker`<br>
       Expected: New contact with alcoholic record is added to the list.

    5. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 sr\Heavy smoker`<br>
       Expected: New contact with smoking record is added to the list.

    6. Test case: `Example: add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB g\M dob\01-01-2000 pmh\Diabetes`<br>
       Expected: New contact with past medical history is added to the list.

### Editing a person

1. Editing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `edit 1 p\91234567`<br>
       Expected: Phone number of first contact is updated. Details of the edited contact shown in the status message.

    3. Test case: `edit 1 id\A9999999Z`<br>
       Expected: Identity number of first contact is updated if no duplicate exists.

    4. Test case: `edit 1 e\newemail@example.com addr\New Address 123`<br>
       Expected: Email and address of first contact are updated.

    5. Test case: `edit 1 t\diabetes`<br>
       Expected: Tags are replaced with only "diabetes" tag.

    6. Test case: `edit 1 al\Aspirin al\Ibuprofen`<br>
       Expected: Allergies are replaced with new list.

    7. Test case: `edit 0 p\91234567`<br>
       Expected: No person is edited. Error details shown in the status message.

    8. Other incorrect edit commands to try: `edit`, `edit x p\12345678` (where x is larger than the list size)<br>
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

    5. Other incorrect view commands to try: `view`, `view abc`<br>
       Expected: Error message shown.

2. Switching between different person views

    1. Test case: `view 1` followed by `view 2`<br>
       Expected: View switches from first person to second person.

    2. Test case: `view 1` followed by `list` followed by `view 1`<br>
       Expected: View persists correctly after list command.

### Listing all persons

1. Listing all persons in the address book

    1. Test case: `list`<br>
       Expected: All persons in the address book are displayed in the person list panel.

    2. Test case after a `find` command: `list`<br>
       Expected: Resets the view to show all persons instead of filtered results.

    3. Test case: `list xhasdnkcsdf` (garbage value after list command)<br>
       Expected:  All persons in the address book are displayed with a note says "Additional arguments detected. You may provide extra arguments, but they will be ignored.".

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

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# HealthNote User Guide

![HealthNoteLogo](images/HealthNoteLogo.png)

Hello independent doctors, welcome to our **HealthNote User Guide**!

### Table of Contents

<!-- TOC -->
* [HealthNote User Guide](#healthnote-user-guide)
  * [Table of Contents](#table-of-contents)
  * [Introduction](#introduction)
    * [What is HealthNote?](#what-is-healthnote)
    * [Who this guide is for](#who-this-guide-is-for)
  * [Getting Started](#getting-started)
    * [1. Java version check and installation](#1-java-version-check-and-installation)
    * [2. Downloading HealthNote](#2-downloading-healthnote)
    * [3. Running HealthNote](#3-running-healthnote)
  * [User Interface](#user-interface)
  * [Features](#features)
    * [Command Format](#command-format)
  * [Features - General Features](#features-general-features)
    * [Viewing help: `help`](#viewing-help-help)
    * [Changing the colour theme: `theme`](#changing-the-colour-theme-theme)
    * [Exiting the program: `exit`](#exiting-the-program-exit)
  * [Features - Managing Patient Records](#features-managing-patient-records)
    * [Adding a patient: `add`](#adding-a-patient-add)
    * [Editing a patient: `edit`](#editing-a-patient-edit)
    * [Deleting a patient: `delete`](#deleting-a-patient-delete)
  * [Features - Viewing Patient Records](#features-viewing-patient-records)
    * [View a patient's medical information: `view`](#view-a-patients-medical-information-view)
    * [Listing all patients: `list`](#listing-all-patients-list)
    * [Locating patients by name or identity number: `find`](#locating-patients-by-name-or-identity-number-find)
  * [Features - Managing Appointment Records](#features-managing-appointment-records)
    * [Scheduling an appointment: `schedule`](#scheduling-an-appointment-schedule-)
    * [Deleting an upcoming appointment: `unschedule`](#deleting-upcoming-appointment-unschedule)
    * [Delete past appointment: `forget`](#delete-past-appointment-forget)
  * [Features - Clearing Data](#features-clearing-data)
    * [Clearing all entries: `clear`](#clearing-all-entries-clear)
  * [Features - Data Management](#features-data-management)
    * [Saving the data](#saving-the-data)
    * [Editing the data file](#editing-the-data-file)
    * [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
  * [FAQ](#faq)
    * [Data Management](#data-management)
    * [Common Issues](#common-issues)
  * [Glossary](#glossary)
  * [Known issues](#known-issues)
  * [Command Summary](#command-summary)
    * [Input Constraints Table](#input-constraints-table)
<!-- TOC -->

--------------------------------------------------------------------------------------------------------------------
## Introduction
Welcome to HealthNote! This guide will help you get started.

### What is HealthNote?
HealthNote is a digital patient record manager. If you are an independent home healthcare provider, this is 
the perfect tool for you. HealthNote helps manage your patients' information, record their medical histories, and 
organise home visit notes. The simple and user-friendly interface enables you to access patient details 
faster than traditional patient files. With HealthNote, you can spend more time caring for your patients instead of 
combing through stacks of paperwork.

### Who this guide is for
This guide is written for medical professionals who are proficient with using a Command Line Interface (CLI).
We assume that you are familiar with your computer's operating system, navigating its files, and using its
CLI. Our goal is to quickly get you up and running by skipping over-simplified instructions.

--------------------------------------------------------------------------------------------------------------------
## Using this Guide

<div markdown="block" class="alert alert-info">

**For Novices**

* For first-time users, you can first jump to the [Getting Started](#getting-started) section to get started on HealthNote.
</div>

<div markdown="block" class="alert alert-success">

**For Amateurs**

* You can jump to the [Table of Contents](#table-of-contents) to start navigating the guide.
</div>

<div markdown="block" class="alert alert-warning">

**For Experts**

* You can jump to the [Command Summary](#command-summary) for a quick summary of all the commands and their formats.
</div>

--------------------------------------------------------------------------------------------------------------------

## Getting Started

HealthNote requires **Java 17** to run. Please follow the instructions according to your computer's operating system.

### 1. Java version check and installation

**For macOS Users**

A specific Java `17` version is required.

1. Please follow the instructions [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. After completing the setup, open the **Terminal** app and run the command `java -version` to confirm that
   the correct version is installed, you should see a Java version starting with `17` (e.g., `17.0.5`).


**For Windows and Linux Users**
1. Open your system's CLI (e.g., PowerShell, Terminal).
2. Execute this command: `java -version`.
3. If the output shows a Java version starting with `17` (e.g., `17.0.5`), you already have the correct version
   installed and may skip to the [Downloading HealthNote](#2-downloading-healthnote) section. If not, keep following the instructions to install Java `17`.
4. Download Java `17` from [this website](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html)
   according to your computer's operating system.
5. Run the downloaded file and follow the on-screen instructions.
6. After the installation, close and reopen your CLI, and repeat `step 2`.


### 2. Downloading HealthNote
Download the latest `healthnote.jar` file from [here](https://github.com/AY2526S1-CS2103T-F11-1/tp/releases).


### 3. Running HealthNote
1. Create a new folder on your computer where you want to store your patient data and move the downloaded
   `healthnote.jar` into this new folder.
2. Open your CLI and navigate to this folder (using commands like `cd`).
3. Run this command: `java -jar healthnote.jar`
4. A GUI similar to the one below should appear in a few seconds. Note how the app contains some sample data.

Congratulations! You are now ready to use HealthNote. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## User Interface

Here are the key components of the HealthNote User Interface (UI), designed for the convenience of our users.

![UI-components.png](images/Ui-components.png)

| No | Component                                | Description                                                                                                                          |
|----|------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| 1  | Menu bar                                 | Provides access to various actions, such as accessing help or exiting the application.                                               |
| 2  | Command Box                              | This is where the user inputs commands to perform various tasks.                                                                     |
| 3  | Command Result                           | Displays the output and the result of performing a command.                                                                          |
| 4  | Patient List                             | Displays the list of patients.                                                                                                       |
| 5  | Patient's information view panel         | Displays all the data fields, including medical records, of the patient that the user requests to view.                              |
| 6  | Upcoming and past appointments list panel | Displays all the appointments, with the top list displaying upcoming appointments, and the bottom list displaying past appointments. |

[Back to Table of Contents](#table-of-contents)
<br>

--------------------------------------------------------------------------------------------------------------------

## Features

### Command Format


In this section, we will break down the core components of a command in HealthNote. Understanding these components will help you input commands accurately and efficiently.

The command formats follow a general structure, containing a **command word**, **prefix** and **field**.

| Component        | Example       | Description                                                                                                      |
|------------------|---------------|------------------------------------------------------------------------------------------------------------------|
| **Command Word** | `add`         | Specifies what task or action to perform.                                                                        |
| **Prefix**       | `n\`          | Indicates the specific field to be edited (e.g., name, phone number).                                            |
| **Field**        | `George Best` | Represents the data or value provided by the user, replacing the `UPPER_CASE` placeholder in the command format. |


For example, this is the command format for the `add` command: <br>

![command-format.png](images/command-format.png)

<box type="info" seamless>

**Note**<br>

* Parameters can be in any order.<br>
  e.g. if the command specifies `n\NAME p\PHONE_NUMBER`, `p\PHONE_NUMBER n\NAME` is also acceptable.

</box>

<br>

In the command format, special symbols surrounding prefixes and fields indicate additional properties for the respective fields. Here’s a breakdown:

| **Symbol** | **Description**                                                            | **Example**             | **Interpretation**                               |
|------------|----------------------------------------------------------------------------|-------------------------|--------------------------------------------------|
| **None**   | **Required field**                                                         | `n\NAME`                | The patient's name is required.                  |
| **[ ]**    | **Optional field with a maximum of one entry**                             | `[ar\ALCOHOLIC_RECORD]` | The patient can have at most one alcohol record. |
| **[ ]...** | **Optional field with no limit on the number of entries (including zero)** | `[t\TAG]...`            | The patient can have multiple tags.              |

<box type="info" seamless>

#### Examples:

* `n\NAME [t\TAG]` can be used as `n\John Doe t\VIP` or as `n\John Doe`.
* `[t\TAG]...`. can be used 0 or more times, e.g., `t\VIP`, `t\VIP t\Urgent`, or not at all.

</box>

<br>

<box type="info" seamless>

**More notes about the command formats:**<br>

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* You can view a summary of all the command formats [here](#command-summary).

* You can view a summary of all the fields' input constraints [here](#input-constraints-table).

</box>

[Back to Table of Contents](#table-of-contents)

<br>

--------------------------------------------------------------------------------------------------------------------

## Features - General Features

### Viewing help: `help`

Displays a window showing all available commands and how to use them.

Format: `help`
* Opens a help window with a list of all commands and their usage.


[Back to Table of Contents](#table-of-contents)
<br>


### Changing the colour theme: `theme`

Changes the colour theme of HealthNote.

Format: `theme THEME_NAME`
* Available themes: `light`, `dark`, `blue`, `pink`.
* The theme name **must be exactly one of the available options**.
* Theme names are case-insensitive, both lowercase and uppercase letters are accepted.

#### Examples:
* `theme dark` changes the application to dark mode.
* `theme pink` changes the application to pink mode.


[Back to Table of Contents](#table-of-contents)
<br>

### Exiting the program: `exit`

Exits the HealthNote application.

Format: `exit`

* Closes the HealthNote application.
* No additional parameters are required.
* All data is automatically saved before exiting.



[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features - Managing Patient Records

### Adding a patient: `add`

Adds a patient to the address book.

Format: `add n\NAME id\IDENTITY_NUMBER p\PHONE e\EMAIL addr\ADDRESS ec\EMERGENCY_CONTACT dob\DATE_OF_BIRTH b\BLOOD_TYPE 
[ar\ALCOHOLIC_RECORD] g\GENDER [sr\SMOKING_RECORD] [pmh\PAST_MEDICAL_HISTORY] [t\TAG]... [al\ALLERGY]... [m\MEDICINE]...`

**Input Constraints Table**

| **Field (with Prefix)**      | **Compulsory?** | **Can have multiple?** | **Description**                                                                     | **Constraints**                                                                                 |
| ---------------------------- | --------------- | ---------------------- | ----------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------------------|
| **n\NAME**                   | ✓               | ✗                      | Full legal name of the patient.                                                     | Names can take any values, and it should not be blank                                           |
| **id\IDENTITY_NUMBER**       | ✓               | ✗                      | Unique identification number (e.g., national ID, passport number, or hospital ID).  | Identity number should only contain alphanumeric characters and/or "-" and ".", without spaces  |
| **p\PHONE_NUMBER**           | ✓               | ✗                      | Primary phone number for reaching the patient.                                      | Must contain at least 2 consecutive digits                                                      |
| **e\EMAIL**                  | ✓               | ✗                      | Patient’s active email address for communication.                                   | Emails should be of the format local-part@domain                                                |
| **addr\HOME_ADDRESS**        | ✓               | ✗                      | Current residential address of the patient.                                         | Addresses can take any values, and it should not be blank                                       |
| **ec\EMERGENCY_CONTACT**     | ✓               | ✗                      | Name and contact details of a patient to call in case of emergency.                 | Must be in the form [{relationship}] {phone} where phone contains at least 2 consecutive digits |
| **dob\DATE_OF_BIRTH**        | ✓               | ✗                      | Patient’s date of birth.                                                            | Date of birth should be of the following formats: d-M-yyyy, d.M.yyyy, d/M/yyyy                  |
| **b\BLOOD_TYPE**             | ✓               | ✗                      | Patient’s blood group (e.g., A+, O-, etc.).                                         | Blood types can take any values, and it should not be blank                                     |
| **g\GENDER**                 | ✓               | ✗                      | Gender identity of the patient (e.g., Male, Female, Non-binary, Prefer not to say). | Genders can take any values, and it should not be blank                                         |
| **ar\ALCOHOLIC_RECORD**      | ✗               | ✗                      | Indicates whether the patient consumes alcohol and relevant details or frequency.   | Alcoholic Record can take any values, and it should not be blank                                |
| **sr\SMOKING_RECORD**        | ✗               | ✗                      | Indicates whether the patient smokes and relevant details or frequency.             | Smoking Record can take any values, and it should not be blank                                  |
| **pmh\PAST_MEDICAL_HISTORY** | ✗               | ✗                      | Summary of major past illnesses, surgeries, or chronic conditions.                  | Past Medical History can take any values, and it should not be blank                            |
| **t\TAG**                    | ✗               | ✓                      | Custom labels or categories for organising patients.                                | Tags names can take any values, and it should not be blank                                      |
| **al\ALLERGY**               | ✗               | ✓                      | List of known allergies (e.g., “Peanuts”, “Penicillin”).                            | Allergy names can take any values, and it should not be blank                                   |
| **m\MEDICINE**               | ✗               | ✓                      | List of current medications prescribed to the patient.                              | Medicine field can take any values, and it should not be blank                                  |


<div markdown="block" class="alert alert-info">

**ℹ️ Info:**<br>
* When adding patients without using the optional fields `ALCOHOLIC_RECORD`, `SMOKING_RECORD` and `PAST_MEDICAL_HISTORY`, they will have default values "None" for these fields. 
* You can enter any number of tags, allergies, medicines (including 0).
</div>

<box type="tip" seamless>

**Tip:** The fields can be entered in any order.
</box>

#### Examples:
* `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567
 b\AB ar\Social drinker g\M dob\01-01-2000 sr\Heavy smoker al\nuts t\priorityHigh t\diabetesFollowUp pmh\Diabetes
 m\100mg painkillers/day m\100mg Panadol/day`

* `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567
  b\AB ar\Social drinker g\M dob\01-01-2000`

**Here is a list of common errors and how to prevent them**

| Error Message                                   | Reason                                                            | Solution                                                                                                        |
|-------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| This person already exists in the address book. | You are adding a patient with the same id as an existing patient. | Ensure that you do not enter a patient with the same id as an existing patient.                                 |
| Invalid command format!                         | Some of the essential prefixes could be missing or misspelled.    | Refer to the [command format](#adding-a-patient-add).                                                           |
| Unknown command.                                | The `add` command may be misspelled or not entirely in lowercase. | Ensure that you use `add` exactly in lowercase.                                                                 |
| Errors relating to invalid field inputs.        | You have entered invalid inputs for a field.                      | Ensure that your inputs meet the constraints stated in the [Input Constraints Table](#input-constraints-table). |


[Back to Table of Contents](#table-of-contents)

<br>

### Editing a patient: `edit`

Edits an existing patient in the address book.

Format: `edit INDEX [n\NAME] [id\IDENTITY_NUMBER] [p\PHONE] [e\EMAIL] [addr\ADDRESS]
 [ec\EMERGENCY_CONTACT] [dob\DATE_OF_BIRTH] [b\BLOOD_TYPE] [ar\ALCOHOLIC_RECORD] [g\GENDER] [sr\SMOKING_RECORD] 
 [pmh\PAST_MEDICAL_HISTORY] [t\TAG]... [al\ALLERGY]... [m\MEDICINE]...`

* Edits the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patient will be removed i.e., adding of tags is not cumulative. This applies to medicines and allergies as well. <br>
  e.g. If a patient at `INDEX` 3 has the allergies "nuts" and "eggs", performing `edit 3 al\Penicillin` will replace all that patient's allergies with "Penicillin" only.

<div markdown="block" class="alert alert-info">

**ℹ️ Info:**<br>
* You can remove all the patient’s tags by typing `t\` without
  specifying any tags after it.
* You can remove all the patient’s medicine record by typing `m\` without
  specifying any medicines after it.
* You can remove all the patient’s allergies by typing `al\` without
  specifying any allergies after it.
* Typing `pmh\` without specifying any past medical history after it will
  cause that patient's past medical history to default to the value "None". <br>
  The same applies to `ar\` and `sr\` for alcoholic and smoking record respectively.
</div>

**Input Constraints Table**

| **Field (with Prefix)**      | **Compulsory?** | **Can have multiple?** | **Description**                                                                     | **Constraints**                                                                               |
| ---------------------------- | --------------- | ---------------------- | ----------------------------------------------------------------------------------- |-----------------------------------------------------------------------------------------------|
| **INDEX**                    | ✓               | ✗                      | Index of the patient in the Patient List Panel.                                     | Must be a positive number and valid INDEX in the patient list                                 |
| **n\NAME**                   | ✗               | ✗                      | Full legal name of the patient.                                                     | Names can take any values, and it should not be blank                                         |
| **id\IDENTITY_NUMBER**       | ✗               | ✗                      | Identification number (e.g., national ID, passport number, or hospital ID).         | Identity number should only contain alphanumeric characters and/or "-" and ".", without spaces |
| **p\PHONE_NUMBER**           | ✗               | ✗                      | Primary phone number for reaching the patient.                                      | Must contain at least 2 consecutive digits                                                    |
| **e\EMAIL**                  | ✗               | ✗                      | Patient’s email address for communication.                                          | Emails should be of the format local-part@domain                                              |
| **addr\HOME_ADDRESS**        | ✗               | ✗                      | Current residential address of the patient.                                         | Addresses can take any values, and it should not be blank                                     |
| **ec\EMERGENCY_CONTACT**     | ✗               | ✗                      | Name and contact details of a patient to contact in case of emergency.              | Must be in the form [{relationship}] {phone} where phone contains at least 2 consecutive digits |
| **dob\DATE_OF_BIRTH**        | ✗               | ✗                      | Patient’s date of birth.                                                            | Date of birth should be of the following formats: d-M-yyyy, d.M.yyyy, d/M/yyyy                |
| **b\BLOOD_TYPE**             | ✗               | ✗                      | Patient’s blood group (e.g., A+, O-, etc.).                                         | Blood types can take any values, and it should not be blank                                   |
| **g\GENDER**                 | ✗               | ✗                      | Gender identity of the patient (e.g., Male, Female, Non-binary, Prefer not to say). | Genders can take any values, and it should not be blank                                       |
| **ar\ALCOHOLIC_RECORD**      | ✗               | ✗                      | Indicates whether the patient consumes alcohol and relevant details or frequency.   | Alcoholic Record can take any values, and it should not be blank                              |
| **sr\SMOKING_RECORD**        | ✗               | ✗                      | Indicates whether the patient smokes and relevant details or frequency.             | Smoking Record can take any values, and it should not be blank                                |
| **pmh\PAST_MEDICAL_HISTORY** | ✗               | ✗                      | Summary of major past illnesses, surgeries, or chronic conditions.                  | Past Medical History can take any values, and it should not be blank                          |
| **t\TAG**                    | ✗               | ✓                      | Custom labels or categories for organising patients.                                | Tags names can take any values, and it should not be blank                                    |
| **al\ALLERGY**               | ✗               | ✓                      | List of known allergies (e.g., “Peanuts”, “Penicillin”).                            | Allergy names can take any values, and it should not be blank                                 |
| **m\MEDICINE**               | ✗               | ✓                      | List of current medications prescribed to the patient.                              | Medicine field can take any values, and it should not be blank                                |


#### Examples:
*  `edit 1 p\91234567 e\johndoe@example.com` Edits the phone number and email address of the first patient to `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n\Betsy Crower t\` Edits the name of the second patient to `Betsy Crower` and clears all existing tags.

**Here is a list of common errors and how to prevent them**

| Error Message                                                           | Reason                                                                                       | Solution                                                                                                        |
|-------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| This person already exists in the address book.                         | You are editing a patient to have the same id as an existing patient.                        | Ensure that you do not enter a patient with the same id as an existing patient.                                 |
| Unknown command.                                                        | The `edit` command may be misspelled or not entirely in lowercase.                           | Ensure that you use `edit` exactly in lowercase.                                                                |
| Errors relating to invalid field inputs.                                | You have entered invalid inputs for a field.                                                 | Ensure that your inputs meet the constraints stated in the [Input Constraints Table](#input-constraints-table). |
| The patient index provided is invalid.                                  | You have entered an invalid index                                                            | Ensure that the index is not more than the number of patients in the currently displayed list.                  |
| At least one field to edit must be provided.                            | You have not entered any fields to edit.                                                     | Enter at least one field to edit.                                                                               |
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer. | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes.           |


[Back to Table of Contents](#table-of-contents)

### Deleting a patient: `delete`

Removes the specified patient from the patient list.

Format: `delete INDEX`
* Deletes the patient at the specified `INDEX`.
* The `INDEX` refers to the index number shown in the currently displayed patient list.
* The `INDEX` **must be a positive integer** 1, 2, 3, ...

#### Example:
* `delete 2` deletes the second patient in the patient list panel (if they exist).

**Here is a list of common errors and how to prevent them**

| Error Message                                                           | Reason                                                                                           | Solution                                                                                              |
|-------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer.     | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes. |
| Unknown command.                                                        | The `delete` command may have been misspelled or not entered in lowercase.                       | Ensure that you use `delete` exactly in lowercase.                                                    |
| The patient index provided is invalid.                                  | You have entered an `INDEX` that exceeds the number of patients in the currently displayed list. | Ensure that the `INDEX` not more than the number of patients in the currently displayed list.         |


[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features - Viewing Patient Records

### View a patient's medical information: `view`

Displays the medical information of the patient at the specified `INDEX`.

Format: `view INDEX`
* Displays the information of the patient at the specified `INDEX` on the view panel.
* The index refers to the index number shown in the currently displayed patient list.
* The index **must be a positive integer** 1, 2, 3, ...


#### Example:
- `view 1` displays the full information of the first patient in the patient list panel.

**Here is a list of common errors and how to prevent them:**

| Error Message                                                           | Reason                                                                                           | Solution                                                                                              |
|-------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer.     | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes. |
| Unknown command.                                                        | The `view` command may have been misspelled or not entered in lowercase.                         | Ensure that you use `view` exactly in lowercase.                                                      |
| The patient index provided is invalid.                                  | You have entered an `INDEX` that exceeds the number of patients in the currently displayed list. | Ensure that the entered `INDEX` not more than the number of patients in the currently displayed list. |

[Back to Table of Contents](#table-of-contents)


### Listing all patients: `list`

Shows a list of all patients stored in the patient list.
* The list will be refreshed to show all patients when the `list` command is used.
* Patients are displayed in the order they were added, with the most recently added patients appearing at the end of the list.

<box type="tip" seamless>

**Tip:** You can use the `list` command to reset the patient list view after using the `find` command.
</box>


[Back to Table of Contents](#table-of-contents)


### Locating patients by name or identity number: `find`

Find patients whose Name or Identity Number match one or more given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

**Matching criteria:**
* **Case-insensitive** for both name and identity. <br>
  Example: `hans` matches `Hans`, `a1234567X` matches `A1234567x`
* **Name** matches if any of the keywords is part of the patient's name.<br>
  Example: keyword `Hans` matches patient with name `Hans Andersen` and `Joanne Hans`, but not `JHans Doe`
* **Identity** matches if any of the keywords match exactly the patient's identity.<br>
  Example: keyword `S1234567A` matches patient with identity `s1234567a` but not `S12345678A`
* The search is **inclusive**, if any of the keywords matches either a part of a patient's name or identity, they are displayed. <br>
* **Keywords are separated by spaces** and a symbol is **not** treated as a separator, it will be considered part of a keyword itself.


<box type="tip" seamless>

**Tip:** The fields can be entered in any order. <br>
e.g. `find John Alex` returns the same results as `find Alex John`<br>
</box>

#### Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

**Here is a list of common errors and how to prevent them**

| Error Message                               | Reason                                                             | Solution                                                                      |
|---------------------------------------------|--------------------------------------------------------------------|-------------------------------------------------------------------------------|
| Error! Please provide at least one keyword. | You may not have provided any keyword.                             | Add at least one keyword, e.g. `John`.                                        |
| Unknown command.                            | The `find` command may be misspelled or not entirely in lowercase. | Ensure that you use `find` exactly in lowercase.                              |

[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features - Managing Appointment Records

### Scheduling an appointment: `schedule`

Adds an appointment for a specific patient at a certain time.

**Format:** `schedule INDEX adt\APPOINTMENT_DATE_TIME [note\APPOINTMENT_NOTE]`
* Adds an appointment to the appointments list for the patient at the specified `INDEX` at the given date and time.
* The added appointment may appear at the "upcoming" list or "past" list depending on the date and time. 
* Appointment date & time should be of the following formats: d-M-yyyy HH:mm, d.M.yyyy HH:mm, or d/M/yyyy HH:mm

**Input Constraints Table**

| **Field (with Prefix)**       | **Compulsory?** | **Can have multiple?** | **Description**                                       | **Constraints**                                             |
|-------------------------------|-----------------|------------------------|-------------------------------------------------------|-------------------------------------------------------------|
| **INDEX**                     | &check;         | &cross;                | Index of the patient in the Patient List Panel.       | Must be a positive number and valid INDEX in the shown list |
| **adt\APPOINTMENT_DATE_TIME** | &check;         | &cross;                | Full date time of the scheduled appointment.          | d-M-yyyy HH:mm, d/M/yyyy HH:mm, or d.M.yyyy HH:mm           |
| **note\APPOINTMENT_NOTE**     | &cross;         | &cross;                | Any additional notes for this particular appointment. | Any string                                                  |

#### Example:
* To schedule an appointment with the first patient in the displayed list, you can enter: `schedule 1 adt\13-10-2025 10:00 note\Needs IV Drip`.


<box type="tip" seamless>

**Tip:** Appointment notes are optional. Hence, this is a perfectly valid command: `schedule 1 adt\13-10-2025 10:00`.

</box>
  
**Here is a list of common errors and how to prevent them**

| Error Message                                                           | Reason                                                                                      | Solution                                                                                              |
|-------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| Invalid command format!                                                 | Some of the essential prefixes could be missing or misspelled.                              | Ensure `INDEX` and `adt\` are provided.                                                               |
| Unknown command.                                                        | The `schedule` command may be misspelled or not entirely in lowercase.                      | Ensure that you use `schedule` exactly in lowercase.                                                  |
| The patient index provided is invalid.                                  | You have entered an invalid index.                                                          | Ensure that the `INDEX` provided is a valid index in the shown list.                                  |
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer. | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes. |

[Back to Table of Contents](#table-of-contents)

### Deleting an upcoming appointment: `unschedule`

Deletes an upcoming appointment from the displayed upcoming appointments list.

**Format:** `unschedule INDEX`
* Deletes an appointment from the upcoming appointments list at the specified INDEX.

#### Example:
* To delete the first upcoming appointment in the displayed list, you can enter: `unschedule 1`


**Here is a list of common errors and how to prevent them**

| Error Message                                                           | Reason                                                                                       | Solution                                                                                              |
|-------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer. | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes. |
| Unknown command.                                                        | The `unschedule` command may be misspelled or not entirely in lowercase.                     | Ensure that you use `unschedule` exactly in lowercase.                                                |
| The appointment index provided is invalid.                              | You have entered an invalid index.                                                           | Ensure that the `INDEX` provided is a valid index in the shown list.                                  |

### Delete past appointment: `forget`

Deletes a past appointment from the displayed past appointments list.

**Format:** `forget INDEX`
* Deletes an appointment from the past appointments list at the specified INDEX.

#### Example:
* To delete the first past appointment in the displayed list, you can enter: `forget 1`

**Here is a list of common errors and how to prevent them**

| Error Message                                                           | Reason                                                                                       | Solution                                                                                              |
|-------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| You must provide an index and it has to be a non-zero unsigned integer. | You did not specify an `INDEX` or the `INDEX` provided is not an unsigned, non-zero integer. | Enter an unsigned, non-zero integer as `INDEX`, and ensure that you do not have any invalid prefixes. |
| Unknown command.                                                        | The `forget` command may be misspelled or not entirely in lowercase.                         | Ensure that you use `forget` exactly in lowercase.                                                    |
| The appointment index provided is invalid.                              | You have entered an invalid index.                                                           | Ensure that the `INDEX` provided is a valid index in the shown list.                                  |


[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features - Clearing Data

### Clearing all entries: `clear`

Clears all entries from the app.

Format: `clear CONFIRM`

<div markdown="block" class="alert alert-danger">

**❗Warning:**<br>
* This action cannot be undone. You must type exactly `clear CONFIRM` (case-sensitive) to execute.
</div>

[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features - Data Management

### Saving the data

HealthNote data is saved locally in the user's computer automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HealthNote data is saved automatically as a JSON file `[JAR file location]/data/healthnote.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**❗Caution:**
If the changes made to the data file make its format invalid, HealthNote will discard all data and start with an empty data file at the next run.  Hence, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause the HealthNote application to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can do so correctly.
</box>


[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## FAQ

### Data Management
**Q**: How do I change the location of the data file?<br>
**A**: Currently, HealthNote does not support changing the data file location. The data file is always stored in `[JAR file location]/data/healthnote.json`.
<box type="tip" seamless>

**Extra notes:** For pro-users, you may change the data path in `preferences.json` file located in `[JAR file location]/preferences.json`. However, this is not officially supported and may lead to unexpected behaviour.
</box>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the automatically generated `healthnote.json` file with the `healthnote.json` from your current HealthNote `folder/data`.

**Q**: May I edit the data file manually?<br>
**A**: Manual editing of the data file is possible but not recommended unless you are confident in maintaining the correct JSON format. Incorrect edits may lead to data corruption.

### Common Issues
**Q**: Can I undo a delete or clear action?<br>
**A**: No, once a record is deleted or cleared, the data is permanently removed. It is recommended to back up your data file `healthnote.json` regularly.

**Q**: Can I open multiple HealthNote windows at the same time?<br>
**A**: Running multiple instances of the app is not supported. Doing so may cause data conflicts. Use only one window at a time.



[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Glossary

| **Term**                     | **Explanation**                            |
|------------------------------|--------------------------------------------|
| Alphanumeric                 | Consists of numbers and/or alphabets only. |
| CLI (Command-Line Interface) |        A text-based interface where you type commands to interact with the app.                                    |
| Command                      |  An instruction entered by the user (e.g., `add`, `edit`, `delete`).                                          |
| Field                        |   A specific piece of information in a patient’s record (e.g., name, address, blood type).                                         |
| Prefix                       |   A short label before a field to identify it in a command (e.g., `n\` for name, `p\` for phone).                                         |
| Index                        |   The number showing a patient’s position in the displayed list. Used in commands like `edit` or `delete`.                                                                                                                                        |
|        Integer                      |     A whole number (no decimals). In HealthNote, indexes must be positive integers such as 1, 2, 3, etc.                                                                                                                                                                                                                                              |
|               GUI (Graphical User Interface)                      |         The visual interface showing panels, buttons, and text boxes.                                                                                                                                                                                                                                                                                                                                              |

[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will be focused, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **If a tag element is made to hold a large number of characters**, its UI could overflow the panel, causing it to be cut off or the panel to start horizontal scroll.
   Specifically, adding a very long `tag` to a patient will cause the tag to be cut off by the border of the `Patient List`. Adding a very long `Allergy` or `Medicine` will cause
   the `Patient View Panel` to horizontal scroll. The remedy to this is to avoid creating overly long entries for these fields.

[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Summary of all the commands.

<box type="info" seamless>

**Info:**<br>
* Fields with square brackets `[prefix\field]` indicates it is an optional field.
* Fields with square brackets and ellipsis `[prefix\field]...` indicates it is an optional field which can be entered multiple times.
* Commands without any arguments will still execute if user inputs additional arguments
* You may view [Command Format](#command-format) for more details.

</box>

| Action                        | Format, Examples                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                       | `add n\NAME id\IDENTITY_NUMBER p\PHONE e\EMAIL addr\ADDRESS ec\EMERGENCY_CONTACT dob\DATE OF BIRTH b\BLOOD TYPE [ar\ALCOHOLIC RECORD] g\GENDER [sr\SMOKING RECORD] [pmh\PAST MEDICAL HISTORY] [t\TAG]... [al\ALLERGY]... [m\MEDICINE]...` <br> e.g., `add n\John Doe id\A91234567 p\98765432 e\johnd@example.com addr\311, Clementi Ave 2, #02-25 ec\[Mother] +6591234567 b\AB ar\Social drinker g\M dob\01-01-2000 sr\Heavy smoker al\nuts t\priorityHigh t\diabetesFollowUp pmh\Diabetes m\100mg painkillers/day m\100mg Panadol/day` |
| **Clear**                     | `clear CONFIRM`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **Delete**                    | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **Edit**                      | `edit INDEX [n\NAME] [id\IDENTITY_NUMBER] [p\PHONE] [e\EMAIL] [addr\ADDRESS] [ec\EMERGENCY_CONTACT] [dob\DATE OF BIRTH] [b\BLOOD TYPE] [ar\ALCOHOLIC RECORD] [g\GENDER] [sr\SMOKING RECORD] [pmh\PAST MEDICAL HISTORY] [t\TAG]... [al\ALLERGY]... [m\MEDICINE]...`<br> e.g.,`edit 2 n\James Lee e\jameslee@example.com`                                                                                                                                                                                                              |
| **Find**                      | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| **List**                      | `list`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **View**                      | `view INDEX`<br> e.g., `view 1`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **Help**                      | `help`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **Theme**                     | `theme THEME_NAME`<br> e.g., `theme dark`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| **Schedule an Appointment**   | `schedule INDEX adt\APPOINTMENT_DATE_TIME [note\NOTE]` <br> e.g., `schedule 1 adt\13-10-2025 10:00 note\Needs IV Drip`                                                                                                                                                                                                                                                                                                                                                                                                               |
| **Unschedule an Appointment** | `unschedule INDEX` <br> e.g., `unschedule 1`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| **Remove a Past Appointment** | `forget INDEX` <br> e.g., `forget 1`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Exit**                      | `exit`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |

[Back to Table of Contents](#table-of-contents)

### Input Constraints Table

<div markdown="block" class="alert alert-warning">

**❗Caution:**

* The symbol `\` is reserved as a command delimiter. While it is not explicitly prohibited in command values, it is
  highly discouraged as it could cause unexpected behaviour.

</div>

| **Field (with Prefix)**        | **Valid Input(s)**                                      | **Invalid Input(s)**           | **Requirement(s)**                                                                                            |
|--------------------------------|---------------------------------------------------------|--------------------------------|---------------------------------------------------------------------------------------------------------------|
| **n\\NAME**                    | Alex the 3rd, John Doe, Damith s/o Sankar Ashish        | (Empty)                        | Names can take any values, and it should not be blank                                                         |
| **id\\IDENTITY_NUMBER**        | S1234567A, T7654321B, 060402-06-6767                    | %&!, 12 3-4                    | Identity number should only contain alphanumeric characters and/or "-" and ".", without spaces                |
| **p\\PHONE_NUMBER**            | 9888-3333 (Office)                                      | abcdefg, 3 3 3                 | Must contain at least 2 consecutive digits                                                                    |
| **e\\EMAIL**                   | e01234567@u.nus.edu, jinHeng@gmail.com                  | joe@, asd@@@asd                | Emails should be of the format local-part@domain                                                              |
| **addr\\HOME_ADDRESS**         | 123 Main St                                             | (Empty)                        | Addresses can take any values, and it should not be blank                                                     |
| **ec\\EMERGENCY_CONTACT**      | [Mother] 9888-3333 (Office), [Brother] 9777-3333 (Home) | 9888-3333, 2222aaaa            | Must be in the form [{relationship}] {phone} where phone contains at least 2 consecutive digits               |
| **dob\\DATE_OF_BIRTH**         | 23-05-1967, 12/10/1987                                  | 99-05-23, 19871312, 2020-12-20 | Date of birth should be of the following formats: d-M-yyyy, d.M.yyyy, d/M/yyyy                                |
| **b\\BLOOD_TYPE**              | A+, O-, AB, Bombay (hh), A Rh(D) negative               | (Empty)                        | Blood types can take any values, and it should not be blank                                                   |
| **g\\GENDER**                  | Male, Female, Non-binary, Genderqueer                   | (Empty)                        | Genders can take any values, and it should not be blank                                                       |
| **ar\\ALCOHOLIC_RECORD**       | None, Occasionally, Heavy                               | (Empty)                        | Alcoholic Record can take any values, and it should not be blank                                              |
| **sr\\SMOKING_RECORD**         | None, Occasionally, Quitter, Heavy                      | (Empty)                        | Smoking Record can take any values, and it should not be blank                                                |
| **pmh\\PAST_MEDICAL_HISTORY**  | None, Diabetes, Hypertension                            | (Empty)                        | Past Medical History can take any values, and it should not be blank                                          |
| **t\\TAG**                     | Urgent, HighRisk, VIP                                   | (Empty)                        | Tags names can take any values, and it should not be blank                                                    |
| **al\\ALLERGY**                | Peanuts, Penicillin                                     | (Empty)                        | Allergy names can take any values, and it should not be blank                                                 |
| **m\\MEDICINE**                | 500mg Ibuprofen, 2 Panadol capsules/day                 | (Empty)                        | Medicine field can take any values, and it should not be blank                                                |
| **adt\\APPOINTMENT_DATE_TIME** | 13-10-2025 10:00                                        | 22233344                       | Appointment date & time should be of the following formats: d-M-yyyy HH:mm, d.M.yyyy HH:mm, or d/M/yyyy HH:mm |
| **note\\APPOINTMENT_NOTE**     | Needs IV Drip                                           | N/A                            | Appointment notes can take any values                                                                         |

[Back to Table of Contents](#table-of-contents)

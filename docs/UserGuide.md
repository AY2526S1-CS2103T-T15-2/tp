---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# iCon User Guide

iCon is a **desktop app for managing contacts, contracts, policies, and appointments optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, iCon can get your management tasks done faster than traditional GUI apps.

## Table of Contents

- [Quick start](#quick-start) 
- [Features](#features)
- [Appointment](#appointment)
- [Contacts](#contacts)
- [Policy](#policy)
- [Contracts](#contracts)
- [General](#general)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T15-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your iCon.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar iCon.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui.png](images/Ui.png))

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `add_contact n:John Doe p:98765432 ic:T0000000A e:johnd@example.com a:John street, block 123, #01-01` : Adds a contact named `John Doe` to iCon.

   * `remove_contact ic:T0000000A` : Removes the contact with NRIC `T0000000A`.

   * `clear` : Deletes all data.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the compulsory parameters to be supplied by the user.<br>
  e.g. in `add_contact n:NAME p:PHONE_NUMBER ic: NRIC`, `NAME`, `PHONE_NUMBER` and `NRIC` are compulsory parameters which can be used as `add_contact n:John Doe p:91234567 ic:T0000000A`.

* Items in square brackets are optional.<br>
  e.g `n:NAME [t:TAG]` can be used as `n:John Doe t:friend` or as `n:John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t:TAG]…​` can be used as ` ` (i.e. 0 times), `t:friend`, `t:friend t:family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n:NAME p:PHONE_NUMBER`, `p:PHONE_NUMBER n:NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Text entered after certain parameters will be counted as we do not perform regex on some fields, fields that can
  be checked such as phone number or NRIC will throw exceptions for incorrect input, but fields like addresses and 
  names cannot be checked, 
  thus any input after the colon will be counted. For example, `add_contact n: REAL_NAME OOPS_EXTRA_TEXT p:....` will 
  include the extra text in your name.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

## Appointment

### Adding an appointment: `add_appointment`

Adds an appointment to iCon.

Format: `add_appointment ic:NRIC dt:APPOINTMENTDATE d:APPOINTMENTDETAILS`

Examples:
* `add_appointment ic:T1234567A dt:2025-10-10 d:Discuss Healthcare coverage...`
* `add_appointment ic:S9876543B dt:2026-08-09 d:Re-evaluate premium for...`

![img.png](images/add_appointment.png)

### Viewing appointment(s) : `view_appointment`

Display appointment(s) in iCon, either all or a set of specific appointments

Format: 
1. `view_appointment -a`
2. `view_appointment a:APPOINTMENTID1 [APPOINTMENTID2] [APPOINTMENTID3]` at least 1 Id to view

Examples:
* `view_appointment -a`
* `view_appointment a:ABCDEF`


### Editing an appointment : `edit_appointment`

Edits an existing appointment in iCon.

Format: `edit_appointment a:APPOINTMENTID ic:[NRIC] dt:[APPOINTMENTDATE] d:[APPOINTMENTDETAILS]` 

* Edits the appointment at the specified `a:`. The appointment id refers to the id number shown in the displayed appointment list. The appointment **must be a positive an alphanumeric, 6 characters long** …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.


Examples:
*  `edit_appointment a:abc123 ic:T1234567A` Edits the Nric of the appointment with id 'abc123' to be T1234567A
*  `edit_appointment a:456XYZ dt:2020-01-01 d:This...` Edits the appointment date and appointment details of the appointment with id '456XYZ' to 2020-01-01 and "This..." respectively.

### Removing an appointment : `remove_appointment`

Removes the specified appointment from iCon.

Format: `remove_appointment a:APPOINTMENTID`

* Removes the appointment with the specified `APPOINTMENTID`.
* The appointment id refers to the appointment id shown in the displayed appointment list.
* The appointment **must be a positive an alphanumeric, 6 characters long** …​

Examples:
* `remove_appointment a:xyz123` removes the appointment with appointment id "xyz123" in iCon.

### Sorting an appointment : `sort_appointment`

Sorts the appointment in iCon by relevant flags.

Format: `sort_appointment`
1. `sort_appointment -i` sort appointment by insertion order
2. `sort_appointment -a` sort appointment by alphabetical order
3. `sort_appointment -da` sort appointment by date in ascending order
4. `sort_appointment -dd` sort appointment by date in descending order



## Contacts

### Adding contacts : `add_contact`

Adds a contact to iCon

Format: `add_contact n:NAME p:PHONE_NUMBER ic:NRIC [e:EMAIL] [a:ADDRESS] [t:TAG]`

### Editing contacts : `edit_contact`

Edits a contact in iCon

Format: `edit_contact INDEX [n:NAME] [p:PHONE_NUMBER] [ic:NRIC] [e:EMAIL] [a:ADDRESS] [t:TAG]`

### Removing contacts : `remove_contact`

Removes a contact in iCon

Format: `remove_contact ic:NRIC`

### Sorting contacts : `sort_contact`

Sorts contacts in iCon by relevant flags.

Format : 
1. `sort_contact -a` in alphabetical order
2. `sort_contact -i` in insertion order

### Viewing contacts : `view_contact`

Shows a list of all contacts or a specific contact in iCon.

Format : 
1. `view_contact -a` 
2. `view_contact ic:NRIC1 [NRIC2] [NRIC3]...` at least 1 IC to view

## Policy

### Adding a policy: `add_policy`

Adds a policy or a list of policies to iCon.

Format:
1. `add_policy n:NAME d:DETAILS` to add one policy; or
2. `add_policy f:FILE_PATH` to add policies from a file

**Note:** Policies loaded from a file should be formatted as lines of ``NAME`DETAILS``. For example:
```
Life Insurance`This policy coverage for family...
Travel - A`This policy covers flights to European countries
```

Examples:
* `add_policy n:Life Insurance d:This policy coverage for family...`
* `add_policy f:policy_file.txt`

![img.png](images/add_policy.png)

### Editing a policy: `edit_policy`

Edits an existing policy in iCon.

Format: `edit_policy p:POLICY_ID [n:NAME] [d:DETAILS]`

* Edits the policy with the specified `POLICY_ID`.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit_policy p:Abc123 n:Healthcare` edits the policy with id `Abc123` to have the name `Healtchare`.

### Removing a policy: `remove_policy`

Removes an existing policy in iCon.

Format: `remove_policy p:POLICY_ID`

* Deletes the policy with the specified `POLICY_ID`.

Examples:
* `delete_policy p:Abc123`

### Viewing policies: `view_policy`

Shows a list of all policies or a specific policy in iCon.

Format:
1. `view_policy -a` to view all policies; or
2. `view_policy p:POLICY_ID` to view a specific policy

Examples:
* `view_policy p:Abc123`


<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, iCon will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the iCon to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

## Contracts

### Add a contract : `add_contract`

Adds a contract to iCon.

Format: `add_contract p: POLICY_ID ic: NRIC dt: DATE_SIGNED e: EXPIRY_DATE pr: PREMIUM_AMOUNT`

* EXPIRY_DATE cannot be before DATE_SIGNED.
* PREMIUM_AMOUNT must be a positive number.

Example: `add_contract p: P1234A ic: S1234567A dt: 2023-01-01 e: 2024-01-01 pr: 1200.50`

### Remove a contract : `remove_contract`

Removes a contract from iCon.

Format: `remove_contract c:CONTRACT_ID`

Example: `remove_contract c:C1234A`

### View contracts : `view_contract`

Displays all contracts in iCon.

Format: 
1. `view_contract -a` for viewing all contracts
2. `view_contract c: CONTRACT_ID` for viewing a specific contract by CONTRACT_ID

Example: 
1. `view_contract -a`
2. `view_contract c: C1234A`

### Sort contracts: `sort_contract`

Sorts contracts in iCon by relevant flags.

Format: 
1. `sort_contract -ea` for expiry date ascending
2. `sort_contract -i` for insertion order

* Expiry date ascending sorts contracts from the earliest expiry date to the latest. 
* Insertion order is the order in which contracts are added by the user

### Edit contract: `edit_contract`

Edits an existing contract in iCon.

Format: `edit_contract c:CONTRACT_ID [p: POLICY_ID] [ic: NRIC] [dt: DATE_SIGNED] [e: EXPIRY_DATE] [pr: PREMIUM_AMOUNT]`

* Edits the contract with the specified `CONTRACT_ID`.
* CONTRACT_ID is a compulsory field
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example: `edit_contract c:C1234A p: P5678B ic: S7654321B dt: 2023-02-01 e: 2024-02-01 pr: 1500.75`

# General

### Viewing help : `help`

Shows a message explaining how to access the help page.

![img.png](images/help.png)

Format: `help`

### Clearing all entries : `clear`

Clears all entries from iCon

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

iCon data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

iCon data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous iCon home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues 

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                 | Format, Examples                                                                                                                                                                                                 |
|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add contact**        | `add_contact n:NAME p:PHONE_NUMBER ic: NRIC [e:EMAIL] [a:ADDRESS] [t:TAG]…​` <br> e.g., `add_contact n:James Ho p:22224444 ic: T0000000A e:jamesho@example.com a:123, Clementi Rd, 1234665 t:friend t:colleague` |
| **Remove contact**     | `remove_contact ic: NRIC`<br> e.g., `remove_contact ic: T0000000A`                                                                                                                                               |
| **Edit contact**       | `edit_contact INDEX [n:NAME] [p:PHONE_NUMBER] [e:EMAIL] [a:ADDRESS] [t:TAG]…​`<br> e.g.,`edit_contact 2 n:James Lee e:jameslee@example.com`                                                                      |
| **Sort contact**       | `sort_contact -a` OR `sort_contact -i`                                                                                                                                                                           |
| **View contact**       | `view_contact -a` OR `view_contact ic: NRIC1 [NRIC2] [NRIC3] ...` <br> e.g., `view_contact ic: T0000000A`                                                                                                        | 
| **Add contract**       | `add_contract p:POLICY_ID ic:NRIC dt:DATE_SIGNED e:EXPIRY_DATE pr:PREMIUM_AMOUNT ` <br> `add_contract p:P1234A ic:T1234567A dt:2024-01-01 e: 2025-12-12 pr: 1000`                                                |
| **Remove contract**    | `remove_contract c:CONTRACT_ID` <br> e.g., `remove_contract c:C1234A`                                                                                                                                            |
| **View contract**      | `view_contract -a` OR `view_contract c:CONTRACT_ID` <br> e.g., `view_contract c:C1234A`                                                                                                                          |
| **Edit contract**      | `edit_contract c:CONTRACT_ID [p:POLICY_ID] [ic:NRIC] [dt:DATE_SIGNED] [e:EXPIRY_DATE] [pr:PREMIUM_AMOUNT]` <br> e.g., `edit_contract c:C1234A ic:T1234567B`                                                      |
| **Sort contract**      | `sort_contract -ea` OR `sort_contract -i`                                                                                                                                                                        |
| **Add policy**         | `add_policy n:POLICY_NAME d:POLICY_DETAILS` OR `add_policy f:FILE_PATH` <br> e.g., `add_policy n:Life d:Covers life` OR `add_policy f:Life.txt`                                                                  |
| **Remove policy**      | `remove_policy p:POLICY_ID` <br> e.g., `remove_policy p:P1234A`                                                                                                                                                  |
| **View policy**        | `view_policy -a` OR `view_policy p:POLICY_ID` <br> e.g., `view_policy p:P1234A`                                                                                                                                  |
| **Edit policy**        | `edit_policy p:POLICY_ID [n:POLICY_NAME] [d:POLICY_DETAILS]` <br> e.g., `edit_policy p:P1234A n:Health`                                                                                                          |
| **Add appointment**    | `add_appointment ic:NRIC dt:DATE d:DETAILS` <br> e.g., `add_appointment ic:T1234567A dt:2025-11-01 d:Meetup`                                                                                                     |
| **Remove appointment** | `remove_appointment a:APPOINTMENT_ID` <br> e.g., `remove_appointment a:A1234A`                                                                                                                                   |
| **View appointments**  | `view_appointment -a` OR `view_appointment a:` <br> e.g., `view_appointment a:A1234A`                                                                                                                            |
| **Edit appointment**   | `edit_appointment a:APPOINTMENT_ID [ic:NRIC] [dt:DATE] [d:DETAILS]` OR <br> e.g., `edit_appointment a:A1234A dt:2025-10-31`                                                                                      |
| **Sort appointments**  | `sort_appointment -a` OR `sort_appointment -i` OR `sort_appointment -da` OR `sort_appointment -dd`                                                                                                               |
| **Clear**              | `clear`                                                                                                                                                                                                          |
| **Exit**               | `exit`                                                                                                                                                                                                           |
| **Help**               | `help`                                                                                                                                                                                                           |

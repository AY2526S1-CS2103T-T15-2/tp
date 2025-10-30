---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `remove_contact ic:T1234567A`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI. The `ListPanel` comprises of `AppointmentListPanel`, `ContactListPanel`, `ContractListPanel` and `PolicyListPanel`

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Contact` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("remove_contact ic:T1234567A")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `RemoveContactCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `RemoveContactCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `RemoveContactCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to remove a contact).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddContactCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddContactCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddContactCommandParser`, `RemoveContactCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Contact`, `Contract`, `Policy` and `Appointment` objects (which are contained in a `UniqueContactList`, `UniqueContractList`, `UniquePolicyList` and `UniqueAppointmentList` object respectively).
* stores the currently 'selected' objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change. For example, `view_contact ic:T1234567A` will return `ObservableList<Contact>` as a filtered list containing the specific `Contact` with `NRIC` 'T1234567A', given the `Contact` exists within the application data. The respective `Contact`, `Contract`, `Policy` and `Appointment` objects are returned in their respective `ObservableList<T>` upon their respective command issued.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Contact` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Contact` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

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

### Add policies from file feature

The `add_policy` command allows users to add multiple policies at once, loaded from a file.

There were a few alternative behaviours and implementations that were considered when a user adds multiple policies
from a file.

First, when an invalid line is read from the file, it was chosen that previous valid lines are ignored instead of
adding all policies until an invalid input. This decision was made so the user will not have issues re-adding the
same file later and encountering an error about duplicate policies from having the same name and details.

An important consideration when implementing this is since policies and other data in iCon are assigned and identified
by a unique alphanumeric string id, there is a need for `Model` to generate new random ids not yet present in iCon.

Therefore, in implementation, the class `UnassignedPolicy` was created to facilitate the above described behaviour. The
parser class `PolicyFileParser` parses and returns `UnassignedPolicy` objects before they are assigned ids, which are
compared with each other and within existing policies for duplicates. Once all lines are parsed and checks are passed
does the `Model` only create `PolicyId`s and assigns and adds the policies to iCon.

The following activity diagram describes the expected behaviour when a user executes the `add_policy` command
with the file option:

<puml src="diagrams/AddPolicyFileActivityDiagram.puml" alt="AddPolicyFileActivityDiagram" />

The alternative was to simply treat each line as its own or similar to an `AddPolicyCommand`, but this does not
as easily implement the desired behaviour.

### Sorting data in the view

iCon allows users to sort their current view of data using `sort_*` commands atop of filtering results with `view_*`
commands. Inspired by CLI syntax, options were chosen to be represented as flags, such as `-a` and `-i`.

Upon implementation, an additional layer using JavaFX's `SortedList<T>` was added atop the `FilteredList<T>`. This is
what allows the user to apply a filter and a sort, which then gets displayed.

One consideration when implementing was to consider what type the `Command` object should permit. JavaFX specifies a
`Comparator<T>` type to determine how to sort the `ObservableList<T>`. It further allows the `null` value to signify
insertion order. The allowance of `null` as a comparator may make it may be unclear to future developers if
`Comparator<T>` was directly used as the type needed by the `Command`.

Therefore, it was chosen to have an enumeration for the different applicable sorting types, which encloses the `null`
comparator to a more descriptive `UNORDERED` enum, and which allows non-null checking. This has the additional benefit
of making it clear to developers what types of sorting options there are, instead of being able to directly define any
`Comparator<T>`.

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

Step 2. The user executes `delete 5` command to delete the 5th contact in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new contact. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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
### Edit Feature

#### Implementation

The edit feature is managed by the respective **EditFIELDCommand** classes. 
Implementation is pretty similar for the three class, hence using `edit_contact` as an example
to demonstrate the structure of the implementation.

How it works:

1. When `edit_contact ic:NRIC [fields to edit]` is called, it is fed through `EditContactCommandParser` first,
   Then, the parser checks for the presence of the `NRIC` identifying field and at least one other editable field.
   The parser parses the fields as **String** initially, then converts the ic string into an `Nric` object. Then,
   it converts the editable field strings into an `EditContactDescriptor` object.
2. `EditContactCommandParser` will now pass the `Nric` and `EditContactDescriptor` into a `EditContactCommand` constructor
3. The `EditContactCommand` will then be executed, which start with getting a unique contact list
4. After that, we check if `Nric` is in the unique contact list, if not, we throw a `CommandException` with `MESSAGE_CONTACT_NOT_FOUND`
5. Following, we find the `contactToEdit` and create a new Contact `editedContact` with since contact is immutable.
6. We then do a check to make sure we put in accurate data, we check if `editedContact` is in iCon (by checking `Nric`).
7. We then replace `contactToEdit` with `editedContact` and update the filtered contact list to show all contacts
8. We then return a `CommandResult` to signify successful command execution.

How to apply to other commands:

1. Identifying fields are different for different commands. Contact: `Nric`, Contract:`CId` Policy: `PId`
2. Replace all `contact` with the field you are trying to edit e.g `editContactCommand` -> `editPolicyCommand`

<puml src="diagrams/EditContactSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `edit_contact ic:T1234567A p:97456321` Command" />

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Independent Insurance Agents who manage the personal portfolio of clients, contracts and insurance policies.

**Value proposition**:

iCon helps insurance agents manage a large pool of clients and contracts, with the flexibility to add policies from any issuer. By enabling fast, accurate retrieval of critical client and policy data, it minimizes admin overhead and let agents focus more on delivering high-quality advisory services.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​               | So that I can …​                                                     |
|----------|-----------------|----------------------------|----------------------------------------------------------------------|
| `* * *`  | Insurance Agent | add a contact              | store contacts                                                       |
| `* * *`  | Insurance Agent | remove a contact           | remove unnecessary contacts                                          |
| `* * *`  | Insurance Agent | view a contact             | see contact details of customers I want to see                       |
| `* * *`  | Insurance Agent | add a policy               | add more policy types                                                |
| `* * *`  | Insurance Agent | remove a policy            | remove unnecessary policy types                                      |
| `* * *`  | Insurance Agent | view a policy              | see the policy details                                               |
| `* * *`  | Insurance Agent | add a contract             | link a policy to a customer                                          |
| `* * *`  | Insurance Agent | remove a contract          | remove unnecessary contracts                                         |
| `* * *`  | Insurance Agent | view a contract            | see the contract details and who signed the contract                 |
| `* *`    | Insurance Agent | tag a contact              | tag a contact with follow-ups                                        |
| `* *`    | Insurance Agent | search for a contact       | find a specific contact by name                                      |
| `* *`    | Insurance Agent | search for a contract      | find a specific contract by its type or date                         |
| `* *`    | Insurance Agent | add client appointments    | add an appointment date for a contact for follow-ups                 |
| `* *`    | Insurance Agent | remove client appointments | remove any misplaced appointment dates for a contact                 |
| `* *`    | Insurance Agent | view client appointments   | see appointments with my clients                                     |
| `* *`    | Insurance Agent | add contract expiry        | start to schedule an appointment closer to contract's expiry         |
| `* *`    | Insurance Agent | add contract premium       | easily reference the rates offered to my customers                   |
| `* *`    | Insurance Agent | edit a contract            | edit any wrong or changed details of a contract                      |
| `* *`    | Insurance Agent | edit a contact             | edit the wrong details of customers                                  |
| `* *`    | Insurance Agent | edit a policy              | edit the policy of the contract                                      |
| `*`      | Insurance Agent | sort by contacts           | order by contacts                                                    |
| `*`      | Insurance Agent | sort by contracts          | order by contracts                                                   |
| `*`      | Insurance Agent | edit client appointments   | reschedule an appointment date for a contact or mark it as completed |
| `*`      | Insurance Agent | sort by appointments       | easily take reference to upcoming appointments                       |
| `*`      | Insurance Agent | search for appointment     | easily search the details of an appointment with a client            |




### Use cases

(For all use cases below, the **System** is `iCon` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: UC1 - Add a contact**

**MSS**

1.  User requests to add a contact with all details specified
2.  iCon adds the contact

    Use case ends.

**Extensions**
* 1a. Some compulsory details are missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. The contact is duplicate.

    * 1b1. iCon shows an error message.

      Use case ends.

**Use case: UC2 - Delete a contact**

**MSS**

1. User requests to delete a specific contact in the list by NRIC
2. iCon deletes the contact

    Use case ends.

**Extensions**

* 1a. The given NRIC is invalid.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. There is no contact stored with the given NRIC.

    * 1b1. iCon shows an error message.

      Use case ends.

* 1c. The contact to be deleted is linked to existing contracts.

    * 1c1. iCon shows an error message.

      Use case ends.

**Use Case: UC3 - Edit a contact's details**

**MSS**

1. User requests to edit a specific contact in the list by NRIC
2. iCon updates the contact's details

    Use case ends.

**Extensions**

* 1a. The given NRIC is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. Valid NRIC but no details to be edited.

    * 1b1. iCon shows an error message.

      Use case ends.

* 1c. There is no contact stored with the given NRIC.

    * 1c1. iCon shows an error message.

      Use case ends.

**Use Case: UC4 - View contacts by NRIC**

**MSS**

1.  User requests to view contacts by NRIC substring
2.  iCon shows a list contacts that matches the NRIC substring given

    Use case ends.

**Extensions**

* 1a. The given NRIC is missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. No contacts found.

  * 2a1. iCon shows an empty list.

    Use case ends.

**Use Case: UC5 - view all contacts**

**MSS**

1.  User requests to view contacts
2.  iCon shows a list of contacts

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. iCon shows an empty list.

    Use case ends.

**Use Case: UC6 - Clear all data**

**MSS**

1.  User requests to clear all data
2.  iCon clears all data

    Use case ends.

**Use Case: UC7 - Exit the App**

**MSS**

1.  User requests to exit the App
2.  iCon saves all data to hard disk
3.  iCon exits

    Use case ends.

**Extensions**

* 2a. iCon fails to save data to hard disk.

    * 2a1. iCon shows an error message.

      Use case resumes at step 3.

**Use Case: UC8 - Add Policy**

**MSS**

1. User adds policy with all compulsory details specified
2. iCon adds the policy
3. iCon shows a success message & returns a policy ID to the user

   Use case ends.

**Extensions**

* 1a. Not all compulsory details are specified.

   * 1a1. iCon shows an error message.

     Use case ends.

**Use Case: UC9 - Add Policy(by file path)**

**MSS**

1. User adds policy using file path
2. iCon adds the policy
3. iCon shows a success message & returns a policy ID to the user

   Use case ends.

**Extensions**

* 1a. File path is invalid or file not found.

    * 1a1. iCon shows an error message.

      Use case ends.

**Use case: UC10 - Remove Policy**

**MSS**

1. User requests to remove a specific policy in the list by policy id
2. iCon removes the policy

    Use case ends.

**Extensions**

* 1a. The given id is invalid.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The policy to be deleted is linked to existing contracts.

    * 2a1. iCon shows an error message.

      Use case ends.

* 2b. There is no policy stored with the given id.

    * 2b1. iCon shows an error message.

      Use case ends.

**Use Case: UC11 - Edit Policy**

**MSS**

1. User requests to edit a specific policy in the list by policy id with some or all details specified
2. iCon updates the policy's details

    Use case ends.

**Extensions**

* 1a. The given id is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. Valid id but no details to be edited.

    * 1b1. iCon shows an error message.

      Use case ends.

* 2a. There is no policy stored with the given id.

    * 2a1. iCon shows an error message.

      Use case ends.

**Use Case: UC12 - View Specific Policies**

**MSS**

1. User requests to view specific policies by policy id substrings
2. iCon shows a list of policies that match the policy id substrings

   Use case ends.

**Extensions**

* 1a. There is no given search substring.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The list is empty.

    * 2a1. iCon shows an empty list.

      Use case ends.

**Use Case: UC13 - View All Policies**

**MSS**

1. User requests to view all policies
2. iCon shows a list of policies

    Use case ends.

**Extensions**

* 2a. The list is empty.

    * 2a1. iCon shows an empty list.

      Use case ends.

**Use Case: UC14 - Sort Policies**

**MSS**

1. User requests to sort policies by a given flag
2. iCon shows a sorted list of policies

    Use case ends.

**Extensions**

* 1a. The given flag is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

**Use Case: UC15 - Add contract**

**MSS**

1. User adds contract with specific details
2. iCon adds the contract
3. iCon shows a success message & returns a contract ID to the user

    Use case ends.

**Extensions**

* 1a. Some compulsory details are missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The contact ID is invalid.

    * 2a1. iCon shows an error message.

      Use case ends.

* 2b. The policy ID is invalid.

    * 2b1. iCon shows an error message.

      Use case ends.

* 2c. The contract is duplicate.

    * 2c1. iCon shows an error message.

      Use case ends.

* 2d. The contract period is invalid.

    * 2d1. iCon shows an error message.

      Use case ends.

* 2e. The date format is invalid.

    * 2e1. iCon shows an error message.

      Use case ends.

* 2f. The premium amount is invalid.

    * 2f1. iCon shows an error message.

      Use case ends.

**Use Case: UC16 - Remove contract**

**MSS**

1. User requests to remove a specific contract in the list by contract id
2. iCon removes the contract

    Use case ends.

**Extensions**

* 1a. The given id is invalid.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. There is no contract stored with the given id.

    * 2a1. iCon shows an error message. 

      Use case ends.

**Use Case: UC17 - Edit contract**

**MSS**

1. User requests to edit a specific contract in the list by contract id with some or all details specified
2. iCon updates the contract's details

    Use case ends.

**Extensions**

* 1a. The given id is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. Valid id but no details to be edited.

    * 1b1. iCon shows an error message.

      Use case ends.

* 2a. There is no contract stored with the given id.

    * 2a1. iCon shows an error message.

      Use case ends.

* 2b. Edited details are invalid.

    * 2b1. iCon shows an error message.

      Use case ends.

**Use Case: UC18 - View specific contracts**

**MSS**

1. User requests to view details of a specific contract in the list by id
2. iCon shows the contract's details that matches the id given

    Use case ends.

**Extensions**

* 1a. There is no given id.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The list is empty.

  * 2a1. iCon shows an empty list.

    Use case ends.

**Use Case: UC19 - View all contracts**

**MSS**

1.  User requests to view all contracts
2. iCon shows a list of contracts

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. iCon shows an empty list.

    Use case ends.

**Use Case: UC20 - Sort contracts**

**MSS**

1. User requests to sort contracts by a given flag
2. iCon shows a sorted list of contracts

    Use case ends.

**Extensions**

* 1a. The given flag is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

**Use Case: UC21 - Add appointment**

**MSS**

1. User adds appointment with specific details
2. iCon adds the appointment
3. iCon shows a success message

    Use case ends.

**Extensions**

* 1a. Some compulsory details are missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The NRIC is invalid.

    * 2a1. iCon shows an error message.

      Use case ends.

* 2b. The date format is invalid.

    * 2b1. iCon shows an error message.

      Use case ends.

**Use Case: UC22 - Remove appointment**

**MSS**

1. User requests to remove a specific appointment in the list by Appointment ID
2. iCon removes the appointment

    Use case ends.

**Extensions**

* 1a. The given Appointment ID is invalid.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. There is no appointment stored with the given Appointment ID.

    * 1b1. iCon shows an error message.

      Use case ends.

**Use Case: UC23 - Edit appointment**

**MSS**

1. User requests to edit a specific appointment in the list by Appointment ID with some or all details specified
2. iCon updates the appointment's details

    Use case ends.

**Extensions**

* 1a. The given Appointment ID is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

* 1b. Valid Appointment ID but no details to be edited.

    * 1b1. iCon shows an error message.

      Use case ends.

* 2a. There is no appointment stored with the given Appointment ID.

    * 2a1. iCon shows an error message.

      Use case ends.

* 2b. Edited details are invalid.

    * 2b1. iCon shows an error message.

      Use case ends.

**Use Case: UC24 - View specific appointments**

**MSS**

1. User requests to view specific appointments by Appointment ID substrings
2. iCon shows a list of appointments that match the Appointment ID substrings

    Use case ends.

**Extensions**

* 1a. There is no given search substring.

    * 1a1. iCon shows an error message.

      Use case ends.

* 2a. The list is empty.

    * 2a1. iCon shows an empty list.

      Use case ends.

**Use Case: UC25 - View all appointments**

**MSS**

1. User requests to view all appointments
2. iCon shows a list of appointments

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. iCon shows an empty list.

    Use case ends.

**Use Case: UC26 - Sort appointments**

**MSS**

1. User requests to sort appointments by a given flag
2. iCon shows a sorted list of appointments

    Use case ends.

**Extensions**

* 1a. The given flag is invalid or missing.

    * 1a1. iCon shows an error message.

      Use case ends.

### Non-Functional Requirements

1.  The system should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  The application should be easily installable, with no additional third-party dependencies.
3.  The application should function entirely offline.
4.  The system should be able to hold up to 1000 contacts, contracts, and policies without a noticeable sluggishness in performance for typical usage.
5.  The system should respond to user input and commands within 100 milliseconds.
6.  The system should manage its own copy of policies separate from the source file when adding or removing policies.
7.  The save data should not take more than 100 MB in typical usage.
8.  The system must save changes to the hard disk after 1 or a few user modifications and after exiting the program, to minimize data loss in the event of a crash.
9.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Contact**: A customer of the insurance agent that has data fields, name, email, and NRIC
* **Policy**: The document that details the terms and conditions of a contract
* **Contract**: A contract that binds a customer to a certain policy

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Start Clean: `clear`

### Contact management

1. Add two contacts. The list should update immediately

   1. add_contact n:Bob Lim p:81112222 ic:G1234567B e:bob@example.com a:456 MBS

   1. add_contact n:Alice Tan p:91234567 ic:S9876543A e:alice@example.com a:123 Orchard Road

1. Sort contacts by alphabetical sort. `sort_contact -a`.
   2. The list should re-order to show Alice first, then Bob.

1. Edit contact by NRIC to change fields such as phone number.

    1. `edit_contact ic: G1234567B p:88888888`

1. View a specific contact: View by NRIC. Only Bob's details should be displayed

   1. `view_contact ic: G1234567B`

1. Remove a contact: Remove Bob using his NRIC. The list should update, leaving only Alice.

    1. `remove_contact ic: G1234567B`

### Policy management

1. Add two new policies.

    1. `add_policy n:Premium Health d:Covers all hospital stays and specialist visits`

    1. `add_policy n:Basic Car d:Covers basic third-party car damage`

1. Add policy from file.

    1. Create a file named `policy_file.txt` in the same folder as your .jar file.

    1. Put this text inside that file: Life Insurance`This policy coverage for family...

    1. Now, run the command: `add_policy f:policy_file.txt`

1. View all policies. The list should show all 3 policies added.
Note the POLICY_IDs (eg. P1234A) assigned by the system in the GUI. Hence, tailor the POLICY_ID to the randomly generated POLICY ID as shown in the GUI

   1.  `view_policy -a`

1. Edit a policy. Use the POLICY_ID for "Premium Health" (eg. P1234A) to edit it

    1. `edit_policy p:P1234A n: Premium Health Gold`

1. Remove the Basic Car policy using the POLICY_ID (eg. P5678B)

    1. remove_policy p:P5678B

### Contract management

1. Setup: We should have "Alice" (NRIC S9876543A) and a Policy (eg. P1234A for "Premium Health Gold", refer to the POLICY_ID in the GUI).

1. Add a new contract for Alice with the policy.

    1. `add_contract p:P1234A ic:S9876543A dt:2024-01-01 e:2025-01-01 pr:1200.50`

1. Add a contract (Bad expiry date): This command should fail because the expiry date is before the signed date

    1. `add_contract p:P1234A ic:S9876543A dt:2025-01-01 e:2024-01-01 pr:100`

1. Add a contract (Bad premium): This command should fail because premium is not a positive number

    1. `add_contract p:P1234A ic:S9876543A dt:2024-01-01 e:2025-01-01 pr:-50`

1. View all contracts. The list should show the contract added.

    1. `view_contract -a`

1. Edit Contract: Similar to POLICY_ID, use the CONTRACT_ID assigned by the system (eg. C1234A) to edit the premium

    1. `edit_contract c:C1234A pr:1300.00`

1. Remove Contract: Remove the contract you edited (use the CONTRACT_ID)

    1. `remove_contract c:C1234A`

### Appointment Management

1. Setup: We still have "Alice" (NRIC S9876543A) in the contact list.

1. Add two appointments for Alice.

    1. `add_appointment ic:S9876543A dt:2025-11-15 d:Discuss contract renewal`

    1. `add_appointment ic:S9876543A dt:2025-10-10 d:Initial healthcare review`

1. Add appointment (Invalid NRIC): This command should fail because the NRIC does not exist.

    1. `add_appointment ic:F9999999Z dt:2025-12-01 d:Non-existent contact`

1. Sort appointments: Sort the appointments by date in ascending order. The "Initial healthcare review" (Oct 10) should now appear before the "contract renewal" (Nov 15).

    1. `sort_appointment -da`

1. Edit appointments: Similar to POLICY_ID, use APPOINTMENTID from the GUI (eg. A1234B) from one of the appointments and change its date

    1. `edit_appointment a:A1234B dt:2025-11-16 d:Sign new contract papers`

1. Remove appointment: Remove the appointment you edited (using APPOINTMENTID) (eg. A1234B)

    1. `remove_appointment a:A1234B`

### Exiting and Relaunching

1. Exit the app

   1. `exit`

1. Relaunch the app

    1. After relaunching the app, you should see the existing contacts, policies, contracts and appointments saved previously.

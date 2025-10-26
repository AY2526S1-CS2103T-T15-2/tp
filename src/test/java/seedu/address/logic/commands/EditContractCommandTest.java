package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.ALICE;
import static seedu.address.testutil.TypicalData.BENSON;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.CONTRACT_B;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalId.CONTRACT_A_ID;
import static seedu.address.testutil.TypicalId.CONTRACT_B_ID;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContractCommand.EditContractDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.testutil.ContractBuilder;
import seedu.address.testutil.EditContractDescriptorBuilder;
import seedu.address.ui.ListPanelType;

public class EditContractCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contract editedContract = new ContractBuilder().build();
        EditContractDescriptor descriptor = new EditContractDescriptorBuilder(editedContract).build();
        EditContractCommand editContractCommand = new EditContractCommand(CONTRACT_A_ID, descriptor);

        String expectedMessage = String.format(EditContractCommand.MESSAGE_EDIT_CONTRACT_SUCCESS,
                editedContract.getCId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContract(model.getFilteredContractList().get(0), editedContract);

        assertCommandSuccess(editContractCommand, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Contract contract = model.getFilteredContractList().get(0);

        ContractBuilder contractInList = new ContractBuilder(contract);
        Contract editedContract = contractInList.withName(BENSON.getName().toString())
                .withNric(BENSON.getNric().toString()).withDate(LocalDate.parse("2023-05-01"))
                .build();

        EditContractDescriptor descriptor = new EditContractDescriptorBuilder().withNric(BENSON.getNric().toString())
                .withDate("2023-05-01").build();
        EditContractCommand editContractCommand = new EditContractCommand(contract.getCId(), descriptor);

        String expectedMessage = String.format(EditContractCommand.MESSAGE_EDIT_CONTRACT_SUCCESS,
                editedContract.getCId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContract(contract, editedContract);

        assertCommandSuccess(editContractCommand, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Contract contract = model.getFilteredContractList().get(0);
        EditContractCommand editContractCommand = new EditContractCommand(contract.getCId(),
                new EditContractDescriptor());

        String expectedMessage = String.format(EditContractCommand.MESSAGE_EDIT_CONTRACT_SUCCESS,
                contract.getCId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editContractCommand, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Contract contract = model.getFilteredContractList().get(0);

        ContractBuilder contractInList = new ContractBuilder(contract);
        Contract editedContract = contractInList.withName(ALICE.getName().toString())
                .withNric(ALICE.getNric().toString()).withDate(LocalDate.parse("2023-06-01"))
                .build();

        EditContractDescriptor descriptor = new EditContractDescriptorBuilder().withNric(ALICE.getNric().toString())
                .withDate("2023-06-01").build();
        EditContractCommand editContractCommand = new EditContractCommand(contract.getCId(), descriptor);

        String expectedMessage = String.format(EditContractCommand.MESSAGE_EDIT_CONTRACT_SUCCESS,
                editedContract.getCId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContract(contract, editedContract);

        assertCommandSuccess(editContractCommand, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void execute_duplicateContractUnfilteredList_failure() {
        Contract contract = model.getFilteredContractList().get(0);

        EditContractDescriptor descriptor = new EditContractDescriptorBuilder(contract).build();
        EditContractCommand editContractCommand = new EditContractCommand(CONTRACT_B_ID, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContract(model.getFilteredContractList().get(0), contract);

        assertCommandFailure(editContractCommand, model, EditContractCommand.MESSAGE_DUPLICATE_CONTRACT);
    }

    @Test
    public void execute_duplicateContractFilteredList_failure() {
        Contract contract = model.getFilteredContractList().get(0);

        EditContractDescriptor descriptor = new EditContractDescriptorBuilder(contract).build();
        EditContractCommand editContractCommand = new EditContractCommand(CONTRACT_B_ID, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContract(model.getFilteredContractList().get(0), contract);

        assertCommandFailure(editContractCommand, model, EditContractCommand.MESSAGE_DUPLICATE_CONTRACT);
    }

    @Test
    public void execute_contractNotFoundUnfilteredList_failure() {
        ContractId invalidContractId = new ContractId("C9999Z");

        EditContractDescriptor descriptor = new EditContractDescriptorBuilder().withName("Charlie Brown").build();
        EditContractCommand editContractCommand = new EditContractCommand(invalidContractId, descriptor);

        assertCommandFailure(editContractCommand, model, Messages.MESSAGE_CONTRACT_NOT_FOUND);
    }

    @Test
    public void execute_invalidContractPeriod_failure() {
        EditContractDescriptor descriptor = new EditContractDescriptorBuilder()
                .withDate("2024-01-01")
                .withExpiryDate("2023-01-01")
                .build();
        EditContractCommand editContractCommand = new EditContractCommand(CONTRACT_A_ID, descriptor);

        assertCommandFailure(editContractCommand, model, Messages.MESSAGE_INVALID_CONTRACT_PERIOD);
    }

    @Test
    public void equals() {
        final EditContractCommand standardCommand = new EditContractCommand(CONTRACT_A_ID,
                new EditContractDescriptorBuilder(CONTRACT_A).withName(CONTRACT_B.getName().toString()).build());

        // same values -> returns true
        EditContractDescriptor copyDescriptor = new EditContractDescriptorBuilder(CONTRACT_A)
                .withName(CONTRACT_B.getName().toString()).build();
        EditContractCommand commandWithSameValues = new EditContractCommand(CONTRACT_A_ID, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different contractId -> returns false
        assertNotEquals(standardCommand, new EditContractCommand(CONTRACT_B_ID, copyDescriptor));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditContractCommand(CONTRACT_A_ID,
                new EditContractDescriptorBuilder().withName("Bob").build()));
    }

    @Test
    public void toStringMethod() {
        EditContractDescriptor descriptor = new EditContractDescriptorBuilder()
                .withPId("P1234A")
                .withNric("S1234567A")
                .withName("Alice Pauline")
                .withDate("2023-01-01")
                .withExpiryDate("2024-01-01")
                .withPremium("1000.00")
                .build();
        EditContractCommand editContractCommand = new EditContractCommand(CONTRACT_A_ID, descriptor);

        String expectedString = EditContractCommand.class.getCanonicalName() + "{cId=" + CONTRACT_A_ID + "}";

        assertEquals(expectedString, editContractCommand.toString());
    }

}

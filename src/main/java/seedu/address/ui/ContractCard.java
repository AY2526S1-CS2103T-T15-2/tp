package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contract.Contract;

/**
 * An UI component that displays information of a {@code Policy}.
 */
public class ContractCard extends UiPart<Region> {

    private static final String FXML = "ContractListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contract contract;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label cId;
    @FXML
    private Label pId;
    @FXML
    private Label nric;
    @FXML
    private Label dateSigned;

    /**
     * Creates a {@code PolicyCode} with the given {@code Policy} and index to display.
     */
    public ContractCard(Contract contract, int displayedIndex) {
        super(FXML);
        this.contract = contract;
        pId.setText("Policy id: " + contract.getPId().toString());
        cId.setText("Contract id: " + contract.getCId().value);
        name.setText("Name: " + contract.getName().fullName);
        nric.setText("NRIC: " + contract.getNric().nric);
        dateSigned.setText("Date Signed: " + contract.getDate().toString());
    }
}

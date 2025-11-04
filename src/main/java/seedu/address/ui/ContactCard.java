package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;

/**
 * A UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label nric;
    @FXML
    private FlowPane address;
    @FXML
    private FlowPane email;
    @FXML
    private FlowPane contracts;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ContactCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        phone.setText(contact.getPhone().value);
        nric.setText(contact.getNric().nric);
        contact.getContracts().forEach(contract -> contracts.getChildren()
                .add(new Label(contract.getCId().value + " ")));
        if (contact.getAddress() != null && !contact.getAddress().value.isBlank()) {
            Label addressLabel = new Label(contact.getAddress().value);
            addressLabel.setWrapText(true);
            address.getChildren().add(addressLabel);
        }
        if (contact.getEmail() != null && !contact.getEmail().value.isBlank()) {
            Label emailLabel = new Label(contact.getEmail().value);
            emailLabel.setWrapText(true);
            email.getChildren().add(emailLabel);
        }
        contact.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}

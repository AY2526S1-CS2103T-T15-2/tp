package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.EditContactCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalData.getAmy;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class EditContactCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private EditContactCommandParser parser = new EditContactCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no ic specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, NRIC_DESC_AMY, EditContactCommand.MESSAGE_NOT_EDITED);

        // no ic and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidNric_failure() {
        // invalid ic
        assertParseFailure(parser, "ic:invalidNric" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // nric not found
        assertParseFailure(parser, "ic:T0000000Z" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Contact} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, NRIC_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NRIC_DESC_AMY + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NRIC_DESC_AMY + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NRIC_DESC_AMY + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(getAmy())
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditContactCommand expectedCommand = new EditContactCommand(new Nric(VALID_NRIC_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NRIC_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(new Nric(VALID_NRIC_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Nric nric = new Nric(VALID_NRIC_AMY);
        // name
        String userInput = NRIC_DESC_AMY + NAME_DESC_AMY;
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withNric(VALID_NRIC_AMY)
                .withName(VALID_NAME_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(nric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = NRIC_DESC_AMY + PHONE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY).withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditContactCommand(nric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = NRIC_DESC_AMY + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY).withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditContactCommand(nric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = NRIC_DESC_AMY + ADDRESS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY).withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditContactCommand(nric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = NRIC_DESC_AMY + TAG_DESC_FRIEND;
        descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY).withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditContactCommand(nric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddContactCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        String userInput = NRIC_DESC_AMY + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid followed by invalid
        userInput = NRIC_DESC_AMY + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = NRIC_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = NRIC_DESC_AMY + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = NRIC_DESC_AMY + TAG_EMPTY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withNric(VALID_NRIC_AMY)
                .withTags()
                .build();
        EditContactCommand expectedCommand = new EditContactCommand(new Nric(VALID_NRIC_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

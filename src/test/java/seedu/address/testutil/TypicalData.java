package seedu.address.testutil;

import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DATE_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DATE_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DETAILS_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DETAILS_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_C;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_D;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_E;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_NRIC_1;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_NRIC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HOME;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

/**
 * A utility class containing a list of {@code Person} and {@code Policy} objects to be used in tests.
 */
public class TypicalData {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withNric("S1234567A").withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withNric("S1234567B").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withNric("S1234567C")
            .withEmail("").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withNric("S1234567D")
            .withEmail("cornelia@example.com").withAddress("").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withNric("T1234567A")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withNric("T1234567B")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withNric("T1234597A")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withNric("S9876543L").withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withNric(VALID_NRIC_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withNric(VALID_NRIC_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Policy LIFE = new Policy(
            new PolicyName("Life Insurance"),
            new PolicyDetails("This policy coverage for family..."),
            new PolicyId("abcdef")
    );

    public static final Policy HEALTH = new Policy(
            new PolicyName("Healthcare - A"),
            new PolicyDetails("Other policy details 123"),
            new PolicyId("123456")
    );

    public static final Policy PROPERTY = new Policy(
            new PolicyName("Property"),
            new PolicyDetails("Non-alphanumeric characters *^$+-"),
            new PolicyId("Abc123")
    );

    public static final Policy TRAVEL = new Policy(
            new PolicyName("Travel - A"),
            new PolicyDetails("This policy covers flights to European countries"),
            new PolicyId("xyz123")
    );

    // Manually added - Policy's details found in {@code PolicyCommandTestUtil}
    public static final Policy HOME = new PolicyBuilder().withName(VALID_POLICY_NAME_HOME)
            .withDetails(VALID_DETAILS_HOME).withId(VALID_POLICY_ID_HOME).build();
    public static final Policy HEALTH_B = new PolicyBuilder().withName(VALID_POLICY_NAME_HEALTH_B)
            .withDetails(VALID_DETAILS_HEALTH_B).withId(VALID_POLICY_ID_HEALTH_B).build();

    public static final Contract CONTRACT_A = new Contract(
            new ContractId("C1234A"),
            ALICE.getName(),
            ALICE.getNric(),
            LIFE.getId(),
            LocalDate.parse("2023-01-01"),
            LocalDate.parse("2025-01-01"),
            new ContractPremium("1000.00")
    );

    public static final Contract CONTRACT_B = new Contract(
            new ContractId("C1234B"),
            BENSON.getName(),
            BENSON.getNric(),
            HEALTH.getId(),
            LocalDate.parse("2023-02-01"),
            LocalDate.parse("2025-02-01"),
            new ContractPremium("1500.00")
    );

    public static final Contract CONTRACT_C = new Contract(
            new ContractId("C1234C"),
            CARL.getName(),
            CARL.getNric(),
            PROPERTY.getId(),
            LocalDate.parse("2023-03-01"),
            LocalDate.parse("2025-03-01"),
            new ContractPremium("2000.00")
    );

    // Spare contracts not added into TypicalAddressBook
    public static final Contract CONTRACT_D = new Contract(
            new ContractId("C1234D"),
            DANIEL.getName(),
            DANIEL.getNric(),
            LIFE.getId(),
            LocalDate.parse("2023-04-01"),
            LocalDate.parse("2025-04-01"),
            new ContractPremium("3000.00")
    );

    public static final Contract CONTRACT_E = new Contract(
            new ContractId("C1234E"),
            ELLE.getName(),
            ELLE.getNric(),
            HEALTH.getId(),
            LocalDate.parse("2023-05-01"),
            LocalDate.parse("2025-05-01"),
            new ContractPremium("1500.00")
    );

    public static final Appointment APPOINTMENT_A = new Appointment(
            new AppointmentId(VALID_APPOINTMENT_ID_A),
            new Nric(VALID_APPOINTMENT_NRIC_1),
            LocalDate.parse(VALID_APPOINTMENT_DATE_A),
            new AppointmentDetails(VALID_APPOINTMENT_DETAILS_A)
    );

    public static final Appointment APPOINTMENT_B = new Appointment(
            new AppointmentId(VALID_APPOINTMENT_ID_B),
            new Nric(VALID_APPOINTMENT_NRIC_2),
            LocalDate.parse(VALID_APPOINTMENT_DATE_B),
            new AppointmentDetails(VALID_APPOINTMENT_DETAILS_B)
    );

    public static final Appointment APPOINTMENT_C = new Appointment(
            new AppointmentId(VALID_APPOINTMENT_ID_C),
            CARL.getNric(),
            LocalDate.parse(VALID_APPOINTMENT_DATE_A),
            new AppointmentDetails(VALID_APPOINTMENT_DETAILS_A)
    );

    // Manually added - Appointment's details found in {@code AppointmentCommandTestUtil} (not in Typical AddressBook)
    public static final Appointment APPOINTMENT_D = new AppointmentBuilder().withId(VALID_APPOINTMENT_ID_D)
            .withNric(VALID_NRIC_BOB)
            .withDate(LocalDate.parse(VALID_APPOINTMENT_DATE_A))
            .withDetails(VALID_APPOINTMENT_DETAILS_A).build();
    public static final Appointment APPOINTMENT_E = new AppointmentBuilder().withId(VALID_APPOINTMENT_ID_E)
            .withNric(VALID_NRIC_AMY)
            .withDate(LocalDate.parse(VALID_APPOINTMENT_DATE_B))
            .withDetails(VALID_APPOINTMENT_DETAILS_B).build();


    private TypicalData() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Policy policy: getTypicalPolicies()) {
            ab.addPolicy(policy);
        }
        for (Contract contract : getTypicalContracts()) {
            ab.addContract(contract);
        }
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        ALICE.addContract(CONTRACT_A);
        BENSON.addContract(CONTRACT_B);
        CARL.addContract(CONTRACT_C);
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Policy> getTypicalPolicies() {
        LIFE.addContract(CONTRACT_A);
        HEALTH.addContract(CONTRACT_B);
        PROPERTY.addContract(CONTRACT_C);
        return new ArrayList<>(Arrays.asList(LIFE, HEALTH, PROPERTY, TRAVEL));
    }

    public static List<Contract> getTypicalContracts() {
        return new ArrayList<>(Arrays.asList(CONTRACT_A, CONTRACT_B, CONTRACT_C));
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APPOINTMENT_A, APPOINTMENT_B, APPOINTMENT_C));
    }
}

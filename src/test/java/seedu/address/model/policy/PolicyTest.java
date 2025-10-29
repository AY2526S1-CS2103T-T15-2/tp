package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HOME;
import static seedu.address.testutil.TypicalData.getHealthB;
import static seedu.address.testutil.TypicalData.getHome;
import static seedu.address.testutil.TypicalData.getLife;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PolicyBuilder;

public class PolicyTest {

    @Test
    public void hasSameId() {
        // same object -> returns true
        assertTrue(getHome().hasSameId(getHome()));

        // null -> returns false
        assertFalse(getHome().hasSameId(null));

        // same id, all other attributes different -> returns true
        Policy editedPolicy = new PolicyBuilder(getHealthB()).withId(VALID_POLICY_ID_HOME).build();
        assertTrue(getHome().hasSameId(editedPolicy));

        // different id, all other attributes same -> returns false
        editedPolicy = new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertFalse(getHome().hasSameId(editedPolicy));

        // id differs in case, all other attributes same -> returns false
        editedPolicy = new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HOME.toLowerCase()).build();
        assertFalse(getHome().hasSameId(editedPolicy));
    }

    @Test
    public void isSamePolicy() {
        // same object -> returns true
        assertTrue(getHome().isSamePolicy(getHome()));

        // null -> returns false
        assertFalse(getHome().isSamePolicy(null));

        // same id, all other attributes different -> returns false
        Policy editedPolicy = new PolicyBuilder(getHealthB()).withId(VALID_POLICY_ID_HOME).build();
        assertFalse(getHome().isSamePolicy(editedPolicy));

        // different id, all other attributes same -> returns true
        editedPolicy = new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertTrue(getHome().isSamePolicy(editedPolicy));

        // name differs in case -> returns false
        editedPolicy = new PolicyBuilder(getHome()).withName(VALID_POLICY_NAME_HOME.toLowerCase()).build();
        assertFalse(getHome().isSamePolicy(editedPolicy));
    }

    @Test
    public void policiesAreUnique() {
        // unique policies -> returns true
        assertTrue(Policy.policiesAreUnique(List.of(getHome(), getHealthB(), getLife())));

        // non-unique policies -> return false
        assertFalse(Policy.policiesAreUnique(List.of(getHome(), getHealthB(), getHome())));

        // non-unique policies with different ids -> return false
        assertFalse(Policy.policiesAreUnique(
                List.of(getHome(), getLife(), new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HEALTH_B).build())
        ));
    }

    @Test
    public void equals() {

        // same values -> returns true
        Policy policyCopy = new PolicyBuilder(getHome()).build();
        assertTrue(getHome().equals(policyCopy));

        // same object -> returns true
        assertTrue(getHome().equals(getHome()));

        // null -> returns false
        assertFalse(getHome().equals(null));

        // different type -> returns false
        assertFalse(getHome().equals(5));

        // different policy -> returns false
        assertFalse(getHome().equals(getHealthB()));

        // different name -> returns false
        Policy editedPolicy = new PolicyBuilder(getHome()).withName(VALID_POLICY_NAME_HEALTH_B).build();
        assertFalse(getHome().equals(editedPolicy));

        // different details -> returns false
        editedPolicy = new PolicyBuilder(getHome()).withName(VALID_DETAILS_HEALTH_B).build();
        assertFalse(getHome().equals(editedPolicy));

        // different id -> returns false
        editedPolicy = new PolicyBuilder(getHome()).withName(VALID_POLICY_ID_HEALTH_B).build();
        assertFalse(getHome().equals(editedPolicy));
    }

    @Test
    public void toStringMethod() {
        String expected = Policy.class.getCanonicalName() + "{name=" + getHome().getName()
                + ", details=" + getHome().getDetails() + ", id=" + getHome().getId() + "}";
        assertEquals(expected, getHome().toString());
    }

}

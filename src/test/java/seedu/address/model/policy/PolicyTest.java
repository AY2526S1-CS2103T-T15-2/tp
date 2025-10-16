package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HOME;
import static seedu.address.testutil.TypicalData.HEALTH_B;
import static seedu.address.testutil.TypicalData.HOME;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PolicyBuilder;

public class PolicyTest {

    @Test
    public void hasSameId() {
        // same object -> returns true
        assertTrue(HOME.hasSameId(HOME));

        // null -> returns false
        assertFalse(HOME.hasSameId(null));

        // same id, all other attributes different -> returns true
        Policy editedPolicy = new PolicyBuilder(HEALTH_B).withId(VALID_POLICY_ID_HOME).build();
        assertTrue(HOME.hasSameId(editedPolicy));

        // different id, all other attributes same -> returns false
        editedPolicy = new PolicyBuilder(HOME).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertFalse(HOME.hasSameId(editedPolicy));

        // id differs in case, all other attributes same -> returns false
        editedPolicy = new PolicyBuilder(HOME).withId(VALID_POLICY_ID_HOME.toLowerCase()).build();
        assertFalse(HOME.hasSameId(editedPolicy));
    }

    @Test
    public void isSamePolicy() {
        // same object -> returns true
        assertTrue(HOME.isSamePolicy(HOME));

        // null -> returns false
        assertFalse(HOME.isSamePolicy(null));

        // same id, all other attributes different -> returns false
        Policy editedPolicy = new PolicyBuilder(HEALTH_B).withId(VALID_POLICY_ID_HOME).build();
        assertFalse(HOME.isSamePolicy(editedPolicy));

        // different id, all other attributes same -> returns true
        editedPolicy = new PolicyBuilder(HOME).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertTrue(HOME.isSamePolicy(editedPolicy));

        // name differs in case -> returns false
        editedPolicy = new PolicyBuilder(HOME).withName(VALID_POLICY_NAME_HOME.toLowerCase()).build();
        assertFalse(HOME.isSamePolicy(editedPolicy));
    }

    @Test
    public void equals() {

        // same values -> returns true
        Policy policyCopy = new PolicyBuilder(HOME).build();
        assertTrue(HOME.equals(policyCopy));

        // same object -> returns true
        assertTrue(HOME.equals(HOME));

        // null -> returns false
        assertFalse(HOME.equals(null));

        // different type -> returns false
        assertFalse(HOME.equals(5));

        // different policy -> returns false
        assertFalse(HOME.equals(HEALTH_B));

        // different name -> returns false
        Policy editedPolicy = new PolicyBuilder(HOME).withName(VALID_POLICY_NAME_HEALTH_B).build();
        assertFalse(HOME.equals(editedPolicy));

        // different details -> returns false
        editedPolicy = new PolicyBuilder(HOME).withName(VALID_DETAILS_HEALTH_B).build();
        assertFalse(HOME.equals(editedPolicy));

        // different id -> returns false
        editedPolicy = new PolicyBuilder(HOME).withName(VALID_POLICY_ID_HEALTH_B).build();
        assertFalse(HOME.equals(editedPolicy));
    }

    @Test
    public void toStringMethod() {
        String expected = Policy.class.getCanonicalName() + "{name=" + HOME.getName()
                + ", details=" + HOME.getDetails() + ", id=" + HOME.getId() + "}";
        assertEquals(expected, HOME.toString());
    }

}

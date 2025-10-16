package seedu.address.model.policy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.HEALTH_B;
import static seedu.address.testutil.TypicalData.HOME;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.exceptions.DuplicatePolicyException;
import seedu.address.model.policy.exceptions.PolicyNotFoundException;
import seedu.address.testutil.PolicyBuilder;

public class UniquePolicyListTest {

    private final UniquePolicyList uniquePolicyList = new UniquePolicyList();

    @Test
    public void contains_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.containsSameId(null));
    }

    @Test
    public void contains_policyNotInList_returnsFalse() {
        assertFalse(uniquePolicyList.containsSameId(HOME));
    }

    @Test
    public void contains_policyInList_returnsTrue() {
        uniquePolicyList.add(HOME);
        assertTrue(uniquePolicyList.containsSameId(HOME));
    }

    @Test
    public void contains_policyWithSameIdInList_returnsTrue() {
        uniquePolicyList.add(HOME);
        Policy editedPolicy = new PolicyBuilder(HOME).withName(VALID_POLICY_NAME_HEALTH_B)
                .withDetails(VALID_DETAILS_HEALTH_B).build();
        assertTrue(uniquePolicyList.containsSameId(editedPolicy));
    }

    @Test
    public void contains_policyWithSameFieldsInList_returnsTrue() {
        uniquePolicyList.add(HOME);
        Policy editedPolicy = new PolicyBuilder(HOME).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertTrue(uniquePolicyList.containsSamePolicy(editedPolicy));
    }

    @Test
    public void add_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.add(null));
    }

    @Test
    public void add_duplicatePolicy_throwsDuplicatePolicyException() {
        uniquePolicyList.add(HOME);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.add(HOME));
    }

    @Test
    public void setPolicy_nullTargetPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(null, HOME));
    }

    @Test
    public void setPolicy_nullEditedPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(HOME, null));
    }

    @Test
    public void setPolicy_targetPolicyNotInList_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () -> uniquePolicyList.setPolicy(HOME, HOME));
    }

    @Test
    public void setPolicy_editedPolicyIsSamePolicy_success() {
        uniquePolicyList.add(HOME);
        uniquePolicyList.setPolicy(HOME, HOME);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(HOME);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasSameIdentity_success() {
        uniquePolicyList.add(HOME);
        Policy editedPolicy = new PolicyBuilder(HOME).withName(VALID_POLICY_NAME_HEALTH_B)
                .withDetails(VALID_DETAILS_HEALTH_B).build();
        uniquePolicyList.setPolicy(HOME, editedPolicy);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(editedPolicy);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasDifferentIdentity_success() {
        uniquePolicyList.add(HOME);
        uniquePolicyList.setPolicy(HOME, HEALTH_B);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(HEALTH_B);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasNonUniqueIdentity_throwsDuplicatePolicyException() {
        uniquePolicyList.add(HOME);
        uniquePolicyList.add(HEALTH_B);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.setPolicy(HOME, HEALTH_B));
    }

    @Test
    public void remove_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.remove(null));
    }

    @Test
    public void remove_policyDoesNotExist_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () -> uniquePolicyList.remove(HOME));
    }

    @Test
    public void remove_existingPolicy_removesPolicy() {
        uniquePolicyList.add(HOME);
        uniquePolicyList.remove(HOME);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullUniquePolicyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicies((UniquePolicyList) null));
    }

    @Test
    public void setPolicies_uniquePolicyList_replacesOwnListWithProvidedUniquePolicyList() {
        uniquePolicyList.add(HOME);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(HEALTH_B);
        uniquePolicyList.setPolicies(expectedUniquePolicyList);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicies((List<Policy>) null));
    }

    @Test
    public void setPolicies_list_replacesOwnListWithProvidedList() {
        uniquePolicyList.add(HOME);
        List<Policy> policyList = Collections.singletonList(HEALTH_B);
        uniquePolicyList.setPolicies(policyList);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(HEALTH_B);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_listWithDuplicatePolicies_throwsDuplicatePolicyException() {
        List<Policy> listWithDuplicatePolicies = Arrays.asList(HOME, HOME);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.setPolicies(listWithDuplicatePolicies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePolicyList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePolicyList.asUnmodifiableObservableList().toString(), uniquePolicyList.toString());
    }
}

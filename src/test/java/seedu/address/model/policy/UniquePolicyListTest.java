package seedu.address.model.policy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getHealthB;
import static seedu.address.testutil.TypicalData.getHome;

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
    public void containsSameId_policyIdNotInList_returnsFalse() {
        assertFalse(uniquePolicyList.containsSameId(getHome()));
    }

    @Test
    public void containsSameId_policyWithSameIdInList_returnsTrue() {
        uniquePolicyList.add(getHome());
        Policy editedPolicy = new PolicyBuilder(getHealthB()).withId(VALID_POLICY_ID_HOME).build();
        assertTrue(uniquePolicyList.containsSameId(editedPolicy));
    }

    @Test
    public void containsSamePolicy_policyNotInList_returnsFalse() {
        assertFalse(uniquePolicyList.containsSamePolicy(getHome()));
    }

    @Test
    public void containsSamePolicy_policyWithSameFieldsInList_returnsTrue() {
        uniquePolicyList.add(getHome());
        Policy editedPolicy = new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HEALTH_B).build();
        assertTrue(uniquePolicyList.containsSamePolicy(editedPolicy));
    }

    @Test
    public void containsId_policyIdNotInList_returnsFalse() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        assertFalse(uniquePolicyList.containsId(policyId));
    }

    @Test
    public void containsId_policyWithIdInList_returnsTrue() {
        uniquePolicyList.add(getHome());
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        assertTrue(uniquePolicyList.containsId(policyId));
    }

    @Test
    public void add_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.add(null));
    }

    @Test
    public void add_duplicatePolicy_throwsDuplicatePolicyException() {
        uniquePolicyList.add(getHome());
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.add(getHome()));
    }

    @Test
    public void addAll_nullPolicyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.addAll(null));
    }

    @Test
    public void addAll_duplicatePolicy_throwsDuplicatePolicyException() {
        uniquePolicyList.add(getHome());
        List<Policy> policies = List.of(getHealthB(), getHome());
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.addAll(policies));
    }

    @Test
    public void addAll_duplicatePolicyInList_throwsDuplicatePolicyException() {
        uniquePolicyList.add(getHome());
        List<Policy> policies = List.of(getHealthB(), getHealthB());
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.addAll(policies));
    }

    @Test
    public void setPolicy_nullTargetPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(null, getHome()));
    }

    @Test
    public void setPolicy_nullEditedPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(getHome(), null));
    }

    @Test
    public void setPolicy_targetPolicyNotInList_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () -> uniquePolicyList.setPolicy(getHome(), getHome()));
    }

    @Test
    public void setPolicy_editedPolicyIsSamePolicy_success() {
        uniquePolicyList.add(getHome());
        uniquePolicyList.setPolicy(getHome(), getHome());
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(getHome());
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasSameIdentity_success() {
        uniquePolicyList.add(getHome());
        Policy editedPolicy = new PolicyBuilder(getHome()).withName(VALID_POLICY_NAME_HEALTH_B)
                .withDetails(VALID_DETAILS_HEALTH_B).build();
        uniquePolicyList.setPolicy(getHome(), editedPolicy);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(editedPolicy);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasDifferentIdentity_success() {
        uniquePolicyList.add(getHome());
        uniquePolicyList.setPolicy(getHome(), getHealthB());
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(getHealthB());
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasNonUniqueIdentity_throwsDuplicatePolicyException() {
        uniquePolicyList.add(getHome());
        uniquePolicyList.add(getHealthB());
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.setPolicy(getHome(), getHealthB()));
    }

    @Test
    public void remove_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.remove(null));
    }

    @Test
    public void remove_policyDoesNotExist_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () -> uniquePolicyList.remove(getHome()));
    }

    @Test
    public void remove_existingPolicy_removesPolicy() {
        uniquePolicyList.add(getHome());
        uniquePolicyList.remove(getHome());
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullUniquePolicyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicies((UniquePolicyList) null));
    }

    @Test
    public void setPolicies_uniquePolicyList_replacesOwnListWithProvidedUniquePolicyList() {
        uniquePolicyList.add(getHome());
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(getHealthB());
        uniquePolicyList.setPolicies(expectedUniquePolicyList);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicies((List<Policy>) null));
    }

    @Test
    public void setPolicies_list_replacesOwnListWithProvidedList() {
        uniquePolicyList.add(getHome());
        List<Policy> policyList = Collections.singletonList(getHealthB());
        uniquePolicyList.setPolicies(policyList);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(getHealthB());
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_listWithDuplicatePolicies_throwsDuplicatePolicyException() {
        List<Policy> listWithDuplicatePolicies = Arrays.asList(getHome(), getHome());
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

package seedu.address.model.contract;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contract.exceptions.ContractNotFoundException;
import seedu.address.model.contract.exceptions.DuplicateContractException;

/**
 * A list of contracts that enforces uniqueness between its elements and does not allow nulls.
 * A contract is considered unique by comparing using {@code Contract#isSameContract(Contract)}.
 * As such, adding and updating of contracts uses Contract#isSameContract(Contract) for equality to ensure that the
 * contract being added or updated is unique in terms of identity in the UniqueContractList.
 * However, the removal of a contract uses Contract#equals(Object) to ensure that the contract with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Contract#isSameContract(Contract)
 */
public class UniqueContractList implements Iterable<Contract> {

    private final ObservableList<Contract> internalList = FXCollections.observableArrayList();
    private final ObservableList<Contract> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contract as the given argument.
     */
    public boolean contains(Contract toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameContract);
    }

    /**
     * Adds a contract to the list.
     * The contract must not already exist in the list.
     */
    public void add(Contract toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateContractException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contract {@code target} in the list with {@code editedContract}.
     * {@code target} must exist in the list.
     * The contract identity of {@code editedContract} must not be the same as another existing contract in the list.
     */
    public void setContract(Contract target, Contract editedContract) {
        requireAllNonNull(target, editedContract);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ContractNotFoundException();
        }

        if (!target.isSameContract(editedContract) && contains(editedContract)) {
            throw new DuplicateContractException();
        }

        internalList.set(index, editedContract);
    }

    /**
     * Removes the equivalent contract from the list.
     * The contract must exist in the list.
     */
    public void remove(Contract toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ContractNotFoundException();
        }
    }

    public void setContracts(UniqueContractList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code contracts}.
     * {@code contracts} must not contain duplicate contracts.
     */
    public void setContracts(List<Contract> contracts) {
        requireAllNonNull(contracts);
        if (!contractsAreUnique(contracts)) {
            throw new DuplicateContractException();
        }

        internalList.setAll(contracts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Contract> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Contract> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueContractList)) {
            return false;
        }

        UniqueContractList otherList = (UniqueContractList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code contracts} contains only unique contracts.
     */
    private boolean contractsAreUnique(List<Contract> contracts) {
        for (int i = 0; i < contracts.size() - 1; i++) {
            for (int j = i + 1; j < contracts.size(); j++) {
                if (contracts.get(i).isSameContract(contracts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

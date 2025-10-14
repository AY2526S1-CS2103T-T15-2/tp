package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contract.Contract;


/**
 * Panel containing the list of policies.
 */
public class ContractListPanel extends UiPart<Region> {
    private static final String FXML = "ContractListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContractListPanel.class);

    @FXML
    private ListView<Contract> contractListView;

    /**
     * Creates a {@code PolicyListPanel} with the given {@code ObservableList}.
     */
    public ContractListPanel(ObservableList<Contract> contractList) {
        super(FXML);
        contractListView.setItems(contractList);
        contractListView.setCellFactory(listView -> new ContractListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Policy} using a {@code PolicyCard}.
     */
    class ContractListViewCell extends ListCell<Contract> {
        @Override
        protected void updateItem(Contract contract, boolean empty) {
            super.updateItem(contract, empty);

            if (empty || contract == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContractCard(contract, getIndex() + 1).getRoot());
            }
        }
    }
}

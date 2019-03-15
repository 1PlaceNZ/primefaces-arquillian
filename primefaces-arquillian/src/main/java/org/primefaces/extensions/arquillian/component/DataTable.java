package org.primefaces.extensions.arquillian.component;

import java.util.List;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

/**
 * Component wrapper for the PrimeFaces {@code p:accordionPanel}.
 */
public abstract class DataTable extends AbstractComponent {

    @FindByParentPartialId(value = "_head")
    private GrapheneElement header;

    @FindByParentPartialId(value = "_data")
    private GrapheneElement data;


    private String cellID(int rowIndex, String columnId) {
        return getId() + ":" + rowIndex + ":" + columnId;
    }

    public WebElement getWebElementInCell(int rowIndex, String columnIdOrLinkId ) {
        return driver.findElement(By.id(cellID(rowIndex, columnIdOrLinkId)));
    }

    public String getCellText(int rowIndex, String elementId) {
        return driver.findElement(By.id(cellID(rowIndex, elementId))).getText();
    }

    public String getCellText(int row, int column) {
        List<WebElement> tableRows =  data.findElements(By.tagName("tr"));
        if (row < tableRows.size()) {
            WebElement rowElements = tableRows.get(row);
            List<WebElement> tdElements = rowElements.findElements(By.xpath("td"));
            return tdElements.get(column).getText();
        }
        else {
            return "";
        }
    }

    public Integer findFirstRowWithText(String columnIdOrLinkId, String text) {
        //only searches the current page. doesn't paginate
        for (int offset = 0; offset < getRowCount(); offset ++) {
            WebElement element = driver.findElement(By.id(cellID(offset, columnIdOrLinkId)));
            if (element.getText().equalsIgnoreCase(text)) {
                return offset;
            }
        }
        return -1;
    }

    public int getRowCount() {
        if (isEmpty()) {
            return 0;
        }
        return data.findElements(By.tagName("tr")).size();
    }

    public boolean isEmpty() {
        return !data.findElements(By.className("ui-datatable-empty-message")).isEmpty();
    }
}

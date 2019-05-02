package org.primefaces.extensions.arquillian.component;

import java.util.List;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
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

    @FindBy(className = "ui-paginator-current")
    private WebElement paginator;


    private String cellID(int rowIndex, String columnId) {
        return getId() + ":" + rowIndex + ":" + columnId;
    }

    public By getCellBy(int rowIndex, String columnIdOrLinkId) {
        return By.id(cellID(rowIndex, columnIdOrLinkId));
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

    public int getTotalCount() {
        if (PrimeGraphene.isElementPresent(paginator)) {
            String paginatorSummary = paginator.getText();
            String[] parts = paginatorSummary.split("\\(");
            String digit = parts[1].replaceAll("\\D", "");
            return Integer.valueOf(digit);
        }
        return getRowCount();
    }

    public int getRowCount() {
        if (isEmpty()) {
            return 0;
        }
        return data.findElements(By.xpath("//tbody[@id = '" + data.getAttribute("id") + "']/tr")).size();
    }

    public boolean isEmpty() {
        return !data.findElements(By.xpath("//tbody[@id = '" + data.getAttribute("id")
                + "']/tr[contains(@class, 'ui-datatable-empty-message')]")).isEmpty();
    }
}

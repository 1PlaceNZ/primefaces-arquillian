package org.primefaces.extensions.arquillian.component;

import java.util.List;
import java.util.function.Function;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class PickList extends AbstractComponent {

    @FindBy(className = "ui-picklist-source")
    private WebElement sourceList;

    @FindBy(className = "ui-picklist-target")
    private WebElement targetList;

    @FindByParentPartialId(value = "_source")
    private WebElement inputSource;

    @FindByParentPartialId(value = "_target")
    private WebElement inputTarget;

    @FindBy(className = "ui-picklist-button-add")
    private WebElement addButton;

    @FindBy(className = "ui-picklist-button-add-all")
    private WebElement addAllButton;

    @FindBy(className = "ui-picklist-button-remove")
    private WebElement removeButton;

    @FindBy(className = "ui-picklist-button-remove-all")
    private WebElement removeAllButton;

    @FindByParentPartialId(value = "_source_filter")
    private WebElement sourceFilter;

    @FindByParentPartialId(value = "_target_filter")
    private WebElement targetFilter;

    public void selectFromSourceAndAdd(String label) {
        if (isSelected(label)) {
            return;
        }
        int targetSize = targetList.findElements(By.tagName("li")).size() + 1;

        for (WebElement element : sourceList.findElements(By.tagName("li"))) {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
                Graphene.guardNoRequest(addButton).click();
                Graphene.waitGui().until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver input) {
                        try {
                            return targetList.findElements(By.tagName("li")).size() == targetSize;
                        }
                        catch (NoSuchElementException e) {
                            return true;
                        }
                    }
                });
                break;
            }
        }
    }

    protected void click(WebElement element) {
        element.click();
    }

    public void selectFromSourceAndDoubleClick(String label) {
        if (isSelected(label)) {
            return;
        }

        sourceList.findElements(By.tagName("li")).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                Actions action = new Actions(driver).doubleClick(element);
                action.build().perform();
            }
        });

    }

    public void clickSelectAll() {
        Graphene.waitGui().until().element(addAllButton).is().enabled();
        Graphene.waitGui().until().element(addAllButton).is().clickable();
        addAllButton.click();
        Graphene.waitGui().until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                try {
                    return sourceList.findElements(By.tagName("li")).size() == 0;
                }
                catch (NoSuchElementException e) {
                    return true;
                }
            }
        });
    }

    public void clickRemoveAll() {
        removeAllButton.click();
        Graphene.waitGui().until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                try {
                    return targetList.findElements(By.tagName("li")).size() == 0;
                }
                catch (NoSuchElementException e) {
                    return true;
                }
            }
        });
    }

    public void selectFromTargetAndRemove(String label) {
        if (!isSelected(label)) {
            return;
        }
        int targetSize = sourceList.findElements(By.tagName("li")).size() + 1;

        for (WebElement element : targetList.findElements(By.tagName("li"))) {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
                Graphene.guardNoRequest(removeButton).click();
                Graphene.waitGui().until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver input) {
                        try {
                            return sourceList.findElements(By.tagName("li")).size() == targetSize;
                        }
                        catch (NoSuchElementException e) {
                            return true;
                        }
                    }
                });
                break;
            }
        }

    }

    public void selectFromTargetAndDoubleClick(String label) {
        if (!isSelected(label)) {
            return;
        }

        targetList.findElements(By.tagName("li")).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                Actions action = new Actions(driver).doubleClick(element);
                action.build().perform();
            }
        });

    }

    public Select getSourceSelect() {
        return new Select(inputSource);
    }

    public Select getTargetSelect() {
        return new Select(inputTarget);
    }

    public boolean isSelected(String label) {
        for (WebElement element : targetList.findElements(By.tagName("li"))) {
            if (element.getAttribute("data-item-label").equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String label) {
        for (WebElement element : sourceList.findElements(By.tagName("li"))) {
            if (element.getAttribute("data-item-label").equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    public int getTargetSize() {
        return targetList.findElements(By.tagName("li")).size();
    }

    public int getSourceSize() {
        return sourceList.findElements(By.tagName("li")).size();
    }

    public boolean isEmpty() {
        return (getTargetSize() == 0 && getSourceSize() == 0);
    }

    private String getLabelFromListAtIndex(WebElement list, int index) {
        List<WebElement> items = list.findElements(By.tagName("li"));
        if (items.size() > index) {
            return items.get(index).getAttribute("data-item-label");
        }
        return null;
    }

    public String getLabelFromSourceAtIndex(int index) {
        return getLabelFromListAtIndex(sourceList, index);
    }

    public String getLabelFromTargetAtIndex(int index) {
        return getLabelFromListAtIndex(targetList, index);
    }
}

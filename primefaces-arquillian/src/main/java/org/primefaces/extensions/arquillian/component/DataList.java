package org.primefaces.extensions.arquillian.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

/**
 * Component wrapper for the PrimeFaces {@code p:DataList}.
 */
public abstract class DataList extends AbstractComponent {

    @FindByParentPartialId(value = "_content")
    private WebElement content;

    @FindByParentPartialId(value = "_list")
    private WebElement list;

    @FindByParentPartialId(value = "_paginator_top")
    private WebElement paginator_top;

    @FindByParentPartialId(value = "_paginator_bottom")
    private WebElement paginator_bottom;

    public int  getListCount() {
        return list.findElements(By.tagName("li")).size();
    }

    public boolean isInList(String label) {
        for (WebElement element :list.findElements(By.className("ui-datalist-item"))) {
            if (element.getText().equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }
}

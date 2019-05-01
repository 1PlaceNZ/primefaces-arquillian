/**
 * Copyright 2011-2018 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.arquillian.component;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;

public abstract class SelectOneRadio extends AbstractInputComponent {

    @FindBy(css = ".ui-radiobutton")
    private List<WebElement> options;

    private WebElement getActiveOption() {
        if (PrimeGraphene.isElementPresent(By.xpath("//table[@id = '" + getId() +  "'] //div[contains(@class, 'ui-state-active')]"))) {
            return root.findElement(By.xpath("//table[@id = '" + getId() +  "']//div[contains(@class, 'ui-state-active')]/parent::div"));
        }
        return null;
    }

    public List<String> getOptionLabels() {
        List<String> result = new ArrayList<>();
        options.forEach((element) -> result.add(element.findElement(By.tagName("label")).getText()));
        return result;
    }

    public String getSelectedLabel() {
        WebElement activeOption = getActiveOption();
        if (activeOption != null) {
            WebElement label = root.findElement(By.xpath("//table[@id = '" + getId() +
                    "']//div[contains(@class, 'ui-state-active')]/parent::div/following-sibling::label"));
            return label.getText();
        }
        return null;
    }

    public boolean isSelected(String label) {
        String selectedLabel = getSelectedLabel();
        return selectedLabel != null ? selectedLabel.equalsIgnoreCase(label) : false;
    }

    public void selectNext() {
        int activeIndex = options.indexOf(getActiveOption());
        int nextIndex = activeIndex + 1;

        if (nextIndex >= options.size()) {
            nextIndex = 0;
        }

        click(options.get(nextIndex));
    }

    public void selectPrevious() {
        int activeIndex = options.indexOf(getActiveOption());
        int previousIndex = activeIndex - 1;

        if (previousIndex < 0) {
            previousIndex = 0;
        }

        click(options.get(previousIndex));
    }

    public void select(String label) {
        if (!isSelected(label)) {
            for (WebElement element : options) {
                if (element.getText().equalsIgnoreCase(label)) {
                    click(element);
                }
            }
        }
    }

    protected void click(WebElement element) {
        if (PrimeGraphene.hasAjaxBehavior(root, "change") || PrimeGraphene.hasAjaxBehavior(root, "onchange")) {
            try {
                Graphene.guardAjax(element).click();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            element.click();
        }
    }
}

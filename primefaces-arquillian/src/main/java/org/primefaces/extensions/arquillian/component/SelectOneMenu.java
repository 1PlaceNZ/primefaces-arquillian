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
import org.primefaces.extensions.arquillian.PrimeExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class SelectOneMenu extends AbstractInputComponent {

    @FindByParentPartialId(value = "_label")
    private WebElement label;

    @FindByParentPartialId(value = "_items", searchFromRoot = true)
    private WebElement items;

    @FindByParentPartialId(value = "_input")
    private WebElement input;

    @FindByParentPartialId(value = "_panel", searchFromRoot = true)
    private WebElement panel;

    public void toggleDropdown() {
        if (panel.isDisplayed()) {
            label.click();

            PrimeGraphene.waitGui().until(PrimeExpectedConditions.invisibleAndAnimationComplete(panel));
        }
        else {
            label.click();

            PrimeGraphene.waitGui().until(PrimeExpectedConditions.visibileAndAnimationComplete(panel));
        }
    }

    public void deselect(String label) {
        if (!isSelected(label)) {
            return;
        }

        if (!panel.isDisplayed()) {
            toggleDropdown();
        }

        items.findElements(By.tagName("li")).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
            }
        });

        if (panel.isDisplayed()) {
            toggleDropdown();
        }
    }

    public void select(String label) {
        if (isSelected(label)) {
            return;
        }

        if (!panel.isDisplayed()) {
            toggleDropdown();
        }

        items.findElements(By.tagName("li")).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
            }
        });

        if (panel.isDisplayed()) {
            toggleDropdown();
        }
    }

    public String getSelectedLabel() {
        return label.getText();
    }

    public boolean isSelected(String label) {
        return getSelectedLabel().equalsIgnoreCase(label);
    }

    @Override
    protected WebElement getInput() {
        return input;
    }

    protected void click(WebElement element) {
        if (PrimeGraphene.isAjaxScript(input.getAttribute("onchange"))) {
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

    public List<String> getOptionLabels() {
        List<String> result = new ArrayList<>();
        List<WebElement> options = items.findElements(By.tagName("li"));
        for (WebElement option : options) {
            result.add(option.getAttribute("data-label"));
        }
        return result;
    }

    public boolean containOption(String optionLabel) {
        List<WebElement> options = items.findElements(By.tagName("li"));
        for (WebElement option : options) {
            if (optionLabel.equals(option.getAttribute("data-label"))) {
                return true;
            }
        }
        return false;
    }
}

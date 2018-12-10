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

import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.primefaces.extensions.arquillian.PrimeGraphene;

public abstract class AutoComplete extends AbstractInputComponent {

    @FindByParentPartialId(value = "_panel", searchFromRoot = true)
    private WebElement panel;

    @FindByParentPartialId(value = "_input")
    private WebElement inputBox;

    @FindByParentPartialId(value = "_hinput")
    private GrapheneElement multiInput;

    protected boolean isOnchangeAjaxified() {
        return true;
    }

    protected WebElement getInput() {
        return inputBox;
    }

    public String getValue() {
        if (multiInput.isPresent()) {
            List<WebElement> options = panel.findElements(By.className("ui-autocomplete-item")) ;
            Select select = new  Select(multiInput);
            StringJoiner value = new StringJoiner("|");
            for (WebElement el :  select.getOptions()) {
                value.add(options.get(Integer.parseInt(el.getAttribute("value"))).getAttribute("value"));
            }
            return value.toString();
        }
        else {
            return getInput().getAttribute("value");
        }
    }

    public void setValue(Serializable value) {
        getInput().clear();
        getInput().sendKeys(value.toString());

        if (isOnchangeAjaxified()) {
            try {
                Graphene.guardAjax(getInput()).sendKeys(Keys.TAB);
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            getInput().sendKeys(Keys.TAB);
        }
    }

    public void typeAutocompleteAndSelect( String value, int minChars) {
        int typeLengthBeforePanelDisplays = 1;
        if (minChars > 1 && value.length() > minChars) {
            typeLengthBeforePanelDisplays = minChars;
        }

        String preFix = value.substring(0, typeLengthBeforePanelDisplays);
        String suffix = value.substring(preFix.length());

        try {
            if (typeLengthBeforePanelDisplays > 1) {
                Graphene.guardAjax(getInput()).sendKeys(preFix);
            }
            else {
                Graphene.guardAjax(getInput()).sendKeys(preFix);
                Graphene.waitGui().until().element(panel).is().visible();
                Graphene.guardAjax(getInput()).sendKeys(suffix);
            }
        }
        catch (RequestGuardException e) {
            PrimeGraphene.handleRequestGuardException(e);
        }
        Graphene.waitGui().until().element(panel).is().visible();
        List<WebElement> options = panel.findElements(By.className("ui-autocomplete-item")) ;
        for (WebElement element : options) {
            if (element.getText().equalsIgnoreCase(value)) {
                element.click();
                Graphene.waitGui().until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver input) {
                        return getInput().getAttribute("value").equals(value);
                    }
                });
                break;
            }
        }
    }
}

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

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class SelectBooleanCheckbox extends AbstractInputComponent {

    @FindByParentPartialId(value = "_input")
    private WebElement input;

    @FindBy(css = ".ui-chkbox-box.ui-state-active")
    private WebElement activeOption;

    public void deselect() {
        if (!input.isSelected()) {
            return;
        }
        click(root);

        PrimeGraphene.waitGui().until(PrimeExpectedConditions.stalenessOfAndAnimationComplete(activeOption));
    }


    public void select() {
        if (input.isSelected()) {
            return;
        }
        click(root);

        PrimeGraphene.waitGui().until(PrimeExpectedConditions.presentAndAnimationComplete(By.cssSelector(".ui-chkbox-box.ui-state-active")));
    }


    public boolean isSelected() {
        return PrimeGraphene.isElementDisplayed(activeOption);
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
}

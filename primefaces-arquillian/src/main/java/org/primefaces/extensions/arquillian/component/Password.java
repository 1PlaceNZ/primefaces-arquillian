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

import java.io.Serializable;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class Password extends InputText {

    @FindByParentPartialId(value = "_panel", searchFromRoot = true)
    private WebElement panel;

    public WebElement getPanel() {
        return panel;
    }

    public void setPanel(WebElement panel) {
        this.panel = panel;
    }

    public boolean isFeedBackDisplayed() {
        return PrimeGraphene.isElementDisplayed(panel);
    }

    public String getFeedBackLabel() {
        return panel.findElement(By.className("ui-password-info")).getText();
    }

    public void setValueAndNotNoTabOut(Serializable value) {
        getInput().clear();
        getInput().sendKeys(value.toString());
    }

    public void tabOut() {
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
}

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class Inplace extends AbstractComponent {

    @FindByParentPartialId(value = "_display")
    private WebElement display;

    @FindBy(className = "ui-inplace-save")
    private WebElement save;

    @FindBy(className = "ui-inplace-cancel")
    private WebElement cancel;

    public void click() {
        display.click();
    }

    public void clickSave() {
        try {
            Graphene.guardAjax(save).click();
        }
        catch (RequestGuardException e) {
            PrimeGraphene.handleRequestGuardException(e);
        }
    }

    public void clickCancel() {
        try {
            Graphene.guardAjax(cancel).click();
        }
        catch (RequestGuardException e) {
            PrimeGraphene.handleRequestGuardException(e);
        }
    }

    public void doubleClick() {
        Actions action = new Actions(driver).doubleClick(display);
        action.build().perform();
        Graphene.waitGui().until().element(save).is().visible();
    }
}

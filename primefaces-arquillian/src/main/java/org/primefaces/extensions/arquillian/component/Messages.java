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
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;

public abstract class Messages extends AbstractComponent {

    @FindBy(className = "ui-messages-close")
    private WebElement close;

    @FindBy(className = " ui-messages-info")
    private WebElement infoMessages;

    public void close() {
        if (PrimeGraphene.hasAjaxBehavior(root, "slideUp")) {
            try {
                Graphene.guardAjax(close).click();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            close.click();
        }

        PrimeGraphene.waitGui().until(PrimeExpectedConditions.invisibleAndAnimationComplete(infoMessages));
    }

    public String getMessageInfoDetail(int offset) {
        return root.findElements(By.className("ui-messages-info-detail")).get(offset).getText();
    }

}

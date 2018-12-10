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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class TabView extends AbstractComponent {

    @FindBy(className = "ui-tabs-nav")
    private WebElement tabList;

    @FindByParentPartialId("_activeIndex")
    private WebElement activeIndex;

    @FindBy(className = "ui-panel-content")
    private WebElement content;

    public void clickTab(String label) {
        WebElement tab = tabList.findElement(By.linkText(label));
        Graphene.waitGui().until(ExpectedConditions.elementToBeClickable(tab));

        if (PrimeGraphene.isAjaxScript(tab.getAttribute("onclick"))) {
            try {
                Graphene.guardAjax(tab).click();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else if ("submit".equals(tab.getAttribute("type"))) {
            try {
                Graphene.guardHttp(tab).click();
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            tab.click();
        }
    }

    public WebElement getTab(String tabId) {
        return root.findElement(By.id(getId() + ":" + tabId));
    }

    public String getCurrentTabLabel() {
        return tabList.findElements(By.className("ui-tabs-header")).get(new Integer(activeIndex.getAttribute("value"))).getText();
    }

}

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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class Signature extends AbstractInputComponent {

    @FindByParentPartialId(value = "_value")
    private WebElement value;

    @FindBy(tagName = "canvas")
    private WebElement canvas;

    protected boolean isOnchangeAjaxified() {
        return PrimeGraphene.isAjaxScript(getInput().getAttribute("onchange"));
    }

    public String getValue() {
        return value.getAttribute("value");
    }

    public void drawSignature() {
        Actions builder = new Actions(driver);
        Action drawAction = builder.moveToElement(canvas, 20, 20)
                                        .clickAndHold()
                                        .moveByOffset(80, 80)
                                        .moveByOffset(50, 20)
                                        .release()
                                        .build();
        drawAction.perform();
    }

    public void clear() {
        String script = getWidgetByIdScript() + ".clear();";
        PrimeGraphene.executeScript(script);
    }
}

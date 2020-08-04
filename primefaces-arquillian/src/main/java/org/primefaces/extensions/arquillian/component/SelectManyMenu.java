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

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class SelectManyMenu extends AbstractInputComponent {


    @FindBy(className = "ui-selectlistbox-list")
    private WebElement items;

    @FindByParentPartialId(value = "_input")
    private WebElement input;

    @FindByParentPartialId(value = "_filter")
    private WebElement inputFilter;

    public void enterFilter(String filter) {
        inputFilter.clear();
        inputFilter.sendKeys(filter);
    }

    public void deselect(String label) {
        if (!isSelected(label)) {
            return;
        }

        items.findElements(By.tagName(getSelectElement())).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
            }
        });
    }

    public void altDeselect(String label) {
        if (!isSelected(label)) {
            return;
        }

        items.findElements(By.tagName(getSelectElement())).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                CharSequence multiSelect = Keys.ALT;
                if (PrimeGraphene.getPlatform(driver) == Platform.MAC) {
                    multiSelect = Keys.COMMAND;
                }
                Actions builder = new Actions(driver);
                builder.keyDown(multiSelect);
                builder.click(element);
                builder.build().perform();
                builder.keyUp(multiSelect).build().perform();
            }
        });
    }

    String getSelectElement() {
        if (items.getTagName().equals("ul")) {
            return "li";
        }
        else {
            return "tr";
        }
    }

    public void select(String label) {
        if (isSelected(label)) {
            return;
        }

        items.findElements(By.tagName(getSelectElement())).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                click(element);
            }
        });
    }

    public void altSelect(String label) {
        if (isSelected(label)) {
            return;
        }

        items.findElements(By.tagName(getSelectElement())).forEach(element -> {
            if (element.getText().equalsIgnoreCase(label)) {
                CharSequence multiSelect = Keys.ALT;
                if (PrimeGraphene.getPlatform(driver) == Platform.MAC) {
                    multiSelect = Keys.COMMAND;
                }
                Actions builder = new Actions(driver);
                builder.keyDown(multiSelect);
                builder.click(element);
                builder.build().perform();
                builder.keyUp(multiSelect).build().perform();
            }
        });
    }

    public boolean isSelected(String label) {
        Select input  = getSelectInput();
        for (WebElement element : input.getAllSelectedOptions()) {
           // System.out.println(element.getTagName());
           // System.out.println(element.getText());
           // System.out.println(element.getAttribute("innerText"));
           // System.out.println(element.getAttribute("innerHTML"));
            //sometime chrome driver reutrn ""
            if (element.getText().equalsIgnoreCase(label) ||
                    element.getAttribute("innerText").equalsIgnoreCase(label) ||
                    element.getAttribute("innerHTML").equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected WebElement getInput() {
        return input;
    }

    public Select getSelectInput() {
        return new Select(input);
    }

    protected void click(WebElement element) {
        element.click();
    }
}

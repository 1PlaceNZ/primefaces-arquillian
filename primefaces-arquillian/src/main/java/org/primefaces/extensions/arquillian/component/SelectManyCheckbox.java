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

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class SelectManyCheckbox extends AbstractInputComponent {


    @FindBy(tagName = "input")
    private List<WebElement> options;

    @FindBy(tagName = "label")
    private List<WebElement> labels;

    @FindBy(className = "ui-chkbox-box")
    private List<WebElement> checkboxes;

    @FindByParentPartialId(value = "_input")
    private WebElement input;

    public List<WebElement> getOptions() {
        //return visible options
        return checkboxes;
    }

    public void deselect(String label) {
        if (!isSelected(label)) {
            return;
        }
        int input = getLabelOffset(label);
        if (input > -1) {
            click(checkboxes.get(input));
        }
    }

    public void select(String label) {
        if (isSelected(label)) {
            return;
        }

        int input = getLabelOffset(label);
        if (input > -1) {
            click(checkboxes.get(input));
        }
    }

    public boolean isSelected(String label) {
        int labelOffset = getLabelOffset(label);
        if (labelOffset > -1) {
            return options.get(labelOffset).isSelected();
        }
        return false;
    }

    public int getLabelOffset(String label) {
        int offset = 0;
        for (WebElement element : labels) {
            if (element.getText().equalsIgnoreCase(label)) {
                return offset;
            }
            offset ++;
        }
        return -1;
    }

    @Override
    protected WebElement getInput() {
        return input;
    }

    protected void click(WebElement element) {
        element.click();
    }
}
